package com.dragonsofmugloar.dto;

import lombok.Getter;
import lombok.Setter;

/**
 * Shopping result DTO
 *
 * @author lex.korovin@gmail.com
 */
@Getter
@Setter
public class ShoppingResultDTO {

    private boolean shoppingSuccess;
    private Integer gold;
    private Integer lives;
    private Integer level;
    private Integer turn;
}
