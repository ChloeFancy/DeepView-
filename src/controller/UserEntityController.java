package controller;

import DAO.Impl.MyStocksDAOImpl;
import DAO.Impl.UserEntityDAOImpl;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import model.UserEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import util.BasicResponse;

import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/user")
@CrossOrigin(origins="*")
public class UserEntityController extends BaseController<UserEntity> {
    ApplicationContext context =
            new ClassPathXmlApplicationContext("../applicationContext.xml");
    //此路径是在生成的class文件（out/artifacts/DeepView_war_exploded/WEB-INF/classes/controller/BaseController.class）中的相对路径
    UserEntityDAOImpl userEntityDAO = (UserEntityDAOImpl) context.getBean("userEntityDAOImpl");
    MyStocksDAOImpl myStocksDAO = (MyStocksDAOImpl) context.getBean("myStocksDAOImpl");

    @RequestMapping(value="/login",method = {RequestMethod.POST})
    public @ResponseBody BasicResponse login(
            @RequestParam(value="name") String name,
            @RequestParam(value="password") String password) {
        BasicResponse response = new BasicResponse();
        response.setResCode("-1");
        response.setResMsg("Error");
        try{
            int login = userEntityDAO.login(name, password);
            if(login>0){
                response.setResCode("1");
                response.setResMsg("success");
                response.setData(login);
            }
            return response;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return response;
    }

    @RequestMapping(value="/myStocks",method = {RequestMethod.GET})
    public @ResponseBody BasicResponse myStocks(@RequestParam(value="userID") String userID) {
        BasicResponse response = new BasicResponse();
        response.setResCode("-1");
        response.setResMsg("Error");
        try{
            JSONObject jsonObject = new JSONObject();
            List<String> list= myStocksDAO.getSecurity(userID);
            for (int i = 0; i < list.size(); i++) {
                System.out.println(list.get(i));
            }
            ArrayList<String> result = RunPython("myStocks.py",list.toArray(new String[list.size()]));
            jsonObject.put("chartData",JSONArray.parseArray(result.get(0)));
            response.setResCode("1");
            response.setResMsg("success");
            response.setData(jsonObject);

            return response;
        }catch(Exception ex){
            ex.printStackTrace();
        }
        return response;
    }

}
