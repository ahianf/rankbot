package cl.ahianf.rankbot;

public class Par {
    private final int left;
    private final int right;

    public Par(int left, int right) {

        this.left = left;
        this.right = right;
    }

    @Override
    public boolean equals(Object objeto) {

        if (objeto == this) {
            return true;
        }

        if (!(objeto instanceof Par)) {
            return false;
        }

        Par par = (Par) objeto;

        return left == par.left && right == par.right;
    }

    @Override
    public String toString() {
        //return "x, " + left + " : y, " + right;
        return left + "," + right;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }


}
