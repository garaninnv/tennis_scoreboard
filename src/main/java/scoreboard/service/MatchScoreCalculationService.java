package scoreboard.service;

import jakarta.servlet.ServletContext;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import scoreboard.model.MatchDAO;
import scoreboard.model.MatcheEntity;
import scoreboard.model.PlayerDAO;

import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

public class MatchScoreCalculationService {
    PlayerDAO playerDAO = new PlayerDAO();
    MatchDAO matchDAO = new MatchDAO();

    public boolean endMatch(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        UUID uuid = UUID.fromString(req.getParameter("uuid"));
        req.setAttribute("uuid", uuid);
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
        int idWinPlayer = Integer.parseInt(req.getParameter("idPlayer"));

        int pointPlayer1 = (int) servletContext.getAttribute("pointPlayer1");
        int pointPlayer2 = (int) servletContext.getAttribute("pointPlayer2");
        int gamePlayer1 = (int) servletContext.getAttribute("gamePlayer1");
        int gamePlayer2 = (int) servletContext.getAttribute("gamePlayer2");
        int setPlayer1 = (int) servletContext.getAttribute("setPlayer1");
        int setPlayer2 = (int) servletContext.getAttribute("setPlayer2");
        int moreLess1 = (int) servletContext.getAttribute("moreLess1");
        int moreLess2 = (int) servletContext.getAttribute("moreLess2");

        if (setPlayer1 < 2 && setPlayer2 < 2) {
            if (gamePlayer1 < 2 && gamePlayer2 < 2) {
                if (pointPlayer1 == 40 && pointPlayer2 == 40) {
                    //больше/меньше
                    if (idWinPlayer == idPlayer1) {
                        if (moreLess1 > moreLess2) {
                            gamePlayer1++;
                            pointPlayer1 = 0;
                            pointPlayer2 = 0;
                            moreLess1 = 0;
                            moreLess2 = 0;
                        } else {
                            moreLess1 = 1;
                        }
                    } else if (idWinPlayer == idPlayer2) {
                        if (moreLess2 > moreLess1) {
                            gamePlayer2++;
                            pointPlayer1 = 0;
                            pointPlayer2 = 0;
                            moreLess1 = 0;
                            moreLess2 = 0;
                        } else {
                            moreLess2 = 1;
                        }
                    }
                    if (moreLess1 == 1 && moreLess2 == 1) {
                        moreLess1 = 0;
                        moreLess2 = 0;
                    }
                } else if (pointPlayer1 == 30 && pointPlayer2 == 0 || pointPlayer1 == 0 && pointPlayer2 == 30) {
                    //гейм выйгран
                    if (pointPlayer1 > pointPlayer2) {
                        pointPlayer1 = 0;
                        gamePlayer1++;
                    } else {
                        pointPlayer2 = 0;
                        gamePlayer2++;
                    }
                    if (gamePlayer1 == 2) {
                        gamePlayer1 = 0;
                        gamePlayer2 = 0;
                        setPlayer1++;
                    } else if (gamePlayer2 == 2) {
                        gamePlayer1 = 0;
                        gamePlayer2 = 0;
                        setPlayer2++;
                    }

                    req.setAttribute("pointPlayer1", pointPlayer1);
                    req.setAttribute("pointPlayer2", pointPlayer2);
                    req.setAttribute("gamePlayer1", gamePlayer1);
                    req.setAttribute("gamePlayer2", gamePlayer2);
                    req.setAttribute("setPlayer1", setPlayer1);
                    req.setAttribute("setPlayer2", setPlayer2);

                    servletContext.setAttribute("pointPlayer1", pointPlayer1);
                    servletContext.setAttribute("pointPlayer2", pointPlayer2);
                    servletContext.setAttribute("gamePlayer1", gamePlayer1);
                    servletContext.setAttribute("gamePlayer2", gamePlayer2);
                    servletContext.setAttribute("setPlayer1", setPlayer1);
                    servletContext.setAttribute("setPlayer2", setPlayer2);

                    if (setPlayer1 == 2 || setPlayer2 == 2) {
                        MatcheEntity matche = uuidMatchHashMap.get(uuid);
                        if (setPlayer1 == 2) {
                            matche.setWinner(matche.getPlayer1());
                        } else {
                            matche.setWinner(matche.getPlayer2());
                        }
                        //        Записываем законченный матч в SQL базу данных
                        matchDAO.addMatchToDB(matche);
                        //        Удаляем матч из коллекции текущих матчей
                        uuidMatchHashMap.remove(uuid);
                        return true;
                    }
                    return false;
                } else {
                    if (idWinPlayer == idPlayer1) {
                        pointPlayer1 = switch (pointPlayer1) {
                            case 0 -> 15;
                            case 15 -> 30;
                            case 30 -> 40;
                            case 40 -> 50;
                            default -> throw new IllegalStateException("Unexpected value: " + pointPlayer1);
                        };
                        if (pointPlayer1 == 50) {
                            pointPlayer1 = 0;
                            pointPlayer2 = 0;
                            gamePlayer1++;
                        }
                    } else {
                        pointPlayer2 = switch (pointPlayer2) {
                            case 0 -> 15;
                            case 15 -> 30;
                            case 30 -> 40;
                            case 40 -> 50;
                            default -> throw new IllegalStateException("Unexpected value: " + pointPlayer2);
                        };
                        if (pointPlayer2 == 50) {
                            pointPlayer2 = 0;
                            pointPlayer1 = 0;
                            gamePlayer2++;
                        }
                    }
                    if (gamePlayer1 == 2) {
                        gamePlayer1 = 0;
                        gamePlayer2 = 0;
                        setPlayer1++;
                    } else if (gamePlayer2 == 2) {
                        gamePlayer1 = 0;
                        gamePlayer2 = 0;
                        setPlayer2++;
                    }
                    if (setPlayer1 == 2 || setPlayer2 == 2) {
                        MatcheEntity matche = uuidMatchHashMap.get(uuid);
                        if (setPlayer1 == 2) {
                            matche.setWinner(matche.getPlayer1());
                        } else {
                            matche.setWinner(matche.getPlayer2());
                        }
                        //        Записываем законченный матч в SQL базу данных
                        matchDAO.addMatchToDB(matche);
                        //        Удаляем матч из коллекции текущих матчей
                        uuidMatchHashMap.remove(uuid);
                        return true;
                    }
                }
            } else if (gamePlayer1 == 2) {
                gamePlayer1 = 0;
                gamePlayer2 = 0;
                setPlayer1++;
            } else if (gamePlayer2 == 2) {
                gamePlayer1 = 0;
                gamePlayer2 = 0;
                setPlayer2++;
            }
            if (setPlayer1 == 2 || setPlayer2 == 2) {
                MatcheEntity matche = uuidMatchHashMap.get(uuid);
                if (setPlayer1 == 2) {
                    matche.setWinner(matche.getPlayer1());
                } else {
                    matche.setWinner(matche.getPlayer2());
                }
                //        Записываем законченный матч в SQL базу данных
                matchDAO.addMatchToDB(matche);
                //        Удаляем матч из коллекции текущих матчей
                uuidMatchHashMap.remove(uuid);
                return true;
            }
        }
        servletContext.setAttribute("gamePlayer1", gamePlayer1);
        servletContext.setAttribute("gamePlayer2", gamePlayer2);
        servletContext.setAttribute("pointPlayer1", pointPlayer1);
        servletContext.setAttribute("pointPlayer2", pointPlayer2);
        servletContext.setAttribute("moreLess1", moreLess1);
        servletContext.setAttribute("moreLess2", moreLess2);
        servletContext.setAttribute("setPlayer1", setPlayer1);
        servletContext.setAttribute("setPlayer2", setPlayer2);

        req.setAttribute("gamePlayer1", gamePlayer1);
        req.setAttribute("gamePlayer2", gamePlayer2);
        req.setAttribute("pointPlayer1", pointPlayer1);
        req.setAttribute("pointPlayer2", pointPlayer2);
        req.setAttribute("moreLess1", moreLess1);
        req.setAttribute("moreLess2", moreLess2);
        req.setAttribute("setPlayer1", setPlayer1);
        req.setAttribute("setPlayer2", setPlayer2);
        return false;
    }
}
