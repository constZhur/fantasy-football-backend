package ru.mirea.fantasyfootballengine.dto.team;

import java.util.List;
import java.util.UUID;

public record UpdateLineupRequest(
        List<UUID> deletedPlayerIds,
        List<UUID> newPlayersIds
) {}

