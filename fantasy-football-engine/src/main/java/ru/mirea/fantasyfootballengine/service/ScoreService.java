package ru.mirea.fantasyfootballengine.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.fantasyfootballengine.entity.jdbc.*;
import ru.mirea.fantasyfootballengine.repository.jdbc.*;
import ru.mirea.fantasyfootballengine.util.enumerate.RuleType;

import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ScoreService {

    private final TeamRepository teamRepo;
    private final FootballerScoreRepository footballerScoreRepo;
    private final FootballerTeamRelationRepository relationRepo;
    private final RuleRepository ruleRepo;
    private final FootballerRepository footballerRepo;

    @Transactional
    public void calculateAllTeamScoresForTournament(UUID tournamentId) {
        List<Team> teams = teamRepo.findAllByTournamentId(tournamentId);

        Map<RuleType, Integer> rulePoints = ruleRepo.findAll().stream()
                .collect(Collectors.toMap(Rule::getRuleType, Rule::getPoints));

        for (Team team : teams) {
            UUID teamId = team.getId();
            int totalScore = 0;

            List<FootballerTeamRelation> relations = relationRepo.findAllByTeamId(teamId);

            for (FootballerTeamRelation rel : relations) {
                UUID footballerId = rel.getFootballerId();

                Footballer footballer = footballerRepo.findById(footballerId).orElse(null);
                if (footballer == null) continue;

                int playerScore = 0;

                playerScore += footballer.getGoals() * rulePoints.getOrDefault(RuleType.GOAL, 0);
                playerScore += footballer.getAssists() * rulePoints.getOrDefault(RuleType.ASSIST, 0);
                playerScore += footballer.getOwnGoals() * rulePoints.getOrDefault(RuleType.OWN_GOAL, 0);
                playerScore += footballer.getYellowCards() * rulePoints.getOrDefault(RuleType.YELLOW_CARD, 0);
                playerScore += footballer.getRedCards() * rulePoints.getOrDefault(RuleType.RED_CARD, 0);
                playerScore += (footballer.getMinutesPlayed() / 90) * rulePoints.getOrDefault(RuleType.FULL_MATCH, 0);
                playerScore += footballer.getCleanSheets() * rulePoints.getOrDefault(RuleType.CLEAN_SHEET, 0);
                playerScore += footballer.getBestPlayerAwards() * rulePoints.getOrDefault(RuleType.BEST_PLAYER, 0);

                if (rel.isCapitan()) {
                    playerScore *= 2;
                }

                totalScore += playerScore;
                footballerScoreRepo.updateScoreByFootballerIdAndTournamentId(footballerId, tournamentId, playerScore);
            }


            team.setPoints(totalScore);
            teamRepo.save(team);
        }
    }
}





