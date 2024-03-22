package mh;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author diego
 */
public class BusquedaAleatoria {

    public static final int MAX = 5000;
    public final int SEED;
    public Random rand;
    public Solucion[] solBA;

    public BusquedaAleatoria(int a) {
        SEED = a;
        rand = new Random(SEED);
        solBA = new Solucion[P1.NUMP];
    }

    public void ejecutarBA() {
        for (int i = 0; i < P1.NUMP; i++) {
            solBA[i] = BA(i);
            System.out.println(solBA[i].coste + "\t" + solBA[i].eval);
        }
    }

    public Solucion BA(int tamP) {
        Integer[] P = P1.P.get(tamP);
        int ciu = P[0];
        int cam = P[2];
        int eval = 0;
        int maxeval = MAX * ciu;
        ArrayList<Integer> listaPal = P1.listaPal.get(tamP);
        Matriz listaDist = P1.listaDist.get(tamP);

        Solucion mejor = Solucion.genRandom(cam, listaPal, rand);
        mejor.coste = Solucion.funCoste(mejor, listaDist);
        eval++;
        mejor.eval = eval;

        while (eval < maxeval) {
            Solucion siguiente = Solucion.genRandom(cam, listaPal, rand);
            siguiente.coste = Solucion.funCoste(siguiente, listaDist);
            eval++;
            siguiente.eval = eval;
            if (mejor.coste > siguiente.coste) {
                mejor = siguiente;
            }
        }

        return mejor;
    }

}
