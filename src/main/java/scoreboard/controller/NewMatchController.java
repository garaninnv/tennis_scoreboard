package scoreboard.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import scoreboard.model.Match;
import scoreboard.model.PlayerDAO;
import scoreboard.model.PlayersEntity;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

@WebServlet("/new-match")
public class NewMatchController extends HttpServlet {
    PlayerDAO playerDAO = new PlayerDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        RequestDispatcher requestDispatcher = req.getRequestDispatcher("view/new-match.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ServletContext servletContext = req.getServletContext();

        PlayersEntity player1 = new PlayersEntity(req.getParameter("player1"));
        PlayersEntity player2 = new PlayersEntity(req.getParameter("player2"));
//        Проверяет существование игроков в таблице Players. Если игрока с таким именем не существует, создаём.
        playerDAO.addPlayer(player1);
        playerDAO.addPlayer(player2);
//        Создаём экземпляр класса Match (содержащий айди игроков и текущий счёт) и кладём в коллекцию текущих матчей
//        (существующую только в памяти приложения, либо в key-value storage). Ключом коллекции является UUID,
//        значением - экземпляр класса Match

        HashMap<UUID, Match> uuidMatchHashMap = (HashMap<UUID, Match>) servletContext.
                getAttribute("uuidMatchHashMap");

        UUID uuidMatch = UUID.randomUUID();
        uuidMatchHashMap.put(uuidMatch, new Match(player1.getId(), player2.getId(),0, 0, -1));

        resp.sendRedirect("match-score?uuid="+uuidMatch);
        System.out.println();
    }
}
