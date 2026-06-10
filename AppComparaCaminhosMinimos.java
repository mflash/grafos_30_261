public class AppComparaCaminhosMinimos {
   
    public static void main(String[] args) {
        // EdgeWeightedDigraph g = new EdgeWeightedDigraph("exemplos/mediumEWG.txt");
        EdgeWeightedDigraph g = WeightedDigraphGenerator.complete(40);
        System.out.println(g.toDot());

        long start = System.nanoTime();
        for(String v: g.getVerts()) {
            DijkstraSP dij = new DijkstraSP(g, v);
        }
        long end = System.nanoTime();
        double delta = (end-start)/1e9;
        System.out.printf("Tempo Dijkstra: %f\n",delta);

        start = System.nanoTime();
        FloydWarshallSP fw = new FloydWarshallSP(g);
        end = System.nanoTime();
        delta = (end-start)/1e9;
        System.out.printf("Tempo F-W:      %f\n",delta);

    }
}
