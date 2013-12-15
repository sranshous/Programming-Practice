import java.io.*;
import java.util.*;

public class gb {
    public static void main(String... args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n = Integer.parseInt(br.readLine().trim());

        while(n != 0) {
            HashMap<Integer, Integer> board = new HashMap<>();

            // Read all the lines of the board
            for (int i = 0; i < n; i++) {
                String[] line = br.readLine().trim().split("\\s");
                int[] boardRow = new int[line.length];

                for (int j = 0; j < boardRow.length; j++) {
                    try {
                        boardRow[j] = Integer.parseInt(line[j]);
                        board.put(boardRow[j], (i*n) + j);
                        //System.out.println("key = " + boardRow[j] + "\tvalue = " + board.get(boardRow[j]));
                    }
                    catch(Exception e) {/* Found the dash */}
                }
                //System.out.println(Arrays.toString(boardRow));
            }

            String[] line = br.readLine().trim().split("\\s");
            ArrayList<Integer> guesses = new ArrayList<>();
            while (true) {
                int[] boardRow = new int[line.length];
                for (int i = 0; i < boardRow.length; i++) {
                    boardRow[i] = Integer.parseInt(line[i]);
                    guesses.add(boardRow[i]);
                }
                if (boardRow[boardRow.length-1] == 0) break;

                line = br.readLine().trim().split("\\s");
            }
            //System.out.println(Arrays.toString(boardRow));

            int[] rows = new int[n];
            int[] cols = new int[n];
            Arrays.fill(rows, n); Arrays.fill(cols, n);
            rows[n/2]--; cols[n/2]--;
            int ldiag = n-1;
            int rdiag = n-1;

            boolean winner = false;
            int calls = 0;
            for (int i = 0; i < guesses.size()-1; i++) {
                calls++;
                //System.out.println("key = " + boardRow[i] + "\tvalue = " + board.get(boardRow[i]));
                if (!board.containsKey(guesses.get(i))) continue;
                int linearIndex = board.get(guesses.get(i));
                int row = linearIndex / n;
                int col = linearIndex % n;
                rows[row]--;
                if (rows[row] == 0) winner = true;

                cols[col]--;
                if (cols[col] == 0) winner = true;

                if (row == col) {
                    ldiag--;
                    if (ldiag == 0) winner = true;
                }

                if ((row+col) == (n-1)) {
                    rdiag--;
                    if (rdiag == 0) winner = true;
                }

                if (winner) break;
            }

            if (winner) {
                System.out.println("Winner after " + calls + " calls");
            }
            else {
                System.out.println("Loser");
            }

            n = Integer.parseInt(br.readLine().trim());
        }
    }
}
