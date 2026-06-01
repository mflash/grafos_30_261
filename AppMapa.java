import java.util.ArrayList;
import java.util.Collections;

public class AppMapa {
    public static void main(String[] args) {
        final int totalArestasPorPonto = 7;
        ArrayList<Ponto> pontos = new ArrayList<>();
        In arq = new In("dados.csv");

        // Lê todos os pontos (no formato latitude;longitude)
        while (arq.hasNextLine()) {
            String line = arq.readLine();
            String[] coords = line.split(";");
            double lat = Double.parseDouble(coords[0]);
            double lon = Double.parseDouble(coords[1]);
            // System.out.println(lat + " " + lon);
            pontos.add(new Ponto(lon, lat));
        }

        // Cria o grafo vazio
        EdgeWeightedGraph g = new EdgeWeightedGraph();

        // Para cada ponto, encontra os 3 mais
        // próximos e cria arestas no grafo

        for (int pos1 = 0; pos1 < pontos.size(); pos1++) {
            // Faz uma cópia da lista original (para ordenar)
            ArrayList<Ponto> pontosOrd = new ArrayList<>();
            Ponto p1 = pontos.get(pos1);
            for (int pos2 = 0; pos2 < pontos.size(); pos2++) {
                // Mesmo ponto? pula!
                if (pos1 == pos2)
                    continue;
                Ponto p2 = pontos.get(pos2);
                // Calcula a distância e armazena em cada ponto
                double dist = p1.calcDist(p2);
                p2.setDist(dist);
                // Adiciona o ponto à lista que será ordenada
                pontosOrd.add(p2);
            }

            // Agora ordena a lista auxiliar pela distância
            Collections.sort(pontosOrd);

            // Finalmente, pega os n primeiros (os n mais próximos)
            for (int pos = 0; pos < totalArestasPorPonto; pos++) {
                Ponto p2 = pontosOrd.get(pos);
                // ... e cria uma aresta de p1 para cada um deles
                g.addEdge(p1.getPos() + "", p2.getPos() + "", p2.getDist());
            }
        }

        // Com o grafo montado, executa o algoritmo de Kruskal
        // (produz a árvore geradora mínima, e "marca" cada
        // aresta da árvore com uma "cor")
        KruskalMST k = new KruskalMST(g);

        // Percorre cada aresta do grafo, se a "cor" não for
        // uma string vazia, coloca um "0" na saída, caso contrário
        // coloca um "1"
        for (Edge e : g.getEdges()) {
            System.out.println(e.getV() + " " + e.getW() + " "
                    + (e.getColor().isEmpty() ? "0" : "1"));
        }
    }
}