import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CicloNaoDirigido {    

    private Set<String> marked; // vértices já visitados
    private Map<String, String> edgeTo; // caminhos

    private Set<String> paths; // conjunto de arestas já percorridas

    private boolean hasCycle; // true se tiver ciclo

    public CicloNaoDirigido(Graph g)
    {
        marked = new HashSet<>();
        edgeTo = new HashMap<>();
        paths = new HashSet<>();
        hasCycle = false;        
        for(String start: g.getVerts()) {
            // Chama o dfs se o vértice ainda não 
            // estiver marcado
            if(!marked.contains(start)) {
                System.out.println("Iniciando DFS em "+start);
                dfs(g, start);
            }
            // Se encontrou um ciclo, termina!
            if(hasCycle)
                break;
        }        
        System.out.println(paths);
    }

    public boolean hasCycle()
    {
        return hasCycle;
    }

    private boolean dfs(Graph g, String v)
    {
        // System.out.println("Entrando em " + v);
        // Marca o vértice atual
        marked.add(v);
        for (String w : g.getAdj(v)) {
            // System.out.println(">>> Vizinho de " + v + ": " + w);
            // Se não estiver marcado, visita!
            if (!marked.contains(w)) {
                // Indica que para chegar em w, viemos de v!
                edgeTo.put(w, v);
                paths.add(v+"-"+w);
                if(dfs(g, w))
                    return true;
            }
            else
            {
                // Já está marcado: pode ser um ciclo!

                // Se a aresta w-v NÃO ESTÁ no conjunto...
                // é ciclo!
                if(!paths.contains(w+"-"+v))
                {
                    System.out.println("Achei ciclo em "+w+"-"+v);
                    hasCycle = true;
                    return true;
                }
            }        
        }
        // System.out.println("Saindo de " + v);
        return false; // não achei ciclo!        
    }

    public static void main(String[] args) {
        Graph g = new Graph();
        g.addEdge("A", "B");
        g.addEdge("A", "C");
        g.addEdge("B", "D");
        g.addEdge("D", "E");
        // g.addEdge("B","C");
        // g.addEdge("C", "D");
        g.addEdge("F", "G");
        g.addEdge("G", "H");
        g.addEdge("H", "F");
        CicloNaoDirigido c = new CicloNaoDirigido(g);
        System.out.println("Tem ciclo? "+c.hasCycle());
    }
}
