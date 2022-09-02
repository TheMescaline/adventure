package com.dragonsofmugloar.domain;

import com.dragonsofmugloar.taskdecoder.TaskDecoder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * Entity object which represented game task.
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
public class Task {

    @EqualsAndHashCode.Include
    private String adId;
    private String message;
    private Integer reward;
    private Integer expiresIn;
    private String probability;
    @ToString.Exclude
    private TaskDecoder.TaskProbability decodedProbability;
}
