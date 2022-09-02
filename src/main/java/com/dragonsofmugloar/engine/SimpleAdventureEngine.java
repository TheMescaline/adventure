package com.dragonsofmugloar.engine;

import com.dragonsofmugloar.domain.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Simple game engine, which always takes maximum reward tasks and never uses shop.
 *
 * @author lex.korovin@gmail.com
 */
@Slf4j
@Service
public class SimpleAdventureEngine extends AdventureEngine {

    @Override
    public void playGame() {
        initiateNewGame();
        gameInstance.setCurrentDifficulty(100);
        log.info("Starting a new adventure! New gameId={}", gameInstance.getGameId());

        while (gameInstance.getLives() > 0) {
            renewTasks();
            Task currentTask = tasks.pollFirst();
            if (currentTask == null) {
                log.warn("There are no more tasks available...");
                break;
            }
            adventureService.solveTask(currentTask, gameInstance);
        }

        log.info("Adventure has been ended on turn {}. Current score - {}", gameInstance.getTurn(), gameInstance.getScore());
    }

}
