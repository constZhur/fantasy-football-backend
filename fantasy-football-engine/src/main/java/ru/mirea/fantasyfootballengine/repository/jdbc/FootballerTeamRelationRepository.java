package ru.mirea.fantasyfootballengine.repository.jdbc;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.mirea.fantasyfootballengine.entity.jdbc.FootballerTeamRelation;
import ru.mirea.fantasyfootballengine.util.id.FootballerTeamRelationId;

import java.util.List;
import java.util.UUID;

public interface FootballerTeamRelationRepository extends JpaRepository<FootballerTeamRelation, FootballerTeamRelationId> {
    List<FootballerTeamRelation> findAllByTeamId(UUID teamId);
}
