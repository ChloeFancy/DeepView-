package DAO.Impl;

import DAO.TestDAO;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

public class TestDAOImpl implements TestDAO {
    @Qualifier("sessionFactory")
    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public void test(){
        Session s = sessionFactory.openSession();
        Transaction tx = s.beginTransaction();
        String sql = "truncate table deepView.test";
        System.out.println(sql);
        s.createSQLQuery(sql).executeUpdate();
        tx.commit();
    }
}
