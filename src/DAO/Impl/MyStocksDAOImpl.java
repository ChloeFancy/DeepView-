package DAO.Impl;

import DAO.MyStocksDAO;
import com.alibaba.fastjson.JSONObject;
import model.MyStocksEntity;
import model.SecurityEntity;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class MyStocksDAOImpl implements MyStocksDAO {
    @Qualifier("sessionFactory")
    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<String> getSecurity(String id) {
        Session s = sessionFactory.openSession();
        Transaction tx = s.beginTransaction();
        String hql = "select s.secuCode  from MyStocksEntity ms,SecurityEntity s where userId = " + id+
                " and ms.secuId=s.id";
        System.out.println(hql);
        Query query = s.createQuery(hql);
        List list = query.list();
        tx.commit();

        Iterator iterator = list.iterator();
        List<String> result = new ArrayList<>();
        while(iterator.hasNext()){
            result.add((String) iterator.next());
        }
        return result;
    }
}
