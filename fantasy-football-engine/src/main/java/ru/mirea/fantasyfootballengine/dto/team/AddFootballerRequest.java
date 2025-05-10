package ru.mirea.fantasyfootballengine.dto.team;

import java.util.UUID;

public record AddFootballerRequest(
        UUID footballerId,
        boolean isCapitan
) {}
