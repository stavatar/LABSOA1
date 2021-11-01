package Service;

import DAO.HumanBeingDAO;
import Model.Car;
import Model.Coordinates;
import Model.HumanBeing;
import Model.util.Mood;
import Model.util.WeaponType;
import Utils.HibernateUtil;
import org.hibernate.LockMode;
import org.hibernate.LockOptions;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.json.JSONObject;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class HumanBeingService
{
    FilterService filterService = new FilterService();
    HumanBeingDAO humanBeingDAO=new HumanBeingDAO();

    public HumanBeing initHumanBeing(String test)
    {
        JSONObject json = new JSONObject(test);
        HumanBeing humanBeing=new HumanBeing();
        if(json.has("id"))
            humanBeing.setId(json.getInt("id"));

        humanBeing.setRealHero(Boolean.parseBoolean(json.getString("realHero")));
        humanBeing.setHasToothpick(Boolean.parseBoolean(json.getString("hasToothpick")));
        humanBeing.setCar(new Car(Boolean.parseBoolean(json.getString("car"))));
        String name=json.getString("name");
        if(name.equals(""))
            throw new IllegalArgumentException();
        humanBeing.setName(name);

        double y=json.getDouble("Y");
        if (y>369)
            throw new IllegalArgumentException();
        humanBeing.setCoordinates(new Coordinates( json.getDouble("X"),y));

        humanBeing.setImpactSpeed( json.getDouble("impactSpeed"));
        humanBeing.setCreationDate(ZonedDateTime.now());
        humanBeing.setWeaponType(WeaponType.valueOf(json.getString("typeWeapon") ));
        humanBeing.setMood(Mood.valueOf(json.getString("typeMood") ));
        return humanBeing;
    }

    public void sort(boolean isAscending,String nameField, List<HumanBeing> humanBeingList) {
        switch (nameField){
            case "id":
                humanBeingList.sort(Comparator.comparingDouble(HumanBeing::getId));
                break;
            case "name":
                humanBeingList.sort(Comparator.comparing(HumanBeing::getName));
                break;
            case "impactSpeed":
                humanBeingList.sort(Comparator.comparingDouble(HumanBeing::getImpactSpeed));
                break;
            case "X":
                humanBeingList.sort(Comparator.comparingDouble(o -> o.getCoordinates().getX()));
                break;
            case "Y":

                humanBeingList.sort(Comparator.comparingDouble(o -> o.getCoordinates().getY()));
                break;
            case "typeWeapon":
                humanBeingList.sort(Comparator.comparingDouble(o -> o.getWeaponType().ordinal()));
                break;
            case "typeMood":
                humanBeingList.sort(Comparator.comparingInt(o -> o.getMood().ordinal()));
                break;
            case "car":
                humanBeingList.sort((o1, o2) ->Boolean.compare(o1.getCar().getCool(),o2.getCar().getCool()));
                break;
            case "realHero":
                humanBeingList.sort((o1, o2) ->Boolean.compare(o1.isRealHero(),o2.isRealHero()));
                break;
            case "hasToothpick":
                humanBeingList.sort((o1, o2) ->Boolean.compare(o1.isHasToothpick(),o2.isHasToothpick()));
                break;
            case "date":
                humanBeingList.sort(Comparator.comparing(HumanBeing::getCreationDate));
                break;
            default:
                throw new IllegalArgumentException();
        }
        if (isAscending)
            Collections.reverse(humanBeingList);

    }

    public List<HumanBeing>  filter(String nameField,String valueFilter,String action, List<HumanBeing> humanBeingList) {
        List<HumanBeing> resultList;
        switch (nameField){
            case "name":
                resultList=humanBeingList.stream().filter(humanBeing -> humanBeing.getName().equals(valueFilter)).collect(Collectors.toList());
                break;
            case "impactSpeed":
                resultList=humanBeingList.stream()
                        .filter(humanBeing ->
                                filterService.filterNumber( valueFilter, action,  humanBeing.getImpactSpeed()))
                        .collect(Collectors.toList());
                break;
            case "X":
                resultList=humanBeingList.stream()
                        .filter(humanBeing ->
                                filterService.filterNumber( valueFilter, action,  humanBeing.getCoordinates().getX()))
                        .collect(Collectors.toList());
                break;
            case "Y":
                resultList=humanBeingList.stream()
                        .filter(humanBeing ->
                                filterService.filterNumber( valueFilter, action,  humanBeing.getCoordinates().getY()))
                        .collect(Collectors.toList());
                break;
            case "typeWeapon":
                resultList=humanBeingList.stream()
                        .filter(humanBeing -> humanBeing.getWeaponType().ordinal() == Integer.parseInt(valueFilter))
                        .collect(Collectors.toList());
                break;
            case "typeMood":
                resultList=humanBeingList.stream()
                        .filter(humanBeing -> humanBeing.getMood().ordinal() == Integer.parseInt(valueFilter))
                        .collect(Collectors.toList());
                break;
            case "car":
                resultList=humanBeingList.stream()
                        .filter(humanBeing ->
                                humanBeing.getCar().getCool() == "1".equals(valueFilter))
                        .collect(Collectors.toList());
                break;
            case "realHero":
                resultList=humanBeingList.stream()
                        .filter(humanBeing -> humanBeing.isRealHero() == "1".equals(valueFilter))
                        .collect(Collectors.toList());
                break;
            case "hasToothpick":
                resultList=humanBeingList.stream()
                        .filter(humanBeing -> humanBeing.isHasToothpick()== "1".equals(valueFilter))
                        .collect(Collectors.toList());
                break;
            case "date":
                resultList=humanBeingList.stream()
                        .filter(humanBeing ->
                                filterService.filterDate( valueFilter, action, humanBeing.getCreationDate()))
                        .collect(Collectors.toList());
                break;
            default:
                throw new IllegalArgumentException();
        }
      return resultList;
    }

    public void deleteMood(Mood mood){
        List<HumanBeing> humanBeingList= humanBeingDAO.findAll();
        for (HumanBeing current:humanBeingList) {
            if (current.getMood()==mood)
                humanBeingDAO.delete(current);
        }
    }
    public int countLessMood(Mood mood){
        int countHuman=0;
        List<HumanBeing>  humanBeingList= humanBeingDAO.findAll();
        for (HumanBeing current:humanBeingList) {
            if (current.getMood().ordinal() < mood.ordinal())
                countHuman++;
        }
        return countHuman;
    }

    public double avrSpeed() {
        double avr;
        double sum=0;
        List<HumanBeing> humanBeingList = humanBeingDAO.findAll();
        for (HumanBeing current : humanBeingList) {
            sum += current.getImpactSpeed();
        }
        avr = sum / humanBeingList.size();
        avr = Math.round(avr * 1000);
        avr = avr / 1000;
        return avr;
    }

}
