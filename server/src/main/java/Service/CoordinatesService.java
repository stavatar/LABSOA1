package Service;


import Model.Coordinates;
import Utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CoordinatesService
{
    public Coordinates findById(int id)
    {
        return (Coordinates)HibernateUtil.getSessionFactory().openSession().get(Coordinates.class, id);
    }

    public void save(Coordinates user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(user);
        tx1.commit();
        session.close();
    }

    public void update(Coordinates user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(user);
        tx1.commit();
        session.close();
    }

    public void delete(Coordinates user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(user);
        tx1.commit();
        session.close();
    }

    public List<Coordinates> findAll() {
        List<Coordinates> users = (List<Coordinates>)  HibernateUtil.getSessionFactory().openSession().
                createQuery("From Coordinates").list();
        return users;
    }
}
