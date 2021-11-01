package Service;

import Model.HumanBeing;

import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

public class FilterService {

    public Boolean filterNumber(String valueFilter,String action, double current){
        double value = Double.parseDouble(valueFilter);
        switch (action){
            case "=":
                return current == value;
            case ">":
                return current > value;
            case ">=":
                return current >= value;
            case "<":
                return current < value;
            case "<=":
                return current <= value;
            case "!=":
                return current != value;
            default:
                return true;
        }
    }
    public Boolean filterDate(String valueFilter,String action, ZonedDateTime current){

        ZonedDateTime value = ZonedDateTime.parse(valueFilter);
        switch (action){
            case "=":
                return current.isEqual(value);
            case ">":
                return current.isAfter(value);
            case ">=":
                return current.isAfter(value)||current.isEqual(value);
            case "<":
                return current.isBefore(value);
            case "<=":
                return current.isBefore(value)||current.isEqual(value);
            case "!=":
                return !current.isEqual(value);
            default:
                return true;
        }
    }


}
