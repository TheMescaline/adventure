package com.dragonsofmugloar.taskdecoder;

import com.dragonsofmugloar.domain.Task;
import com.dragonsofmugloar.utils.Base64Utils;
import org.springframework.stereotype.Component;

/**
 * Decoder for Base64 encoded strings
 *
 * @author lex.korovin@gmail.com
 */
@Component
class Base64TaskDecoder extends TaskDecoder {

    @Override
    public boolean decodeTask(Task task) {
        String probability = task.getProbability();
        if (Base64Utils.isBase64(probability) && probabilities.contains(Base64Utils.decodeBase64(probability))) {
            task.setAdId(Base64Utils.decodeBase64(task.getAdId()));
            task.setMessage(Base64Utils.decodeBase64(task.getMessage()));
            task.setProbability(Base64Utils.decodeBase64(probability));
            task.setDecodedProbability(resolveProbability(task.getProbability()));
            return true;
        }
        return false;
    }
}
