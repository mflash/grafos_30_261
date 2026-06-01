public class Ponto implements Comparable<Ponto> {

    private static int numPontos = 0;
    private int pos;
    private double x, y;
    private double dist;

    Ponto(double x, double y) {
        this.pos = numPontos;
        this.x = x;
        this.y = y;
        numPontos++;
    }

    public static int getNumPontos() {
        return numPontos;
    }

    public int getPos() {
        return pos;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public double getDist() {
        return dist;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setDist(double dist) {
        this.dist = dist;
    }

    @Override
    public String toString() {
        return x + ", " + y + " (" + dist + ")";
    }

    public double calcDist(Ponto outro) {
        return Math.sqrt(Math.pow(this.x - outro.x, 2) +
                Math.pow(this.y - outro.y, 2));
    }

    @Override
    public int compareTo(Ponto o) {
        if (this.dist - o.dist > 0)
            return 1;
        else if (this.dist - o.dist < 0)
            return -1;
        return 0;
    }
}
