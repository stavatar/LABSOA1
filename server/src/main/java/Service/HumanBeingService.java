package Service;

import Model.HumanBeing;
import Utils.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class HumanBeingService
{
    public HumanBeing findById(int id) {
        return (HumanBeing)HibernateUtil.getSessionFactory().openSession().get(HumanBeing.class, id);
    }

    public void save(HumanBeing user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.save(user);
        tx1.commit();
        session.close();
    }

    public void update(HumanBeing user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(user);
        tx1.commit();
        session.close();
    }

    public void delete(HumanBeing user) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx1 = session.beginTransaction();
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
