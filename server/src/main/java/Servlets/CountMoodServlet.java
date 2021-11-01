package Servlets;

import DAO.HumanBeingDAO;
import Model.HumanBeing;
import Model.util.Mood;
import Service.HumanBeingService;
import org.hibernate.exception.JDBCConnectionException;
import org.json.JSONException;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/countHuman")
public class CountMoodServlet  extends HttpServlet  {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject weatherJsonObject;
        HumanBeingService humanBeingService= new HumanBeingService();
        JSONObject message = new JSONObject();
        PrintWriter printWriter = resp.getWriter();
        resp.setContentType("application/json");

        Mood mood;
        String test=req.getParameter("mood");
        try {
            weatherJsonObject = new JSONObject(test);
            mood = Mood.valueOf(weatherJsonObject.getString("typeMood"));
            int countHuman = humanBeingService.countLessMood(mood);
            message.put("code",1);
            message.put("data", "Count of objects found =  " + countHuman);

        }catch (IllegalArgumentException | JSONException e){
            message.put("code",0);
            message.put("data","Error! Invalid input data");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }catch (JDBCConnectionException e){
            message.put("code",0);
            message.put("data","Error! No database connection ");
            resp.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        }catch (Exception e){
            message.put("code",0);
            message.put("data","Request failed");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
        }finally {
            printWriter.write(message.toString());
            printWriter.close();
        }
    }
}
