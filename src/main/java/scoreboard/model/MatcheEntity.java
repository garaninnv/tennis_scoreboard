package scoreboard.model;

import javax.persistence.*;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "Matches")
public class MatcheEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "player1", referencedColumnName = "id")
    private PlayerEntity player1;
    @ManyToOne(cascade=CascadeType.ALL)
    @JoinColumn(name = "player2", referencedColumnName = "id")
    private PlayerEntity player2;
    @ManyToOne (cascade=CascadeType.ALL)
    @JoinColumn(name = "winner", referencedColumnName = "id")
    private PlayerEntity winner;

    public MatcheEntity(PlayerEntity player1, PlayerEntity player2, PlayerEntity winner) {
        this.player1 = player1;
        this.player2 = player2;
        this.winner = winner;
    }
}