import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.function.Consumer;

public class MatrizEsparsaTriplas {
    private final int linhas, colunas;
    private final List<Tripla> elementos = new ArrayList<>();

    public MatrizEsparsaTriplas(int linhas, int colunas) {
        this.linhas = linhas;
        this.colunas = colunas;
    }

    public int getLinhas() { return linhas; }
    public int getColunas() { return colunas; }
    public int getNaoNulos() { return elementos.size(); }

    public double get(int i, int j) {
        int idx = Collections.binarySearch(elementos, new Tripla(i, j, 0));
        return idx >= 0 ? elementos.get(idx).getValor() : 0.0;
    }

    public void set(int i, int j, double valor) {
        int idx = Collections.binarySearch(elementos, new Tripla(i, j, 0));
        if (valor == 0.0) {
            if (idx >= 0) elementos.remove(idx);
        } else if (idx >= 0) {
            elementos.get(idx).setValor(valor);
        } else {
            elementos.add(-(idx + 1), new Tripla(i, j, valor));
        }
    }

    public void percorrerNaoNulos(Consumer<Tripla> c) {
        for (Tripla t : elementos) c.accept(t);
    }

    public void imprimir() {
        System.out.printf("Esparsa %dx%d, nnz=%d (%.2f%%)%n",
                linhas, colunas, elementos.size(),
                100.0 * elementos.size() / ((long) linhas * colunas));
        for (Tripla t : elementos) System.out.println("  " + t);
    }

    // ~36 bytes por tripla (16B header + 12B campos + 4B ref no ArrayList + padding)
    public long estimarMemoriaBytes() {
        return 64 + (long) elementos.size() * 36;
    }
}
