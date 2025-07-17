public class Main {
    private static final int GRID_SIZE = 9;

    public static void main(String[] args) {
        int [][] board = {
                {0, 0, 5, 3, 0, 0, 0, 0, 0},
                {8, 0, 0, 0, 0, 0, 0, 2, 0},
                {0, 7, 0, 0, 1, 0, 5, 0, 0},
                {4, 0, 0, 0, 0, 5, 3, 0, 0},
                {0, 1, 0, 0, 7, 0, 0, 0, 6},
                {0, 0, 3, 2, 0, 0, 0, 8, 0},
                {0, 6, 0, 5, 0, 0, 0, 0, 9},
                {0, 0, 4, 0, 0, 0, 0, 3, 0},
                {0, 0, 0, 0, 0, 9, 7, 0, 0}
        };

        long startingTimeMillis = System.currentTimeMillis();

        if (!solveBoard(board)){
            System.out.println(formatBoard(board));
            System.out.println("\n[SERVER] Unsolvable board...");
            return;
        }

        long endingTimeMillis = System.currentTimeMillis();

        System.out.println(formatBoard(board));
        System.out.println("\n[SUDOKU DONE] Board solved in " + ((endingTimeMillis - startingTimeMillis) / 1000.0) + " seconds.");
    }

    private static String formatBoard(int [][] board){
        StringBuilder sb = new StringBuilder();
        String divBox = "─".repeat(9);

        // BOARD
        sb.append("┌" + divBox + ("┬" + divBox).repeat(2) + "┐" + "\n");

        // ROW
        for(int row = 0; row <  GRID_SIZE; row++){
            if (row % 3 == 0 && row != 0){
                sb.append("├" + divBox + ("┼" + divBox).repeat(2) + "┤" + "\n");
            }

            sb.append("│ ");

            // COLUMN
            for (int column = 0; column < GRID_SIZE; column++){
                if (column % 3 == 0 && column != 0){
                    sb.append(" │ ");
                }

                sb.append((board[row][column] != 0 ? board[row][column] : ".") + (column % 3 < 2 ? "  " : ""));
            }

            sb.append(" │" + "\n");
        }

        sb.append("└" + divBox + ("┴" + divBox).repeat(2) + "┘");
        return sb.toString();
    }

    private static boolean isNumberInColumn(int [][] grid, int number, int column){
        for (int i = 0; i < GRID_SIZE; i++){
            if (grid[i][column] == number){
                return true;
            }
        }

        return false;
    }

    private static boolean isNumberInRow(int [][] grid, int number, int row){
        for (int i = 0; i < GRID_SIZE; i++){
            if (grid[row][i] == number){
                return true;
            }
        }

        return false;
    }

    private static boolean isNumberInBox(int [][] grid, int number, int row, int column){
        int localRowBox = row - row % 3;
        int localColumnBox = column - column % 3;

        for(int i = localRowBox; i < localRowBox + 3; i++){
            for(int j = localColumnBox; j < localColumnBox + 3; j++){
                if (grid[i][j] == number){
                    return true;
                }
            }
        }

        return false;
    }

    private static boolean isValidPlacement(int [][] grid, int number, int row, int column){
        return !(
                isNumberInRow(grid, number, row) ||
                isNumberInColumn(grid, number, column) ||
                isNumberInBox(grid, number, row, column)
        );
    }

    private static boolean solveBoard(int [][] board){
        for(int row = 0; row < GRID_SIZE; row++){
            for(int column = 0; column < GRID_SIZE; column++){

                if (board[row][column] == 0){
                    for (int numberToTry = 1; numberToTry <= GRID_SIZE; numberToTry++){

                        if (isValidPlacement(board, numberToTry, row, column)){
                            board[row][column] = numberToTry;

                            if (solveBoard(board)){
                                // DEFAUTL RECURSION CASE
                                return true;
                            }
                            else {
                                board[row][column] = 0;
                            }
                        }
                    }
                    // IMPOSSIBLE FLAG
                    return false;
                }
            }
        }
        // RESOLVED FLAG
        return true;
    }
}
