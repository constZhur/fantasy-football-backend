package ru.mirea.fantasyfootballengine.dto.team;

import java.util.UUID;

public record CreateTeamRequest(
        String name,
        UUID tournamentId
) {}

