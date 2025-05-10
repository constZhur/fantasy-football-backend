package ru.mirea.fantasyfootballengine.dto.footballer;

public record FootballerDTO(
        String firstName,
        String lastName,
        String position,
        short goals,
        short assists,
        short ownGoals,
        short yellowCards,
        short redCards,
        short minutesPlayed,
        short cleanSheets,
        short bestPlayerAwards,
        short cost
) {}
