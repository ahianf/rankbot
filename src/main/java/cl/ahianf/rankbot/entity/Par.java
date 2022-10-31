/* (C)2022 - Ahian Fernández Puelles*/
package cl.ahianf.rankbot.entity;

public record Par(int left, int right) {

    @Override
    public boolean equals(Object objeto) {

        if (objeto == this) {
            return true;
        }

        if (!(objeto instanceof Par par)) {
            return false;
        }

        return left == par.left && right == par.right;
    }

    @Override
    public String toString() {
        // return "x, " + left + " : y, " + right;
        return left + "," + right;
    }
}
