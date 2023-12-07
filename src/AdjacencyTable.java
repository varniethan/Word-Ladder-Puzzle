import java.util.*;

public class AdjacencyTable {

    private GraphNode[] table;

    /*
     * Takes an array of Strings
     * Creates a fresh hash table containing one graphNode object labelled with each string in the array
     * All strings are distinct
     * Initially each node has no neighbours
     * Breath-first search
     * Static method -> Creates a graph corresponding to the word-ladder game
     * */
    public AdjacencyTable(String[] nodes) {
        int capacity = 2 * nodes.length;
        this.table = new GraphNode[capacity];
        for (String node : nodes) {
            int hashValue = calculateHash(node);
            if (this.checkCollisions(hashValue, node)) {
                int probeIndex = probe(hashValue);
                if (this.table[probeIndex] == null) {
                    this.table[probeIndex] = new GraphNode(node);
                }
            } else {
                this.table[hashValue] = new GraphNode(node);
            }
        }
    }

    public GraphNode[] getTable() {
        return table;
    }

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


    // search the hash table for a node with label s
    public boolean find(String s) {
        return this.get(s) != null;
    }

    //returns the GraphNode object labelled with s or null pointer if s does not exist in the table
    public GraphNode get(String s) {
        GraphNode node = null;
        int hashValue = calculateHash(s);
        //if the node is not null and the label is equal to s
        if (this.table[hashValue] != null)
        {
            if (this.table[hashValue].label.equals(s))
            {
                node = this.table[hashValue];
            }
            else
            {
                while (this.table[hashValue] != null)
                {
                    hashValue = (hashValue + 1) % this.table.length;
                    if (this.table[hashValue] != null)
                    {
                        if (this.table[hashValue].label.equals(s))
                        {
                            node = this.table[hashValue];
                        }
                    }
                    else
                    {
                        break;
                    }
                }
            }
        }
        return node;
    }

    public String getPath(String s, String t) {
        GraphNode start = this.get(s);
        GraphNode end = this.get(t);
        if (start == null || end == null) {
            return "There is no path from " + s + " to " + t + ".";
        }

        if (start.equals(end)) {
            return s;
        }

        Queue<GraphNode> queue = new LinkedList<>();
        ArrayList<GraphNode> visited = new ArrayList<>();
        Map<GraphNode, String> pathMap = new HashMap<>();

        queue.add(start);
        visited.add(start);
        pathMap.put(start, s);

        while (!queue.isEmpty()) {
            GraphNode currentNode = queue.remove();
            for (int i = 0; i < currentNode.neighbours.length; i++) {
                if (currentNode.neighbours[i] == null) {
                    break;
                }
                if (!visited.contains(this.get(currentNode.neighbours[i])) && currentNode.neighbours[i] != null) {
                    GraphNode neighbour = this.get(currentNode.neighbours[i]);
                    pathMap.put(neighbour, pathMap.get(currentNode) + "-" + neighbour.label);
                    if (neighbour.equals(end)) {
                        return pathMap.get(neighbour); //path found
                    } else {
                        queue.add(neighbour);
                        visited.add(neighbour);
                    }
                }
            }
        }
        return "There is no path from " + s + " to " + t;
    }

    public boolean existsPath(String s, String t) {
        String path = this.getPath( s, t);
        return !path.equals("There is no path from " + s + " to " + t);
    }

    public int pathLength(String s, String t) {
        String path = this.getPath(s, t);
        if (path.equals("There is no path from " + s + " to " + t)) {
            return 0;
        } else {
            return path.split("-").length;
        }
    }

    public static List<String> generateNeighbours(String word, String[] words) {
        List<String> neighbours = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            char[] wordArray = word.toCharArray();
            for (char c = 'a'; c <= 'z'; c++) {
                wordArray[i] = c;
                String newWord = new String(wordArray);
                if (isValidWord(newWord, words)) {
                    neighbours.add(newWord);
                }
            }
        }
        return neighbours;
    }

    public static boolean isValidWord(String word, String[] words) {
        return Arrays.asList(words).contains(word);
    }

    public static AdjacencyTable weaver() {
        String[] words = WeaverWords.words;
        AdjacencyTable wordLadder = new AdjacencyTable(words);
        for (String word : words) {
            GraphNode wordNode = wordLadder.get(word);
            List<String> neighbours = generateNeighbours(word, words);
            for (String neighbour : neighbours) {
                wordNode.addNeighbour(neighbour);
            }
        }
            return wordLadder;
    }
}
