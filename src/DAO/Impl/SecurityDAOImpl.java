package DAO.Impl;

import DAO.SecurityDAO;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import model.SecurityEntity;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.query.Query;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SecurityDAOImpl implements SecurityDAO {
    @Qualifier("sessionFactory")
    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    public List<JSONObject> dimSearchByCode(String code) {
        Session s = sessionFactory.openSession();
        Transaction tx = s.beginTransaction();
        //String hql = "from SecurityEntity where SecurityEntity.secuCode like '" + code + "%'";
        String hql = "from SecurityEntity where secuCode like '" + code + "%'";
        System.out.println(hql);
        Query query = s.createQuery(hql);
        List list = query.list();
        Iterator iterator = list.iterator();
        tx.commit();
        List<JSONObject> result = new ArrayList<>();
        while(iterator.hasNext()){
            SecurityEntity securityEntity = (SecurityEntity) iterator.next();
            JSONObject item = new JSONObject();
            item.put("code",securityEntity.getSecuCode());
            item.put("name", securityEntity.getSecuAbbr());
            result.add(item);
        }
        return result;
    }

    public List<JSONObject> dimSearchByName(String name) {
        Session s = sessionFactory.openSession();
        Transaction tx = s.beginTransaction();
        String hql = "from SecurityEntity where secuAbbr like '%" + name + "%'";
        Query query = s.createQuery(hql);
        List list = query.list();
        Iterator iterator = list.iterator();
        tx.commit();
        List<JSONObject> result = new ArrayList<>();
        while(iterator.hasNext()){
            SecurityEntity securityEntity = (SecurityEntity) iterator.next();
            JSONObject item = new JSONObject();
            item.put("code",securityEntity.getSecuCode());
            item.put("name", securityEntity.getSecuAbbr());
            result.add(item);
        }
        return result;
    }

    public String getIndustry(String secuCode) {
        Session s = sessionFactory.openSession();
        Transaction tx = s.beginTransaction();
        String hql = "select ind.indCode from SecurityEntity se, IndustryEntity ind where se.indId=ind.id and se.secuCode = '"+secuCode+"'";
        System.out.println(hql);
        Query query = s.createQuery(hql);
        List list = query.list();
        tx.commit();

        Iterator iterator = list.iterator();
        List<String> result = new ArrayList<>();
        while(iterator.hasNext()){
            result.add((String) iterator.next());
        }
        return result.get(0);
    }

    public void update(List<SecurityEntity> list) {
        delete();
        Session s = sessionFactory.openSession();
        Transaction tx = s.beginTransaction();
        SecurityEntity securityEntity=null;
        for (int i = 0; i < list.size(); i++) {
            securityEntity = list.get(i);
            s.save(securityEntity);
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
        String sql = "truncate table deepView.Security";
        System.out.println(sql);
        s.createSQLQuery(sql).executeUpdate();
        tx.commit();

    }


}
