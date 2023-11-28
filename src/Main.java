public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        AdjacencyTable table = AdjacencyTable.weaver();
        System.out.println(table.existsPath("cold", "cord"));
    }
}
