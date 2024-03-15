package mh;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author diego
 */
public class Parser {

    public static Matriz leerDist(int ciu, String filename) {
        Matriz listaDist = new Matriz(ciu, ciu, -1);
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            String line;
            String[] tokens;
            int contador = 0;
            while (contador < ciu) {
                line = scanner.nextLine();
                tokens = line.split("\\s+");
                int[] fila = new int[ciu];
                for (int i = 0; i < ciu; i++) {
                    fila[i] = Integer.parseInt(tokens[i]);
                }
                listaDist.m[contador] = fila;
                contador++;
            }
            scanner.close();
        } catch (IOException ex) {
            return null;
        }
        return listaDist;
    }

    public static ArrayList<Integer> leerPal(String filename) {
        ArrayList<Integer> listaPal = new ArrayList();
        try {
            File file = new File(filename);
            Scanner scanner = new Scanner(file);
            String line;
            while (scanner.hasNextLine()) {
                line = scanner.nextLine();
                listaPal.add(Integer.valueOf(line));
            }
            scanner.close();
        } catch (IOException ex) {
            return null;
        }
        return listaPal;
    }

}
