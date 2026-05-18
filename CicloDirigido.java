import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

public class CicloDirigido {    

    private enum Estado {
        WHITE, GRAY, BLACK
    };

    private Map<String, Estado> marked; // vértices já visitados
    private boolean hasCycle; // true se tiver ciclo

    public CicloDirigido(Digraph g)
    {
        marked = new HashMap<>();
        for(String v: g.getVerts())
            marked.put(v, Estado.WHITE);
        hasCycle = false;        
        for(String start: g.getVerts()) {
            // Chama o dfs se o vértice ainda não 
            // estiver marcado
            if(marked.get(start) == Estado.WHITE) {
                System.out.println("Iniciando DFS em "+start);
                dfs(g, start);
            }
            // Se encontrou um ciclo, termina!
            if(hasCycle)
                break;
        }        
    }

    public boolean hasCycle()
    {
        return hasCycle;
    }

    private boolean dfs(Digraph g, String v)
    {
        // System.out.println("Entrando em " + v);
        // Marca o vértice atual como em processsamento
        marked.put(v, Estado.GRAY);
        for (String w : g.getAdj(v)) {
            // System.out.println(">>> Vizinho de " + v + ": " + w);
            // Se não estiver marcado, visita!
            if (marked.get(w) == Estado.WHITE) {
                // Indica que para chegar em w, viemos de v!
                if(dfs(g, w))
                    return true;
            }
            else
            {
                // Já está marcado: pode ser um ciclo!

                if(marked.get(w) == Estado.GRAY)
                {
                    System.out.println("Achei ciclo em "+w+"-"+v);
                    hasCycle = true;
                    return true;
                }
            }        
        }
        // System.out.println("Saindo de " + v);
        // Marca como finalizado
        marked.put(v, Estado.BLACK);
        return false; // não achei ciclo!        
    }

    public static void main(String[] args) {
        Digraph g = new Digraph();
        g.addEdge("A", "B");
        g.addEdge("B", "C");
        // g.addEdge("C", "A");
        g.addEdge("B", "D");
        g.addEdge("D", "E");
        g.addEdge("F", "G");
        g.addEdge("G", "H");
        g.addEdge("H", "F");
        CicloNaoDirigido c = new CicloNaoDirigido(g);
        System.out.println("Tem ciclo? "+c.hasCycle());
    }
}
