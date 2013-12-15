import java.io.*;
import java.util.*;

public class cfc {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static int numPaths = 0;

    public static void main(String[] args) throws IOException {
        int rows, cols;

        String line;
        //final long startTime = System.currentTimeMillis();
        while((line = br.readLine()) != null) {
            String[] dims = line.split("\\s+");
            rows = Integer.parseInt(dims[0]);
            cols = Integer.parseInt(dims[1]);

            if(rows == 0 && cols == 0) break;

            char[][] map = new char[rows][cols];
            for (int i = 0; i < rows; i++) {
                char[] l = br.readLine().toCharArray();
                for (int j = 0; j < l.length; j++)
                    map[i][j] = l[j];
            }

            //numPaths = 0;
            //pathsR(map, new boolean[rows][cols], new pair(rows-1, 0, null));
            //System.out.println(numPaths);
            //System.out.println(rows + " " + cols + ": " + numPaths);
            //System.out.println(paths(map));
            System.out.println(pathsDP(map));
        }
        //final long endTime = System.currentTimeMillis();
        //System.out.println("total time = " + (endTime-startTime));
    }

    /*
     * This method uses a DFS style approach. By keeping a path stack we are
     * able to reset the path up until the point when we added the next node
     * we want to start from, same way it works in the recursive DFS.
     *
     * Currently there are portions of code that compare memory addresses
     * instead of equality based on position in the grid. This is working for
     * now and will be left this way for a slight speed increase, but is
     * something to consider if it does not work on a later test case.
     */
    public static int paths(char[][] map) {
        boolean[][] visited = new boolean[map.length][map[0].length];

        // Being a 10x10 maximum size map we're guaranteed to never have more
        // than 100 elements in the arraydeque. Starting with this capacity
        // we never have to resize it, making all inserts and removes constant
        // time.
        ArrayDeque<pair> s = new ArrayDeque<>(100);
        ArrayDeque<pair> path = new ArrayDeque<>(100);

        // start in bottom left
        pair root = new pair(map.length-1, 0, null);
        s.offerLast(root);

        // Useful for checking the squares around the current one. Instead
        // of having 4 giant if statements or something we can just iterate
        // over these arrays. More elegent.
        // Exclude the case for going to the left as per the problem statement.
        int[] di = {-1, 0, 1};
        int[] dj = {0, 1, 0};

        int numPaths = 0;
        while(!s.isEmpty()) {
            pair curr = s.pollLast();

            if (visited[curr.i][curr.j]) {
                // Shouldn't ever be in this part of the code, but just a
                // safety measure.
                continue;
            }
            else if (curr.i == 0 && curr.j == map[0].length-1) {
                // I need to figure out a better way for this. This is a ridiculous line of code.
                // This is the part of the code that backtracks, "freeing" the portion of the
                // path from the end node back to the next node in the recursive DFS.
                pair prev;
                //while(path.peekLast() != null && s.peekLast() != null && !path.peekLast().equals(s.peekLast().prev)) {
                while(path.peekLast() != null && s.peekLast() != null && path.peekLast() != s.peekLast().prev) {
                    prev = path.pollLast();
                    visited[prev.i][prev.j] = false;
                }

                numPaths++;
                continue;
            }
            else {
                visited[curr.i][curr.j] = true;
                path.offerLast(curr);
            }

            boolean addedNode = false;
            for (int i = 0; i < di.length; i++) {
                int ni = curr.i + di[i];
                int nj = curr.j + dj[i];
                if (ni >= 0 && nj >= 0 && ni < map.length && nj < map[0].length
                        && !visited[ni][nj] && map[ni][nj] != '#') {
                    s.offerLast(new pair(ni, nj, curr));
                    addedNode = true;
                }
            }

            // This is necessary because if we hit a dead end in the DFS that
            // is not the end goal we still need to backtrack. Same code and
            // logic as above in the situation when you reach the target.
            if(!addedNode) {
                pair prev;
                //while(path.peekLast() != null && s.peekLast() != null && !path.peekLast().equals(s.peekLast().prev)) {
                while(path.peekLast() != null && s.peekLast() != null && path.peekLast() != s.peekLast().prev) {
                    prev = path.pollLast();
                    visited[prev.i][prev.j] = false;
                }
            }

        }

        return numPaths;
    }

