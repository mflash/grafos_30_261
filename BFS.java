import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Map;
import java.util.Set;

public class BFS {
    private Set<String> marked;
    private Map<String, String> edgeTo;
    private Map<String, Integer> distTo;

    private String start;
    private Graph g;

    public BFS(Graph g, String start) {
        this.start = start;
        this.g = g;
        marked = new HashSet<>();
        edgeTo = new HashMap<>();
        distTo = new HashMap<>();
        bfs(g, start);
    }

    private void bfs(Graph g, String s) {
        LinkedList<String> fila = new LinkedList<>();
        fila.add(s);
        while (!fila.isEmpty()) {
            // Remove o primeiro da fila
            // Exibe na tela o vértice
            // Insere na fila todos os vizinhos não marcados
        }
    }

    public static void main(String[] args) {
        Graph g = new Graph("exemplos/tinyG.txt");
        BFS bfs = new BFS(g, "0");
    }

}
