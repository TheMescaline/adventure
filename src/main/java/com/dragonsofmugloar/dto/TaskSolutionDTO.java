package com.dragonsofmugloar.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Result of a task solution DTO
 *
 * @author lex.korovin@gmail.com
 */
@Getter
@Setter
public class TaskSolutionDTO {

    private boolean success;
    private Integer lives;
    private Integer gold;
    private Integer score;
    private Integer highScore;
    private Integer turn;
    private String message;
}
