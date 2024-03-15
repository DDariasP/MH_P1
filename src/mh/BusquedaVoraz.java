package mh;

import java.util.ArrayList;

/**
 *
 * @author diego
 */
public class BusquedaVoraz {

    public Solucion[] sbv;

    public BusquedaVoraz() {
        sbv = new Solucion[P1.NUMP];
    }

    public void ejecutarBV() {
        for (int i = 0; i < P1.NUMP; i++) {
            sbv[i] = BV(i);
            System.out.println(sbv[i].coste);
            System.out.println(sbv[i].m);
        }
    }

    public Solucion BV(int tamP) {
        Integer[] P = P1.P.get(tamP);
        int cam = P[2];
        ArrayList<Integer> listaPal = P1.listaPal.get(tamP);
        Matriz listaDist = P1.listaDist.get(tamP);

        int[] ultimopal = new int[cam];
        int[] palxcam = new int[cam];
        for (int i = 0; i < cam; i++) {
            ultimopal[i] = 1;
            palxcam[i] = 0;
        }

        Matriz matriz = new Matriz(cam, P1.MAXPAL, -1);

        for (int i = 0; i < listaPal.size(); i++) {
            int ciupal = listaPal.get(i) - 1;
            int[] distcalc = new int[cam];
            for (int j = 0; j < cam; j++) {
                int ciucam = ultimopal[j] - 1;
                distcalc[j] = listaDist.m[ciucam][ciupal];
            }
            int distmin = Integer.MAX_VALUE;
            int elegido = -1;
            for (int j = 0; j < cam; j++) {
                if (palxcam[j] < P1.MAXPAL && distmin > distcalc[j]) {
                    distmin = distcalc[j];
                    elegido = j;
                }
            }
            matriz.m[elegido][palxcam[elegido]] = ciupal + 1;
            ultimopal[elegido] = ciupal + 1;
            palxcam[elegido]++;
        }

        Solucion s = new Solucion(matriz);
        s.coste = Solucion.funCoste(s, listaDist);
        return s;
    }

}
