package Utils;

import Model.HumanBeing;
import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

public class ConverterJSON {

    public JSONArray listToJSON(List<HumanBeing> humanBeingList){
        JSONArray head = new JSONArray();
        for (HumanBeing current:humanBeingList) {
            JSONObject child = new JSONObject();
            child.put("id",current.getId());
            child.put("name",current.getName());
            child.put("car",current.getCar().getCool());
            child.put("impactSpeed",current.getImpactSpeed());
            child.put("X",current.getCoordinates().getX());
            child.put("Y",current.getCoordinates().getY());
            child.put("typeMood",current.getMood().name());
            child.put("typeWeapon",current.getWeaponType().name());
            child.put("date",current.getCreationDate().toLocalDate());
            child.put("realHero",current.isRealHero());
            child.put("hasToothpick",current.isHasToothpick());
            head.put(child);

        }
        return head;
    }

}
