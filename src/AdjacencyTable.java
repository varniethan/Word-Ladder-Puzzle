import java.util.*;

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
        GraphNode start = this.get(s);
        GraphNode end = this.get(t);
        if (start != null && end != null) {
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

        while (!queue.isEmpty())
        {
            GraphNode currentNode = queue.remove();
            for (int i = 0; i < currentNode.neighbours.length; i++) {
                if (!visited.contains(currentNode.neighbours[i])) {
                    GraphNode neighbour = this.get(currentNode.neighbours[i]);
                    pathMap.put(neighbour, pathMap.get(currentNode) + " " + neighbour.label);
                    if (neighbour.equals(end)) {
                       return pathMap.get(neighbour); //path found
                    }
                    else {
                        queue.add(neighbour);
                        visited.add(neighbour);
                    }
                }
            }
        }
        return "There is no path from " + s + " to " + t + ".";
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

        Queue<GraphNode> queue = new LinkedList<>();
        ArrayList<GraphNode> visited = new ArrayList<>();

        queue.add(start);
        visited.add(start);

        while (!queue.isEmpty()) {
            GraphNode currentNode = queue.remove();
            if (currentNode.equals(end)) {
                existsPath = true;
                break;
            }
            else {
                for (int i = 0; i < currentNode.neighbours.length; i++) {
                    if (currentNode.neighbours[i] != null) {
                        GraphNode neighbour = this.get(currentNode.neighbours[i]);
                        if (!visited.contains(neighbour)) {
                            queue.add(neighbour);
                            visited.add(neighbour);
                        }
                    }
                }
            }
        }
        return existsPath;
    }

    public int pathLength(String s, String t) {
        GraphNode start = this.get(s);
        GraphNode end = this.get(t);
        int pathLength = 0;
        if (start != null && end != null) {
            pathLength = 0;
        }

        if (start.equals(end)) {
            pathLength = 1;
        }

        Queue<GraphNode> queue = new LinkedList<>();
        ArrayList<GraphNode> visited = new ArrayList<>();
        Map<GraphNode, Integer> distance = new HashMap<>();

        queue.add(start);
        visited.add(start);
        distance.put(start, 1);


        while (!queue.isEmpty()) {
            GraphNode currentNode = queue.remove();
            int currentDistance = distance.get(currentNode);
            if (currentNode.equals(end)) {
                pathLength = currentDistance;
                break;
            }
            else {
                for (int i = 0; i < currentNode.neighbours.length; i++) {
                    if (currentNode.neighbours[i] != null) {
                        GraphNode neighbour = this.get(currentNode.neighbours[i]);
                        if (!visited.contains(neighbour)) {
                            queue.add(neighbour);
                            visited.add(neighbour);
                            distance.put(neighbour, currentDistance+1);
                        }
                    }
                }
            }
        }
return pathLength;
    }

    public static List<String> generateNeighbours(String word, String[] words) {
        List<String> neighbours = new ArrayList<>();
        for (int i = 0; i < word.length(); i++) {
            char[] wordArray = word.toCharArray();
            for (char c = 'a'; c <= 'z'; c++) {
                wordArray[i] = c;
                String newWord = new String(wordArray);
                if (Arrays.asList(words).contains(newWord)) {
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
            List<String> neighbours = generateNeighbours(word, words);
            for (String neighbour : neighbours) {
                if ((isValidWord(neighbour, words)) && (!neighbour.equals(word))) {
                    GraphNode wordNode = wordLadder.get(word);
                    wordNode.addNeighbour(neighbour);
                }
            }
        }
        return wordLadder;
    }
}
