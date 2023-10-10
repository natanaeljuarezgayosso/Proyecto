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
    }
    
}
