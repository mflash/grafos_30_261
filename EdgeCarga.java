public class EdgeCarga extends Edge {
  private double carga;

  public EdgeCarga(String v, String w, double weight) {
    super(v, w, weight);
    this.carga = 0;
  }

  public EdgeCarga(String v, String w, double weight, double carga) {
    super(v, w, weight);
    this.carga = carga;
  }

  public double getCarga() {
    return carga;
  }

  public void setCarga(double carga) {
      this.carga = carga;
  }

  @Override
  public String toString() {
    return String.format("%s-%s (%.2f/%.2f)",
        getV(), getW(), getWeight(), carga);
  }
}
