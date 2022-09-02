package com.dragonsofmugloar.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Task DTO
 *
 * @author lex.korovin@gmail.com
 */
@Getter
@Setter
public class TaskDTO {

    private String adId;
    private String message;
    private Integer reward;
    private Integer expiresIn;
    private String probability;
}
