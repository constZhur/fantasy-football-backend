package ru.mirea.fantasyfootballengine.service;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import ru.mirea.fantasyfootballengine.dto.tournament.CreateTournamentRequest;
import ru.mirea.fantasyfootballengine.entity.jdbc.Rule;
import ru.mirea.fantasyfootballengine.entity.jdbc.Tournament;
import ru.mirea.fantasyfootballengine.entity.jdbc.User;
import ru.mirea.fantasyfootballengine.repository.jdbc.RuleRepository;
import ru.mirea.fantasyfootballengine.repository.jdbc.TournamentRepository;
import ru.mirea.fantasyfootballengine.repository.jdbc.UserRepository;
import ru.mirea.fantasyfootballengine.util.enumerate.RuleType;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class TournamentService {

    private final TournamentRepository tournamentRepo;
    private final UserRepository userRepo;
    private final RuleRepository ruleRepo;

    public Tournament createTournament(CreateTournamentRequest request) {
        User owner = userRepo.findById(request.ownerId())
                .orElseThrow(() -> new EntityNotFoundException("User not found"));

        UUID tournamentId = UUID.randomUUID();

        Tournament tournament = new Tournament();
        tournament.setId(tournamentId);
        tournament.setName(request.name());
        tournament.setOwner(owner);
        tournament.setBaseTournamentId(request.baseTournamentId());

        tournamentRepo.save(tournament);

        List<Rule> rules = new ArrayList<>();

        for (Map.Entry<String, Integer> entry : request.rules().entrySet()) {
            RuleType ruleType;
            try {
                ruleType = RuleType.valueOf(entry.getKey());
            } catch (IllegalArgumentException e) {
                throw new EntityNotFoundException("Rule type " + entry.getKey() + " not found");
            }

            Rule rule = new Rule();
            rule.setId(UUID.randomUUID());
            rule.setRuleType(ruleType);
            rule.setPoints(entry.getValue());

            ruleRepo.save(rule);

            rules.add(rule);
        }

        tournament.setRules(rules);
        return tournamentRepo.save(tournament);
    }
}


