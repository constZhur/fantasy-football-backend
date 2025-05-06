package ru.mirea.fantasyfootballengine.repository.jdbc;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.mirea.fantasyfootballengine.entity.jdbc.Rule;
import ru.mirea.fantasyfootballengine.util.enumerate.RuleType;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface RuleRepository extends JpaRepository<Rule, UUID> {
    Optional<Rule> findByRuleType(RuleType ruleType);
}
