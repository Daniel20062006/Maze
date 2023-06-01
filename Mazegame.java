import java.util.Random;
import java.util.Scanner;

public class Mazegame {
    private static final int MAZE_SIZE = 11;
    private static final char WALL = '#';
    private static final char PATH = ' ';
    private static final char START = 'S';
    private static final char END = 'E';
    private static final char VISITED = '.';
    private static final int[][] DIRECTIONS = {{-1, 0}, {0, -1}, {1, 0}, {0, 1}}; // up, left, down, right

    private static char[][] maze;
    private static Hero hero;

    public static void main(String[] args) {
        maze = generateMaze();
        hero = new Hero(1, 1); // initial position of the hero
        play();
    }

    private static char[][] generateMaze() {
        char[][] maze = new char[MAZE_SIZE][MAZE_SIZE];

        // fill maze with walls
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                maze[i][j] = WALL;
            }
        }

        // generate random maze
        Random random = new Random();
        int startX = random.nextInt(MAZE_SIZE - 2) + 1;
        int startY = random.nextInt(MAZE_SIZE - 2) + 1;
        int endX = random.nextInt(MAZE_SIZE - 2) + 1;
        int endY = random.nextInt(MAZE_SIZE - 2) + 1;
        while (endX == startX && endY == startY) {
            endX = random.nextInt(MAZE_SIZE - 2) + 1;
            endY = random.nextInt(MAZE_SIZE - 2) + 1;
        }
        maze[startX][startY] = START;
        maze[endX][endY] = END;
        generateMazeDFS(maze, startX, startY, endX, endY);

        return maze;
    }

    private static void generateMazeDFS(char[][] maze, int x, int y, int endX, int endY) {
        Random random = new Random();
        int[] directions = {0, 1, 2, 3};
        for (int i = 0; i < directions.length; i++) {
            int j = random.nextInt(directions.length);
            int temp = directions[i];
            directions[i] = directions[j];
            directions[j] = temp;
        }
        for (int d : directions) {
            int dx = x + DIRECTIONS[d][0];
            int dy = y + DIRECTIONS[d][1];
            if (dx >= 1 && dx < MAZE_SIZE - 1 && dy >= 1 && dy < MAZE_SIZE - 1 && maze[dx][dy] == WALL) {
                maze[x + DIRECTIONS[d][0] / 2][y + DIRECTIONS[d][1] / 2] = PATH;
                generateMazeDFS(maze, dx, dy, endX, endY);
            }
        }
    }

    private static void printMaze(char[][] maze) {
        for (int i = 0; i < maze.length; i++) {
            for (int j = 0; j < maze[i].length; j++) {
                if (maze[i][j] == START) {
                	System.out.print("S ");
                } else if (maze[i][j] == END) {
                System.out.print("E ");
                } else if (i == hero.getX() && j == hero.getY()) {
                System.out.print("H ");
                } else {
                System.out.print(maze[i][j] + " ");
                }
                }
                System.out.println();
                }
                }
    private static void play() {
        Scanner scanner = new Scanner(System.in);
        boolean gameOver = false;

        while (!gameOver) {
            printMaze(maze);
            System.out.print("Enter your move (WASD): ");
            String input = scanner.nextLine().toUpperCase();
            char move = input.charAt(0);

            int dx = 0, dy = 0;

            switch (move) {
                case 'W':
                    dx = -1;
                    break;
                case 'A':
                    dy = -1;
                    break;
                case 'S':
                    dx = 1;
                    break;
                case 'D':
                    dy = 1;
                    break;
                default:
                    System.out.println("Invalid move! You hit a wall.");
                    continue;
            }

            int newX = hero.getX() + dx;
            int newY = hero.getY() + dy;

            if (isValidMove(newX, newY)) {
                moveHero(dx, dy);
                if (maze[newX][newY] == END) {
                    System.out.println("Congratulations! You reached the end of the maze!");
                    gameOver = true;
                }
            } else {
                System.out.println("Invalid move! You hit a wall.");
            }
        }

        scanner.close();
    }

    private static boolean isValidMove(int x, int y) {
        return x >= 0 && x < MAZE_SIZE && y >= 0 && y < MAZE_SIZE && (maze[x][y] == PATH || maze[x][y] == END);
    }

    private static void moveHero(int dx, int dy) {
        int newX = hero.getX() + dx;
        int newY = hero.getY() + dy;
        maze[hero.getX()][hero.getY()] = VISITED;
        maze[newX][newY] = START;
        hero.setX(newX);
        hero.setY(newY);
    }

    private static class Hero {
        private int x;
        private int y;

        public Hero(int x, int y) {
            this.x = x;
            this.y = y;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public void setX(int x) {
            this.x = x;
        }

        public void setY(int y) {
            this.y = y;
        }
    }
}