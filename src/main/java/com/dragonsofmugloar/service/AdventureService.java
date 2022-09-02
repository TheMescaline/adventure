package com.dragonsofmugloar.service;

import com.dragonsofmugloar.client.AdventureClient;
import com.dragonsofmugloar.domain.GameInstance;
import com.dragonsofmugloar.domain.Task;
import com.dragonsofmugloar.dto.ShopItemDTO;
import com.dragonsofmugloar.dto.TaskSolutionDTO;
import com.dragonsofmugloar.mapper.GameInstanceMapper;
import com.dragonsofmugloar.mapper.TaskMapper;
import com.dragonsofmugloar.taskdecoder.TaskDecoderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * Service layer for Adventure client
 *
 * @author lex.korovin@gmail.com
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class AdventureService {

    private final AdventureClient adventureClient;
    private final TaskDecoderService taskDecoderService;

    /**
     * Retrieves new game instance
     *
     * @return new game instance
     */
    public GameInstance startNewGame() {
        return Mappers.getMapper(GameInstanceMapper.class).toEntity(adventureClient.startGame());
    }

    /**
     * Retrieves task list for specified game instance
     *
     * @param gameInstance game instance
     * @return task list for specified game instance
     */
    public List<Task> fetchTasks(GameInstance gameInstance) {
        return Mappers.getMapper(TaskMapper.class).toEntityCollection(adventureClient.fetchTasks(gameInstance));
    }

    /**
     * Solves specified task
     *
     * @param task          task for solving
     * @param gameInstance  game instance
     * @return {@code true} if task was solved correctly (with victory or defeat), {@code false} otherwise
     */
    public boolean solveTask(Task task, GameInstance gameInstance) {
        boolean decodeSuccess = taskDecoderService.decodeTask(task);
        if (!decodeSuccess) {
            log.warn("Task can't be decoded! Task={}", task);
            return false;
        }

        if (!isSuitable(task, gameInstance)) {
            log.warn("This task is too hard for us. Skipping. Current difficulty = {}, Task complexity = {}", gameInstance.getCurrentDifficulty(), task.getDecodedProbability().getComplexity());
            return false;
        }

        TaskSolutionDTO solution = adventureClient.solveTask(task, gameInstance);

        if (solution == null) {
            return false;
        }

        log.info("Task resolved. {} {}", (solution.isSuccess() ? "Victory!" : "Defeat..."), solution.getMessage());

        gameInstance.setGold(solution.getGold());
        gameInstance.setScore(solution.getScore());
        gameInstance.setLives(solution.getLives());
        gameInstance.setTurn(solution.getTurn());
        gameInstance.setHighScore(solution.getHighScore());
        if (gameInstance.getLives() < 2 && gameInstance.getCurrentDifficulty() > 7) {
            gameInstance.setCurrentDifficulty(gameInstance.getCurrentDifficulty() - 2);
        }
        log.info("Current state. Turn={}, Lives={}, score={}, gold={}, level={}",
                gameInstance.getTurn(),
                gameInstance.getLives(),
                gameInstance.getScore(),
                gameInstance.getGold(),
                gameInstance.getLevel());
        return true;
    }

    /**
     * Checks if task suitable for us to continue
     *
     * @param task          checked Task
     * @param gameInstance  game instance
     * @return {@code true} if current gameDifficulty equal or greater than Probability relatively complexity
     */
    private boolean isSuitable(Task task, GameInstance gameInstance) {
        return gameInstance.getCurrentDifficulty() >= task.getDecodedProbability().getComplexity();
    }

    /**
     * Tries to buy healing potion from shop
     *
     * @param gameInstance game instance, where healing applies
     */
    public void heal(GameInstance gameInstance) {
        List<ShopItemDTO> shopItems = adventureClient.fetchShop(gameInstance.getGameId());
        for (ShopItemDTO shopItem : shopItems) {
            if (shopItem.getName().toLowerCase().contains("heal") && shopItem.getCost() <= gameInstance.getGold()) {
                adventureClient.buyItem(gameInstance, shopItem.getId());
            }
        }
    }

    /**
     * Buys random piece of gear from shop based on current wealth
     *
     * @param gameInstance game instance, where purchase applies
     */
    public void gearUp(GameInstance gameInstance) {
        log.info("Looks like we need some gear!");
        if (gameInstance.getGold() < 300) {
            log.info("Let's buy something simple.");
            buySimpleGear(gameInstance);
        } else {
            log.info("Time to spend a lot of coins!");
            buyQualityGear(gameInstance);
        }
        log.info("Current state. Turn={}, Lives={}, score={}, gold={}, level={}",
                gameInstance.getTurn(),
                gameInstance.getLives(),
                gameInstance.getScore(),
                gameInstance.getGold(),
                gameInstance.getLevel());
    }

    /**
     * Buys random piece of simple gear (for 100 gold)
     *
     * @param gameInstance game instance, where purchase applies
     */
    public void buySimpleGear(GameInstance gameInstance) {
        List<ShopItemDTO> shopItems = adventureClient.fetchShop(gameInstance.getGameId())
                .stream()
                .filter(shopItemDTO -> !shopItemDTO.getName().toLowerCase().contains("heal") && shopItemDTO.getCost() < 300)
                .collect(Collectors.toList());

        ShopItemDTO itemToBuy = shopItems.get(new Random(System.currentTimeMillis()).nextInt(shopItems.size()));
        if (itemToBuy.getCost() < gameInstance.getGold()) {
            adventureClient.buyItem(gameInstance, itemToBuy.getId());
            log.info("Could've been better, but this {} is also fine.", itemToBuy.getName());
        }
    }

    /**
     * Buys random piece of good gear (for 300 gold)
     *
     * @param gameInstance game instance, where purchase applies
     */
    public void buyQualityGear(GameInstance gameInstance) {
        List<ShopItemDTO> shopItems = adventureClient.fetchShop(gameInstance.getGameId())
                .stream()
                .filter(shopItemDTO -> !shopItemDTO.getName().toLowerCase().contains("heal") && shopItemDTO.getCost() >= 300)
                .collect(Collectors.toList());

        ShopItemDTO itemToBuy = shopItems.get(new Random(System.currentTimeMillis()).nextInt(shopItems.size()));
        if (itemToBuy.getCost() < gameInstance.getGold()) {
            adventureClient.buyItem(gameInstance, itemToBuy.getId());
            log.info("Come to papa, my dear {}", itemToBuy.getName());
        }
    }
}
