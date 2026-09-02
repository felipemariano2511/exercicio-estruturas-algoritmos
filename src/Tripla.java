public class Tripla implements Comparable<Tripla> {
    private int linha, coluna;
    private double valor;

    public Tripla(int linha, int coluna, double valor) {
        this.linha = linha;
        this.coluna = coluna;
        this.valor = valor;
    }

    public int getLinha() { return linha; }
    public int getColuna() { return coluna; }
    public double getValor() { return valor; }
    public void setValor(double valor) { this.valor = valor; }

    @Override
    public int compareTo(Tripla o) {
        if (this.linha != o.linha) return Integer.compare(this.linha, o.linha);
        return Integer.compare(this.coluna, o.coluna);
    }

    @Override
    public String toString() {
        return String.format("(%d, %d, %.4f)", linha, coluna, valor);
    }
}
