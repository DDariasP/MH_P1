package mh;

import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author diego
 */
public class EnfriamientoSimulado {

    public static final int MAX = 5000;
    public final int SEED;
    public Random rand;
    public Solucion[] solES;

    public static final double KA = 0.9;
    public static final int VECIN = 100;
    public static final int KI = 50;
    public double T, delta, aceptacion, T0, sigma, lnCiu;

    public EnfriamientoSimulado(int a) {
        SEED = a;
        rand = new Random(SEED);
        solES = new Solucion[P1.NUMP];
    }

    public void ejecutarES() {
        for (int i = 0; i < P1.NUMP; i++) {
            solES[i] = ES(i);
            System.out.println("T0=" + solES[i].T0);
            System.out.println("enfr=" + solES[i].enfr);
            System.out.println("TF=" + solES[i].TF);
            System.out.println(solES[i].coste + "\t" + solES[i].eval);
        }
    }

    public Solucion ES(int tamP) {
        Integer[] P = P1.P.get(tamP);
        int ciu = P[0];
        int cam = P[2];
        ArrayList<Integer> listaPal = P1.listaPal.get(tamP);
        Matriz listaDist = P1.listaDist.get(tamP);
        int filas = listaDist.filas;

        double dividendo = 0.0;
        double divisor = 0.0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j <= i; j++) {
                int d = listaDist.m[i][j];
                dividendo = dividendo + d;
                divisor++;

            }
        }
        double media = dividendo / divisor;

        double sumatorio = 0.0;
        for (int i = 0; i < filas; i++) {
            for (int j = 0; j <= i; j++) {
                int d = listaDist.m[i][j];
                double resta = d - media;
                sumatorio = sumatorio + Math.pow(resta, 2);

            }
        }
        sigma = Math.sqrt(sumatorio / divisor);
        lnCiu = Math.log(ciu);
        T0 = sigma / lnCiu;

        T = T0;
        int eval = 0;
        int maxeval = MAX * ciu;
        int enfr = 0;
        int maxenfr = KI * ciu;

        Solucion mejor = Solucion.genRandom(cam, listaPal, rand);
        mejor.coste = Solucion.funCoste(mejor, listaDist);
        eval++;
        mejor.eval = eval;
        mejor.T0 = T0;
        mejor.TF = T0;
        mejor.enfr = enfr;

        while (true) {
            int vecindario = 0;
            while (true) {
                Solucion candidata = Solucion.gen2optAlt(cam, mejor, rand);
                candidata.coste = Solucion.funCoste(candidata, listaDist);
                eval++;
                candidata.eval = eval;
                candidata.T0 = T0;
                candidata.TF = T;
                candidata.enfr = enfr;
                vecindario++;
                delta = candidata.coste - mejor.coste;
                aceptacion = rand.nextDouble();
                if (delta < 0 || aceptacion < Math.exp(-delta / T)) {
                    mejor = candidata;
                }
                if (vecindario == VECIN - 1) {
                    T = KA * T;
                    enfr++;
                    break;
                }
                if (eval == maxeval - 1) {
                    break;
                }
            }
            if (enfr == maxenfr - 1) {
                break;
            }
            if (eval == maxeval - 1) {
                break;
            }
        }

        return mejor;
    }
}
