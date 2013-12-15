import java.io.*;
import java.util.*;

public class umst {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
    private static int INF = 9999999;

    public static void main(String[] args) throws IOException {

        int test = 0;
        while (true) {
            test++;
            ArrayList<ArrayList<Edge>> g = readGraph();
            if (g == null) break;

            Edge[] cheapest = new Edge[g.size()];
            Arrays.fill(cheapest, null);
            boolean[] visited = new boolean[g.size()]; // keep track of whether it has 2 edges or 1
            Arrays.fill(visited, false);

            /* Debug
            System.out.println("Test case " + test);
            for (int i = 0; i < g.size(); i++) {
                ArrayList<Edge> temp = g.get(i);
                System.out.print("Vertex" + i + ": ");
                for (int j = 0; j < temp.size(); j++)
                    System.out.print(temp.get(j) + " ");
                System.out.println();
            }
            */

            // Start the algorithm
            int treeWeight = 0;
            cheapest[0] = null;
            visited[0] = true;
            for (Edge e : g.get(0)) {
                cheapest[e.dest] = e;
            }

            boolean invalid = false;
            int verticesOutOfTree = g.size()-1;
            for (int i = 0; i < g.size()-1; i++) {
                int v = INF;
                // Find the cheapest edge to add
                for (int j = 0; j < cheapest.length; j++) {
                    if (cheapest[j] != null && !visited[j] && cheapest[j].weight < v) {
                        v = j;
                    }
                }

                if (v == INF) {
                    System.out.println("Graph " + test + ": No minimum spanning tree");
                    invalid = true;
                    break;
                }

                visited[v] = true;
                treeWeight += cheapest[v].weight;

                // Update the cheapest paths for every neighbor of the added vertex
                for (Edge e : g.get(v)) {
                    if (cheapest[e.dest] == null && !visited[e.dest]) {
                        cheapest[e.dest] = e;
                    }
                    else if (cheapest[e.dest] == null && visited[e.dest]) {
                        continue;
                    }
                    else if (cheapest[e.dest] != null && e.weight < cheapest[e.dest].weight && !visited[e.dest]) {
                        cheapest[e.dest] = e;
                    }
                }
            }

            /*
            if (!invalid) {
                System.out.println("Graph " + test + ": " + treeWeight);
            }
            */

            // Output the edges in the tree
            /*
            if (!invalid) {
                for (int i = 0; i < cheapest.length; i++) {
                    if (cheapest[i] != null) {
                        System.out.print(cheapest[i] + " ");
                    }
                }
                System.out.println();
            }
            */

            // Try to find a single edge replacement such that the spanning tree is of equal weight.
            for (int i = 1; i < g.size(); i++) {
                // Try to replace a single edge in the tree with another edge
                HashSet<Integer> cc1 = new HashSet<>();
                HashSet<Integer> cc2 = new HashSet<>();

                Edge e = cheapest[i];

                // Go through the component for the source of the edge
                int u = e.src;
                cc1.add(u);
                while (true) {
                    
                }

                // Go through the component for the destination of the edge
                u = e.dest;

            }

            br.readLine();
        }
    }

    private static ArrayList<ArrayList<Edge>> readGraph() throws IOException {
        String[] line = br.readLine().split("\\s");

        int n = Integer.parseInt(line[0]);
        int m = Integer.parseInt(line[1]);

        if (n == 0 && m == 0) return null;

        ArrayList<ArrayList<Edge>> g = new ArrayList<>();
        for (int i = 0; i < n; i++) g.add(new ArrayList<Edge>());

        for (int i = 0; i < m; i++) {
            line = br.readLine().split("\\s");
            int u = Integer.parseInt(line[0]);
            int v = Integer.parseInt(line[1]);
            int weight = Integer.parseInt(line[2]);

            g.get(u).add(new Edge(u, v, weight));
            g.get(v).add(new Edge(v, u, weight));
        }

        return g;
    }

    private static class Edge {
        public int src, dest, weight;

        public Edge(int src, int dest, int weight) {
            this.src = src;
            this.dest = dest;
            this.weight = weight;
        }

        public String toString() {
            return "(" + src + ", " + dest + ", " + weight + ")";
        }
    }
}
