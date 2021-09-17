package Utils;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;


import javax.servlet.ServletContext;
import java.io.File;

public class HibernateUtil
{
    private HibernateUtil()
        {}
    private static final SessionFactory sessionFactory;
    static {
        try {
            Configuration config = new Configuration();
            config.configure();
            sessionFactory = config.buildSessionFactory();
        } catch (Throwable ex) {
            System.err.println("Initial SessionFactory creation failed." + ex);
            throw new ExceptionInInitializerError(ex);
        }
    }

    public static SessionFactory getSessionFactory() {
        return sessionFactory;
    }
}