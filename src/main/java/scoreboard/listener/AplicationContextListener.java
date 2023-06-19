package scoreboard.listener;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletContextEvent;
import jakarta.servlet.ServletContextListener;
import jakarta.servlet.annotation.WebListener;
import scoreboard.db.DataBaseConnectionManager;
import scoreboard.model.MatcheEntity;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.UUID;

@WebListener
public class AplicationContextListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        ServletContext context = sce.getServletContext();
        String jdbcURL = "jdbc:h2:mem:default";
        Connection con;
        HashMap<UUID, MatcheEntity> uuidMatchHashMap = new HashMap<>();
        context.setAttribute("uuidMatchHashMap", uuidMatchHashMap);
        DataBaseConnectionManager dataBaseConnectionManager = null;
        try {
            dataBaseConnectionManager = new DataBaseConnectionManager(jdbcURL);
            con = dataBaseConnectionManager.getConnection();
            context.setAttribute("dbConnection", con);
            System.out.println("DB connection up!");
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