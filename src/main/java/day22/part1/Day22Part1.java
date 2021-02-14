package day22.part1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayDeque;
import java.util.Queue;

public class Day22Part1 {
    private static final String INPUT_PATH = "src/main/resources/day22/input.txt";

    private static final Queue<Integer> player1 = new ArrayDeque<>();
    private static final Queue<Integer> player2 = new ArrayDeque<>();

    public static void main(String[] args) throws IOException {
        parseInput();

        while (!player1.isEmpty() && !player2.isEmpty()) {
            playRound();
        }

        int winningScore = calculateWinningScore();

        System.out.println(winningScore);
    }

    private static int calculateWinningScore() {
        Queue<Integer> winningPlayer = player1.isEmpty() ? player2 : player1;
        int score = 0;
        while (!winningPlayer.isEmpty()) {
            score += winningPlayer.size() * winningPlayer.remove();
        }
        return score;
    }

    private static void playRound() {
        int player1Card = player1.remove();
        int player2Card = player2.remove();

        if (player1Card > player2Card) {
            receiveCards(player1, player1Card, player2Card);
        } else {
            receiveCards(player2, player2Card, player1Card);
        }
    }

    private static void receiveCards(Queue<Integer> player, Integer winningCard, Integer losingCard) {
        player.add(winningCard);
        player.add(losingCard);
    }

    private static void parseInput() throws IOException {
        boolean isPlayer1 = true;
        for (String line : Files.readAllLines(Path.of(INPUT_PATH))) {
            if (line.isEmpty()) {
                isPlayer1 = false;
                continue;
            } else if (line.startsWith("Player")) {
                continue;
            }

            int card = Integer.parseInt(line);
            if (isPlayer1) {
                player1.add(card);
            } else {
                player2.add(card);
            }
        }
    }
}
