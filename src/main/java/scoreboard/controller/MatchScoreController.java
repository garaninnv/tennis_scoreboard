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


import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

@WebServlet("/match-score/*")
public class MatchScoreController extends HttpServlet {
    PlayerDAO playerDAO = new PlayerDAO();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        UUID uuid = UUID.fromString(req.getParameter("uuid"));

        req.setAttribute("uuid", uuid);


//        Таблица с именами игроков, текущим счётом
        ServletContext servletContext = req.getServletContext();
        HashMap<UUID, Match> uuidMatchHashMap = (HashMap<UUID, Match>) servletContext.
                getAttribute("uuidMatchHashMap");
        int idPlayer1 = uuidMatchHashMap.get(uuid).getPlayer1();
        int idPlayer2 = uuidMatchHashMap.get(uuid).getPlayer2();
        req.setAttribute("idPlayer1", idPlayer1);
        req.setAttribute("idPlayer2", idPlayer2);

        String namePlayer1 = playerDAO.getName(idPlayer1);
        String namePlayer2 = playerDAO.getName(idPlayer2);
        req.setAttribute("namePlayer1", namePlayer1);
        req.setAttribute("namePlayer2", namePlayer2);
        int pointPlayer1 = uuidMatchHashMap.get(uuid).getPointPlayer1();
        int pointPlayer2 = uuidMatchHashMap.get(uuid).getPointPlayer2();
        req.setAttribute("pointPlayer1", pointPlayer1);
        req.setAttribute("pointPlayer2", pointPlayer2);


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
//        Если матч закончился:
//        Удаляем матч из коллекции текущих матчей
//        Записываем законченный матч в SQL базу данных
//        Рендерим финальный счёт
        UUID uuid = UUID.fromString(req.getParameter("uuid"));
        req.setAttribute("uuid", uuid);
        ServletContext servletContext = req.getServletContext();
        HashMap<UUID, Match> uuidMatchHashMap = (HashMap<UUID, Match>) servletContext.
                getAttribute("uuidMatchHashMap");
        int idPlayer1 = uuidMatchHashMap.get(uuid).getPlayer1();
        int idPlayer2 = uuidMatchHashMap.get(uuid).getPlayer2();
        req.setAttribute("idPlayer1", idPlayer1);
        req.setAttribute("idPlayer2", idPlayer2);

        String namePlayer1 = playerDAO.getName(idPlayer1);
        String namePlayer2 = playerDAO.getName(idPlayer2);
        req.setAttribute("namePlayer1", namePlayer1);
        req.setAttribute("namePlayer2", namePlayer2);
        int idWinPlayer = Integer.parseInt(req.getParameter("idPlayer"));

        if (idWinPlayer == idPlayer1) {
            uuidMatchHashMap.get(uuid).setPointPlayer1(uuidMatchHashMap.get(uuid).getPointPlayer1() + 1);
        } else {
            uuidMatchHashMap.get(uuid).setPointPlayer2(uuidMatchHashMap.get(uuid).getPointPlayer2() + 1);
        }
        int pointPlayer1 = uuidMatchHashMap.get(uuid).getPointPlayer1();
        int pointPlayer2 = uuidMatchHashMap.get(uuid).getPointPlayer2();
        req.setAttribute("pointPlayer1", pointPlayer1);
        req.setAttribute("pointPlayer2", pointPlayer2);

        RequestDispatcher requestDispatcher = req.getRequestDispatcher("view/match-score.jsp");
        requestDispatcher.forward(req, resp);
    }
}
