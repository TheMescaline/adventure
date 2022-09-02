package com.dragonsofmugloar.taskdecoder;

import com.dragonsofmugloar.domain.Task;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Abstract decoder for text in tasks. Decoding can be based on matches for probabilities, because of sustainability of that list.
 * Right now there are three possible variants: </br>
 * - No encoding for text </br>
 * - Base64 encoding </br>
 * - CaesarCipher encoding </br>
 *
 * For extension of the decoding variants you can simply extend this class and implement custom logic in
 * {@link TaskDecoder#decodeTask(com.dragonsofmugloar.domain.Task)} method. Decoders registers via DI in
 * {@link TaskDecoderService} and uses sequentially until first match for decoded probability with probabilities set.
 *
 * @author lex.korovin@gmail.com
 */
public abstract class TaskDecoder {

    private final Map<String, TaskProbability> taskProbabilityMap = new HashMap<>();

    protected final Set<String> probabilities = Arrays.stream(TaskProbability.values())
            .peek(taskProbability -> taskProbabilityMap.put(taskProbability.getValue(), taskProbability))
            .map(TaskProbability::getValue)
            .collect(Collectors.toSet());

    /**
     * Tries to decode text in Task.
     * @param task Task to be decoded
     * @return {@code true} if task probability matches any in {@link TaskDecoder#probabilities}, {@code false} otherwise
     */
    public abstract boolean decodeTask(Task task);

    public TaskProbability resolveProbability(String taskProbability) {
        return taskProbabilityMap.get(taskProbability);
    }

    @RequiredArgsConstructor
    @Getter
    public enum TaskProbability {

        PIECE_OF_CAKE("Piece of cake", 1),
        WALK_IN_THE_PARK("Walk in the park", 2),
        QUITE_LIKELY("Quite likely", 3),
        SURE_THING("Sure thing", 4),
        HMMM("Hmmm....", 5),
        DETRIMENTAL("Rather detrimental", 6),
        GAMBLE("Gamble", 7),
        RISKY("Risky", 8),
        PLAYING_WITH_FIRE("Playing with fire", 9),
        IMPOSSIBLE("Impossible", 10),
        SUICIDE("Suicide mission", 11);

        public final String value;
        public final int complexity;
    }
}
