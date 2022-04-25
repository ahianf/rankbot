package cl.ahianf.rankbot;

public class ParElo {
    private final double left;
    private final double right;

    public ParElo(double left, double right) {
        // assert left != null;
        // assert right != null;

        this.left = left;
        this.right = right;
    }

    @Override
    public boolean equals(Object o) {

        // If the object is compared with itself then return true
        if (o == this) {
            return true;
        }

        /* Check if o is an instance of Complex or not
          "null instanceof [type]" also returns false */
        if (!(o instanceof ParElo)) {
            return false;
        }

        // typecast o to Complex so that we can compare data members
        ParElo c = (ParElo) o;

        // Compare the data members and return accordingly
        return left == c.left && right == c.right;
    }

    @Override
    public String toString() {
        //return "x, " + left + " : y, " + right;
        return left + "," + right;
    }


    public double getLeft() {
        return left;
    }

    public double getRight() {
        return right;
    }


}
