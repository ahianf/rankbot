package cl.ahianf.rankbot.entity;

import cl.ahianf.rankbot.Par;
import cl.ahianf.rankbot.ParElo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static cl.ahianf.rankbot.Elo.eloRating;
import static java.lang.Math.sqrt;

public class Test {


    public static void main(String[] args) {

        System.out.println(matchIdToParAlgebraic(78));

    }

    private static ParElo resultadoAParElo(ParElo parelo, Results resultado) {
        ArrayList<Integer> listOfDoubles = new ArrayList<>();
        for (int i = 0; i < resultado.getWinsX(); i++) {
            listOfDoubles.add(1);
        }

        for (int i = 0; i < resultado.getWinsY(); i++) {
            listOfDoubles.add(2);
        }

        for (int i = 0; i < resultado.getDraws(); i++) {
            listOfDoubles.add(3);
        }
        Collections.shuffle(listOfDoubles);

        for (int i : listOfDoubles) {
            parelo = eloRating(parelo, i);
        }
        return parelo;
    }

    public static Par matchIdToParAlgebraic(int i) {
        //TODO: Revisar si calcular nLessOnetriangular es más rápido que solo verificar si es numero sin decimales
        int inverseTriangular = (int) ((-1 + sqrt(1 + (8 * i))) / 2) + 1;
        int x;
        int y;

        if (nLessOneTriangular(inverseTriangular) == i) {
            x = inverseTriangular;
            y = x - 1;
        } else {
            x = inverseTriangular + 1;
            y = (nLessOneTriangular(x - 1) - i) * -1;
        }
        return new Par(x, y);
    }

    public static int unrollPar(Par par) {
        int x = par.getLeft();
        int y = par.getRight();

        int resultado = nLessOneTriangular(x - 1);
        return resultado + y;
    }


    public static int nLessOneTriangular(int i) {
        i--;
        return ((i * i) + i) >> 1; //xd!
    }

    @Deprecated
    public static int unrollParIterative(Par par) {
        // TODO: Metodo algebraico
        int targetX = par.getLeft();
        int targetY = par.getRight();

        int x = 1;
        int y = 1;

        int UPPERBOUND = targetX * targetX;
        if (targetY >= targetX) {
            throw new RuntimeException("Par invalido. Valor y no puede ser igual o mayor que x\n" + "x: " + targetX + ", y: " + targetY);
        }
        for (int i = 1; i < UPPERBOUND; i++) {
            if (y < x) {
                y++;
            }
            if (y == x) {
                y = 1;
                x++;
            }

            if (x == targetX && y == targetY) {
                return i;
            }
        }
        throw new RuntimeException("Par no encontrado, jamás debería llegar acá " + "x: " + targetX + " y, " + y);
    }

    @Deprecated
    public static Par matchIdToPar(int matchId) {
        int x = 1;
        int y = 1;

        for (int i = 1; i <= matchId; i++) {
            if (y < x) {
                y++;
            }
            if (y == x) {
                y = 1;
                x++;
            }
        }
        return new Par(x, y);
    }




}

//    private static Par matchIdToParAlgebraic(int i) {
//        //TODO: Revisar si calcular triangular es más rápido que solo verificar si es numero sin decimales
//        double temp = reverseT(i);
//        int x;
//        int y;
//
//        if (nLessOneTriangular((int) temp) == i) {
//            x = (int) temp;
//            y = x - 1;
//        } else {
//            temp++;
//            x = (int) temp;
//            y = (nLessOneTriangular(x - 1) - i) * -1;
//        }
//        return new Par(x, y);
//    }