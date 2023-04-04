package scoreboard.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Match {
   private int id;
    private int player1;
    private int player2;
   private int pointPlayer1;
    private int pointPlayer2;
    private int winner;

    public Match(int player1, int player2,int pointPlayer1, int pointPlayer2, int winner) {
        this.player1 = player1;
        this.player2 = player2;
        this.pointPlayer1 = pointPlayer1;
        this.pointPlayer2 = pointPlayer2;
        this.winner = winner;
    }
}
