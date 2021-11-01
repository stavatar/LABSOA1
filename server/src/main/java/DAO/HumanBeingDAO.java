package DAO;

import Model.Car;
import Model.Coordinates;
import Model.HumanBeing;
import Model.util.Mood;
import Model.util.WeaponType;
import Service.FilterService;
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

public class HumanBeingDAO
{

    public HumanBeing findById(long id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        HumanBeing humanBeing= session.get(HumanBeing.class, id);
        session.close();
        return humanBeing;
    }

    public void save(HumanBeing user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(user);
        tx1.commit();
        session.close();
    }

    public void update(HumanBeing user,String nameField,String newValue) {

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        switch (nameField){
            case "name":
                if(newValue.equals(""))
                    throw new IllegalArgumentException();
                user.setName(newValue);
                break;
            case "impactSpeed":
                user.setImpactSpeed(Double.parseDouble(newValue));
                break;
            case "typeWeapon":
                user.setWeaponType(WeaponType.valueOf(newValue));
                break;
            case "typeMood":
                user.setMood(Mood.valueOf(newValue));
                break;
            case "X":
                user.getCoordinates().setX(Double.parseDouble(newValue));
                break;
            case "Y":
                double y=Double.parseDouble(newValue);
                if (y>369)
                    throw new IllegalArgumentException();
                user.getCoordinates().setY(y);
                break;
            case "car":
                user.getCar().setCool(Boolean.parseBoolean(newValue));
                break;
            case "realHero":
                user.setRealHero(Boolean.parseBoolean(newValue));
                break;
            case "hasToothpick":
                user.setHasToothpick(Boolean.parseBoolean(newValue));
                break;
            default:
                throw new IllegalArgumentException("");
        }

        session.update(user);
        tx1.commit();
        session.close();
    }

    public void delete(HumanBeing user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();

        session.delete(user.getCoordinates());
        session.delete(user.getCar());
        session.delete(user);

        tx1.commit();
        session.close();
    }

    public List<HumanBeing> findAll() {
        List<HumanBeing> users = (List<HumanBeing>)  HibernateUtil.getSessionFactory().openSession().
                createQuery("From HumanBeing").list();
        return users;
    }




}
