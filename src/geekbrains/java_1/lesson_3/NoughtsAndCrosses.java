package geekbrains.java_1.lesson_3;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class NoughtsAndCrosses {

    private static final char HUMAN_DOT = 'X';
    private static final char AI_DOT = 'O';
    private static final char EMPTY_DOT = '*';
    private static final int MAP_SIZE_Y = 3;
    private static final int MAP_SIZE_X = 3;
    private static final char[][] map = new char[MAP_SIZE_Y][MAP_SIZE_X];
    private static final Scanner sc = new Scanner(System.in);
    private static final Random rnd = new Random();
    private static final int LEN_WIN = 3;

    public static void main(String[] args) {
        initMap();
        printMap();

        while (true) {
            humanTurn();
            printMap();
            if (checkWin(HUMAN_DOT)) {
                System.out.println("Выиграл игрок!!!");
                break;
            }
            if (isMapFull()) {
                System.out.println("Ничья!!!");
                break;
            }
            aiTurn(AI_DOT);
            printMap();
            if (checkWin(AI_DOT)) {
                System.out.println("Выиграл компьютер!!!");
                break;
            }
            if (isMapFull()) {
                System.out.println("Ничья!!!");
                break;
            }
        }
        sc.close();
    }

    private static void initMap() {
        for (int i = 0; i < MAP_SIZE_Y; i++) {
            for (int j = 0; j < MAP_SIZE_X; j++) {
                map[i][j] = EMPTY_DOT;
            }
        }
    }

    private static void printMap() {
        for (int i = 0; i <= MAP_SIZE_X; i++) System.out.print(i + " ");
        System.out.println();
        for (int i = 0; i < MAP_SIZE_Y; i++) {
            System.out.print(i + 1 + " ");
            for (int j = 0; j < MAP_SIZE_X; j++) System.out.print(map[i][j] + " ");
            System.out.println();
        }
        System.out.println();
    }

    private static void humanTurn() {
        int x, y;

        do {
            System.out.println("Введите координаты X и Y:");
            x = sc.nextInt() - 1;
            y = sc.nextInt() - 1;
        } while (!isValidCell(x, y) || !isEmptyCell(x, y));
        map[y][x] = HUMAN_DOT;
    }

    /**
     *
     */
    private static void aiTurn(char DOT) {
        if (detectAIWinCell()) return;
        if (detectHumanWinCell()) return;
        int x, y;
        do {
            x = rnd.nextInt(MAP_SIZE_X);
            y = rnd.nextInt(MAP_SIZE_Y);
        } while (!isEmptyCell(x, y));
        map[y][x] = AI_DOT;
    }

    private static boolean checkWin(char c) {
        //Проверка горизонтальных линий
        for (int i = 0; i < MAP_SIZE_X; i++)
            for (int j = 0; j < MAP_SIZE_Y; j++) {
                if (checkLine(i, j, 0, 1, 2, c)) return true;
                if (checkLine(i, j, 1, 0, 2, c)) return true;
                if (checkLine(i, j, 1, 1, 2, c)) return true;
                if (checkLine(i, j, 1, -1, 2, c)) return true;
            }
        return false;
    }

    private static boolean isMapFull() {
        for (int i = 0; i < MAP_SIZE_Y; i++) {
            for (int j = 0; j < MAP_SIZE_X; j++) {
                if (map[i][j] == EMPTY_DOT) return false;
            }
        }
        return true;
    }

    private static boolean isValidCell(int x, int y) {
        return x >= 0 && x < MAP_SIZE_X && y >= 0 && y < MAP_SIZE_Y;
    }

    private static boolean isEmptyCell(int x, int y) {
        return map[y][x] == EMPTY_DOT;
    }

    /**
     * @param x   координата отсчета по x
     * @param y   координата отсчета по y
     * @param vx  сдвиг по x
     * @param vy  сдвиг по y
     * @param len текущая длина совпадений
     * @param c   какого игрока проверяем
     * @return boolean
     */
    private static boolean checkLine(int x, int y, int vx, int vy, int len, char c) {
        if (isValidCell(x, y) && isValidCell(x + vx, y + vy)) {
            if (map[x][y] == c && map[x + vx][y + vy] == c) {
                if (len == LEN_WIN) return true;
                return checkLine(x + vx, y + vy, vx, vy, len + 1, c);
            }
        }
        return false;

    }

    private static boolean detectAIWinCell() {
        for (int i = 0; i < MAP_SIZE_Y; i++) {
            for (int j = 0; j < MAP_SIZE_X; j++) {
                if (isEmptyCell(j, i)) {
                    map[i][j] = AI_DOT;
                    if (checkWin(AI_DOT)) return true;
                    map[i][j] = EMPTY_DOT;
                }
            }
        }
        return false;
    }

    private static boolean detectHumanWinCell() {
        for (int i = 0; i < MAP_SIZE_Y; i++) {
            for (int j = 0; j < MAP_SIZE_X; j++) {
                if (isEmptyCell(j, i)) {
                    map[i][j] = HUMAN_DOT;
                    if (checkWin(HUMAN_DOT)) {
                        map[i][j] = AI_DOT;
                        return true;
                    }
                    map[i][j] = EMPTY_DOT;
                }
            }
        }
        return false;
    }

}