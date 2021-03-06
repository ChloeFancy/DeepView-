package DAO.Impl;

import DAO.UserEntityDAO;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

public class UserEntityDAOImpl implements UserEntityDAO {

    @Qualifier("sessionFactory")
    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public int login(String name, String password) {
        Session s = sessionFactory.openSession();
        Transaction tx = s.beginTransaction();
        String hql = "select id from UserEntity where name = '" + name + "' and password = '" + password+"'";
        Query query = s.createQuery(hql);
        tx.commit();
        List list = query.list();
        if(list.size() > 0)
            return Integer.parseInt(list.get(0).toString());
        else return 0;
    }

}
