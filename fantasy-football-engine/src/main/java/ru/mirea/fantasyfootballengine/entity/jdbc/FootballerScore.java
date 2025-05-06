package ru.mirea.fantasyfootballengine.entity.jdbc;

import jakarta.persistence.*;
import lombok.*;
import ru.mirea.fantasyfootballengine.util.id.FootballerScoreId;
import java.util.UUID;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@IdClass(FootballerScoreId.class)
@Table(name = "footballer_score")
public class FootballerScore {
    @Id
    @Column(name = "footballer_id")
    private UUID footballerId;

    @Id
    @Column(name = "tournament_id")
    private UUID tournamentId;

    @Column(name = "score")
    private int score;

}
