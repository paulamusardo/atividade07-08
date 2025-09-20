import java.io.*;
import java.util.*;

public class matrizCalculator {
    
    // Função para somar dois números
    public static double somar(double a, double b) {
        return a + b;
    }
    
    // Função para multiplicar dois números
    public static double multiplicar(double a, double b) {
        return a * b;
    }
    
    // Função para ler matriz de arquivo
    public static double[][] lerMatrizDoArquivo(String nomeArquivo) throws IOException {
        List<String[]> linhas = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(nomeArquivo));
        String linha;
        
        while ((linha = reader.readLine()) != null) {
            if (!linha.trim().isEmpty()) {
                String[] valores = linha.trim().split("\\s+");
                linhas.add(valores);
            }
        }
        reader.close();
        
        if (linhas.isEmpty()) {
            throw new IOException("Arquivo vazio ou inválido");
        }
        
        int linhasCount = linhas.size();
        int colunasCount = linhas.get(0).length;
        
        // Verificar se todas as linhas têm o mesmo número de colunas
        for (String[] valores : linhas) {
            if (valores.length != colunasCount) {
                throw new IOException("Matriz não é retangular - número de colunas inconsistente");
            }
        }
        
        double[][] matriz = new double[linhasCount][colunasCount];
        
        for (int i = 0; i < linhasCount; i++) {
            for (int j = 0; j < colunasCount; j++) {
                try {
                    matriz[i][j] = Double.parseDouble(linhas.get(i)[j]);
                } catch (NumberFormatException e) {
                    throw new IOException("Valor inválido na posição [" + i + "][" + j + "]: " + linhas.get(i)[j]);
                }
            }
        }
        
        return matriz;
    }
    
    // Função para verificar se a matriz é quadrada
    public static boolean ehQuadrada(double[][] matriz) {
        return matriz.length == matriz[0].length;
    }
    
    // Função para imprimir matriz
    public static void imprimirMatriz(double[][] matriz, String titulo) {
        System.out.println("\n" + titulo + ":");
        System.out.println("Tamanho: " + matriz.length + "x" + matriz[0].length);
        System.out.println("Matriz:");
        
        for (int i = 0; i < matriz.length; i++) {
            System.out.print("[");
            for (int j = 0; j < matriz[i].length; j++) {
                System.out.printf("%8.2f", matriz[i][j]);
                if (j < matriz[i].length - 1) {
                    System.out.print(", ");
                }
            }
            System.out.println("]");
        }
    }
    
    // Função para multiplicar duas matrizes com passo a passo
    public static double[][] multiplicarMatrizes(double[][] matrizA, double[][] matrizB) {
        int linhasA = matrizA.length;
        int colunasA = matrizA[0].length;
        int linhasB = matrizB.length;
        int colunasB = matrizB[0].length;
        
        System.out.println("\n=== PASSO A PASSO DA MULTIPLICAÇÃO ===");
        System.out.println("Matriz A: " + linhasA + "x" + colunasA);
        System.out.println("Matriz B: " + linhasB + "x" + colunasB);
        
        if (colunasA != linhasB) {
            throw new IllegalArgumentException("Não é possível multiplicar: colunas de A (" + colunasA + 
                                             ") deve ser igual às linhas de B (" + linhasB + ")");
        }
        
        double[][] resultado = new double[linhasA][colunasB];
        
        System.out.println("Resultado será: " + linhasA + "x" + colunasB);
        System.out.println("\nCalculando cada elemento:");
        
        for (int i = 0; i < linhasA; i++) {
            for (int j = 0; j < colunasB; j++) {
                System.out.printf("\nElemento [%d][%d]: ", i, j);
                double soma = 0;
                
                for (int k = 0; k < colunasA; k++) {
                    double produto = multiplicar(matrizA[i][k], matrizB[k][j]);
                    soma = somar(soma, produto);
                    
                    System.out.printf("A[%d][%d] * B[%d][%d] = %.2f * %.2f = %.2f", 
                                    i, k, k, j, matrizA[i][k], matrizB[k][j], produto);
                    
                    if (k < colunasA - 1) {
                        System.out.print(" + ");
                    }
                }
                
                resultado[i][j] = soma;
                System.out.printf(" = %.2f", soma);
            }
        }
        
        return resultado;
    }
    
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        try {
            System.out.println("=== CALCULADORA DE MATRIZES ===");
            System.out.println("Este programa lê matrizes de arquivos e calcula sua multiplicação");
            
            // Ler primeira matriz
            System.out.print("\nDigite o nome do arquivo da primeira matriz (ex: matriz1.txt): ");
            String arquivo1 = scanner.nextLine();
            double[][] matrizA = lerMatrizDoArquivo(arquivo1);
            
            // Verificar se é quadrada
            boolean quadradaA = ehQuadrada(matrizA);
            System.out.println("Matriz A é quadrada: " + quadradaA);
            
            // Ler segunda matriz
            System.out.print("Digite o nome do arquivo da segunda matriz (ex: matriz2.txt): ");
            String arquivo2 = scanner.nextLine();
            double[][] matrizB = lerMatrizDoArquivo(arquivo2);
            
            // Verificar se é quadrada
            boolean quadradaB = ehQuadrada(matrizB);
            System.out.println("Matriz B é quadrada: " + quadradaB);
            
            // Mostrar matrizes de entrada
            imprimirMatriz(matrizA, "MATRIZ A (ENTRADA)");
            imprimirMatriz(matrizB, "MATRIZ B (ENTRADA)");
            
            // Calcular multiplicação
            double[][] resultado = multiplicarMatrizes(matrizA, matrizB);
            
            // Mostrar resultado
            imprimirMatriz(resultado, "MATRIZ RESULTADO (SAÍDA)");
            
            System.out.println("\n=== RESUMO ===");
            System.out.println("✓ Matriz A é quadrada: " + quadradaA);
            System.out.println("✓ Matriz B é quadrada: " + quadradaB);
            System.out.println("✓ Multiplicação realizada com sucesso!");
            
        } catch (IOException e) {
            System.err.println("Erro ao ler arquivo: " + e.getMessage());
        } catch (IllegalArgumentException e) {
            System.err.println("Erro na multiplicação: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }
}