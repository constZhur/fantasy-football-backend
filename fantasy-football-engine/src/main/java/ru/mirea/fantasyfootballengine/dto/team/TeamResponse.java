package ru.mirea.fantasyfootballengine.dto.team;

import ru.mirea.fantasyfootballengine.entity.jdbc.Team;

import java.util.UUID;

public record TeamResponse(
        UUID id,
        String name,
        UUID ownerId,
        UUID tournamentId,
        int points
) {
    public static TeamResponse from(Team team) {
        return new TeamResponse(team.getId(), team.getName(), team.getOwner().getId(), team.getTournament().getId(), team.getPoints());
    }
}

