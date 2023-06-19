package scoreboard.model;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import java.util.List;

public class MatchDAO {
    public void addMatchToDB(MatcheEntity match) {
        SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(PlayerEntity.class)
                .addAnnotatedClass(MatcheEntity.class)
                .buildSessionFactory();
        try {
            Session session = factory.openSession();
            session.beginTransaction();
            session.save(match);
            session.getTransaction().commit();
        } finally {
            factory.close();
        }
    }

    public List<MatcheEntity> matcheEntityList() {
        List<MatcheEntity> list;
        SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(PlayerEntity.class)
                .addAnnotatedClass(MatcheEntity.class)
                .buildSessionFactory();
        try {
            Session session = factory.openSession();
            session.beginTransaction();
            list = session.createQuery("FROM MatcheEntity").getResultList();
            session.getTransaction().commit();
        } finally {
            factory.close();
        }
        return list;
    }

    public List<MatcheEntity> matcheEntityList(int playerId) {
        List<MatcheEntity> list;
        SessionFactory factory = new Configuration().configure("hibernate.cfg.xml")
                .addAnnotatedClass(PlayerEntity.class)
                .addAnnotatedClass(MatcheEntity.class)
                .buildSessionFactory();
        try {
            Session session = factory.openSession();
            session.beginTransaction();
            list = session.createQuery("FROM MatcheEntity where player1 = '" + playerId
                    + "' or player2 = '" + playerId + "'").getResultList();
            session.getTransaction().commit();
        } finally {
            factory.close();
        }
        return list;
    }
}