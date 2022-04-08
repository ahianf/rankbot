package cl.ahianf.rankbot;

public class Par {
    private final int left;
    private final int right;

    public Par(int left, int right) {
        // assert left != null;
        // assert right != null;

        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "x, " + left + " : y, " + right;
    }

    public int getLeft() {
        return left;
    }

    public int getRight() {
        return right;
    }
}
