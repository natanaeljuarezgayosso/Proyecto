/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Main.java to edit this template
 */
package proyecto;

import java.util.Scanner;

/**
 *
 * @author natan
 */
public class Proyecto {

    /**
     * @param args the command line arguments
     */
    
   public static void main(String[] args) {
    Scanner scanner = new Scanner(System.in);

    System.out.println("Ingrese 'max' para maximizar o 'min' para minimizar: ");
    String objetivo = scanner.nextLine();

    System.out.println("Ingrese la cantidad de variables de decisión: ");
    int numVariables = scanner.nextInt();

    System.out.println("Ingrese la cantidad de restricciones: ");
    int numRestricciones = scanner.nextInt();

    double[][] matriz = new double[numRestricciones + 1][numVariables + numRestricciones + 2];

    System.out.println("Ingrese los coeficientes de la función objetivo (los números hacia abajo pulsando enter):");
    for (int i = 0; i < numVariables; i++) {
        matriz[0][i] = scanner.nextDouble();
    }

    for (int i = 1; i <= numRestricciones; i++) {
        System.out.println("Ingrese el tipo de restricción (empezando por '<=' y los demás números hacia abajo) para la restricción " + i + ":");
        String tipoRestricciones = scanner.next();

        for (int j = 0; j < numVariables; j++) {
            matriz[i][j] = scanner.nextDouble();
        }

        if (tipoRestricciones.equals("<=")) {
            matriz[i][numVariables + i - 1] = 1.0;
        } else {
            matriz[i][numVariables + i - 1] = -1.0;
        }

        System.out.println("Ingrese el valor del lado derecho de la restricción " + i + ":");
        matriz[i][numVariables + numRestricciones] = scanner.nextDouble();
    }

    System.out.println("Ingrese los coeficientes de las variables de holgura o exceso en la función objetivo:");
    for (int i = 1; i <= numRestricciones; i++) {
        matriz[0][numVariables + i - 1] = scanner.nextDouble();
    }

    System.out.println("Ingrese los valores iniciales de las variables básicas:");
    for (int i = 1; i <= numRestricciones; i++) {
        matriz[i][numVariables + numRestricciones + 1] = scanner.nextDouble();
    }

       
        Proyecto solver = new Proyecto();
        boolean solucionEncontrada = solver.solve(matriz, numVariables, numRestricciones);

        if (solucionEncontrada) {
            System.out.println("\nMatriz resultante:");
            solver.imprimirMatriz(matriz);

            System.out.println("\nResultado:");
            solver.mostrarResultado(matriz, numVariables, numRestricciones);
        } else {
            System.out.println("No se encontró una solución factible inicial.");
        }

        scanner.close();
    }

    public boolean solve(double[][] matriz, int numVariables, int numRestricciones) {
        while (true) {
            int columnaPivot = encontrarColumnaPivot(matriz);
            if (columnaPivot == -1) {
                return true;
            }

            int filaPivot = encontrarFilaPivot(matriz, columnaPivot);
            if (filaPivot == -1) {
                return false;
            }

            realizarPasoSimplex(matriz, filaPivot, columnaPivot);
        }
    }
    private int encontrarColumnaPivot(double[][] matriz) {
        int numColumnas = matriz[0].length;
        int columnaPivot = -1;
        for (int j = 0; j < numColumnas - 1; j++) {
            if (matriz[0][j] > 0) {
                columnaPivot = j;
                break;
            }
        }
        return columnaPivot;
    }
    private int encontrarFilaPivot(double[][] matriz, int columnaPivot) {
        int numFilas = matriz.length;
        int numColumnas = matriz[0].length;

        int filaPivot = -1;
        double minRatio = Double.MAX_VALUE;

        for (int i = 1; i < numFilas; i++) {
            if (matriz[i][columnaPivot] > 0) {
                double ratio = matriz[i][numColumnas - 1] / matriz[i][columnaPivot];
                if (ratio < minRatio) {
                    minRatio = ratio;
                    filaPivot = i;
                }
            }
        }
        return filaPivot;
    }
    
    private void realizarPasoSimplex(double[][] matriz, int filaPivot, int columnaPivot) {
        int numFilas = matriz.length;
        int numColumnas = matriz[0].length;

        
        double pivot = matriz[filaPivot][columnaPivot];
        for (int j = 0; j < numColumnas; j++) {
            matriz[filaPivot][j] /= pivot;
        }

     
        for (int i = 0; i < numFilas; i++) {
            if (i != filaPivot) {
                double factor = matriz[i][columnaPivot];
                for (int j = 0; j < numColumnas; j++) {
                    matriz[i][j] -= factor * matriz[filaPivot][j];
                }
            }
        }
    }
    public void imprimirMatriz(double[][] matriz) {
        int numFilas = matriz.length;
        int numColumnas = matriz[0].length;

        for (int i = 0; i < numFilas; i++) {
            for (int j = 0; j < numColumnas; j++) {
                System.out.printf("%.2f\t", matriz[i][j]);
            }
            System.out.println();
        }
    }
    public void mostrarResultado(double[][] matriz, int numVariables, int numRestricciones) {
        int numColumnas = matriz[0].length;

        System.out.println("Valor de Z (Función Objetivo): " + matriz[0][numColumnas - 1]);

        for (int i = 1; i <= numRestricciones; i++) {
            int indiceVariableBasica = -1;
            for (int j = 0; j < numVariables; j++) {
                if (matriz[i][j] == 1) {
                    indiceVariableBasica = j;
                    break;
                }
            }

            if (indiceVariableBasica != -1) {
                System.out.println("Variable básica x" + (indiceVariableBasica + 1) + ": " + matriz[i][numColumnas - 1]);
            }
        }
    }
}
    

