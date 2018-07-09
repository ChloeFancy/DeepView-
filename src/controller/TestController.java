package controller;

import DAO.Impl.TestDAOImpl;
import model.TestEntity;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import util.BasicResponse;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/test")
@CrossOrigin(origins="*")
public class TestController extends BaseController<TestEntity> {
    ApplicationContext context =
            new ClassPathXmlApplicationContext("../applicationContext.xml");
    //此路径是在生成的class文件（out/artifacts/DeepView_war_exploded/WEB-INF/classes/controller/BaseController.class）中的相对路径
    TestDAOImpl testDAO = (TestDAOImpl) context.getBean("testDAOImpl");


    @RequestMapping(value="/test",method = {RequestMethod.GET})
    public @ResponseBody
    BasicResponse test(HttpServletRequest request){
        BasicResponse response = new BasicResponse();
        testDAO.test();
//        response.setData();
        return response;
    }


}
