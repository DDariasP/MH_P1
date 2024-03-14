//package mh;
//
//import java.util.Random;
//
///**
// *
// * @author diego
// */
//public class BusquedaLocal {
//
//    public static final int MAX = 5000;
//    public final int SEED;
//    public Random rand;
//
//    public BusquedaLocal(int a) {
//        SEED = a;
//        rand = new Random(SEED);
//    }
//
//    public Solucion BL(Integer[] p) {
//
//        int ciu = p[0];
//        int pal = p[1];
//        int cam = p[2];
//        
//        int eval = 0;
//        int maxeval = MAX * ciu;
//
//        Solucion inicial = Solucion.genAleatoria(cam, pal, ciu, rand);
//        eval++;
//        inicial.eval = eval;
//        inicial.coste = Solucion.funCoste(inicial);
//
//        Solucion mejor = new Solucion(inicial);
//
////        while (eval < maxeval) {
////            Solucion actual = 
////            eval++;
////            actual.eval = eval;
////            actual.coste = Solucion.funCoste(actual);
////            if (actual.coste < mejor.coste) {
////                mejor = actual;
////            }
////        }
//
//        return mejor;
//    }
//
//}
