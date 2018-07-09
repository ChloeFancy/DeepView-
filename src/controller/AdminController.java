package controller;

import DAO.Impl.AdminDAOImpl;
import model.AdminEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import util.BasicResponse;

@Controller
@RequestMapping("/admin")
@CrossOrigin(origins="*")
public class AdminController extends BaseController<AdminEntity> {
    ApplicationContext context =
            new ClassPathXmlApplicationContext("../applicationContext.xml");
    //此路径是在生成的class文件（out/artifacts/DeepView_war_exploded/WEB-INF/classes/controller/BaseController.class）中的相对路径
    AdminDAOImpl adminDAO = (AdminDAOImpl) context.getBean("adminDAOImpl");

    @RequestMapping(value = "/login", method = {RequestMethod.POST})
    public @ResponseBody
    BasicResponse login(
            @RequestParam(value = "name") String name,
            @RequestParam(value = "password") String password) {
        BasicResponse response = new BasicResponse();
        response.setResCode("-1");
        response.setResMsg("Error");
        try {
            int login = adminDAO.login(name, password);
            if (login > 0) {
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