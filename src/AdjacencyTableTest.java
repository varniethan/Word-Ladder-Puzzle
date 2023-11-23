import static org.junit.jupiter.api.Assertions.*;

class AdjacencyTableTest {

    @org.junit.jupiter.api.Test
    void calculateHash() {
        AdjacencyTable adjacencyTable = new AdjacencyTable(new String[]{"apple", "banana", "orange"});

        // Test cases for calculateHash method
        // Test 1: Test hash calculation for a string
        String testString1 = "apple";
        int expectedHash1 = testString1.hashCode() & 0x7fffffff;
        int calculatedHash1 = adjacencyTable.calculateHash(testString1);
        assertEquals(expectedHash1 % (2 * 3), calculatedHash1);

        // Test 2: Test hash calculation for another string
        String testString2 = "banana";
        int expectedHash2 = testString2.hashCode() & 0x7fffffff;
        int calculatedHash2 = adjacencyTable.calculateHash(testString2);
        assertEquals(expectedHash2 % (2 * 3), calculatedHash2);
    }

    @org.junit.jupiter.api.Test
    void checkCollisions() {
        AdjacencyTable adjacencyTable = new AdjacencyTable(new String[]{"apple", "banana", "orange", "grape", "melon"});

        // Test cases for checkCollisions method
        // Test 1: No collision expected for a unique string
        String testString1 = "apple";
        int hashValue1 = adjacencyTable.calculateHash(testString1);
        assertFalse(adjacencyTable.checkCollisions(hashValue1, testString1));

        // Test 2: Collision expected for the same hash value with a different string
        String testString2 = "orange"; // This may generate the same hash as "apple" in the given scenario
        int hashValue2 = adjacencyTable.calculateHash(testString1);
        assertTrue(adjacencyTable.checkCollisions(hashValue2, testString2));
    }

    @org.junit.jupiter.api.Test
    void probe() {
        AdjacencyTable adjacencyTable = new AdjacencyTable(new String[]{"apple", "banana", "orange", "grape", "melon"});

        // Test cases for probe method
        // Test 1: No collision, should return the same index
        String testString1 = "apple";
        int hashValue1 = adjacencyTable.calculateHash(testString1);
        int probeIndex1 = adjacencyTable.probe(hashValue1);
        assertEquals(hashValue1, probeIndex1);

        // Test 2: Collision occurs, should probe to find the next available index
        String testString2 = "orange"; // Collision with "apple"
        int hashValue2 = adjacencyTable.calculateHash(testString2);
        int probeIndex2 = adjacencyTable.probe(hashValue2);
        assertNotEquals(hashValue2, probeIndex2); // Ensure probeIndex2 is different from the hashValue2

        // Test 3: Test probing for another collision scenario
        String testString3 = "melon"; // Collision with "apple" and "orange"
        int hashValue3 = adjacencyTable.calculateHash(testString3);
        int probeIndex3 = adjacencyTable.probe(hashValue3);
        assertNotEquals(hashValue3, probeIndex3); // Ensure probeIndex3 is different from the hashValue3
    }

    @org.junit.jupiter.api.Test
    void getTable() {
        String[] words = {"apple", "banana", "orange", "grape", "melon"};
        AdjacencyTable adjacencyTable = new AdjacencyTable(words);

        // Test the initial table content after initialization
        GraphNode[] initialTable = adjacencyTable.getTable();
        assertNotNull(initialTable);
        assertEquals(2 * words.length, initialTable.length);

        // Test if modifications to the table are reflected when getting the table
        adjacencyTable.get("apple").addNeighbour("banana");
        GraphNode[] modifiedTable = adjacencyTable.getTable();
        assertNotNull(modifiedTable);
        assertEquals(2 * words.length, modifiedTable.length);
    }

