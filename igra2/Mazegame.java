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
    private static int startX, startY, endX, endY;
    private static Hero hero;
    private static Scanner scanner;

    public static void main(String[] args) {
        maze = generateMaze();
        hero = new Hero(startX, startY);
        scanner = new Scanner(System.in);

        System.out.println("Welcome to the Maze Game!");
        play();

        scanner.close();
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
        startX = random.nextInt(MAZE_SIZE - 2) + 1;
        startY = random.nextInt(MAZE_SIZE - 2) + 1;
        endX = random.nextInt(MAZE_SIZE - 2) + 1;
        endY = random.nextInt(MAZE_SIZE - 2) + 1;
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
                System.out.print(maze[i][j] + " ");
            }
            System.out.println();
        }
    }

    private static void play() {
        while (true) {
            printMaze(maze);

            if (hero.getX() == endX && hero.getY() == endY) {
                System.out.println("Congratulations! You reached the end of the maze!");
                break;
            }

            System.out.print("Enter your move (w/a/s/d): ");
            String move = scanner.nextLine();

            if (move.equalsIgnoreCase("w")) {
                moveHero(-1, 0);
            } else if (move.equalsIgnoreCase("a")) {
                moveHero(0, -1);
            } else if (move.equalsIgnoreCase("s")) {
                moveHero(1, 0);
            } else if (move.equalsIgnoreCase("d")) {
                moveHero(0, 1);
            } else {
                System.out.println("Invalid move! Please enter w/a/s/d.");
            }
        }
    }

    private static void moveHero(int dx, int dy) {
        int newX = hero.getX() + dx;
        int newY = hero.getY() + dy;

        if (newX >= 0 && newX < MAZE_SIZE && newY >= 0 && newY < MAZE_SIZE && maze[newX][newY] != WALL) {
            hero.setX(newX);
            hero.setY(newY);
        } else {
            System.out.println("Invalid move! You hit a wall.");
        }
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

        public void setX(int x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }
}
