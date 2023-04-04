package scoreboard.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import scoreboard.db.DataBaseConnectionManager;
import scoreboard.model.Match;
import scoreboard.model.PlayersEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.UUID;

@WebListener
public class AplicationContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        //String jdbcURL = "jdbc:h2:mem:default";
        String jdbcURL = "jdbc:postgresql://localhost/test";
        Statement stmt = null;
        Connection con = null;
        HashMap<UUID, Match> uuidMatchHashMap = new HashMap<>();
        context.setAttribute("uuidMatchHashMap", uuidMatchHashMap);
        DataBaseConnectionManager dataBaseConnectionManager = null;
        try {
            dataBaseConnectionManager = new DataBaseConnectionManager(jdbcURL);
            con = dataBaseConnectionManager.getConnection();
            context.setAttribute("dbConnection", con);
//            System.out.println("DB connection up!");
//            System.out.println("Creating table in given database...");
//            stmt = con.createStatement();
//            String sql = "CREATE TABLE Players " +
//                    "(id int auto_increment," +
//                    " Name varchar not null," +
//                    " constraint PLAYERS_PK" +
//                    " primary key ( id ))";
//            stmt.executeUpdate(sql);
//            sql = "create table Matches " +
//                    "(id int auto_increment," +
//                    " player1 int not null," +
//                    " player2 int not null," +
//                    " winner  int not null," +
//                    " constraint MATCHES_PK" +
//                    " primary key (id)," +
//                    " constraint MATCHES_PLAYERS_ID_FK" +
//                    " foreign key (player1) references Players," +
//                    " constraint MATCHES_PLAYERS_ID_FK_2" +
//                    " foreign key (player2) references Players," +
//                    " constraint MATCHES_PLAYERS_ID_FK_3" +
//                    " foreign key (winner) references Players)";
//            stmt.executeUpdate(sql);
//            System.out.println("Created table in given database...");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            ((Connection) sce.getServletContext().getAttribute("dbConnection")).close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
