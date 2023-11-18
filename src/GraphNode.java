 public class GraphNode {

    // A node holds a String label, and an array of its neighbours
    public String label;

    public String[] neighbours = new String[6]; // 6 neighbours to start with

    // You may add further fields and methods. 
     
    public GraphNode(String label) {
      this.label = label;
    }

    public int calculateNextIndexToAdd() {
        int nextIndex = 0;
        for (int i = 0; i < this.neighbours.length; i++) {
            if (this.neighbours[i] == null) {
            nextIndex = i;
            break;
            }
        }
        return nextIndex;
    }

    //Calculate if resize is needed
     public boolean isFull() {
        boolean isFull = true;
        for (int i = 0; i < this.neighbours.length-1; i++) {
            if (this.neighbours[i] == null) {
                isFull = false;
                break;
            }
        }
        return isFull;
    }
    /*
     * Neighbours are added using addNeighbour
     * Resizing array
     * Initially 6 slots -> holding null entries
     * At least one empty slot
     * If its full, replace the existing array by one that is double the size
     *
     * */
    public void addNeighbour(String s) {
        if (isFull()) {
            String[] newNeighbours = new String[this.neighbours.length*2];
            for (int i = 0; i < this.neighbours.length; i++) {
                newNeighbours[i] = this.neighbours[i];
            }
            this.neighbours = newNeighbours;
        }
        else {
            int index = calculateNextIndexToAdd();
            this.neighbours[index] = s;
        }
   }

}
