public class AppCarga {
    public static void main(String[] args) {
        GrafoCarga g = new GrafoCarga("exemplos/carga.txt");
        System.out.println(g.toDot());
    }
}