    @org.junit.jupiter.api.Test
    void find() {
        String[] words = {"apple", "banana", "orange", "grape", "melon"};
        AdjacencyTable adjacencyTable = new AdjacencyTable(words);

        // Test cases for find method
        // Test 1: Search for an existing node
        String existingNode = "banana";
        assertTrue(adjacencyTable.find(existingNode));

        // Test 2: Search for a non-existing node
        String nonExistingNode = "pineapple";
        assertFalse(adjacencyTable.find(nonExistingNode));

        // Test 3: Search for an existing node after modification
        adjacencyTable.get("apple").addNeighbour("orange");
        assertTrue(adjacencyTable.find("orange"));

        // Add more test cases as needed to cover different scenarios
    }

    @org.junit.jupiter.api.Test
    void get() {
        String[] words = {"apple", "banana", "orange", "grape", "melon"};
        AdjacencyTable adjacencyTable = new AdjacencyTable(words);

        // Test cases for get method
        // Test 1: Get an existing node
        String existingNode = "banana";
        GraphNode existingGraphNode = adjacencyTable.get(existingNode);
        assertNotNull(existingGraphNode);
        assertEquals(existingNode, existingGraphNode.label);

        // Test 2: Get a non-existing node
        String nonExistingNode = "pineapple";
        GraphNode nonExistingGraphNode = adjacencyTable.get(nonExistingNode);
        assertNull(nonExistingGraphNode);

        // Test 3: Get an existing node after modification
        adjacencyTable.get("apple").addNeighbour("orange");
        GraphNode modifiedGraphNode = adjacencyTable.get("orange");
        assertNotNull(modifiedGraphNode);
        assertEquals("orange", modifiedGraphNode.label);

        // Add more test cases as needed to cover different scenarios
    }

    @org.junit.jupiter.api.Test
    void getPath() {
        AdjacencyTable adjacencyTable = new AdjacencyTable(WeaverWords.words);

        // Test cases for getPath method
        // Test 1: Get path between existing nodes
        String startNode = "moon";
        String endNode = "walk";
        String existingPath = adjacencyTable.getPath(startNode, endNode);
        assertNotNull(existingPath);
        System.out.println(existingPath);
        assertEquals("There is no path between ", existingPath);

        // Test 2: Get path between nodes when no path exists
        String startNodeNoPath = "apple";
        String endNodeNoPath = "grape";
        String nonExistingPath = adjacencyTable.getPath(startNodeNoPath, endNodeNoPath);
        assertNotNull(nonExistingPath);
        assertEquals("There is no path from apple to grape.", nonExistingPath);

        // Test 3: Get path when start or end nodes are not found
        String startNodeNotFound = "pear";
        String endNodeNotFound = "banana";
        String nodeNotFoundPath = adjacencyTable.getPath(startNodeNotFound, endNodeNotFound);
        assertNotNull(nodeNotFoundPath);
        assertEquals("There is no path from pear to banana.", nodeNotFoundPath);
    }

    @org.junit.jupiter.api.Test
    void existsPath() {
        String[] words = {"apple", "banana", "orange", "grape", "melon"};
        AdjacencyTable adjacencyTable = new AdjacencyTable(words);

        // Test cases for existsPath method
        // Test 1: Check if path exists between existing nodes
        String startNode = "apple";
        String endNode = "orange";
        boolean existsExistingPath = adjacencyTable.existsPath(startNode, endNode);
        assertTrue(existsExistingPath);

        // Test 2: Check if path exists between nodes when no path exists
        String startNodeNoPath = "apple";
        String endNodeNoPath = "grape";
        boolean existsNonExistingPath = adjacencyTable.existsPath(startNodeNoPath, endNodeNoPath);
        assertFalse(existsNonExistingPath);

        // Test 3: Check if path exists when start or end nodes are not found
        String startNodeNotFound = "pear";
        String endNodeNotFound = "banana";
        boolean existsNodeNotFoundPath = adjacencyTable.existsPath(startNodeNotFound, endNodeNotFound);
        assertFalse(existsNodeNotFoundPath);

        // Add more test cases as needed to cover different scenarios
    }

