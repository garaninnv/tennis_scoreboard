package scoreboard.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "matches")
public class MatchesEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @Column(name = "Player1")
    private int player1;
    @Column(name = "Player2")
    private int player2;
    @Column(name = "Winner")
    private int winner;

    public MatchesEntity(int player1, int player2, int winner) {
        this.player1 = player1;
        this.player2 = player2;
        this.winner = winner;
    }
}
