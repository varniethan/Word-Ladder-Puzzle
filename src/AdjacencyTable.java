public class AdjacencyTable {

    private GraphNode[] table;

    public int calculateHash(String s) {
        int hashCode = s.hashCode();
        int hashValue;
        hashValue = (hashCode & 0x7fffffff) % this.table.length;
        return hashValue;
    }

    public boolean checkCollisions(int hashValue, String s) {
        boolean isCollision = false;
        if (this.table[hashValue] != null) {
            if (!this.table[hashValue].label.equals(s)) {
                isCollision = true;
            }
        }
        return isCollision;
    }

   //resolve collisions with open addressing with linear probing
   public int probe(int hashValue) {
       int probeIndex = hashValue;
       while (this.table[probeIndex] != null) {
           probeIndex = (probeIndex + 1) % this.table.length;
       }
       return probeIndex;
   }

    /*
    * Takes an array of Strings
    * Creates a fresh hash table containing one graphNode object labelled with each string in the array
    * All strings are distinct
    * Initially each node has no neighbours
    * Breath-first search
    * Static method -> Creates a graph corresponding to the word-ladder game
    * */
    public AdjacencyTable(String[] nodes) {
        int capacity = 2*nodes.length;
        this.table = new GraphNode[capacity];
        for (String node : nodes) {
            int hashValue = calculateHash(node);
            if (this.checkCollisions(hashValue, node)) {
                int probeIndex = probe(hashValue);
                this.table[probeIndex] = new GraphNode(node);
            }
            else {
                this.table[hashValue] = new GraphNode(node);
            }
        }
    }

    public GraphNode[] getTable() {
        return table;
    }

    // search the hash table for a node with label s
    public boolean find(String s) {
        boolean isFound = false;
        int hashValue = calculateHash(s);
        if (this.table[hashValue] != null) {
            if (this.table[hashValue].label.equals(s)) {
                isFound = true;
            }
            else {
                int probeIndex = this.probe(hashValue);
                if (this.table[probeIndex] != null) {
                    if (this.table[probeIndex].label.equals(s)) {
                        isFound = true;
                    }
                }
            }
        }
        return isFound;
    }

    //returns the GraphNode object labelled with s or null pointer if s does not exist in the table
    public GraphNode get(String s) {
        GraphNode node = null;
        int hashValue = calculateHash(s);
        if (this.table[hashValue] != null) {
            if (this.table[hashValue].label.equals(s)) {
                node = this.table[hashValue];
            }
            else {
                int probeIndex = this.probe(hashValue);
                if (this.table[probeIndex] != null) {
                    if (this.table[probeIndex].label.equals(s)) {
                        node = this.table[probeIndex];
                    }
                }
            }
        }
        return node;
    }

    public String getPath(String s, String t) {
	// add your code here
	return ""; // delete this line
    }

    public boolean existsPath(String s, String t) {
        GraphNode start = this.get(s);
        GraphNode end = this.get(t);
        boolean existsPath = false;
        if (start != null && end != null) {
            existsPath = false;
        }

        if (start.equals(end)) {
            existsPath = true;
        }



    }

    public int pathLength(String s, String t) {
	// add your code here
	return 111; // delete this line
    }

    public static AdjacencyTable weaver() {
	// add your code here
	return null; // delete this line
    }
}
