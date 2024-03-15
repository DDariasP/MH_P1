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
    public double T0;
    public double TF;
    public int enfr;

    public Solucion(Matriz n) {
        eval = -1;
        m = new Matriz(n);
        coste = -1;
    }

    public static Solucion genRandom(int cam, ArrayList<Integer> listaPal, Random rand) {
        Matriz matriz = new Matriz(cam, P1.MAXPAL, -1);

        int[] palxcam = new int[cam];
        for (int i = 0; i < cam; i++) {
            palxcam[i] = 0;
        }

        int contador = 0;
        while (contador < listaPal.size()) {
            int palet = listaPal.get(contador);
            int x = rand.nextInt(cam);
            int y = 0;
            while (palxcam[x] == P1.MAXPAL) {
                x = (x + 1) % cam;
            }
            while (matriz.m[x][y] != -1) {
                y++;
            }
            matriz.m[x][y] = palet;
            contador++;
            palxcam[x]++;
        }

        return (new Solucion(matriz));
    }

    public static Solucion gen2opt(int cam, Solucion s, Random rand) {
        Matriz matriz = new Matriz(s.m);

        int x1 = rand.nextInt(cam);
        int y1 = rand.nextInt(P1.MAXPAL);
        int x2 = rand.nextInt(cam);
        int y2 = rand.nextInt(P1.MAXPAL);

        matriz.m[x1][y1] = matriz.m[x2][y2];

        return (new Solucion(matriz));
    }

    public static int funCoste(Solucion s, Matriz listaDist) {
        int coste = 0;
        for (int i = 0; i < s.m.filas; i++) {
            ArrayList<Integer> visitadas = new ArrayList<>();
            int actual = 0;
            visitadas.add(actual);
            int[] camion = s.m.m[i];
            for (int j = 0; j < camion.length; j++) {
                int siguiente = camion[j] - 1;
                if (!visitadas.contains(siguiente) && siguiente != actual) {
                    coste = coste + listaDist.m[actual][siguiente];
                    actual = siguiente;
                    visitadas.add(actual);
                }
            }
            coste = coste + listaDist.m[actual][0];
        }
        return coste;
    }
}
