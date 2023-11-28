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
        AdjacencyTable adjacencyTable = AdjacencyTable.weaver();

        // Test cases for getPath method
        // Test 1: Get path between existing nodes
        String startNode = "moon";
        String endNode = "walk";
        String existingPath = adjacencyTable.getPath(startNode, endNode);
        assertNotNull(existingPath);
        assertEquals("moon-morn-worn-warn-wark-walk", existingPath);

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

        String[][] testWordPairsWithPaths = {
                {"cold", "cord", "cold-coed-cord"},
                {"card", "ward", "card-ward"},
                {"warm", "cold", "warm-ward-card-cord-cold"},
                {"java", "code", "java-fava-fave-cave-cade-code"},
                {"play", "slay", "play-slay"},
                {"lead", "gold", "lead-load-goad-gold"},
                {"dark", "dusk", "dark-dank-dunk-dusk"},
                {"meal", "deal", "meal-deal"},
                {"send", "sent", "send-sent"},
                {"rise", "fall", "rise-bise-bile-file-fill-fall"},
                {"hope", "hype", "hope-hype"},
                {"bake", "cake", "bake-cake"},
                {"king", "ring", "king-ring"},
                {"road", "read", "road-roam-ream-read"},
                {"spin", "spam", "spin-spun-span-spam"}
                // Add more pairs here...
        };

        // Testing paths for each word pair
        for (String[] wordPairWithPath : testWordPairsWithPaths) {
            String startWord = wordPairWithPath[0];
            String endWord = wordPairWithPath[1];
            String expectedPath = wordPairWithPath[2];

            String path = adjacencyTable.getPath(startWord, endWord);

            if (expectedPath.startsWith("There is no path")) {
                assertEquals(expectedPath, path);
            } else {
                assertEquals(expectedPath, path);
            }
        }
    }

    @org.junit.jupiter.api.Test
    void existsPath() {
        AdjacencyTable table = AdjacencyTable.weaver();

        String[][] testWordPairs = {
                {"cold", "cord"},
                {"card", "ward"},
                {"warm", "cold"},
                {"java", "code"},
                {"play", "slay"},
                {"lead", "gold"},
                {"dark", "dusk"},
                {"meal", "deal"},
                {"send", "sent"},
                {"rise", "fall"},
                {"hope", "hype"},
                {"bake", "cake"},
                {"king", "ring"},
                {"road", "read"},
                {"spin", "spam"}
                // Add more pairs here...
        };

        // Testing existsPath for each word pair
        for (String[] wordPair : testWordPairs) {
            String startWord = wordPair[0];
            String endWord = wordPair[1];
            System.out.println(startWord + " " + endWord);
            boolean pathExists = table.existsPath(startWord, endWord);
            // Asserting the existence of the path
            assertTrue(pathExists);
        }
    }

    @org.junit.jupiter.api.Test
    void pathLength() {
        AdjacencyTable table = AdjacencyTable.weaver();
        String[][] testWordPairs = {
                {"cold", "cord", "cold-coed-cord"},
                {"card", "ward", "card-ward"},
                {"warm", "cold", "warm-ward-card-cord-cold"},
                {"java", "code", "java-fava-fave-cave-cade-code"},
                {"play", "slay", "play-slay"},
                {"lead", "gold", "lead-load-goad-gold"},
                {"dark", "dusk", "dark-dank-dunk-dusk"},
                {"meal", "deal", "meal-deal"},
                {"send", "sent", "send-sent"},
                {"rise", "fall", "rise-bise-bile-file-fill-fall"},
                {"hope", "hype", "hope-hype"},
                {"bake", "cake", "bake-cake"},
                {"king", "ring", "king-ring"},
                {"road", "read", "road-roam-ream-read"},
                {"spin", "spam", "spin-spun-span-spam"}
                // Add more pairs here...
        };

        // Testing pathLength for each word pair
        for (String[] wordPairWithPath : testWordPairs) {
            String startWord = wordPairWithPath[0];
            String endWord = wordPairWithPath[1];
            String expectedPath = wordPairWithPath[2];

            int pathLength = table.pathLength(startWord, endWord);

            if (expectedPath.startsWith("There is no path")) {
                assertEquals(0, pathLength);
            } else {
                int expectedLength = expectedPath.split("-").length;
                assertEquals(expectedLength, pathLength);
            }
        }
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

}