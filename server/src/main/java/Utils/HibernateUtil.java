package Utils;

import Model.Car;
import Model.Coordinates;
import Model.HumanBeing;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


public class HibernateUtil
{
    private HibernateUtil()
        {}
    private static final SessionFactory sessionFactory;
    static {
        try {
            sessionFactory  = new Configuration().configure().addAnnotatedClass(HumanBeing.class).addAnnotatedClass(Car.class).addAnnotatedClass(Coordinates.class).buildSessionFactory();


        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}