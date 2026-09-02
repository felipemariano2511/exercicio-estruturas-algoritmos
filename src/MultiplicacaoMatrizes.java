import java.util.Random;

public class MultiplicacaoMatrizes {

    // Ordem I-J-K: laço interno varia k, acesso a B[k][j] salta linhas (cache miss)
    public static double[][] multiplicarIJK(double[][] A, double[][] B) {
        int n = A.length;
        double[][] C = new double[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) {
                double soma = 0;
                for (int k = 0; k < n; k++) soma += A[i][k] * B[k][j];
                C[i][j] = soma;
            }
        return C;
    }

    // Ordem I-K-J: laço interno varia j, acessos contíguos em B[k][j] e C[i][j]
    public static double[][] multiplicarIKJ(double[][] A, double[][] B) {
        int n = A.length;
        double[][] C = new double[n][n];
        for (int i = 0; i < n; i++)
            for (int k = 0; k < n; k++) {
                double r = A[i][k];
                for (int j = 0; j < n; j++) C[i][j] += r * B[k][j];
            }
        return C;
    }

    public static double[][] gerarAleatoria(int n, long seed) {
        Random rand = new Random(seed);
        double[][] M = new double[n][n];
        for (int i = 0; i < n; i++)
            for (int j = 0; j < n; j++) M[i][j] = rand.nextDouble();
        return M;
    }

    public static void benchmark(int n, int reps) {
        // Warmup JIT
        double[][] aw = gerarAleatoria(200, 1), bw = gerarAleatoria(200, 2);
        for (int w = 0; w < 3; w++) { multiplicarIJK(aw, bw); multiplicarIKJ(aw, bw); }

        double[][] A = gerarAleatoria(n, 12345), B = gerarAleatoria(n, 67890);
        long tIJK = 0, tIKJ = 0;

        for (int r = 0; r < reps; r++) {
            System.gc();
            long t0 = System.nanoTime(); multiplicarIJK(A, B); tIJK += System.nanoTime() - t0;
            System.gc();
            long t1 = System.nanoTime(); multiplicarIKJ(A, B); tIKJ += System.nanoTime() - t1;
        }

        double msIJK = tIJK / (double) reps / 1e6;
        double msIKJ = tIKJ / (double) reps / 1e6;
        System.out.printf("  %dx%d => IJK: %.1f ms | IKJ: %.1f ms | Speedup: %.2fx%n", n, n, msIJK, msIKJ, msIJK / msIKJ);
    }
}
