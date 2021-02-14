package day22.part2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

public class Day22Part2 {
    private static final String INPUT_PATH = "src/main/resources/day22/input.txt";

    public static void main(String[] args) throws IOException {
        final Deque<Integer> player1 = new ArrayDeque<>();
        final Deque<Integer> player2 = new ArrayDeque<>();
        final Deque<Integer> winner = new ArrayDeque<>();

        parseInput(player1, player2);

        getWinner(player1, player2, winner, player1.size(), player2.size());

        int score = getScore(winner);

        System.out.println(score);
    }

    private static int getScore( Deque<Integer> winner) {
        int score = 0;
        while (!winner.isEmpty()) {
            score += winner.size() * winner.remove();
        }
        return score;
    }

    private static Deque<Integer> getWinner(Deque<Integer> originalPlayer1, Deque<Integer> originalPlayer2, Deque<Integer> winner, int l1, int l2) {
        final Deque<Integer> player1 = new ArrayDeque<>(originalPlayer1);
        final Deque<Integer> player2 = new ArrayDeque<>(originalPlayer2);

        for (int i = originalPlayer1.size() - l1; i > 0; i--) {
            player1.removeLast();
        }

        for (int i = originalPlayer2.size() - l2; i > 0; i--) {
            player2.removeLast();
        }

        final Set<String> history = new HashSet<>();
        while (!player1.isEmpty() && !player2.isEmpty()) {
            final String hash = player1.toString() + player2.toString();
            if (!history.add(hash)) {
                return originalPlayer1;
            }

            final int player1Card = player1.removeFirst();
            final int player2Card = player2.removeFirst();
            if (player1.size() >= player1Card && player2.size() >= player2Card) {
                if (getWinner(player1, player2, null, player1Card, player2Card) == player1) {
                    receiveCards(player1, player1Card, player2Card);
                } else {
                    receiveCards(player2, player2Card, player1Card);
                }
            } else {
                if (player1Card > player2Card) {
                    receiveCards(player1, player1Card, player2Card);
                } else {
                    receiveCards(player2, player2Card, player1Card);
                }
            }
        }

        if (winner != null) {
            winner.addAll(player2.isEmpty() ? player1 : player2);
        }

        return player2.isEmpty() ? originalPlayer1 : originalPlayer2;
    }

    private static void receiveCards(Queue<Integer> player, Integer winningCard, Integer losingCard) {
        player.add(winningCard);
        player.add(losingCard);
    }

    private static void parseInput(Queue<Integer> player1, Queue<Integer> player2) throws IOException {
        Queue<Integer> player = player1;
        for (String line : Files.readAllLines(Path.of(INPUT_PATH))) {
            if (line.isEmpty()) {
                player = player2;
                continue;
            } else if (line.startsWith("Player")) {
                continue;
            }

            int card = Integer.parseInt(line);
            player.add(card);
        }
    }
}