    /*
     * Regular DFS, except once you are "done" with a node you set it's
     * location back to unvisited so it can be used again in the next path.
     */
    public static void pathsR(char[][] map, boolean[][] visited, pair root) {
        if(root.i == 0 && root.j == map[0].length-1) {
            numPaths++;
            return;
        }

        visited[root.i][root.j] = true;

        int[] di = {-1, 0, 1};
        int[] dj = {0, 1, 0};
        for (int i = 0; i < di.length; i++) {
            int ni = root.i + di[i];
            int nj = root.j + dj[i];
            if (ni >= 0 && nj >= 0 && ni < map.length && nj < map[0].length
                    && !visited[ni][nj] && map[ni][nj] != '#') {
                pathsR(map, visited, new pair(ni, nj, root));
            }
        }

        visited[root.i][root.j] = false;
    }

    /* Dynamic programming ftw!
     * Optimal substructure: For any map, the total number of paths for column
     * j is built upon the solution for the total number of paths for column
     * j-1.
     * Overlapping subproblems: To find column j we must recompute all of the
     * paths paths for columns 1..j-1. Then for column j+1 we would have to
     * recompute all of the paths for columns 1..j.
     *
     * With this in mind the approach is simple: Solve it column by column.
     * Initialization is by finding all of the reachable paths in column 0.
     * Practically speaking this means starting from the bottom left go up
     * until you hit a '#'.
     * From then on we assume that we have the optimal solution for column j-1.
     * Expansion works as follows: for every valid spot in column j-1, check if
     * the spot to the right is valid. If it is then you can reach it in as
     * many paths as you reached j-1 in, so you add that number of paths to
     * location i,j. Then check if you can go up or down, using the same logic.
     * For every reachable spot in the column add the number of paths that can
     * get to the starting location in column j-1.
     *
     * The solution will then be placed in the top right of the dp matrix.
     *
     * The complexity of this solution is O(m^2 * n)
     * Worst case: The board has no invalid locations (i.e. its all dots).
     * Then starting at column 0 you would go element by element [0][m]
     * up to [0][0], and for each of those elements you would traverse the
     * entire column to the right, which is O(m), therefore for each column we
     * are doing O(m) * O(m) work, or O(m^2). We will perform this operation
     * n-1 times, O(n). Therefore overall it is O(m^2 * n).
     *
     * Note: For a square map it is O(n^3).
     */
    public static int pathsDP(char[][] map) {
        int[][] dp = new int[map.length][map[0].length];
        for (int i = dp.length-1; i >= 0 && map[i][0] != '#'; i--)
            dp[i][0] = 1;

        //for (int i = 0; i < dp.length; i++)
            //System.out.println(Arrays.toString(dp[i]));
        //System.out.println();

        for (int j = 0; j < map[0].length-1; j++) {
            for (int i = 0; i < map.length; i++) {
                if (map[i][j+1] == '#')
                    continue;

                // check right and up
                for (int k = i; k >= 0; k--) {
                    if (map[k][j+1] == '#')
                        break;

                    dp[k][j+1] += dp[i][j];
                }
                // check right and down
                for (int k = i+1; k < map.length; k++) {
                    if (map[k][j+1] == '#')
                        break;

                    dp[k][j+1] += dp[i][j];
                }

            }
        }

        //for (int i = 0; i < dp.length; i++)
            //System.out.println(Arrays.toString(dp[i]));

        //System.out.println("\n-----------------\n");

        return dp[0][map[0].length-1];
    }
}

class pair {
    public int i, j;
    public pair prev;

    public pair(int i, int j, pair prev) {
        this.i = i;
        this.j = j;
        this.prev = prev;
    }

    public boolean equals(Object o) {
        if (!(o instanceof pair))
            return false;

        pair p = (pair)o;

        return this.i == p.i && this.j == p.j;
    }

    public String toString() {
        return i + ", " + j;
    }
}
