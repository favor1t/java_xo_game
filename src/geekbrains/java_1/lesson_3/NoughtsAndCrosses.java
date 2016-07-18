package geekbrains.java_1.lesson_3;

import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class NoughtsAndCrosses {

    private static final char HUMAN_DOT = 'X';
    private static final char AI_DOT = 'O';
    private static final char EMPTY_DOT = '*';
    private static final int MAP_SIZE_Y = 5;
    private static final int MAP_SIZE_X = 5;
    private static final char[][] map = new char[MAP_SIZE_Y][MAP_SIZE_X];
    private static final Scanner sc = new Scanner(System.in);
    private static final Random rnd = new Random();
    private static final int LEN_WIN = 4;

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
        int x, y;
        checkWin(HUMAN_DOT, 1, 0);
        checkWin(HUMAN_DOT, 0, 0);
        /*
        int[] point = checkWin(HUMAN_DOT, 1,3);

        System.out.println("Ищем комбинации 3 в ряд");
        if (isValidCell(point[0], point[1]) && isEmptyCell(point[0], point[1])) {
            System.out.println("Ставим на = " + Arrays.toString(point));
            map[point[1]][point[0]] = DOT;
        } else {
            System.out.println("Ищем комбинации 2 в ряд");
            point = checkWin(HUMAN_DOT, 0,2);
            if (isValidCell(point[0], point[1]) && isEmptyCell(point[0], point[1])) {
                System.out.println("Ставим на = " + Arrays.toString(point));
                map[point[1]][point[0]] = DOT;
            } else {
                System.out.println("Случайно ставим");
                do {
                    x = rnd.nextInt(MAP_SIZE_X);
                    y = rnd.nextInt(MAP_SIZE_Y);
                } while (!isEmptyCell(x, y));
                map[y][x] = DOT;
            }
        }
*/

    }

    private static boolean checkWin(char c) {
        //Проверка горизонтальных линий
        for (int i = 0; i < MAP_SIZE_X; i++)
            for (int j = 0; j <= MAP_SIZE_Y - LEN_WIN; j++)
                if (checkLine(i, j, 0, 1, 2, c)) return true;


        //Проверка вертикальных линий
        for (int i = 0; i < MAP_SIZE_Y; i++)
            for (int j = 0; j <= MAP_SIZE_X - LEN_WIN; j++)
                if (checkLine(j, i, 1, 0, 2, c)) return true;


        //Проветка диагоналей
        for (int i = 0; i <= MAP_SIZE_X - LEN_WIN; i++)
            for (int j = 0; j <= MAP_SIZE_Y - LEN_WIN; j++)
                if (checkLine(i, j, 1, 1, 2, c)) return true;

        for (int i = 0; i <= MAP_SIZE_X - LEN_WIN; i++)
            for (int j = MAP_SIZE_Y - 1; j >= LEN_WIN - 1; j--)
                if (checkLine(i, j, 1, -1, 2, c)) return true;


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

    private static int[] checkWin(char c, int len, int s) {
        int x = -1, y = -1;
        int shift = s; // поправка для координат;
        //Проверка горизонтальных линий
        for (int i = 0; i < MAP_SIZE_X; i++)
            for (int j = 0; j < MAP_SIZE_Y; j++)
                if (checkLine(i, j, 0, 1, LEN_WIN - len, c)) {
                    System.out.println("Опасность по горизонтале [" + j + "][" + i + "]" + " " + (len + 2));
                }


        //Проверка вертикальных линий
        for (int i = 0; i < MAP_SIZE_Y; i++)
            for (int j = 0; j < MAP_SIZE_X; j++)
                if (checkLine(j, i, 1, 0, LEN_WIN - len, c)) {
                    System.out.println("Опасность по верткиале [" + j + "][" + i + "]" + " " + (len + 2));
                }

/*
        //Проверка диагоналей
        for (int i = 0; i <= MAP_SIZE_X - LEN_WIN; i++)
            for (int j = 0; j <= MAP_SIZE_Y - LEN_WIN - len; j++)
                if (checkLine(i, j, 1, 1, LEN_WIN - len, c) && canWin(i, j, 1, 1, 2)) {
                    if (isValidCell(i + shift, j + shift) && isEmptyCell(i + shift, j + shift)) {
                        x = j + shift;
                        y = i + shift;
                    }
                    if (isValidCell(i - shift, j - shift) && isEmptyCell(i - shift, j - shift)) {
                        x = j - shift;
                        y = i - shift;
                    }
                }

        for (int i = 0; i <= MAP_SIZE_X - LEN_WIN; i++)
            for (int j = MAP_SIZE_Y - 1; j >= LEN_WIN; j--)
                if (checkLine(i, j, 1, -1, LEN_WIN - len, c) && canWin(i, j, 1, -1, 2)) {
                    if (isValidCell(i + shift, j - shift) && isEmptyCell(i + shift, j - shift)) {
                        x = j - shift;
                        y = i + shift;
                    }
                    if (isValidCell(i - shift, j + shift) && isEmptyCell(i - shift, j + shift)) {
                        x = j + shift;
                        y = i - shift;
                    }
                }
*/
        int[] res = {y, x};
        return res;
    }

    private static boolean canWin(int x, int y, int vx, int vy, int len) {
        if (isValidCell(x + vx, y + vy) && isValidCell(x, y)) {
            if (map[x][y] != AI_DOT && map[x + vx][y + vy] != AI_DOT) {
                if (len == LEN_WIN) return true;
                return canWin(x + vx, y + vy, vx, vy, len + 1);
            }
        }
        return false;
    }

}