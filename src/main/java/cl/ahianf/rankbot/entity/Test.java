package cl.ahianf.rankbot.entity;

import cl.ahianf.rankbot.Par;

import java.util.Random;

public class Test {

    public static void main(String[] args) {

        System.out.println(duelGenerator(15));





    }


    public static int triangular(int i) {
        i--;
        return ((i * i) + i) >> 1;
    }

    public static  Par duelGenerator(int matchId) {
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
