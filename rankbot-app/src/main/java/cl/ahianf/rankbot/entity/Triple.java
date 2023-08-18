/* (C)2022-2023 - Ahian Fernández Puelles*/
package cl.ahianf.rankbot.entity;

import java.util.Objects;

public record Triple(int left, int right, String center) {

    @Override
    public boolean equals(Object objeto) {

        if (objeto == this) {
            return true;
        }

        if (!(objeto instanceof Triple triple)) {
            return false;
        }

        return left == triple.left
                && right == triple.right
                && Objects.equals(center, triple.center);
    }

    @Override
    public String toString() {
        return left + "," + center + "," + right;
    }
}
