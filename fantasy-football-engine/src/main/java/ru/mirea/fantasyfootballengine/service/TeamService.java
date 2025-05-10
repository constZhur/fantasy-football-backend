package ru.mirea.fantasyfootballengine.service;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;
import ru.mirea.fantasyfootballengine.dto.team.AddFootballerRequest;
import ru.mirea.fantasyfootballengine.dto.team.CreateTeamRequest;
import ru.mirea.fantasyfootballengine.dto.team.TeamResponse;
import ru.mirea.fantasyfootballengine.dto.team.UpdateLineupRequest;
import ru.mirea.fantasyfootballengine.entity.jdbc.Footballer;
import ru.mirea.fantasyfootballengine.entity.jdbc.FootballerTeamRelation;
import ru.mirea.fantasyfootballengine.entity.jdbc.Team;
import ru.mirea.fantasyfootballengine.repository.jdbc.*;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TeamService {

    private final TeamRepository teamRepo;
    private final FootballerRepository footballerRepo;
    private final FootballerTeamRelationRepository relationRepo;
    private final UserRepository userRepo;
    private final TournamentRepository tournamentRepo;

    @Transactional
    public TeamResponse createTeam(CreateTeamRequest request, UUID userId) {
        var owner = userRepo.findById(userId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        var tournament = tournamentRepo.findById(request.tournamentId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Tournament not found"));

        Team team = new Team();
        team.setName(request.name());
        team.setOwner(owner);
        team.setTournament(tournament);
        team.setPoints(0);
        teamRepo.save(team);
        return TeamResponse.from(team);
    }

    public List<TeamResponse> getTeamsByUser(UUID userId) {
        return teamRepo.findAllByOwnerId(userId)
                .stream()
                .map(TeamResponse::from)
                .toList();
    }

    @Transactional
    public void addFootballerToTeam(UUID teamId, AddFootballerRequest request) {
        Team team = teamRepo.findById(teamId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found"));

        Footballer footballer = footballerRepo.findById(request.footballerId())
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Footballer not found"));

        int price = footballer.getCost();
        if (team.getBudget() < price) {
            throw new IllegalArgumentException("Недостаточно средств для покупки футболиста");
        }

        FootballerTeamRelation relation = new FootballerTeamRelation();
        relation.setFootballerId(footballer.getId());
        relation.setTeamId(team.getId());
        relation.setCapitan(request.isCapitan());
        relationRepo.save(relation);

        team.setBudget(team.getBudget() - price);
        teamRepo.save(team);
    }

    @Transactional
    public void updateLineup(UUID teamId, UpdateLineupRequest request) {
        Team team = teamRepo.findById(teamId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Team not found"));

        int budget = team.getBudget();

        for (UUID id : request.deletedPlayerIds()) {
            Footballer footballer = footballerRepo.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Footballer not found for removal"));
            budget += footballer.getCost();

            relationRepo.deleteByTeamIdAndFootballerId(teamId, id);
        }

        for (UUID id : request.newPlayersIds()) {
            Footballer footballer = footballerRepo.findById(id)
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Footballer not found for addition"));

            if (budget < footballer.getCost()) {
                throw new IllegalArgumentException("Недостаточно средств на покупку футболиста: " + footballer.getLastName());
            }

            budget -= footballer.getCost();

            FootballerTeamRelation rel = new FootballerTeamRelation(teamId, id, false);
            relationRepo.save(rel);
        }

        team.setBudget(budget);
        teamRepo.save(team);
    }

}

