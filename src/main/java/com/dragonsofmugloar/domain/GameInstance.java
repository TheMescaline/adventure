package com.dragonsofmugloar.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Entity object which represented game instance.
 *
 * Right now there are now need for splitting DTOs and Entities, but it's a strong habit - to split DTOs and entities,
 * so consider this as template for persistence integration, where we can store current state of GameInstance and Tasks
 * in DB for sustainability
 *
 * @author lex.korovin@gmail.com
 */
@Getter
@Setter
@ToString
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class GameInstance {

    @EqualsAndHashCode.Include
    private String gameId;
    private Integer lives;
    private Integer gold;
    private Integer level;
    private Integer score;
    private Integer highScore;
    private Integer turn;
    private Integer currentDifficulty = 1;
}
