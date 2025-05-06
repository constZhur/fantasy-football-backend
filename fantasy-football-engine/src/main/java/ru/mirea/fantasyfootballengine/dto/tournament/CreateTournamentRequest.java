package ru.mirea.fantasyfootballengine.dto.tournament;

import java.util.Map;
import java.util.UUID;

public record CreateTournamentRequest(
        String name,
        UUID ownerId,
        Integer baseTournamentId,
        Map<String, Integer> rules
) {}

