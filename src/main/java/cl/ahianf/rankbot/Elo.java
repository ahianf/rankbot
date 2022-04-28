package cl.ahianf.rankbot;

public class Elo {
    private final double playerA;
    private final double playerB;

    public Elo(double playerA, double playerB) {

        this.playerA = playerA;
        this.playerB = playerB;
    }


    static double probabilidad(double player1Elo, double player2Elo) {
        return 1.0 / (1 + (Math.pow(10, (player1Elo - player2Elo) / 400)));
    }

    // Function to calculate Elo rating
    // K is a constant.
    public static Elo eloRating(Elo elo, int result) {

        double playerA = elo.getPlayerA();
        double playerB = elo.getPlayerB();

        final int K = 10;

        double probabilityPlayerA = probabilidad(playerB, playerA);
        double probabilityPlayerB = probabilidad(playerA, playerB);

        switch (result) {
            case 1 -> {
                playerA += K * (1 - probabilityPlayerA);
                playerB += K * (0 - probabilityPlayerB);
            }
            case 2 -> {
                playerA += K * (0 - probabilityPlayerA);
                playerB += K * (1 - probabilityPlayerB);
            }
            case 3 -> {
                playerA += K * (0.5d - probabilityPlayerA);
                playerB += K * (0.5d - probabilityPlayerB);
            }
            default -> {
                throw new RuntimeException("Invalid argument on eloRating");
            }
        }
        return new Elo(playerA, playerB);

    }


    public double getPlayerA() {
        return playerA;
    }

    public double getPlayerB() {
        return playerB;
    }

    @Override
    public boolean equals(Object o) {

        // Objeto comparado consigo mismo
        if (o == this) {
            return true;
        }

        //Revisa si o es una instancia de Elo
        if (!(o instanceof Elo)) {
            return false;
        }

        // Cast objeto a Elo para comparar los datos
        Elo c = (Elo) o;

        // Compara valores y devuelve
        return playerA == c.playerA && playerB == c.playerB;
    }

    @Override
    public String toString() {
        return "x, " + playerA + " : y, " + playerB;
    }
}
