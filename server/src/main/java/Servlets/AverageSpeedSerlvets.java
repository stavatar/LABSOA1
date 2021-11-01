package Servlets;

import DAO.HumanBeingDAO;
import Model.HumanBeing;
import Service.HumanBeingService;
import org.hibernate.exception.JDBCConnectionException;
import org.json.JSONArray;
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

@WebServlet("/averageSpeed")
public class AverageSpeedSerlvets extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        HumanBeingService humanBeingService= new HumanBeingService();
        JSONObject message = new JSONObject();
        PrintWriter printWriter = resp.getWriter();

        try {
            double avr= humanBeingService.avrSpeed();
            message.put("code", 1);
            message.put("data", "Mean = " + avr);

        }catch (IllegalArgumentException | JSONException e){
            message.put("code",0);
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            message.put("data","Error! Invalid input data");
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
