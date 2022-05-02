package cl.ahianf.rankbot.entity;

import cl.ahianf.rankbot.Par;
import net.jodah.expiringmap.ExpiringMap;

import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Test {


    public static void main(String[] args) throws InterruptedException {

        Map<Integer, String> map = ExpiringMap.builder()
                .maxSize(123)
                .expiration(30, TimeUnit.SECONDS)
                .build();


        map.put(123, "Unodostres");
        map.put(456, "cuatrocincoseis");

        System.out.println(map.get(123));
        System.out.println(map.get(000));
        System.out.println(map.get(456));


    }

//    private static Elo resultadoAParElo(Elo parelo, Results resultado) {
//        ArrayList<Integer> listOfDoubles = new ArrayList<>();
//        for (int i = 0; i < resultado.getWinsX(); i++) {
//            listOfDoubles.add(1);
//        }
//
//        for (int i = 0; i < resultado.getWinsY(); i++) {
//            listOfDoubles.add(2);
//        }
//
//        for (int i = 0; i < resultado.getDraws(); i++) {
//            listOfDoubles.add(3);
//        }
//        Collections.shuffle(listOfDoubles);
//
//        for (int i : listOfDoubles) {
//            parelo = eloRating(parelo, i);
//        }
//        return parelo;
//    }

//    public static int unrollPar(Par par) {
//        int x = par.getLeft();
//        int y = par.getRight();
//
//        int resultado = nMenosUnoTriangular(x - 1);
//        return resultado + y;
//    }

    @Deprecated
    public static int unrollParIterative(Par par) {
        // TODO: Método algebraico
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