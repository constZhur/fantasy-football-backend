package ru.mirea.fantasyfootballengine.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScoreSchedulerService {

    private final ScoreService scoreService;

    // Захардкоженный UUID турнира
    private static final UUID TOURNAMENT_ID = UUID.fromString("c2998c41-e472-4671-bf80-a414cd68037a");

    /**
     * Метод будет выполняться каждые 2 секунды
     */
    @Scheduled(fixedRate = 5000)
    public void scheduledScoreCalculation() {
        System.gc();

        long beforeUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();
        long startTime = System.nanoTime();

        try {
            scoreService.calculateAllTeamScoresForTournament(TOURNAMENT_ID);
        } catch (Exception e) {
            log.error("Ошибка при подсчете очков команд: {}", e.getMessage(), e);
        }

        long endTime = System.nanoTime();
        long afterUsedMem = Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory();

        long duration = endTime - startTime;
        long usedMem = afterUsedMem - beforeUsedMem;

        log.info("Подсчёт завершён: Время = {} мс, Память = {} КБ",
                duration / 1_000_000, usedMem / 1024);
    }
}
