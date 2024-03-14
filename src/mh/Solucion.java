package mh;

import java.util.Random;
import java.util.ArrayList;

/**
 *
 * @author diego
 */
public class Solucion {

    public int eval;
    public Matriz m;
    public int coste;

    public Solucion(int a, int b, int[][] n) {
        eval = -1;
        m = new Matriz(a, b, n);
        coste = -1;
    }

    public Solucion(Solucion s) {
        eval = s.eval;
        m = new Matriz(s.m);
        coste = s.coste;
    }

    public static Solucion genAleatoria(int cam, ArrayList<Integer> listaPal, Random rand) {
        int[][] matriz = new int[cam][P1.MAXPAL];
        int[] guardados = new int[cam];
        for (int i = 0; i < cam; i++) {
            guardados[i] = 0;
            for (int j = 0; j < P1.MAXPAL; j++) {
                matriz[i][j] = -1;
            }
        }

        int contador = 0;
        while (contador < listaPal.size()) {
            int palet = listaPal.get(contador);
            int x = rand.nextInt(cam);
            int y = 0;
            while (guardados[x] == P1.MAXPAL) {
                x = (x + 1) % cam;
            }
            while (matriz[x][y] != -1) {
                y++;
            }
            matriz[x][y] = palet;
            contador++;
            guardados[x]++;
        }

        Solucion s = new Solucion(cam, P1.MAXPAL, matriz);
        return s;
    }

    public static int funCoste(Solucion s, Matriz listaDist) {
        int coste = 0;

        ArrayList<Integer> visitadas = new ArrayList<>();
        int actual = 0;
        visitadas.add(actual);

        for (int i = 0; i < s.m.filas; i++) {
            int[] camion = s.m.m[i];
            for (int j = 0; j < camion.length; j++) {
                int siguiente = camion[j] - 1;
                if (!visitadas.contains(siguiente) && siguiente != actual) {
                    coste = coste + listaDist.m[actual][siguiente];
                    actual = siguiente;
                    visitadas.add(actual);
                }
            }
        }
        coste = coste + listaDist.m[actual][0];

        return coste;
    }
}
