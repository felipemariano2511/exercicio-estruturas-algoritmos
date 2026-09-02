public class Main {
    public static void main(String[] args) {
        System.out.println("=== MULTIPLICAÇÃO: ORDEM DOS LAÇOS ===");
        MultiplicacaoMatrizes.benchmark(500, 3);
        MultiplicacaoMatrizes.benchmark(800, 3);
        ComparadorEsparsaDensa.demonstrarDezLinhas();
        ComparadorEsparsaDensa.compararDensidades(800);
    }
}