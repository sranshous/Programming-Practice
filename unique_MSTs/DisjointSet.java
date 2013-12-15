public class DisjointSet {
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
    private int nSets = 0;

    public DisjointSet(int n) {
        children = new node[n];
        nSets = n;

        for (int i = 0; i < n; i++) {
            node nnode = new node(null, 0, i);
            nnode.parent = nnode;
            children[i] = nnode;
        }
    }

    /* Find set with path compression */
    public int find(int id) {
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
    public boolean union(int n1, int n2) {
        node s1 = children[find(n1)];
        node s2 = children[find(n2)];

        if (s1 == s2)
            return false;

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

        nSets--;
        return true;
    }

    public int nSets() {
        return nSets;
    }
}
