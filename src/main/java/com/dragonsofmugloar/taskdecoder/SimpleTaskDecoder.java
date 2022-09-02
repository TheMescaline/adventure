package com.dragonsofmugloar.taskdecoder;

import com.dragonsofmugloar.domain.Task;
import org.springframework.stereotype.Component;

/**
 * Decoder for not encoded strings (do nothing)
 *
 * @author lex.korovin@gmail.com
 */
@Component
class SimpleTaskDecoder extends TaskDecoder {

    @Override
    public boolean decodeTask(Task task) {
        if (probabilities.contains(task.getProbability())) {
            task.setDecodedProbability(resolveProbability(task.getProbability()));
            return true;
        }
        return false;
    }
}
