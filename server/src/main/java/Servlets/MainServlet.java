package Servlets;

import Model.Car;
import Model.Coordinates;
import Model.HumanBeing;
import Model.util.WeaponType;
import Service.HumanBeingService;
import Utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@WebServlet("/collection")
public class MainServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        HumanBeingService humanBeingService=new HumanBeingService();
        List<HumanBeing> humanBeingList=humanBeingService.findAll();

        PrintWriter printWriter = resp.getWriter();
        printWriter.write("Hello!");
        printWriter.close();

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String test = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        JSONObject weatherJsonObject = new JSONObject(test);

        HumanBeing humanBeing=new HumanBeing();
        humanBeing.setName((String) weatherJsonObject.get("name"));
        humanBeing.setRealHero((boolean) weatherJsonObject.get("realHero"));
        humanBeing.setHasToothpick((boolean) weatherJsonObject.get("hasToothpick"));
        humanBeing.setCoordinates(new Coordinates((Integer) weatherJsonObject.get("X"),(Integer)weatherJsonObject.get("Y")));
        humanBeing.setCar(new Car((boolean)weatherJsonObject.get("car")));
        humanBeing.setImpactSpeed((Float) weatherJsonObject.get("impactSpeed"));
        humanBeing.setCreationDate((LocalDate)weatherJsonObject.get("date"));
        humanBeing.setWeaponType((WeaponType)weatherJsonObject.get("weaponType") );

        resp.setContentType("text/html");
        HumanBeingService humanBeingService=new HumanBeingService();
        List<HumanBeing> humanBeingList=humanBeingService.findAll();

        PrintWriter printWriter = resp.getWriter();
        printWriter.write("Hello!");
        printWriter.close();

    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        HumanBeingService humanBeingService=new HumanBeingService();
        List<HumanBeing> humanBeingList=humanBeingService.findAll();

        PrintWriter printWriter = resp.getWriter();
        printWriter.write("Hello!");
        printWriter.close();
    }
}