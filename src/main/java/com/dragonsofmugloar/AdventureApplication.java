package com.dragonsofmugloar;

import com.dragonsofmugloar.engine.AdventureEngine;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;

import java.util.List;
import java.util.Scanner;

@Slf4j
@RequiredArgsConstructor
@SpringBootApplication
public class AdventureApplication {

    private final List<AdventureEngine> gameEngines;

    public static void main(String[] args) {
        SpringApplication.run(AdventureApplication.class, args);
    }

    @EventListener(ApplicationReadyEvent.class)
    public void startAdventure(ApplicationReadyEvent event) {
        log.info("We are ready to play the game. Now you need to choose game engine. Currently available:");
        while (true) {
            int i;
            for (i = 0; i < gameEngines.size(); i++) {
                log.info(i + 1 + " - " + gameEngines.get(i).getClass().getSimpleName());
            }
            log.info(++i + " - Stop program");

            log.info("Enter number of preferable engine below:");
            Scanner scanner = new Scanner(System.in);
            String userInput = scanner.nextLine();
            try {
                int input = Integer.parseInt(userInput);
                if (input < 1 || input > gameEngines.size() + 1) {
                    throw new IllegalArgumentException();
                }
                if (input == i) {
                    log.info("Farewell!");
                    System.exit(0);
                } else {
                    gameEngines.get(input - 1).playGame();
                }
            } catch (Exception e) {
                log.error("Input is incorrect! Please, write correct number of preferable engine.", e);
            }
        }
    }
}
