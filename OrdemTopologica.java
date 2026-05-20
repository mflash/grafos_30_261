import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class OrdemTopologica {

    private Graph g;

    private Set<String> marked; // vértices já visitados
    private List<String> ordemTopo;

    public OrdemTopologica(Graph g) {
        this.g = g;
        marked = new HashSet<>();
        ordemTopo = new LinkedList<>();
        List<String> vertices = g.getVerts().stream().sorted().toList();
        System.out.println("Vértices: " + vertices);
        for (String start : vertices) {
            if (!marked.contains(start)) {
                dfs(g, start);
            }
        }
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
                dfs(g, w);
            }
        }
        System.out.println("Saindo de " + v);
        ordemTopo.addFirst(v); // insere no início de ordemTopo (inverte)
    }

    Iterable<String> getOrdemTopologica() {
        return ordemTopo;
    }

    public static void main(String[] args) {
        Digraph g = new Digraph("exemplos/tinyG.txt");
        CicloDirigido cd = new CicloDirigido(g);
        if (cd.hasCycle()) {
            System.out.println("Impossível! Há ciclos!");
        } else {
            OrdemTopologica ot = new OrdemTopologica(g);

            for (String v : ot.getOrdemTopologica()) {
                System.out.print(v + " -> ");
            }
            System.out.println();
        }
        System.out.println(g.toDot());
    }

}
