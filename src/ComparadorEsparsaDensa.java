import java.util.Random;

public class ComparadorEsparsaDensa {

    public static void demonstrarDezLinhas() {
        System.out.println("\n=== ESPARSA POR TRIPLAS: DEZ LINHAS ESCRITAS ===");
        MatrizEsparsaTriplas m = new MatrizEsparsaTriplas(10, 10);

        m.set(0, 2, 10.5);  m.set(1, 4, 25.0);  m.set(2, 0, 7.8);
        m.set(3, 8, 42.1);  m.set(4, 3, 15.3);  m.set(5, 5, 99.9);
        m.set(6, 1, 3.14);  m.set(7, 9, 88.0);  m.set(8, 7, 50.2);
        m.set(9, 6, 12.4);

        System.out.printf("  get(5,5)=%.2f  get(0,0)=%.2f%n", m.get(5, 5), m.get(0, 0));

        m.set(3, 8, 100.0);
        System.out.printf("  update(3,8)=%.2f%n", m.get(3, 8));

        m.imprimir();
    }

    public static void compararDensidades(int n) {
        System.out.printf("%n=== BENCHMARK ESPARSA vs DENSA (%dx%d) ===%n", n, n);
        long total = (long) n * n;
        MatrizDensa densaRef = new MatrizDensa(n, n);
        long memDensa = densaRef.estimarMemoriaBytes();

        System.out.printf("%-8s | %8s | %10s | %10s | %10s | %10s%n",
                "Dens.", "nnz", "Mem.Densa", "Mem.Esp.", "T.Densa", "T.Esparsa");
        System.out.println("-".repeat(72));

        Random rand = new Random(42);
        double[] densidades = {0.001, 0.01, 0.05, 0.10, 0.15, 0.20, 0.25, 0.30, 0.50};

        for (double d : densidades) {
            int nnz = (int) (total * d);
            MatrizEsparsaTriplas esp = new MatrizEsparsaTriplas(n, n);
            MatrizDensa den = new MatrizDensa(n, n);

            for (int k = 0; k < nnz; k++) {
                int i = rand.nextInt(n), j = rand.nextInt(n);
                double v = 1 + rand.nextDouble() * 10;
                esp.set(i, j, v); den.set(i, j, v);
            }

            long memEsp = esp.estimarMemoriaBytes();
            double[] acc = {0};
            int reps = 5;
            long tEsp = 0, tDen = 0;

            for (int r = 0; r < reps; r++) {
                long t0 = System.nanoTime(); esp.percorrerNaoNulos(t -> acc[0] += t.getValor()); tEsp += System.nanoTime() - t0;
                long t1 = System.nanoTime(); den.percorrerNaoNulos(t -> acc[0] += t.getValor()); tDen += System.nanoTime() - t1;
            }

            System.out.printf("%6.1f%% | %8d | %8.2f MB | %8.2f MB | %7.2f ms | %7.2f ms%n",
                    d * 100, esp.getNaoNulos(),
                    memDensa / 1048576.0, memEsp / 1048576.0,
                    tDen / (double) reps / 1e6, tEsp / (double) reps / 1e6);
        }

        System.out.println("\nPonto de corte em memória: ~22% de densidade (8B densa / 36B tripla).");
        System.out.println("Acima disso, a representação esparsa por triplas deixa de valer a pena.");
    }
}
