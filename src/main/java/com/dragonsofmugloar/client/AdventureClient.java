package com.dragonsofmugloar.client;

import com.dragonsofmugloar.domain.GameInstance;
import com.dragonsofmugloar.domain.Task;
import com.dragonsofmugloar.dto.GameInstanceDTO;
import com.dragonsofmugloar.dto.ShopItemDTO;
import com.dragonsofmugloar.dto.ShoppingResultDTO;
import com.dragonsofmugloar.dto.TaskDTO;
import com.dragonsofmugloar.dto.TaskSolutionDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Collections;
import java.util.List;

import static com.dragonsofmugloar.config.AdventureResources.BUY_ITEM;
import static com.dragonsofmugloar.config.AdventureResources.GET_TASKS;
import static com.dragonsofmugloar.config.AdventureResources.SHOP;
import static com.dragonsofmugloar.config.AdventureResources.SOLVE_TASK;
import static com.dragonsofmugloar.config.AdventureResources.START_GAME;

/**
 * Client for REST requests to Adventure game API
 *
 * @author lex.korovin@gmail.com
 */
@RequiredArgsConstructor
@Slf4j
@Service
public class AdventureClient {

    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${adventure.url:https://www.dragonsofmugloar.com}")
    private String adventureUrl;

    public GameInstanceDTO startGame() {
        return restTemplate.postForEntity(UriComponentsBuilder.fromHttpUrl(adventureUrl).path(START_GAME).toUriString(), null, GameInstanceDTO.class).getBody();
    }

    public List<TaskDTO> fetchTasks(GameInstance gameInstance) {
        ResponseEntity<TaskDTO[]> response = restTemplate.getForEntity(UriComponentsBuilder.fromHttpUrl(adventureUrl).path(GET_TASKS).build(gameInstance.getGameId()).toString(), TaskDTO[].class);
        return response.getBody() != null ? List.of(response.getBody()) : Collections.emptyList();
    }

    public TaskSolutionDTO solveTask(Task task, GameInstance gameInstance) {
        try {
            log.info("Going to {}. Success probability = {}", task.getMessage(), task.getProbability());
            return restTemplate.postForEntity(UriComponentsBuilder.fromHttpUrl(adventureUrl).path(SOLVE_TASK).build(gameInstance.getGameId(), task.getAdId()).toString(), null, TaskSolutionDTO.class).getBody();
        } catch (Exception e) {
            return null;
        }
    }

    public List<ShopItemDTO> fetchShop(String gameId) {
        ResponseEntity<ShopItemDTO[]> response = restTemplate.getForEntity(UriComponentsBuilder.fromHttpUrl(adventureUrl).path(SHOP).build(gameId).toString(), ShopItemDTO[].class);
        return response.getBody() != null ? List.of(response.getBody()) : Collections.emptyList();
    }

    public void buyItem(GameInstance gameInstance, String shopItemId) {
        ShoppingResultDTO shoppingResult = restTemplate.postForEntity(UriComponentsBuilder.fromHttpUrl(adventureUrl).path(BUY_ITEM).build(gameInstance.getGameId(), shopItemId).toString(), null, ShoppingResultDTO.class).getBody();
        Assert.notNull(shoppingResult, "Shopping did not get any results!");
        gameInstance.setTurn(shoppingResult.getTurn());
        gameInstance.setGold(shoppingResult.getGold());
        gameInstance.setLives(shoppingResult.getLives());
        gameInstance.setLevel(shoppingResult.getLevel());
    }
}
