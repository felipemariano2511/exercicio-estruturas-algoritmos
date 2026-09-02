import java.util.function.Consumer;

public class MatrizDensa {
    private final int linhas, colunas;
    private final double[][] dados;

    public MatrizDensa(int linhas, int colunas) {
        this.linhas = linhas;
        this.colunas = colunas;
        this.dados = new double[linhas][colunas];
    }

    public double get(int i, int j) { return dados[i][j]; }
    public void set(int i, int j, double v) { dados[i][j] = v; }

    public void percorrerNaoNulos(Consumer<Tripla> c) {
        for (int i = 0; i < linhas; i++)
            for (int j = 0; j < colunas; j++)
                if (dados[i][j] != 0.0) c.accept(new Tripla(i, j, dados[i][j]));
    }

    // 8 bytes por posição + overhead de arrays
    public long estimarMemoriaBytes() {
        return 24 + 16 + (long) linhas * 4 + (long) linhas * (16 + (long) colunas * 8);
    }
}
