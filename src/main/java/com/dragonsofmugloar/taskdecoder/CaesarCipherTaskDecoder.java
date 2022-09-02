package com.dragonsofmugloar.taskdecoder;

import com.dragonsofmugloar.domain.Task;
import com.dragonsofmugloar.utils.CaesarCipher;
import org.springframework.stereotype.Component;

/**
 * Decoder for Caesar encrypted strings
 *
 * @author lex.korovin@gmail.com
 */
@Component
class CaesarCipherTaskDecoder extends TaskDecoder {

    @Override
    public boolean decodeTask(Task task) {
        String probability = task.getProbability();
        int caesarOffset = CaesarCipher.breakCipher(probability);
        String decipheredProbability = CaesarCipher.decipher(probability, caesarOffset);
        if (probabilities.contains(decipheredProbability)) {
            task.setAdId(CaesarCipher.decipher(task.getAdId(), caesarOffset));
            task.setMessage(CaesarCipher.decipher(task.getMessage(), caesarOffset));
            task.setProbability(decipheredProbability);
            task.setDecodedProbability(resolveProbability(decipheredProbability));
            return true;
        }
        return false;
    }
}
