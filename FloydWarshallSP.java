import java.util.LinkedList;

public class FloydWarshallSP {

    private static final String NEWLINE = System.getProperty("line.separator");
    private double[][] dist;
    private int[][] next;
    private AdjMatrixEdgeWeightedDigraph mat;
    private long tempo;

    public FloydWarshallSP(EdgeWeightedDigraph g) {
        this.mat = new AdjMatrixEdgeWeightedDigraph(g);

        int totVert = g.getTotalVerts();
        dist = new double[totVert][totVert];
        next = new int[totVert][totVert];

        // Inicializar as matrizes
        for (int linha = 0; linha < totVert; linha++)
            for (int col = 0; col < totVert; col++) {
                if (linha != col) {
                    dist[linha][col] = Double.POSITIVE_INFINITY;
                }
                next[linha][col] = -1;
            }

        for (Edge e : g.getEdges()) {
            String u = e.getV();
            String v = e.getW();
            double weight = e.getWeight();
            // mapToArray: converte um nome de vértice (string) para
            // índice na matriz (int)
            int ind_u = mat.mapToArray(u);
            int ind_v = mat.mapToArray(v);
            dist[ind_u][ind_v] = weight;
            next[ind_u][ind_v] = ind_u;
        }

        //System.out.println(this);

        // Executar o algoritmo!
        long start = System.currentTimeMillis();
        for (int k = 0; k < totVert; k++) {
            for (int j = 0; j < totVert; j++) {
                for (int i = 0; i < totVert; i++) {
                    if (dist[i][j] > dist[i][k] + dist[k][j]) {
                        dist[i][j] = dist[i][k] + dist[k][j];
                        next[i][j] = next[k][j];
                    }
                }
            }
        }
        long end = System.currentTimeMillis();
        tempo = end - start;
    }

    public long getTempoTotal() {
        return tempo;
    }

    public boolean hasPathTo(String u, String v) {
        int u1 = mat.mapToArray(u);
        int v1 = mat.mapToArray(v);
        // Retorna false se a matriz tiver -1
        return next[u1][v1] != -1;
    }

    public double distTo(String u, String v) {
        int u1 = mat.mapToArray(u);
        int v1 = mat.mapToArray(v);
        // Retorna a distância total (ou infinito, se não houver caminho)
        return dist[u1][v1];
    }

    public Iterable<String> pathTo(String u, String v) {
        LinkedList<String> path = new LinkedList<>();
        // Converte os nomes de vértices para os índices na matriz
        int ind_u = mat.mapToArray(u);
        int ind_v = mat.mapToArray(v);
        // Quando ind_v for -1, chegamos no primeiro
        while (ind_v != -1) {
            // Insere no início da lista (pois percorremos do fim para o início)
            path.addFirst(v);
            // Próximo vértice (i.e. vértice anterior)
            ind_v = next[ind_u][ind_v];
            // Obtém o nome do vértice a partir do índice
            v = mat.mapToString(ind_v);
        }
        return path;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Distâncias:" + NEWLINE);
        sb.append("  ");
        for (int i = 0; i < dist.length; i++) {
            String v = mat.mapToString(i);
            sb.append(String.format("%-5s ", v));
        }
        sb.append(NEWLINE);
        for (int i = 0; i < dist.length; i++) {
            String v = mat.mapToString(i);
            sb.append(v + " ");
            for (int j = 0; j < dist[i].length; j++) {
                if (next[i][j] != -1)
                    sb.append(String.format("%5.2f ", dist[i][j]));
                else
                    sb.append("----- ");
            }
            sb.append(NEWLINE);
        }
        // Caminhos
        sb.append(NEWLINE + "Caminhos:" + NEWLINE);
        sb.append("  ");
        for (int i = 0; i < next.length; i++) {
            String v = mat.mapToString(i);
            sb.append(String.format("%-5s ", v));
        }
        sb.append(NEWLINE);
        for (int i = 0; i < next.length; i++) {
            String v = mat.mapToString(i);
            sb.append(v + " ");
            for (int j = 0; j < next[i].length; j++) {
                if (next[i][j] != -1) {
                    String w = mat.mapToString(next[i][j]);
                    sb.append(String.format("%-5s ", w));
                } else
                    sb.append("----- ");
            }
            sb.append(NEWLINE);
        }
        return sb.toString();
    }

    public static void main(String[] args) {
        EdgeWeightedDigraph g = new EdgeWeightedDigraph("exemplos/tinyEWD.txt");
        FloydWarshallSP fw = new FloydWarshallSP(g);
        System.out.println(fw);

        System.out.println();
        System.out.print("Caminho de 0 até 6: ");
        if (fw.hasPathTo("0", "6")) {
            for (String v : fw.pathTo("0", "6")) {
                System.out.print(v + " ");
            }
            System.out.println("(" + fw.distTo("0", "6") + ")");
        }
    }
}
