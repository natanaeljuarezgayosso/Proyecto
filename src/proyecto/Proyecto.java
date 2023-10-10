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
        System.out.println("Ingrese los coeficientes de la función objetivo:");
        for (int i = 0; i < numVariables; i++) {
            matriz[0][i] = scanner.nextDouble();
        }
        for (int i = 1; i <= numRestricciones; i++) {
            System.out.println("Ingrese el tipo de restricción ('<=') para la restricción " + i + ":");
            String tipoRestriccion = scanner.next();
            for (int j = 0; j < numVariables; j++) {
                matriz[i][j] = scanner.nextDouble();
            }
            if (tipoRestriccion.equals("<=")) {
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

       
        SimplexSolver solver = new SimplexSolver();
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
    }
    
}
