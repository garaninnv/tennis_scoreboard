package scoreboard.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "players")
public class PlayerEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @OneToMany(mappedBy = "player1")
    private List<MatcheEntity> matchesOnePleyer;

    @OneToMany(mappedBy = "player2")
    private List<MatcheEntity> matchesTwoPleyer;

    @OneToMany(mappedBy = "winner")
    private List<MatcheEntity> matchesWinnerPleyer;

    public PlayerEntity(String name) {
        this.name = name;
    }
}
