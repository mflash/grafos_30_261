public class ProcessaMiniES {

    public static void main(String[] args) {
        In arq = new In("exemplos/miniES.txt");
        Digraph minies = new Digraph();
        while (arq.hasNextLine()) {
            String linha = arq.readLine();
            String[] campos = linha.split("/");
            String pre = campos[0];
            for (int pos = 1; pos < campos.length; pos++) {
                String disc = campos[pos];
                minies.addEdge(pre, disc);
            }
            if (campos.length == 1) {
                minies.addVertex(pre);
            }
        }
        minies.addVertex("Ética e Cidadania");
        System.out.println(minies.toDot());

        System.out.println();
        OrdemTopologica ot = new OrdemTopologica(minies);
        for (String disc : ot.getOrdemTopologica())
            System.out.println(disc);
    }
}
