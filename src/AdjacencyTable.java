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
	// add your constructor code here
    }

    public GraphNode[] getTable() {
        return table;
    }

    public boolean find(String s) {
	// add your table search code here
	return false; // delete this line
    }

    public GraphNode get(String s) {
	// add your table lookup code here
	return null; // delete this line
    }

    public String getPath(String s, String t) {
	// add your code here
	return ""; // delete this line
    }

    public boolean existsPath(String s, String t) {
	// add your code here
	return false; // delete this line
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
