package mh;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author diego
 */
public class BusquedaTaboo {

    public static final int MAX = 5000;
    public final int SEED;
    public Random rand;
    public Solucion[] solBT;

    public static final int RESTART = 10;
    public static final int VECIN = 100;
    public static final double KRAND = 0.25;
    public static final double KGREED = 0.50;
    public static final double KREST = 0.25;
    public static final double KSIZE = 0.50;
    public double tenencia;
    public ArrayList<Movimiento> listaTaboo;
    public Solucion elite;
    public int[][][] memoriaM;

    public BusquedaTaboo(int a) {
        SEED = a;
        rand = new Random(SEED);
        solBT = new Solucion[P1.NUMP];
    }

    public void ejecutarBT() {
        for (int i = 0; i < P1.NUMP; i++) {
            solBT[i] = BT(i);
            System.out.println(solBT[i].coste + "\t" + solBT[i].eval);
        }
    }

    public Solucion BT(int tamP) {
        Integer[] P = P1.P.get(tamP);
        int ciu = P[0];
        int cam = P[2];
        int eval = 0;
        int maxeval = MAX * ciu;
        int iter = 0;
        int maxiter = maxeval / RESTART;
        int reini = 0;
        int vecindario = 0;
        ArrayList<Integer> listaPal = P1.listaPal.get(tamP);
        Matriz listaDist = P1.listaDist.get(tamP);

        tenencia = 4.0;
        memoriaM = new int[cam][P1.MAXPAL][ciu];
        for (int i = 0; i < cam; i++) {
            for (int j = 0; j < P1.MAXPAL; j++) {
                for (int k = 0; k < ciu; k++) {
                    memoriaM[i][j][k] = 0;
                }
            }
        }
        Solucion inicial = Solucion.genRandom(cam, listaPal, rand);
        inicial.coste = Solucion.funCoste(inicial, listaDist);
        eval++;
        inicial.eval = eval;
        Solucion actual, candidata, mejor;
        elite = new Solucion(new Matriz(1, 1, 0));
        double[] probAcum = {KRAND, KRAND + KGREED, KRAND + KGREED + KREST};

        while (eval < maxeval && reini < RESTART) {

            //Limpiando la lista tabu
            listaTaboo = new ArrayList<>();
            if (reini > 0) {
                if (rand.nextBoolean()) {
                    tenencia = Math.round(tenencia + tenencia * KSIZE);
                } else {
                    tenencia = Math.max(Math.round(tenencia - tenencia * KSIZE), 2.0);
                }

                double selector = rand.nextDouble();
                int indice = 0;
                while (indice < probAcum.length && selector >= probAcum[indice]) {
                    indice++;
                }
                switch (indice) {
                    case 0:
                        inicial = Solucion.genRandom(cam, listaPal, rand);
                        break;
                    case 1:
                        inicial = Solucion.genMemoriaM(cam, listaPal, memoriaM);
                        break;
                    case 2:
                        inicial = elite;
                        break;
                    default:
                        throw new AssertionError();
                }
                inicial.coste = Solucion.funCoste(inicial, listaDist);
                eval++;
                inicial.eval = eval;
                if (elite.coste > inicial.coste) {
                    elite = inicial;
                }
            }

            actual = inicial;
            mejor = new Solucion(new Matriz(1, 1, 0));
            iter = 0;
            while (iter < maxiter) {
                vecindario = 0;
                while (vecindario < VECIN) {
                    candidata = Solucion.genTaboo(cam, actual, rand, listaTaboo);
                    candidata.coste = Solucion.funCoste(candidata, listaDist);
                    eval++;
                    candidata.eval = eval;
                    iter++;
                    vecindario++;
                    if (mejor.coste > candidata.coste) {
                        mejor = candidata;
                    }
                }
                actual = mejor;
                actualizarTaboo(actual);
                actualizarMemoria(actual.m, cam);
                if (elite.coste > actual.coste) {
                    elite = actual;
                }
            }
            reini++;
        }

        return elite;
    }

    private void actualizarTaboo(Solucion s) {
        if (!listaTaboo.contains(s.mov)) {
            if (listaTaboo.size() == tenencia) {
                listaTaboo.remove(0);
            }
            listaTaboo.add(s.mov);
        }
    }

    private void actualizarMemoria(Matriz m, int cam) {
        for (int i = 0; i < cam; i++) {
            for (int j = 0; j < P1.MAXPAL; j++) {
                int ciu = m.m[i][j] - 1;
                memoriaM[i][j][ciu]++;
            }
        }
    }

}
