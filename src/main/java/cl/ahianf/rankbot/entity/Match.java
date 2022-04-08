package cl.ahianf.rankbot.entity;

public class Match {

    public Song songA;

    public Song songB;

    public int matchId;


    public Match(Song songA, Song songB, int matchId) {
        this.songA = songA;
        this.songB = songB;
        this.matchId = matchId;
    }
}
