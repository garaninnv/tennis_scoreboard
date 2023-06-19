package scoreboard.controller;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import scoreboard.model.MatchDAO;
import scoreboard.model.MatcheEntity;
import scoreboard.model.PlayerDAO;
import scoreboard.service.MatchScoreCalculationService;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

@WebServlet("/match-score/*")
public class MatchScoreController extends HttpServlet {
    PlayerDAO playerDAO = new PlayerDAO();
    MatchDAO matchDAO = new MatchDAO();
    MatchScoreCalculationService matchScoreCalculation = new MatchScoreCalculationService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID uuid = UUID.fromString(req.getParameter("uuid"));
        req.setAttribute("uuid", uuid);

//        Таблица с именами игроков, текущим счётом
        ServletContext servletContext = req.getServletContext();
        HashMap<UUID, MatcheEntity> uuidMatchHashMap = (HashMap<UUID, MatcheEntity>) servletContext.
                getAttribute("uuidMatchHashMap");
        int idPlayer1 = uuidMatchHashMap.get(uuid).getPlayer1().getId();
        int idPlayer2 = uuidMatchHashMap.get(uuid).getPlayer2().getId();
        req.setAttribute("idPlayer1", idPlayer1);
        req.setAttribute("idPlayer2", idPlayer2);

        String namePlayer1 = playerDAO.getName(idPlayer1);
        String namePlayer2 = playerDAO.getName(idPlayer2);
        req.setAttribute("namePlayer1", namePlayer1);
        req.setAttribute("namePlayer2", namePlayer2);

        servletContext.setAttribute("pointPlayer1", Integer.valueOf(0));
        servletContext.setAttribute("pointPlayer2", Integer.valueOf(0));
        servletContext.setAttribute("gamePlayer1", Integer.valueOf(0));
        servletContext.setAttribute("gamePlayer2", Integer.valueOf(0));
        servletContext.setAttribute("setPlayer1", Integer.valueOf(0));
        servletContext.setAttribute("setPlayer2", Integer.valueOf(0));
        servletContext.setAttribute("moreLess1", Integer.valueOf(0));
        servletContext.setAttribute("moreLess2", Integer.valueOf(0));

        req.setAttribute("pointPlayer1", Integer.valueOf(0));
        req.setAttribute("pointPlayer2", Integer.valueOf(0));
        req.setAttribute("gamePlayer1", Integer.valueOf(0));
        req.setAttribute("gamePlayer2", Integer.valueOf(0));
        req.setAttribute("setPlayer1", Integer.valueOf(0));
        req.setAttribute("setPlayer2", Integer.valueOf(0));

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("view/match-score.jsp");
        requestDispatcher.forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//        Формы и кнопки для действий - “игрок 1 выиграл текущее очко”, “игрок 2 выиграл текущее очко”
//        Нажатие кнопок приводит к POST запросу по адресу /match-score?uuid=${UUID}, в полях отправленной формы
//        содержится айди выигравшего очко игрока

//        Извлекает из коллекции экземпляр класса Match
//        В соответствии с тем, какой игрок выиграл очко, обновляет счёт матча
//        Если матч не закончился - рендерится таблица счёта матча с кнопками, описанными выше

        if (matchScoreCalculation.endMatch(req, resp)) {
            resp.sendRedirect("matches?page=1&filter_by_player_name");
        } else {
            RequestDispatcher requestDispatcher = req.getRequestDispatcher("view/match-score.jsp");
            requestDispatcher.forward(req, resp);
        }
    }
}