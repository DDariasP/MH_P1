package mh;

import java.util.ArrayList;

/**
 *
 * @author diego
 */
public class P1 {

    public static final int MAXPAL = 14;
    public static final int NUMP = 3;
    public static final Integer[] P0 = {25, 84, 6};
    public static final Integer[] P1 = {38, 126, 9};
    public static final Integer[] P2 = {50, 168, 12};
    public static final int[] SEED = {111, 222, 333, 123, 321};
    public static ArrayList<Integer[]> P;
    public static ArrayList<Matriz> listaDist;
    public static ArrayList<ArrayList<Integer>> listaPal;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {

        RandomTest.randomTest();

        P = new ArrayList<>();
        P.add(P0);
        P.add(P1);
        P.add(P2);

        listaDist = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int ciu = P.get(i)[0];
            listaDist.add(Parser.leerDist(ciu, "matriz_distancias_" + ciu + ".txt"));
        }

        listaPal = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            int pal = P.get(i)[1];
            listaPal.add(Parser.leerPal("destinos_palets_" + pal + ".txt"));
        }

        BusquedaAleatoria[] ba = new BusquedaAleatoria[SEED.length];
        for (int i = 0; i < SEED.length; i++) {
                ba[i] = new BusquedaAleatoria(SEED[i]);
                ba[i].ejecutarBA();
            }
        }


}
