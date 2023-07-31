import java.util.*;

public class MazeSolver {
    private static int[][] maze;
    private static int[][] visited;
    private static int[][] parentX;
    private static int[][] parentY;
    private static int startRow, startCol, goalRow, goalCol;

    public static void initializeMaze(int[][] maze, int startRow, int startCol, int goalRow, int goalCol) {
        MazeSolver.maze = maze;
        visited = new int[maze.length][maze[0].length];
        parentX = new int[maze.length][maze[0].length];
        parentY = new int[maze.length][maze[0].length];
        MazeSolver.startRow = startRow;
        MazeSolver.startCol = startCol;
        MazeSolver.goalRow = goalRow;
        MazeSolver.goalCol = goalCol;
    }

    public static boolean isValidCell(int row, int col) {
        return row >= 0 && row < maze.length && col >= 0 && col < maze[0].length && maze[row][col] == 0 && visited[row][col] == 0;
    }

    public static List<int[]> solveMaze() {
        Queue<int[]> queue = new LinkedList<>();
        queue.add(new int[]{startRow, startCol});
        visited[startRow][startCol] = 1;

        while (!queue.isEmpty()) {
            int[] cell = queue.poll();
            int row = cell[0];
            int col = cell[1];

            if (row == goalRow && col == goalCol) {
                return getPath();
            }

            // Explore neighboring cells (up, down, left, right)
            int[] dx = {-1, 1, 0, 0};
            int[] dy = {0, 0, -1, 1};
            for (int i = 0; i < 4; i++) {
                int newRow = row + dx[i];
                int newCol = col + dy[i];
                if (isValidCell(newRow, newCol)) {
                    queue.add(new int[]{newRow, newCol});
                    visited[newRow][newCol] = 1;
                    parentX[newRow][newCol] = row;
                    parentY[newRow][newCol] = col;
                }
            }
        }

        return new ArrayList<>(); // Return an empty list if no path is found
    }

    public static List<int[]> getPath() {
        List<int[]> path = new ArrayList<>();
        int row = goalRow;
        int col = goalCol;

        while (row != startRow || col != startCol) {
            path.add(0, new int[]{row, col});
            int parentRow = parentX[row][col];
            int parentCol = parentY[row][col];
            row = parentRow;
            col = parentCol;
        }

        path.add(0, new int[]{startRow, startCol});
        return path;
    }

    public static void printMaze() {
        for (int[] row : maze) {
            for (int cell : row) {
                System.out.print(cell + " ");
            }
            System.out.println();
        }
    }

    public static void main(String[] args) {
        int[][] maze = {
                {0, 1, 0, 0, 0},
                {0, 0, 0, 1, 0},
                {1, 1, 0, 1, 0},
                {0, 0, 0, 1, 0},
                {0, 1, 0, 0, 0}
        };
        int startRow = 0;
        int startCol = 0;
        int goalRow = 4;
        int goalCol = 4;

        initializeMaze(maze, startRow, startCol, goalRow, goalCol);
        List<int[]> path = solveMaze();

        if (!path.isEmpty()) {
            System.out.println("Path from start to goal:");
            for (int[] cell : path) {
                System.out.println("(" + cell[0] + ", " + cell[1] + ")");
            }
        } else {
            System.out.println("No path found!");
        }
    }
}