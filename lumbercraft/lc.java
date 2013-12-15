import java.util.*;
import java.io.*;

public class lc {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        int n, r, c;

        String[] line = br.readLine().trim().split("\\s");
        n = Integer.parseInt(line[0]);
        r = Integer.parseInt(line[1]);
        c = Integer.parseInt(line[2]);

        while (n != 0 || r != 0 || c != 0) {
            char[][] board = new char[r][];

            ArrayList<Player> players = new ArrayList<>();

            for (int i = 0; i < board.length; i++) {
                board[i] = br.readLine().toCharArray();
                //System.out.println(Arrays.toString(board[i]));

                for (int j = 0; j < board[i].length; j++) {
                    if (board[i][j] >= 'A' && board[i][j]<= 'Z') {
                        players.add(new Player(board[i][j], i, j));
                    }
                }
            }

            for (int i = 0; i < players.size(); i++) {
                //System.out.println(players.get(i));
            }

            // Go through the 2D map one time and create a list, for each player,
            // in sorted order, the trees he would want.
            for (int i = 0; i < board.length; i++) {
                for (int j = 0; j < board[i].length; j++) {
                    if (board[i][j] < 'A' || board[i][j] > 'Z') {
                        int distance = 0;
                        for (int k = 0; k < players.size(); k++) {
                            Player temp = players.get(k);
                            int x = (i - temp.row) * (i - temp.row);
                            int y = (j - temp.col) * (j - temp.col);
                            distance = x + y;
                            temp.addTree(new Location(i, j, distance));
                        }
                    }
                }
            }

            for (int i = 0; i < players.size(); i++) {
                players.get(i).sortTrees();
                //System.out.println("" + players.get(i).trees.size());
            }

            for (int i = 0; i < n; i++) {
                // play round
                for (int j = 0; j < players.size(); j++) {
                    Player temp = players.get(j);
                    Location tree = temp.nextTree(board);
                }

                for (int j = 0; j < players.size(); j++) {
                    Player temp = players.get(j);
                    temp.addScore(board);
                }

                for (int j = 0; j < players.size(); j++) {
                    Player temp = players.get(j);
                    Location ttree = temp.getTree();
                    board[ttree.row][ttree.col] = '.';
                }
            }

            for (int i = 0; i < board.length; i++) {
                System.out.println(new String(board[i]));
            }

            Collections.sort(players);
            for (int i = 0; i < players.size(); i++) {
                Player temp = players.get(i);
                System.out.format("%s %.2f\n", temp.name, temp.lumber);
            }
            System.out.println();

            line = br.readLine().trim().split("\\s");
            n = Integer.parseInt(line[0]);
            r = Integer.parseInt(line[1]);
            c = Integer.parseInt(line[2]);
        }
    }
}

class Player implements Comparable<Player> {
    public char name;
    public int row, col, treeIndex = 0;
    public double lumber = 0;
    ArrayList<Location> trees = new ArrayList<>();
    public Location current = null;

    public Player(char name, int row, int col) {
        this.name = name;
        this.row = row;
        this.col = col;
    }

    public void addTree(Location l) {
        trees.add(l);
    }

    public void sortTrees() {
        Collections.sort(trees);
    }

    public Location nextTree(char[][] board) {
        Location next = null;
        while (next == null) {
            int tempi = trees.get(treeIndex).row;
            int tempj = trees.get(treeIndex).col;

            if ((board[tempi][tempj] < 'A' || board[tempi][tempj] > 'Z') && board[tempi][tempj] != '.') {
                next = trees.get(treeIndex);

                if (board[tempi][tempj] < '!') {
                    board[tempi][tempj]++;
                }
                else {
                    board[tempi][tempj] = 1;
                }
            }
            treeIndex++;
        }

        this.current = next;
        return this.current;
    }

    public Location getTree() {
        return this.current;
    }

    public void addScore(char[][] board) {
        int i = current.row;
        int j = current.col;
        //System.out.println("Player " + this.name + " adding " + (int)board[i][j]);
        this.lumber += (1 / (double) board[i][j]);
    }

    public int compareTo(Player p) {
        if (this.name < p.name)
            return -1;
        else if (this.name > p.name)
            return 1;
        else
            return 0;
    }

    public String toString() {
        return "Name: " + name + "\trow: " + row + "\tcol: " + col;
    }
}

class Location implements Comparable<Location> {
    public int row, col, distance;
    public Location(int row, int col, int distance) {
        this.row = row;
        this.col = col;
        this.distance = distance;
    }

    public int compareTo(Location other) {
        if (this.distance < other.distance)
            return -1;
        else if (this.distance > other.distance)
            return 1;
        else {
            if (this.col > other.col)
                return -1;
            else if (this.col < other.col)
                return 1;
            else {
                if (this.row > other.row)
                    return -1;
                else if (this.row < other.row)
                    return 1;
                else
                    return 0; // should never happen
            }
        }
    }
}
