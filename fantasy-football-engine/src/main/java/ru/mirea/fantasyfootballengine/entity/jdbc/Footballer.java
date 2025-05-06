package ru.mirea.fantasyfootballengine.entity.jdbc;

import jakarta.persistence.*;
import lombok.*;

import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "footballer")
public class Footballer {
    @Id
    @Column(name = "id")
    private UUID id;

    @Column(name = "first_name")
    private String firstName;

    @Column(name = "last_name")
    private String lastName;

    @Column(name = "position")
    private String position;

    @Column(name = "goals")
    private short goals;

    @Column(name = "assists")
    private short assists;

    @Column(name = "own_goals")
    private short ownGoals;

    @Column(name = "yellow_cards")
    private short yellowCards;

    @Column(name = "red_cards")
    private short redCards;

    @Column(name = "minutes_played")
    private short minutesPlayed;

    @Column(name = "clean_sheets")
    private short cleanSheets;

    @Column(name = "best_player_awards")
    private short bestPlayerAwards;
}