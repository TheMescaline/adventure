package com.dragonsofmugloar.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Game instance DTO
 *
 * @author lex.korovin@gmail.com
 */
@Getter
@Setter
public class GameInstanceDTO {

    private String gameId;
    private Integer lives;
    private Integer gold;
    private Integer level;
    private Integer score;
    private Integer highScore;
    private Integer turn;
}
