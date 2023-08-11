/* (C)2022-2023 - Ahian Fernández Puelles*/
package cl.ahianf.rankbot.extra;

import static java.lang.Math.sqrt;

import cl.ahianf.rankbot.entity.Par;
import net.openhft.hashing.LongTupleHashFunction;

import java.util.UUID;

public class Functions {
    public static int nMenosUnoTriangular(int i) {
        i--;
        return ((i * i) + i) >> 1; // xd!
    }

    public static Par unrollMatchId(int matchId) {

        int inverseTriangular = (int) ((-1 + sqrt(1 + (8 * matchId))) / 2) + 1;
        int x;
        int y;

        if (nMenosUnoTriangular(inverseTriangular) == matchId) {
            x = inverseTriangular;
            y = x - 1;
        } else {
            x = inverseTriangular + 1;
            y = (nMenosUnoTriangular(x - 1) - matchId) * -1;
        }
        return new Par(x, y);
    }

    public static UUID stringToUUID(String string){

        System.out.println(string);

        long[] hash = LongTupleHashFunction.xx128().hashChars(string);
        UUID uuid = new UUID(hash[0], hash[1]);

        System.out.println(uuid);
        return uuid;
    }
}
