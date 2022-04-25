package cl.ahianf.rankbot;

// Java program to count rotationally
public class Elo {

    // Function to calculate the Probability
    static double probability(double rating1, double rating2) {
        return 1.0d / (1 + (Math.pow(10, (rating1 - rating2) / 400)));
    }

    // Function to calculate Elo rating
    // K is a constant.
    public static ParElo eloRating(ParElo parElo, int result) {

        double playerA = parElo.getLeft();
        double playerB = parElo.getRight();

        final int K = 20;

        double probabilityPlayerA = probability(playerB, playerA);
        double probabilityPlayerB = probability(playerA, playerB);

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
        }

        return new ParElo(playerA, playerB);
    }

    //driver code
    public static void main(String[] args) {

        // playerA and playerB are current ELO ratings
        double playerA = 1238, playerB = 1656.5d;

        int result = 2;

//        eloRating(playerA, playerB, result);
    }
}