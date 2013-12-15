import java.io.*;
import java.util.*;

public class umst {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static int testCase = 0;
    private static int n = -1;  // number nodes in graph
    private static int m = -1;  // number of edges in graph

    public static void main(String[] args) throws IOException {
        ArrayList<Edge> g = readGraph();

        while (g != null) {
            testCase++;
            ModifiedKruskals(g);
            g = readGraph();
        }
    }

    /**
     * Given an edge list sorted in ascending order perform a modified
     * version of Kruskals Minimum Spanning Tree algorithm which will
     * determine if there exists an MST, its weight if it does, and if
     * it is unique or not.
     */
    public static void ModifiedKruskals(ArrayList<Edge> g) {
        // For Union-Find
        DisjointSet dj = new DisjointSet(n);

        // The key insight in this algorithm is that while examining all
        // edges of an equal weight, say 3, if we are about to add an edge
        // that creates a cycle then that means there is more than one
        // unique minimum spanning tree. This is true since we could swap
        // one of the edges in the would be cycle for the new edge.
        int prevWeight = -1;    // weight of the previously added edge
        int treeEdges = 0;      // keeps track of how many edges are in the tree
        int treeWeight = 0;     // total weight of the MST
        boolean unique = true;  // assume it is unique until proven otherwise
        for (int i = 0; i < g.size(); i++) {
            Edge e = g.get(i);
            int setU = dj.find(e.u);
            int setV = dj.find(e.v);

            if (setU == setV && e.weight == prevWeight) {
                unique = false;
            }
            else if (setU != setV) {
                dj.union(e.u, e.v);
                treeEdges++;
                prevWeight = e.weight;
                treeWeight += e.weight;
            }
        }

        // We did find an MST so output it's weight and if it was unique
        if (dj.nSets() == 1) {
            if (unique) {
                System.out.format("Graph %d: Unique MST with a weight of %d\n",
                        testCase, treeWeight);
            }
            else {
                System.out.format("Graph %d: Non-Unique MST with a weight of %d\n",
                        testCase, treeWeight);
            }
        }
        else {
            System.out.format("Graph %d: No MST\n", testCase);
        }
    }

    /**
     * Read the input graph and return an array list of undirected edges sorted
     * in ascending order of edge weight.
     */
    private static ArrayList<Edge> readGraph() throws IOException {
        String[] line = br.readLine().split("\\s");

        n = Integer.parseInt(line[0]);
        m = Integer.parseInt(line[1]);

        if (n == 0 && m == 0) return null;

        ArrayList<Edge> g = new ArrayList<>();

        for (int i = 0; i < m; i++) {
            line = br.readLine().split("\\s");
            int u = Integer.parseInt(line[0]);
            int v = Integer.parseInt(line[1]);
            int weight = Integer.parseInt(line[2]);

            g.add(new Edge(u, v, weight));
        }

        br.readLine();  // Eat the extra line
        Collections.sort(g);
        return g;
    }

    /**
     * A weighted, undirected edge for a graph
     */
    private static class Edge implements Comparable<Edge> {
        public int u, v, weight;

        public Edge(int u, int v, int weight) {
            this.u = u;
            this.v = v;
            this.weight = weight;
        }

        public int compareTo(Edge e) {
            if (this.weight < e.weight) return -1;
            else if (this.weight > e.weight) return 1;
            else return 0;
        }

        public String toString() {
            return "(" + u + ", " + v + ", " + weight + ")";
        }
    }

}
