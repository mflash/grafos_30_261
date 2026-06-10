import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GrafoCarga {
  protected static final String NEWLINE = System.getProperty("line.separator");

  protected Map<String, List<EdgeCarga>> graph;
  protected Set<String> vertices;
  protected int totalVertices;
  protected int totalEdges;

  public GrafoCarga() {
    graph = new HashMap<>();
    vertices = new HashSet<>();
    totalVertices = totalEdges = 0;
  }

  public GrafoCarga(String filename) {
    this();
    In in = new In(filename);
    String line;
    while ((line = in.readLine()) != null) {
      String[] edge = line.trim().split("[ ]+");
      addEdge(edge[0], edge[1], Double.parseDouble(edge[2]),
        Double.parseDouble(edge[3]));
    }
    in.close();
  }

  public void addEdge(String v, String w, double weight, double carga) {
    EdgeCarga e = new EdgeCarga(v, w, weight, carga);
    addToList(v, e);
    if (!vertices.contains(v)) {
      vertices.add(v);
      totalVertices++;
    }
    if (!vertices.contains(w)) {
      vertices.add(w);
      totalVertices++;
    }
    totalEdges += 1;
  }

  public Iterable<EdgeCarga> getAdj(String v) {
    List<EdgeCarga> res = graph.get(v);
    if (res == null)
      res = new LinkedList<>();
    return res;
  }

  public int getTotalVerts() {
    return totalVertices;
  }

  public int getTotalEdges() {
    return totalEdges;
  }

  public Set<String> getVerts() {
    return vertices;
  }

  public Iterable<EdgeCarga> getEdges() {
    Set<EdgeCarga> ed = new HashSet<>();
    for (String v : getVerts().stream().sorted().toList()) {
      for (EdgeCarga e : getAdj(v)) {
        if (!ed.contains(e)) {
          ed.add(e);
        }
      }
    }
    return ed;
  }

  public String toDot() {
    StringBuilder sb = new StringBuilder();
    sb.append("graph {" + NEWLINE);
    sb.append("rankdir = LR;" + NEWLINE);
    sb.append("node [shape = circle];" + NEWLINE);
    for (EdgeCarga e : getEdges())
      sb.append(
          String.format("%s -> %s [label=\"%.2f / %.2f\" %s]", e.getV(), e.getW(), e.getWeight(), e.getCarga(), e.getColor()) + NEWLINE);
    sb.append("}" + NEWLINE);
    return sb.toString();
  }

  // Adiciona um vértice adjacente a outro, criando a lista
  // de adjacências caso ainda não exista no dicionário
  protected List<EdgeCarga> addToList(String v, EdgeCarga e) {
    List<EdgeCarga> list = graph.get(v);
    if (list == null)
      list = new LinkedList<>();
    list.add(e);
    graph.put(v, list);
    return list;
  }

  public static void main(String[] args) {
    GrafoCarga g = new GrafoCarga(args[0]);
    System.out.println(g.toDot());
  }
}

