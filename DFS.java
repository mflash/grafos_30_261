import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class DFS {

    private Graph g;
    private String start; // início do caminhamento

    private Set<String> marked; // vértices já visitados
    private Map<String, String> edgeTo; // caminhos

    public DFS(Graph g, String start) {
        this.g = g;
        this.start = start;
        marked = new HashSet<>();
        edgeTo = new HashMap<>();

        dfs(g, start);
    }

    private void dfs(Graph g, String v) {
        System.out.println("Entrando em " + v);
        // Marca o vértice atual
        marked.add(v);
        for (String w : g.getAdj(v)) {
            System.out.println(">>> Vizinho de " + v + ": " + w);
            // Se não estiver marcado, visita!
            if (!marked.contains(w)) {
                // Indica que para chegar em w, viemos de v!
                edgeTo.put(w, v);
                dfs(g, w);
            }
        }
        System.out.println("Saindo de " + v);
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

    public static void main(String[] args) {
        Graph g = new Graph("exemplos/tinyG.txt");
        DFS dfs = new DFS(g, "0");

        // Mostra todos os caminhos a partir de start:
        // Para cada vértice do grafo...
        for (String v : g.getVerts()) {
            // Se há caminho para ele...
            if (dfs.hasPathTo(v)) {
                // Mostra o caminho
                System.out.print(v + ": ");
                for (String w : dfs.pathTo(v))
                    System.out.print(w + " ");
                System.out.println();
            } else {
                System.out.println(v + ": sem caminho");
            }
        }
    }

}
