package scoreboard.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import scoreboard.model.MatchDAO;
import scoreboard.model.MatcheEntity;
import scoreboard.model.PlayerDAO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/matches/*")
public class MatchesController extends HttpServlet {
    MatchDAO matchDAO = new MatchDAO();
    PlayerDAO playerDAO = new PlayerDAO();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // обработка и вывод списка сыгранных матчей на страницу

        int page = (req.getParameter("page") == null ? 1 : Integer.parseInt(req.getParameter("page")));
        String filtrPlayerName = req.getParameter("filter_by_player_name").toString();
        int playerId = playerDAO.getIdPlayer(filtrPlayerName);
        List<MatcheEntity> entityList = new ArrayList<>();
        if (filtrPlayerName.isEmpty()) {
            entityList = matchDAO.matcheEntityList();
            //возвращается лист всех матчей. Нужно доделать отбор по имени игрока и отображение на странице.
        } else {
            entityList = matchDAO.matcheEntityList(playerId);
        }
        req.setAttribute("entitys", entityList);
        req.getRequestDispatcher("view/matches.jsp").forward(req, resp);
    }
}