import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
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
        fila.add(s); // inclui o primeiro na fila
        marked.add(s); // e marca como visitado
        distTo.put(s, 0); // distância de s até s = 0
        while (!fila.isEmpty()) {
            // Remove o primeiro da fila
            String v = fila.removeFirst();
            // Exibe na tela o vértice
            System.out.println("Estou em " + v);
            // Insere na fila todos os vizinhos não marcados
            for (String w : g.getAdj(v)) {
                if (!marked.contains(w)) {
                    fila.add(w);
                    marked.add(w); // marca como visitado
                    edgeTo.put(w, v); // indica que viemos de v
                    distTo.put(w, distTo.get(v) + 1); // distância até w = distância até v + 1
                }
            }
        }
    }

    public boolean hasPathTo(String v) {
        return marked.contains(v);
        /*
         * if(marked.contains(v))
         * return true;
         * else
         * return false;
         */
    }

    public List<String> pathTo(String v) {
        ArrayList<String> result = new ArrayList<>();
        // Se não tem caminho, retorna a lista vazia
        if (!hasPathTo(v))
            return result;
        // Enquanto não chegarmos no início (start)...
        while (!v.equals(start)) {
            // Insere no início para inverter a ordem!
            result.add(0, v);
            // vai para o antecessor
            v = edgeTo.get(v);
        }
        // Por fim, adiciona o vértice inicial
        result.add(0, start);
        return result;
    }

    public int getDist(String v) {
        if (!hasPathTo(v))
            return -1; // não há caminho para v
        return distTo.get(v);
    }

    public static void main(String[] args) {
        Graph g = new Graph("exemplos/tinyG.txt");
        BFS bfs = new BFS(g, "0");

        // Mostra todos os caminhos a partir de start:
        // Para cada vértice do grafo...
        for (String v : g.getVerts()) {
            // Se há caminho para ele...
            if (bfs.hasPathTo(v)) {
                // Mostra o caminho
                System.out.print(v + ": ");
                for (String w : bfs.pathTo(v))
                    System.out.print(w + " ");
                System.out.println("(" + bfs.getDist(v) + ")");
            } else {
                System.out.println(v + ": sem caminho");
            }
        }
    }

}
