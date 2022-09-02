package com.dragonsofmugloar.taskdecoder;

import com.dragonsofmugloar.domain.Task;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Iterator;
import java.util.List;

/**
 * Decoder service
 *
 * @author lex.korovin@gmail.com
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class TaskDecoderService {

    private final List<TaskDecoder> taskDecoders;

    /**
     * Tries to decode Task in every registered decoder. Throws exception, if not a single decoder successfully decoded task
     *
     * @param task task to be decoded
     * @return {@code true} if task was successfully decoded, {@code false}
     */
    public boolean decodeTask(Task task) {
        boolean decodeSuccess = false;
        Iterator<TaskDecoder> tdIterator = taskDecoders.listIterator();
        while (!decodeSuccess && tdIterator.hasNext()) {
            decodeSuccess = tdIterator.next().decodeTask(task);
        }
        return decodeSuccess;
    }
}
