package com.dragonsofmugloar.engine;

import com.dragonsofmugloar.domain.GameInstance;
import com.dragonsofmugloar.domain.Task;
import com.dragonsofmugloar.service.AdventureService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Iterator;
import java.util.TreeSet;

/**
 * Abstract class for all Game engines
 *
 * @author lex.korovin@gmail.com
 */
@Component
public abstract class AdventureEngine {

    protected TreeSet<Task> tasks = new TreeSet<>((o1, o2) -> o2.getReward().compareTo(o1.getReward()));
    protected GameInstance gameInstance;

    @Autowired
    protected AdventureService adventureService;

    /**
     * Starts game engine logic
     */
    public abstract void playGame();

    /**
     * Initiates a new game
     */
    protected void initiateNewGame() {
        gameInstance = adventureService.startNewGame();
    }


    /**
     * Refreshes task list, supports tasks expiration timers
     */
    protected void renewTasks() {
        Iterator<Task> oldTaskIterator = tasks.iterator();
        while (oldTaskIterator.hasNext()) {
            Task oldTask = oldTaskIterator.next();
            if (oldTask.getExpiresIn() == 1) {
                oldTaskIterator.remove();
            } else {
                oldTask.setExpiresIn(oldTask.getExpiresIn() - 1);
            }
        }
        tasks.addAll(adventureService.fetchTasks(gameInstance));
    }
}
