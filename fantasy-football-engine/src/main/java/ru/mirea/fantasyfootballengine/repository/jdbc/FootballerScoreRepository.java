package ru.mirea.fantasyfootballengine.repository.jdbc;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;
import ru.mirea.fantasyfootballengine.entity.jdbc.FootballerScore;
import ru.mirea.fantasyfootballengine.util.id.FootballerScoreId;

import java.util.UUID;

public interface FootballerScoreRepository extends JpaRepository<FootballerScore, FootballerScoreId> {

    @Modifying
    @Transactional
    @Query("UPDATE FootballerScore fs SET fs.score = :score WHERE fs.footballerId = :footballerId AND fs.tournamentId = :tournamentId")
    int updateScoreByFootballerIdAndTournamentId(@Param("footballerId") UUID footballerId,
                                                 @Param("tournamentId") UUID tournamentId,
                                                 @Param("score") int score);
}
