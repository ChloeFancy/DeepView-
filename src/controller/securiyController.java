package controller;

import DAO.Impl.AdminDAOImpl;
import DAO.Impl.IndustryDAOImpl;
import DAO.Impl.SecurityDAOImpl;
import com.alibaba.fastjson.JSONArray;
import model.IndustryEntity;
import model.SecurityEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import util.BasicResponse;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/security")
@CrossOrigin(origins="*")
public class securiyController extends BaseController<SecurityEntity> {
    ApplicationContext context =
            new ClassPathXmlApplicationContext("../applicationContext.xml");
    //此路径是在生成的class文件（out/artifacts/DeepView_war_exploded/WEB-INF/classes/controller/BaseController.class）中的相对路径
    SecurityDAOImpl securityDAO = (SecurityDAOImpl) context.getBean("securityDAOImpl");
    IndustryDAOImpl industryDAO = (IndustryDAOImpl) context.getBean("industryDAOImpl");
    AdminDAOImpl adminDAO = (AdminDAOImpl) context.getBean(("adminDAOImpl"));

    @RequestMapping(value="/dimSearchByCode",method = {RequestMethod.GET})
    public @ResponseBody
    BasicResponse dimSearchByCode(
            @RequestParam(value="code") String code) {
        BasicResponse response = new BasicResponse();
        response.setResCode("-1");
        response.setResMsg("Error");
        try{
            response.setData(securityDAO.dimSearchByCode(code));
            response.setResCode("1");
            response.setResMsg("success");
            return response;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return response;
    }

    @RequestMapping(value="/dimSearchByName",method = {RequestMethod.GET})
    public @ResponseBody
    BasicResponse dimSearchByName(
            @RequestParam(value="name") String name) {
        BasicResponse response = new BasicResponse();
        response.setResCode("-1");
        response.setResMsg("Error");
        try{
            response.setData(securityDAO.dimSearchByName(name));
            response.setResCode("1");
            response.setResMsg("success");
            return response;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return response;
    }



    @RequestMapping(value="/updateDB",method = {RequestMethod.POST})
    public @ResponseBody
    BasicResponse updateDB(
        @RequestParam(value = "name") String name,
        @RequestParam(value = "password") String password) {
            BasicResponse response = new BasicResponse();
            response.setResCode("-1");
            response.setResMsg("Error");
            try {
                int login = adminDAO.login(name, password);
                if (login > 0) {

                    ArrayList<String> result = RunPython("update.py", new String[]{});
                    System.out.println(result.size());

                    JSONArray sucuArray = JSONArray.parseArray(result.get(1));
                    List<SecurityEntity> suculist=new ArrayList<>();
                    for (int i = 0; i < sucuArray.size(); i++) {
//                        System.out.println(sucuArray.get(i).toString());
                        JSONArray jsonArray1 = JSONArray.parseArray(sucuArray.get(i).toString());
                        SecurityEntity securityEntity = new SecurityEntity();
                        securityEntity.setSecuCode(jsonArray1.get(0).toString());
//                        System.out.println(jsonArray1.get(0).toString());
                        securityEntity.setSecuAbbr(jsonArray1.get(1).toString());
                        securityEntity.setChiSpelling(jsonArray1.get(2).toString());
                        securityEntity.setListedDate(java.sql.Date.valueOf(jsonArray1.get(3).toString()));
                        securityEntity.setEndDate(java.sql.Date.valueOf(jsonArray1.get(4).toString()));
                        securityEntity.setIndId(Integer.parseInt(jsonArray1.get(5).toString()));
                        securityEntity.setId(Integer.parseInt(jsonArray1.get(5).toString()));
                        securityEntity.setId(i+1);
                        suculist.add(securityEntity);
                    }
                    securityDAO.update(suculist);

                    JSONArray indArray = JSONArray.parseArray(result.get(0));
                    List<IndustryEntity> indlist=new ArrayList<>();
                    for (int i = 0; i < indArray.size(); i++) {
//                        System.out.println(indArray.get(i).toString());
                        JSONArray jsonArray1 = JSONArray.parseArray(indArray.get(i).toString());
                        IndustryEntity industryEntity = new IndustryEntity();
                        industryEntity.setIndCode(jsonArray1.get(0).toString());
//                        System.out.println(jsonArray1.get(0).toString());
                        industryEntity.setIndName(jsonArray1.get(1).toString());
                        industryEntity.setStartDate(java.sql.Date.valueOf(jsonArray1.get(2).toString()));
                        industryEntity.setId(i+1);
                        indlist.add(industryEntity);
                    }
                    industryDAO.update(indlist);

                    response.setResCode("1");
                    response.setResMsg("success");
                    response.setData(login);
                }
                return response;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return response;
        }

    @RequestMapping(value="/updateDBByGet",method = {RequestMethod.GET})
    public @ResponseBody
    BasicResponse updateDBByGet(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "password") String password) {
        BasicResponse response = new BasicResponse();
        response.setResCode("-1");
        response.setResMsg("Error");
        try {
            int login = adminDAO.login(name, password);
            if (login > 0) {

                ArrayList<String> result = RunPython("update.py", new String[]{});
                System.out.println(result.size());

                JSONArray sucuArray = JSONArray.parseArray(result.get(1));
                List<SecurityEntity> suculist=new ArrayList<>();
                for (int i = 0; i < sucuArray.size(); i++) {
//                        System.out.println(sucuArray.get(i).toString());
                    JSONArray jsonArray1 = JSONArray.parseArray(sucuArray.get(i).toString());
                    SecurityEntity securityEntity = new SecurityEntity();
                    securityEntity.setSecuCode(jsonArray1.get(0).toString());
//                        System.out.println(jsonArray1.get(0).toString());
                    securityEntity.setSecuAbbr(jsonArray1.get(1).toString());
                    securityEntity.setChiSpelling(jsonArray1.get(2).toString());
                    securityEntity.setListedDate(java.sql.Date.valueOf(jsonArray1.get(3).toString()));
                    securityEntity.setEndDate(java.sql.Date.valueOf(jsonArray1.get(4).toString()));
                    securityEntity.setIndId(Integer.parseInt(jsonArray1.get(5).toString()));
                    securityEntity.setId(Integer.parseInt(jsonArray1.get(5).toString()));
                    securityEntity.setId(i+1);
                    suculist.add(securityEntity);
                }
                securityDAO.update(suculist);

                JSONArray indArray = JSONArray.parseArray(result.get(0));
                List<IndustryEntity> indlist=new ArrayList<>();
                for (int i = 0; i < indArray.size(); i++) {
//                        System.out.println(indArray.get(i).toString());
                    JSONArray jsonArray1 = JSONArray.parseArray(indArray.get(i).toString());
                    IndustryEntity industryEntity = new IndustryEntity();
                    industryEntity.setIndCode(jsonArray1.get(0).toString());
//                        System.out.println(jsonArray1.get(0).toString());
                    industryEntity.setIndName(jsonArray1.get(1).toString());
                    industryEntity.setStartDate(java.sql.Date.valueOf(jsonArray1.get(2).toString()));
                    industryEntity.setId(i+1);
                    indlist.add(industryEntity);
                }
                industryDAO.update(indlist);

                response.setResCode("1");
                response.setResMsg("success");
                response.setData(login);
            }
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return response;
    }


    @RequestMapping(value="/empty",method = {RequestMethod.GET})
    public @ResponseBody
    BasicResponse empty(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "password") String password) {
        BasicResponse response = new BasicResponse();
        response.setResCode("-1");
        response.setResMsg("Error");
        try {
            int login = adminDAO.login(name, password);
            if (login > 0) {
                industryDAO.delete();
                securityDAO.delete();
                response.setResCode("1");
                response.setResMsg("success");
                response.setData(login);
            }
            return response;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return response;
    }

}