    @org.junit.jupiter.api.Test
    void pathLength() {
        String[] words = {"apple", "banana", "orange", "grape", "melon"};
        AdjacencyTable adjacencyTable = new AdjacencyTable(words);

        // Test cases for pathLength method
        // Test 1: Get path length between existing nodes
        String startNode = "apple";
        String endNode = "orange";
        int pathLengthExistingPath = adjacencyTable.pathLength(startNode, endNode);
        assertEquals(2, pathLengthExistingPath);

        // Test 2: Get path length between nodes when no path exists
        String startNodeNoPath = "apple";
        String endNodeNoPath = "grape";
        int pathLengthNonExistingPath = adjacencyTable.pathLength(startNodeNoPath, endNodeNoPath);
        assertEquals(0, pathLengthNonExistingPath);

        // Test 3: Get path length when start or end nodes are not found
        String startNodeNotFound = "pear";
        String endNodeNotFound = "banana";
        int pathLengthNodeNotFound = adjacencyTable.pathLength(startNodeNotFound, endNodeNotFound);
        assertEquals(0, pathLengthNodeNotFound);
    }

    @org.junit.jupiter.api.Test
    void generateNeighbours() {
        String[] words = {"cat", "hat", "hot", "dog", "dot"};

        // Test cases for generateNeighbours method
        // Test 1: Generate neighbors for a word with valid neighbors
        String testWord1 = "cat";
        String[] expectedNeighbours1 = {"bat", "hat", "cat", "dat", "eat", "fat", "gat", "hat", "iat", "jat", "kat",
                "lat", "mat", "nat", "oat", "pat", "qat", "rat", "sat", "tat", "uat", "vat",
                "wat", "xat", "yat", "zat"};
        assertEquals(expectedNeighbours1.length, AdjacencyTable.generateNeighbours(testWord1, words).size());
        assertArrayEquals(expectedNeighbours1, AdjacencyTable.generateNeighbours(testWord1, words).toArray());

        // Test 2: Generate neighbors for a word with no valid neighbors
        String testWord2 = "xyz"; // Assuming "xyz" is not in the word list
        assertEquals(0, AdjacencyTable.generateNeighbours(testWord2, words).size());

        // Test 3: Generate neighbors for an empty word
        String testWord3 = "";
        assertEquals(0, AdjacencyTable.generateNeighbours(testWord3, words).size());
    }

    @org.junit.jupiter.api.Test
    void isValidWord() {
        String[] words = {"cat", "hat", "hot", "dog", "dot"};

        // Test cases for isValidWord method
        // Test 1: Check for a valid word that exists in the word list
        String validWord1 = "cat";
        assertTrue(AdjacencyTable.isValidWord(validWord1, words));

        // Test 2: Check for a valid word that doesn't exist in the word list
        String validWord2 = "hat";
        assertTrue(AdjacencyTable.isValidWord(validWord2, words));

        // Test 3: Check for an invalid word that doesn't exist in the word list
        String invalidWord = "xyz";
        assertFalse(AdjacencyTable.isValidWord(invalidWord, words));

        // Test 4: Check for an empty word
        String emptyWord = "";
        assertFalse(AdjacencyTable.isValidWord(emptyWord, words));
    }

    @org.junit.jupiter.api.Test
    void weaver() {
        // Assuming you have a known set of words or a predefined graph
        String[] words = {"cat", "cot", "cog", "dog"};

        AdjacencyTable wordLadder = AdjacencyTable.weaver();

        // Here you could perform some operations on the wordLadder object and then check the behavior of its methods.
        // For instance, you could check if certain words are connected in the generated graph.

        // Example test: Check if "cat" is connected to "cot" and "cog" but not to "dog"
        assertTrue(wordLadder.existsPath("cat", "cot"));
        assertTrue(wordLadder.existsPath("cat", "cog"));
        assertFalse(wordLadder.existsPath("cat", "dog"));
    }
}