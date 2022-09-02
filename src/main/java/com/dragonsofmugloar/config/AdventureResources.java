package com.dragonsofmugloar.config;

/**
 * Placeholder for API resources
 *
 * @author lex.korovin@gmail.com
 */
public interface AdventureResources {
    String START_GAME = "/api/v2/game/start";
    String INVESTIGATE = "/api/v2/{gameId}/investigate/reputation";
    String GET_TASKS = "/api/v2/{gameId}/messages";
    String SOLVE_TASK = "/api/v2/{gameId}/solve/{adId}";
    String SHOP = "/api/v2/{gameId}/shop";
    String BUY_ITEM = "/api/v2/{gameId}/shop/buy/{itemId}";
}
