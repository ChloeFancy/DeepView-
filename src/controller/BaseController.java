package controller;

import DAO.Impl.BaseDAOImpl;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import util.BasicResponse;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

@Controller
@CrossOrigin(origins="*")
public abstract class BaseController<T> {
    ApplicationContext context =
            new ClassPathXmlApplicationContext("../applicationContext.xml");
    //此路径是在生成的class文件（out/artifacts/DeepView_war_exploded/WEB-INF/classes/controller/BaseController.class）中的相对路径
    BaseDAOImpl<T> baseDAO = (BaseDAOImpl<T>) context.getBean("baseDaoImpl");

    @RequestMapping(value="/add",method={RequestMethod.POST})
    public @ResponseBody
    BasicResponse add(T t, HttpServletRequest request){
        BasicResponse response = new BasicResponse();
        response.setResCode("-1");
        response.setResMsg("Error");

//        BaseDAOImpl<T> baseDAO = new BaseDAOImpl<>();
        try{
            request.setCharacterEncoding("utf-8");
            if(baseDAO.insert(t).equals("success")){
                response.setResCode("1");
                response.setResMsg("success");
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return response;
    }

//    @RequestMapping(value="/login",method = {RequestMethod.POST})
//    public @ResponseBody
//    BasicResponse login(T t, HttpServletRequest request) {
//        BasicResponse response = new BasicResponse();
//        response.setResCode("-1");
//        response.setResMsg("Error");
//        try{
//            BaseDAOImpl<T> baseDAO = new BaseDAOImpl<>();
//            T result = baseDAO.findOne(t);
//            if(result!=null){
//                //生成用户登录的token标记登录状态
//                String token = JWT.sign(t, 30L * 24L * 3600L * 1000L);
//                if (token != null) {
//                    JSONObject tokenObj = new JSONObject();
//                    tokenObj.put("token",token);
//                    response.setData(tokenObj);
//                }
//                response.setResCode("1");
//                response.setResMsg("success");
//            }
//            return response;
//        }catch(Exception ex){
//            ex.printStackTrace();
//        }
//        return response;
//    }

    @RequestMapping(value="/logout",method = {RequestMethod.GET})
    public @ResponseBody
    BasicResponse logout(T t, HttpServletRequest request) {
        BasicResponse response = new BasicResponse();
        response.setResCode("-1");
        response.setResMsg("Error");
        try{
            request.getSession().removeAttribute("session_id");
            response.setResCode("1");
            response.setResMsg("success");
            return response;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return response;
    }


    @RequestMapping(value="/findById",method = {RequestMethod.GET})
    public @ResponseBody
    BasicResponse findById(T t, HttpServletRequest request) {
        BasicResponse response = new BasicResponse();
        response.setResCode("-1");
        response.setResMsg("Error");
//        BaseDAOImpl<T> admin = new BaseDAOImpl<>();
        try{
            response.setData(baseDAO.findById(t));
            response.setResCode("1");
            response.setResMsg("success");
            return response;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value="/findAll",method = {RequestMethod.GET})
    public @ResponseBody
    BasicResponse findAll(T t, HttpServletRequest request) {
        BasicResponse response = new BasicResponse();
        response.setResCode("-1");
        response.setResMsg("Error");
//        BaseDAOImpl<T> admin = new BaseDAOImpl<>();
        try{
            response.setData(baseDAO.findList(t));
            response.setResMsg(baseDAO.countAll(t)+"");
            response.setResCode("1");
            response.setResMsg("success");
            return response;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value="/findOne",method = {RequestMethod.GET})
    public @ResponseBody
    BasicResponse findOne(T t, HttpServletRequest request) {
        BasicResponse response = new BasicResponse();
        response.setResCode("-1");
        response.setResMsg("Error");
//        BaseDAOImpl<T> admin = new BaseDAOImpl<>();
        try{
            T tmp = baseDAO.findOne(t);
            response.setData(tmp);
            if(tmp==null){
                //fail
                response.setResCode("0");
            }else{
                //success
                response.setResCode("1");
                response.setResMsg("success");
            }
            return response;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    @RequestMapping(value="/update",method = {RequestMethod.POST})
    public @ResponseBody
    BasicResponse update(T t, HttpServletRequest request) {
        BasicResponse response = new BasicResponse();
        response.setResCode("-1");
        response.setResMsg("Error");
//        BaseDAOImpl<T> admin = new BaseDAOImpl<>();
        try{
            request.setCharacterEncoding("utf-8");
            baseDAO.update(t);
            response.setResCode("1");
            response.setResMsg("success");
            return response;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return response;
    }

    @RequestMapping(value="/delete",method = {RequestMethod.POST})
    public @ResponseBody
    BasicResponse delete(T t,HttpServletRequest request) {
        BasicResponse response = new BasicResponse();
        response.setResCode("-1");
        response.setResMsg("Error");
//        BaseDAOImpl<T> admin = new BaseDAOImpl<>();
        try{
            baseDAO.delete(t);
            response.setResCode("1");
            response.setResMsg("success");
            return response;
        }catch(Exception ex){
            response.setResCode("-1");
            response.setResMsg("Error");
            ex.printStackTrace();
        }
        return response;
    }


    @RequestMapping(value="/findByQuery",method = {RequestMethod.GET})
    public @ResponseBody
    BasicResponse findByQuery(T t,HttpServletRequest request) {
        BasicResponse response = new BasicResponse();
        response.setResCode("-1");
        response.setResMsg("Error");
//        BaseDAOImpl<T> admin = new BaseDAOImpl<>();
        try{
            response.setData(baseDAO.findByQuery(t));
            response.setResCode("1");
            response.setResMsg("success");
            return response;
        }catch(Exception ex){
            response.setResCode("-1");
            response.setResMsg("Error");
            ex.printStackTrace();
        }
        return response;
    }
    public ArrayList<String> RunPython(String fileName, String [] argv ) throws Exception {
        String path=getClass().getResource("").getPath();
        System.out.println(path);

        path=path.substring(0,path.length()-11)+ "python/" + fileName;
        System.out.println(path);
        String[] runpy= new String[2+argv.length];
        runpy[0] = "python";
        runpy[1] = path;
        System.arraycopy(argv, 0, runpy, 2, argv.length);
        for (int i = 0; i < runpy.length; i++) {
            System.out.println(runpy[i]);
        }
        Process pr=Runtime.getRuntime().exec(runpy);
        BufferedReader in = new BufferedReader(new InputStreamReader(pr.getInputStream()));
        ArrayList<String> result = new ArrayList<>();
        String line;
        boolean flag=true;//去除返回值第一行的auth success
        while ((line = in.readLine()) != null) {
            if(!flag){
                System.out.println(line);
//                String a = String.valueOf(34);
                line=line.replaceAll("'","\"");
                System.out.println(line);

//                line=line.replaceAll("\\\\","");
                result.add(line);
            }
            flag=false;
        }
        in.close();
        pr.waitFor();
        return result;
    }


}