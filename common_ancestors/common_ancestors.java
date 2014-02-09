import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.ArrayDeque;
import java.util.HashMap;

/* Given the pre-order and post-order traversals of a tree, be able to answer
 * lowest common ancestor queries.
 */

public class common_ancestors {
    private static BufferedReader br = new BufferedReader(new InputStreamReader(System.in));

    public static void main(String[] args) throws IOException {
        int numNodes = Integer.parseInt(br.readLine().trim());

        while (numNodes != 0) {
            String[] pre  = br.readLine().trim().split("\\s");
            String[] post = br.readLine().trim().split("\\s");

            // map from name to pre and post indexes
            HashMap<String, Integer[]> nmap = new HashMap<String, Integer[]>();
            for (int i = 0; i < pre.length; i++) {
                Integer[] idx = new Integer[2];     // index 0 is pre 1 is post

                idx[0] = i;
                for (int j = 0; j < post.length; j++) {
                    if (post[j].equals(pre[i])) {
                        idx[1] = j;
                        break;
                    }
                }

                nmap.put(pre[i], idx);
            }

            // name to the name of the parent, essentially a parent pointer
            HashMap<String, String> parents = new HashMap<String, String>();

            // Used to find the first _valid_ vertex which is the parent.
            boolean[] prevalid = new boolean[pre.length];
            Arrays.fill(prevalid, true);

            // -1 because the last one is the root of the tree, add that manually
            for (int i = 0; i < post.length-1; i++) {
                int pos = nmap.get(post[i])[0];     // position in the pre-order array
                for (int j = pos-1; j >= 0; j--) {
                    // this must be the parent then
                    if (prevalid[j]) {
                        parents.put(post[i], pre[j]);
                        prevalid[pos] = false;
                        break;
                    }
                }
            }

            int numQueries = Integer.parseInt(br.readLine().trim());
            for (int i = 0; i < numQueries; i++) {
                String[] qnodes = br.readLine().trim().split("\\s");

                // build each path back to the root, then find the lowest common person
                ArrayList<String> q0path = new ArrayList<String>();
                ArrayList<String> q1path = new ArrayList<String>();

                String node = qnodes[0];
                q0path.add(node);   // a node is an ancestor to itself
                while (parents.get(node) != null) {
                    String parent = parents.get(node);
                    q0path.add(parent);
                    node = parent;
                }

                node = qnodes[1];
                q1path.add(node);   // a node is an ancestor to itself
                while (parents.get(node) != null) {
                    String parent = parents.get(node);
                    q1path.add(parent);
                    node = parent;
                }

                int p0len = q0path.size()-1;
                int p1len = q1path.size()-1;
                boolean output = false;
                while (q0path.get(p0len).equals(q1path.get(p1len))) {
                    if (p0len == 0 || p1len == 0) {
                        System.out.println(q0path.get(p0len));
                        output = true;
                        break;
                    }
                    else {
                        p0len--;
                        p1len--;
                    }
                }
                if (!output) {
                    System.out.println(q0path.get(p0len+1));
                }
            }

            System.out.println();

            numNodes = Integer.parseInt(br.readLine().trim());
        }
    }
}
