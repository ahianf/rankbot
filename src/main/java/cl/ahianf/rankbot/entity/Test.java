package cl.ahianf.rankbot.entity;

import cl.ahianf.rankbot.Par;

import java.util.Random;

public class Test {

    public static void main(String[] args) {

        System.out.println(unrollPar(new Par(2234, 1233 )));

    }

    public static int unrollPar(Par par) {
        int targetX = par.getLeft();
        int targetY = par.getRight();

        int x = 1;
        int y = 1;

        int UPPERBOUND = targetX * targetX;
        if (targetY >= targetX){
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

            if (x == targetX && y == targetY){
                return i;
            }
        }
        throw new RuntimeException("Par no encontrado, jamás debería llegar acá " + "x: " + targetX + " y, " + y) ;
    }

    public static Par tst(int matchId) {
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
