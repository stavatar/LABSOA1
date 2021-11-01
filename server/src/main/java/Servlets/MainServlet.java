package Servlets;

import DAO.HumanBeingDAO;
import Model.HumanBeing;
import Utils.ConverterJSON;
import Service.HumanBeingService;

import org.hibernate.exception.DataException;
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
import java.util.Objects;
import java.util.stream.Collectors;

@WebServlet("/humanBeings/*")
public class MainServlet extends HttpServlet {
    HumanBeingService humanBeingService= new HumanBeingService();
    ConverterJSON converterJSON = new ConverterJSON();
    HumanBeingDAO humanBeingDAO=new HumanBeingDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        JSONObject message = new JSONObject();
        PrintWriter printWriter = resp.getWriter();
        JSONArray filters=new JSONArray("[]");
        try {
            String sort_nameField = req.getParameter("sortField");
            boolean isAscending =  Boolean.parseBoolean(req.getParameter("isAsc"));
            int sizePage =Integer.parseInt(req.getParameter("sizePage"));
            int numberPage= Integer.parseInt( req.getParameter("numberPage"));
            int startIndex=(numberPage-1)*sizePage;
            int endIndex=(numberPage-1)*sizePage+sizePage;
            String string_filters;
            if(req.getParameter("filters")!=null) {
                string_filters = req.getParameter("filters");
                if (!Objects.equals(string_filters, "")) {
                    filters = new JSONArray(string_filters);
                }
            }

            List<HumanBeing> humanBeingList=humanBeingDAO.findAll();
            List<HumanBeing> filterList=humanBeingList;
            for (int i=0;i<filters.length();i++) {
                JSONObject current=filters.getJSONObject(i);
                String nameField= current.getString("nameField");
                String action = current.getString("action");
                String valueFilter =current.getString("value");
                filterList=humanBeingService.filter(nameField,valueFilter,action,filterList);
            }
            humanBeingService.sort( isAscending,sort_nameField,filterList);
            List<HumanBeing> resultList =filterList;
            if (resultList.size()>0)
                resultList=resultList.subList(startIndex, Math.min(resultList.size(), endIndex));
            JSONArray jsonText = converterJSON.listToJSON(resultList);
            message.put("code",1);
            message.put("data",jsonText.toString());
            message.put("allSizeList",filterList.size());

        }catch (IllegalArgumentException | JSONException | DataException e){
            message.put("code",0);
            message.put("data","Error! Invalid input data");
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }catch (JDBCConnectionException e){
            message.put("code",0);
            message.put("data","Error! No database connection ");
            resp.setStatus(HttpServletResponse.SC_SERVICE_UNAVAILABLE);
        }catch(Exception e){
            message.put("code",0);
            message.put("data","Request failed");
            resp.setStatus(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);

        }finally {
            printWriter.write(message.toString());
            printWriter.close();
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String jsonBody = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        JSONObject message = new JSONObject();
        resp.setContentType("application/json");
        PrintWriter printWriter = resp.getWriter();
        HumanBeing newHuman = null;
        try {
             newHuman=humanBeingService.initHumanBeing(jsonBody);
             humanBeingDAO.save(newHuman);
             message.put("code",1);
             message.put("data","Objects added!");
             resp.setStatus(HttpServletResponse.SC_CREATED);
        }catch (IllegalArgumentException | JSONException| DataException e){
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

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json");
        JSONObject message = new JSONObject();
        PrintWriter printWriter = resp.getWriter();
        HumanBeing humanBeing;
        try {
            String body = req.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
            JSONObject jsonBody = new JSONObject(body);
            int id= jsonBody.getInt("id");
            String fieldName=  jsonBody.getString("fieldName");
            String newValue=  jsonBody.getString("newValue");
            humanBeing = humanBeingDAO.findById(id);
            humanBeingDAO.update(humanBeing,fieldName,newValue);
            message.put("code",1);
            message.put("data","Objects updated!");
        }catch (IllegalArgumentException | JSONException | DataException e){
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
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        JSONObject message = new JSONObject();
        PrintWriter printWriter = resp.getWriter();
        resp.setContentType("application/json");
        int id;
        try {
            //String test = req.getParameter("id");
            String param=req.getPathInfo().replace("/", "");
            id = Integer.parseInt(param.replace("/", ""));
            humanBeingDAO.delete(humanBeingDAO.findById(new Long(id)));
            message.put("code",1);
            message.put("data","Object successfully deleted!");
        }catch (IllegalArgumentException | JSONException | DataException e){
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