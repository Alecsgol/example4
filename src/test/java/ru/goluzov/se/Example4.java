package ru.goluzov.se;

import java.util.Random;
import java.util.Scanner;

public class Example4 {
    private static char[][] tiles;
    private static final int SIZE = 3;
    private static final int ACTION_SUM = SIZE * SIZE;
    private static final int DOTS_TO_WIN = SIZE;
    private static final char DOT_EMPTY = '_';
    private static final char DOT_X = 'X';
    private static final char DOT_O = 'O';
    private static Scanner scanner = new Scanner(System.in);
    private static Random random = new Random();
    private static boolean trigger = true;
    private static int actionCounter = 0;
    private static char currentDot;

    public static void main(String[] args) {
        fillMap();
        printMap();
        while (checkGameLoop()) {
            if (trigger) {
                currentDot = DOT_X;
                humanPlayer();
                stupidPcPlayer();
                aiPlayer();
            } else {
                currentDot = DOT_O;
                stupidPcPlayer();
                aiPlayer();
            }
            trigger = !trigger;
            printMap();
            actionCounter++;
        }
    }
    private static void aiPlayer() {
    }
    private static void fillMap() {
        tiles = new char[SIZE][SIZE];
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                tiles[i][j] = DOT_EMPTY;
            }
        }
    }
    private static void printMap() {
        System.out.printf("   ");
        for (int i = 0; i < SIZE; i++) {
            System.out.printf("%2d ", i + 1);
        }
        System.out.println();
        for (int i = 0; i < SIZE; i++) {
            System.out.printf("%2d ", i + 1);
            for (int j = 0; j < SIZE; j++) {
                System.out.printf("%2c ", tiles[i][j]);
            }
            System.out.println();
        }
        System.out.println();
    }
    private static int[] humanInput() {
        int input[] = new int[2];
        while (!scanner.hasNextInt()) {
            scanner.next();
        }
        while (!scanner.hasNextInt()) {
            scanner.next();
        }
        input[0] = scanner.nextInt() - 1;
        input[1] = scanner.nextInt() - 1;
        return input;
    }
    private static void humanPlayer() {
        int y;
        int x;
        do {
            System.out.printf("Ходит человек [%c]. Введите Y X: ", currentDot);
            int[] input = humanInput();
            y = input[0];
            x = input[1];
        } while (!checkAction(y, x));
        tiles[y][x] = currentDot;
    }
    private static void stupidPcPlayer() {
        int y;
        int x;
        System.out.printf("Ходит AI [%c]", currentDot);
        do {
            y = random.nextInt(SIZE);
            x = random.nextInt(SIZE);
        } while (!checkAction(y, x));
        System.out.printf(" (y: %d, x: %d)\n", y + 1, x + 1);
        tiles[y][x] = currentDot;
    }
    private static boolean checkAction(int y, int x) {
        if (x < 0 || y < 0 || x >= SIZE || y >= SIZE) return false;
        if (DOT_EMPTY == tiles[y][x]) return true;
        return false;
    }
    private static boolean isContinue() {
        for (int i = 0; i < SIZE; i++) {
            for (int j = 0; j < SIZE; j++) {
                if (findRightUp(i, j)) return false;
                if (findRight(i, j)) return false;
                if (findRightDown(i, j)) return false;
                if (findDown(i, j)) return false;
            }
        }
        return true;
    }
    private static boolean findRightUp(int y, int x) {
        for (int j = 0; j < DOTS_TO_WIN; j++) {
            if (y < 0 || y >= SIZE || x < 0 || x >= SIZE || currentDot != tiles[y][x]) return false;
            y--;
            x++;
        }
        return true;
    }
    private static boolean findRight(int y, int x) {
        for (int j = 0; j < DOTS_TO_WIN; j++) {
            if (y < 0 || y >= SIZE || x < 0 || x >= SIZE || currentDot != tiles[y][x]) return false;
            x++;
        }
        return true;
    }
    private static boolean findRightDown(int y, int x) {
        for (int j = 0; j < DOTS_TO_WIN; j++) {
            if (y < 0 || y >= SIZE || x < 0 || x >= SIZE || currentDot != tiles[y][x]) return false;
            y++;
            x++;
        }
        return true;
    }
    private static boolean findDown(int y, int x) {
        for (int j = 0; j < DOTS_TO_WIN; j++) {
            if (y < 0 || y >= SIZE || x < 0 || x >= SIZE || currentDot != tiles[y][x]) return false;
            y++;
        }
        return true;
    }
    private static boolean checkGameLoop() {
        if (actionCounter == ACTION_SUM) return false;
        if (!isContinue()) return false;
        return true;
    }
}