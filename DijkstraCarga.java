import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class DijkstraCarga {

    private Map<String, Double> distTo;
    private Map<String, EdgeCarga> edgeTo;
    private IndexMinHeap<String, Double> pq;

    public DijkstraCarga(GrafoCarga g, String s, double pesoCaminhao) {
        distTo = new HashMap<>();
        edgeTo = new HashMap<>();
        pq = new IndexMinHeap<>();

        // Inicializa todas as distâncias com infinito,
        // exceto a do vértice inicial
        for(String v: g.getVerts())
            distTo.put(v, Double.POSITIVE_INFINITY);
        // Distância do início até o início é... zero
        distTo.put(s, 0.0);
        dijkstra(g, s, pesoCaminhao);
    }

    private void dijkstra(GrafoCarga g, String s, double pesoCaminhao)
    {
        pq.insert(s, distTo.get(s)); // distTo.get(s) ==> 0.0
        // Enquanto houver algum vértice na fila...
        while(!pq.isEmpty()) {
            // Retira o vértice com menor distância total
            String v = pq.delMin();
            // E "relaxa" todas as arestas a partir dele
            for(EdgeCarga e: g.getAdj(v)) {
                if(e.getCarga() >= pesoCaminhao)
                    relax(e);
            }
        }        
    }

    private void relax(EdgeCarga e) {
        String v = e.getV();
        String w = e.getW();
        // Custo acumulado de v até w
        double dist = distTo.get(v) + e.getWeight();
        // Se o custo for menor do que o atual para w...
        if(distTo.get(w) > dist) {
            // ...significa que achamos um caminho melhor
            distTo.put(w, dist);
            edgeTo.put(w, e);
            if(pq.contains(w))
                // Já existe na pq, então reduz o peso (distância)
                // e faz "swim" (se necessário)
                pq.decreaseValue(w, dist);
            else
                // Não existe na pq, então insere
                pq.insert(w, dist);
        }
    }

    public double distTo(String v) {
        return distTo.get(v);
    }

    public boolean hasPathTo(String v) {
        return edgeTo.get(v) != null;
    }

    public Iterable<Edge> pathTo(String v) {
        LinkedList<Edge> path = new LinkedList<>();
        Edge e = edgeTo.get(v);
        // Enquanto não chegar na primeira aresta...
        while(e != null) {
            // Adiciona no início, pois o caminho é
            // percorrido ao contrário (do fim para o início)
            path.addFirst(e);
            // A próxima aresta é aquela que vem de V (início desta aresta)
            // (lembrando: estamos percorrendo ao CONTRÁRIO)
            e = edgeTo.get(e.getV());
        }
        return path;
    }

    public static void main(String[] args) {
        GrafoCarga g = new GrafoCarga("exemplos/carga.txt");
        String origem = "0";
        String destino = "10";
        double pesoCam = 12;
        DijkstraCarga dij = new DijkstraCarga(g, origem, pesoCam);
        if(!dij.hasPathTo(destino)) {
            System.out.println("SEM CAMINHO para "+destino);
        }
        else {
           for(Edge e: dij.pathTo(destino)) {
               System.out.print(e+" ");
           }
           System.out.println("-> "+dij.distTo(destino));
        }
    }
}
