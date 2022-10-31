/* (C)2022 - Ahian Fernández Puelles*/
package cl.ahianf.rankbot.entity;

public record Elo(double playerA, double playerB) {

    static double probabilidad(double player1Elo, double player2Elo) {
        return 1.0 / (1 + (Math.pow(10, (player1Elo - player2Elo) / 400)));
    }

    public static Elo eloRating(Elo elo, int result) {

        double playerA = elo.playerA();
        double playerB = elo.playerB();

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
            default -> throw new RuntimeException("Argumento inválido");
        }
        return new Elo(playerA, playerB);
    }

    @Override
    public boolean equals(Object objeto) {

        // Objeto comparado consigo mismo
        if (objeto == this) {
            return true;
        }

        // Instancia de Elo?
        if (!(objeto instanceof Elo elo)) {
            return false;
        }

        // Cast objeto a Elo para comparar los datos

        // Compara valores y devuelve
        return playerA == elo.playerA && playerB == elo.playerB;
    }

    @Override
    public String toString() {
        return "x, " + playerA + " : y, " + playerB;
    }
}
