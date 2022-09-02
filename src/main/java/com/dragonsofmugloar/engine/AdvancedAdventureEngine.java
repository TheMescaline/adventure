package com.dragonsofmugloar.engine;

import com.dragonsofmugloar.domain.Task;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * Advanced game engine. Uses game shop, but carefully. Tries to have some spare gold for unexpected healing.
 *
 * @author lex.korovin@gmail.com
 */
@Slf4j
@Service
public class AdvancedAdventureEngine extends AdventureEngine {

    @Override
    public void playGame() {
        initiateNewGame();
        tasks.clear();

        log.info("Starting a new adventure! New gameId={}", gameInstance.getGameId());

        while (gameInstance.getLives() > 0) {
            renewTasks();

            if (gameInstance.getLives() < 3 && gameInstance.getGold() > 100) {
                adventureService.heal(gameInstance);
                if (gameInstance.getGold() > 200) {
                    adventureService.gearUp(gameInstance);
                }
                continue;
            }

            if (gameInstance.getLives() < 2 && gameInstance.getGold() >= 50) {
                adventureService.heal(gameInstance);
                continue;
            }

            if (gameInstance.getGold() > 400) {
                adventureService.gearUp(gameInstance);
                continue;
            }

            boolean success = false;
            while (!success) {
                Task currentTask = tasks.pollFirst();
                if (currentTask == null) {
                    log.warn("There are no more tasks available...");
                    gameInstance.setCurrentDifficulty(gameInstance.getCurrentDifficulty() + 1);
                    break;
                }
                success = adventureService.solveTask(currentTask, gameInstance);
            }
        }

        log.info("Adventure has been ended on turn {}. Current score - {}", gameInstance.getTurn(), gameInstance.getScore());
    }

}
