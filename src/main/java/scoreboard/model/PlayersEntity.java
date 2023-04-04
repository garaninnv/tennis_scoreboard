package scoreboard.model;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "players")
public class PlayersEntity {
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Id
    @Column(name = "id", nullable = false)
    private int id;
    @Basic
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "Player1")
    private MatchesEntity matchesEntity;

    public PlayersEntity(String name) {
        this.name = name;
    }
}
