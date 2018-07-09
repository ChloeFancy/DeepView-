package DAO.Impl;

import DAO.IndustryDAO;
import model.IndustryEntity;
import model.SecurityEntity;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.List;

public class IndustryDAOImpl implements IndustryDAO {
    @Qualifier("sessionFactory")
    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }
    public void update(List<IndustryEntity> list) {
        delete();
        Session s = sessionFactory.openSession();
        Transaction tx = s.beginTransaction();
        IndustryEntity industryEntity=null;
        for (int i = 0; i < list.size(); i++) {
            industryEntity = list.get(i);
            s.save(industryEntity);
            if (i%10==0){
                s.flush();
                s.clear();
            }
        }
        tx.commit();
    }

    public void delete() {
        Session s = sessionFactory.openSession();
        Transaction tx = s.beginTransaction();
        String sql = "truncate table deepView.Industry";
        System.out.println(sql);
        s.createSQLQuery(sql).executeUpdate();
        tx.commit();

    }
}
