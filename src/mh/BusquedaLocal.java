package mh;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author diego
 */
public class BusquedaLocal {

    public static final int MAX = 1000;
    public final int SEED;
    public Random rand;
    public Solucion[] sbl;

    public BusquedaLocal(int a) {
        SEED = a;
        rand = new Random(SEED);
        sbl = new Solucion[P1.NUMP];
    }

    public void ejecutarBL() {
        for (int i = 0; i < P1.NUMP; i++) {
            sbl[i] = BL(i);
            System.out.println(sbl[i].coste + "\t" + sbl[i].eval);
        }
    }

    public Solucion BL(int tamP) {
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
            Solucion siguiente = Solucion.gen2opt(cam, mejor, rand);
            siguiente.coste = Solucion.funCoste(siguiente, listaDist);
            eval++;
            siguiente.eval = eval;
            if (mejor.coste > siguiente.coste) {
                mejor = siguiente;
            }
        }
        System.out.println("maxeval=" + eval);

        return mejor;
    }

}
