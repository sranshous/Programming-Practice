import java.util.Arrays;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.PriorityQueue;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;

/* Given a list of who is in what clubs together, minimize the risk in passing
 * a note from person A to person B. Risk is a function of the size of the
 * club two people are in together, and how many intermediate persons are used
 * to pass the note to its final destination.
 */

public class passing_secrets {
    // map the names to vertex IDs using a hashmap
    // as names are read check if they are in the hashmap, if not add them with
    // vid + 1
    private static HashMap<String, Integer> vmap = new HashMap<String, Integer>();
    private static HashMap<Integer, String> nmap = new HashMap<Integer, String>();  // vid to name
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        String line = null;

        while ((line = br.readLine()) != null) {
            vmap.clear(); nmap.clear(); // start fresh with global variables
            ArrayList<ArrayList<Edge>> al = new ArrayList<ArrayList<Edge>>();   // adjacency list
            int vid = 0;

            // find source/dest, doesn't matter which is which its an undirected graph
            String[] splitLine = line.trim().split("\\s");
            int source = -1, dest = -1;

            if (vmap.containsKey(splitLine[0])) {
                source = vmap.get(splitLine[0]);
            }
            else {
                vmap.put(splitLine[0], vid);
                nmap.put(vid++, splitLine[0]);
                source = vmap.get(splitLine[0]);
                al.add(new ArrayList<Edge>());
            }

            if (vmap.containsKey(splitLine[1])) {
                dest = vmap.get(splitLine[1]);
            }
            else {
                vmap.put(splitLine[1], vid);
                nmap.put(vid++, splitLine[1]);
                dest = vmap.get(splitLine[1]);
                al.add(new ArrayList<Edge>());
            }

            int numClubs = Integer.parseInt(br.readLine().trim());

            for (int i = 0; i < numClubs; i++) {
                ArrayList<Integer> members = new ArrayList<Integer>();

                String[] mem = br.readLine().trim().split("\\s");

                // Get all the members in the club before we add the edges
                for (int j = 0; j < mem.length; j++) {
                    int mid = -1;
                    if (vmap.containsKey(mem[j])) {
                        mid = vmap.get(mem[j]);
                    }
                    else {
                        vmap.put(mem[j], vid);
                        nmap.put(vid++, mem[j]);
                        mid = vmap.get(mem[j]);
                        al.add(new ArrayList<Edge>());
                    }

                    members.add(mid);
                }

                // add the edges for each member, checking if an edge already
                // exists between two people and if it does just change the
                // weight. No parallel edges.
                for (int j = 0; j < members.size(); j++) {
                    ArrayList<Edge> elist = al.get(members.get(j));
                    // pairwise edge addition
                    for (int k = 0; k < members.size(); k++) {
                        if (j == k) continue;   // self edge

                        // check for already present edge
                        boolean present = false;
                        for (int l = 0; l < elist.size(); l++) {
                            if (elist.get(l).dest == members.get(k)) {
                                // take the minimum weight edge
                                Edge e = elist.get(l);
                                e.weight = e.weight < (members.size()-2) ? e.weight : (members.size()-2);
                                present = true;
                            }
                        }
                        if (!present) {
                            elist.add(new Edge(members.get(k), members.size()-2));
                        }
                    }
                }
            }

            // Do dijkstras to get the lowest risk path
            PriorityQueue<HeapNode> h = new PriorityQueue<HeapNode>();
            h.offer(new HeapNode(source, 0, null));    // source node
            HeapNode n = null;
            boolean success = false;
            boolean[] visited = new boolean[al.size()];
            Arrays.fill(visited, false);

            while (h.size() != 0) {
                n = h.poll();

                if (visited[n.vid]) continue;
                else visited[n.vid] = true;

                if (n.vid == dest) {
                    success = true;
                    break;
                }

                for (Edge e : al.get(n.vid)) {
                    int w = e.weight + n.weight;    // new weight
                    h.offer(new HeapNode(e.dest, w, n));
                }
            }

            if (success) {
                int risk = n.weight; // base risk, need to add the intermediate hops though

                // reverse the path
                ArrayList<String> path = new ArrayList<String>();
                path.add(nmap.get(n.vid));
                while (n.prev != null) {
                    n = n.prev;
                    risk++; // another hop
                    path.add(nmap.get(n.vid));
                }
                risk--; // we counted a risk for the source, which is not correct
                System.out.print(risk + " ");
                for (int i = path.size()-1; i >= 0; i--) {
                    if (i == 0) {
                        System.out.println(path.get(i));
                    }
                    else {
                        System.out.print(path.get(i) + " ");
                    }
                }
            }
            else {
                System.out.println("impossible");
            }
        }
    }

    private static class HeapNode implements Comparable<HeapNode> {
        public int vid;     // which node is this
        public int weight;  // cost to get there
        public HeapNode prev;   // how did I get here?

        public HeapNode(int vid, int weight, HeapNode prev) {
            this.vid = vid;
            this.weight = weight;
            this.prev = prev;
        }

        public int compareTo(HeapNode other) {
            if (this.weight < other.weight) return -1;
            else if (this.weight > other.weight) return 1;
            else return 0;
        }
    }

    private static class Edge {
        public int dest;    // who is the other end of this edge
                            // edges will be undirected
        public int weight;  // "risk"

        public Edge(int dest, int weight) {
            this.dest = dest;
            this.weight = weight;
        }
    }
}
