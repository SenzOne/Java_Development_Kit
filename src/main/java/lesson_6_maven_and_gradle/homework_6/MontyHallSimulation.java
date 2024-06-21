package lesson_6_maven_and_gradle.homework_6;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.stream.IntStream;

public class MontyHallSimulation {

    private static final int NUM_SIMULATIONS = 1000;
    private static final Random RANDOM = new Random();

    public static void main(String[] args) {
        Map<Integer, Boolean> results = new HashMap<>();

        for (int i = 0; i < NUM_SIMULATIONS; i++) {
            boolean stayWin = simulateGame(false);
            results.put(i, stayWin);

            boolean switchWin = simulateGame(true);
            results.put(i + NUM_SIMULATIONS, switchWin);
        }

        long stayWins = countWins(results, 0, NUM_SIMULATIONS);
        long switchWins = countWins(results, NUM_SIMULATIONS, 2 * NUM_SIMULATIONS);

        double stayWinPercentage = Math.round((stayWins / (double) NUM_SIMULATIONS) * 100);
        double switchWinPercentage = Math.round((switchWins / (double) NUM_SIMULATIONS) * 100);

        System.out.println("Выигрывает, оставаясь при первоначальном выборе: " + stayWins + " (" + stayWinPercentage + "%)");
        System.out.println("Выигрывает при смене выбора: " + switchWins + " (" + switchWinPercentage + "%)");

    }

    private static boolean simulateGame(boolean switchChoice) {
        Game game = Game.builder()
                .prizeDoor(RANDOM.nextInt(3))
                .initialChoice(RANDOM.nextInt(3))
                .build();

        if (switchChoice) {
            game.switchDoor();
        }
        return game.isWin();
    }

    private static long countWins(Map<Integer, Boolean> results, int start, int end) {
        return IntStream.range(start, end)
                .filter(results::get)
                .count();
    }

    @Data
    @AllArgsConstructor
    @Builder
    private static class Game {
        private int prizeDoor;
        private int initialChoice;

        public void switchDoor() {
            int revealedDoor;
            do {
                revealedDoor = RANDOM.nextInt(3);
            } while (revealedDoor == prizeDoor || revealedDoor == initialChoice);

            int newChoice;
            do {
                newChoice = RANDOM.nextInt(3);
            } while (newChoice == revealedDoor || newChoice == initialChoice);

            initialChoice = newChoice;
        }

        public boolean isWin() {
            return initialChoice == prizeDoor;
        }
    }
}
