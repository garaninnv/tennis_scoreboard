package scoreboard.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class PlayerDAO {

    public int idPleyer(PlayerEntity player) {
        //Проверяет существование игроков в таблице Players. Если игрока с таким именем не существует, создаём
        SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(PlayerEntity.class)
                .addAnnotatedClass(MatcheEntity.class)
                .buildSessionFactory();
        try {
            Session session = factory.openSession();
            session.beginTransaction();
            List<PlayerEntity> playersEntityList = session.createQuery("from PlayerEntity where name = '" + player.getName() + "'")
                    .getResultList();
            if (!playersEntityList.isEmpty()) {
                for (PlayerEntity el : playersEntityList) {
                    return el.getId();
                }
            }
            session.getTransaction().commit();
        } finally {
            factory.close();
        }
        return -1;
    }

    public void addPlayer(PlayerEntity player) {
        if (idPleyer(player) == -1) {
            SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
                    .addAnnotatedClass(PlayerEntity.class)
                    .addAnnotatedClass(MatcheEntity.class)
                    .buildSessionFactory();
            try {
                Session session = factory.openSession();
                session.beginTransaction();
                session.save(player);
                session.getTransaction().commit();
            } finally {
                factory.close();
            }
        } else {
            player.setId(idPleyer(player));
        }
    }

    public String getName(int idPlayer) {
        PlayerEntity playersEntity;
        SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(PlayerEntity.class)
                .addAnnotatedClass(MatcheEntity.class)
                .buildSessionFactory();
        try {
            Session session = factory.openSession();
            session.beginTransaction();
            playersEntity = session.get(PlayerEntity.class, idPlayer);
            session.getTransaction().commit();
        } finally {
            factory.close();
        }
        return playersEntity.getName();
    }

    public int getIdPlayer(String name) {
        SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(PlayerEntity.class)
                .addAnnotatedClass(MatcheEntity.class)
                .buildSessionFactory();
        try {
            Session session = factory.openSession();
            session.beginTransaction();
            List<PlayerEntity> playersEntityList = session.createQuery("from PlayerEntity where name = '" + name + "'")
                    .getResultList();
            if (!playersEntityList.isEmpty()) {
                for (PlayerEntity el : playersEntityList) {
                    return el.getId();
                }
            }
            session.getTransaction().commit();
        } finally {
            factory.close();
        }
        return -1;
    }
}