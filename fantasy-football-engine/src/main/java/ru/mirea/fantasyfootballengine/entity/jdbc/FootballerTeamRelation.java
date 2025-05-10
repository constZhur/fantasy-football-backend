package ru.mirea.fantasyfootballengine.entity.jdbc;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import ru.mirea.fantasyfootballengine.util.id.FootballerTeamRelationId;

import java.util.UUID;

@Entity
@IdClass(FootballerTeamRelationId.class)
@Table(name = "footballer_team_relation")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class FootballerTeamRelation {
    @Id
    @Column(name = "footballer_id")
    private UUID footballerId;

    @Id
    @Column(name = "team_id")
    private UUID teamId;

    @Column(name = "is_capitan")
    private boolean isCapitan;
}

