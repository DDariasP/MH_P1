package mh;

import java.util.Random;

/**
 *
 * @author diego
 */
public class Movimiento {

    public int oriX, oriY, destX, destY;

    public Movimiento() {
        oriX = -1;
        oriY = -1;
        destX = -1;
        destY = -1;
    }

    Movimiento(Random rand, int cam) {
        oriX = rand.nextInt(cam);
        destX = rand.nextInt(cam);
        oriY = rand.nextInt(P1.MAXPAL);
        destY = rand.nextInt(P1.MAXPAL);
    }

    public static void aplicar(Movimiento mov, Matriz matriz) {
        int tmp;
        tmp = matriz.m[mov.oriX][mov.oriY];
        matriz.m[mov.oriX][mov.oriY] = matriz.m[mov.destX][mov.destY];
        matriz.m[mov.destX][mov.destY] = tmp;
    }

    @Override
    public boolean equals(Object o) {

        if (o == this) {
            return true;
        }

        if (!(o instanceof Movimiento)) {
            return false;
        }

        Movimiento m = (Movimiento) o;

        return (oriX == m.oriX && oriY == m.oriY
                && destX == m.destX && destY == m.destY);
    }

    @Override
    public String toString() {
        String output = "[" + oriX + "][" + oriY + "] -> [" + destX + "][" + destY + "]";
        return output;
    }

}
