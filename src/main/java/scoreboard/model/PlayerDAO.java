package scoreboard.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class PlayerDAO {

    public int idPleyer(PlayersEntity player) {
        //Проверяет существование игроков в таблице Players. Если игрока с таким именем не существует, создаём
        SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(PlayersEntity.class)
                .buildSessionFactory();
        try {
            Session session = factory.openSession();
            session.beginTransaction();
            List<PlayersEntity> playersEntityList = session.createQuery("from PlayersEntity where name = '"+ player.getName()+"'")
                            .getResultList();
            if (!playersEntityList.isEmpty()) {
                for (PlayersEntity el: playersEntityList) {
                    return el.getId();
                }
            }
            session.getTransaction().commit();
        }
        finally {
            factory.close();
        }
        return -1;
    }

    public void addPlayer (PlayersEntity player) {
        if (idPleyer(player) == -1) {
            SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
                    .addAnnotatedClass(PlayersEntity.class)
                    .buildSessionFactory();
            try {
                Session session = factory.openSession();
                session.beginTransaction();
                session.save(player);
                session.getTransaction().commit();
            }
            finally {
                factory.close();
            }
        }
    }

    public String getName(int idPlayer) {
        PlayersEntity playersEntity;
        SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(PlayersEntity.class)
                .buildSessionFactory();
        try {
            Session session = factory.openSession();
            session.beginTransaction();
            playersEntity = session.get(PlayersEntity.class, idPlayer);
            session.getTransaction().commit();
        }
        finally {
            factory.close();
        }
        return playersEntity.getName();
    }
}
