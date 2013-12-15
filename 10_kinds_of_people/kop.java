import java.util.*;
import java.io.*;

public class kop {
    public static void main(String[] args) throws IOException {
        BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

        String[] setup;
        boolean first = true;
        while (true) {
            setup = br.readLine().split(" ");
            if (setup[0].equals("0") && setup[1].equals("0")) {
                break;
            }

            if (!first) {
                System.out.println();
            }

            int nRows = Integer.parseInt(setup[0]);
            int nCols = Integer.parseInt(setup[1]);

            char[][] map = new char[nRows][];

            for (int i = 0; i < nRows; i++) {
                map[i] = br.readLine().toCharArray();
            }

            DisjointSet d = createDS(map);

            int nQueries = Integer.parseInt(br.readLine());
            for (int i = 0; i < nQueries; i++) {
                String[] q = br.readLine().split(" ");
                int[] query = new int[q.length];
                for (int j = 0; j < query.length; j++) {
                    query[j] = Integer.parseInt(q[j]) - 1;
                }

                int p1 = query[0] * map[0].length + query[1];
                int p2 = query[2] * map[0].length + query[3];
                int s1 = d.findSet(p1);
                int s2 = d.findSet(p2);
                //System.out.println("set for " + p1 + " is " + s1);
                //System.out.println("set for " + p2 + " is " + s2);
                if (s1 == s2) {
                    if (map[query[0]][query[1]] == '1') {
                        System.out.println("decimal");
                    }
                    else {
                        System.out.println("binary");
                    }
                }
                else {
                    System.out.println("neither");
                }
            }
            first = false;
        }
    }

    public static DisjointSet createDS(char[][] map) {
        DisjointSet d = new DisjointSet(map.length * map[0].length);
        for (int i = 0; i < map.length; i++) {
            for (int j = 0; j < map[i].length; j++) {
                if (i-1 >= 0) {
                    if (map[i-1][j] == map[i][j]) {
                        d.union(i*map[0].length + j, (i-1)*map[0].length + j);
                    }
                }
                if (j-1 >= 0) {
                    if (map[i][j-1] == map[i][j]) {
                        d.union(i*map[0].length + j, i*map[0].length + (j-1));
                    }
                }
            }
        }

        return d;
    }
}

class DisjointSet {
    private class node {
        public node parent;
        public int rank;
        public int id;

        public node(node parent, int rank, int id) {
            this.parent = parent;
            this.rank = rank;
            this.id = id;
        }
    }

    public node[] children;

    public DisjointSet(int n) {
        children = new node[n];

        for (int i = 0; i < n; i++) {
            node nnode = new node(null, 0, i);
            nnode.parent = nnode;
            children[i] = nnode;
        }
    }

    /* Find set with path compression */
    public int findSet(int id) {
        node root = children[id];

        while (root.parent != root) {
            root = root.parent;
        }

        node current = children[id];
        while (current != root) {
            current.parent = root;
            current = current.parent;
        }

        return root.id;
    }

    /* Union by rank */
    public void union(int n1, int n2) {
        node s1 = children[findSet(n1)];
        node s2 = children[findSet(n2)];

        if (s1 == s2)
            return;

        if (s1.rank > s2.rank) {
            s2.parent = s1;
        }
        else {
            if (s1.rank == s2.rank) {
                s2.parent = s1;
                s1.rank++;
            }
            else {
                s1.parent = s2;
            }
        }
    }
}
