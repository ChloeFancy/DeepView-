package controller;

import DAO.Impl.SecurityDAOImpl;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import util.BasicResponse;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.JSONArray;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;

@Controller
@RequestMapping("/chart")
@CrossOrigin(origins="*")
public class ChartController extends BaseController{

    ApplicationContext context =
            new ClassPathXmlApplicationContext("../applicationContext.xml");
    //此路径是在生成的class文件（out/artifacts/DeepView_war_exploded/WEB-INF/classes/controller/BaseController.class）中的相对路径
    SecurityDAOImpl securityDAO = (SecurityDAOImpl) context.getBean("securityDAOImpl");

    @RequestMapping(value="/realTimePrice",method={RequestMethod.GET})
    public @ResponseBody
    BasicResponse realTimePrice(@RequestParam String secuCode, HttpServletRequest request){
        BasicResponse response = new BasicResponse();
        response.setResCode("1");
        response.setResMsg("success");
        try {
            ArrayList<String> result=RunPython("realTimePrice.py",new String[]{secuCode});
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("secuName",result.get(0));
            jsonObject.put("chartData", JSONArray.parseArray(result.get(1)));
            response.setData(jsonObject);
        }catch (Exception e){
            response.setResCode("-1");
            response.setResMsg("error");
            e.printStackTrace();
        }
        return response;
    }

    @RequestMapping(value="/abnormal",method={RequestMethod.GET})
    public @ResponseBody
    BasicResponse abnormal(@RequestParam String secuCode, HttpServletRequest request){
        BasicResponse response = new BasicResponse();
        response.setResCode("1");
        response.setResMsg("success");
        try {
            ArrayList<String> result=RunPython("abnormal.py",new String[]{secuCode});
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("secuName",result.get(0));
            jsonObject.put("stat_list", JSONArray.parseArray(result.get(1)));
            jsonObject.put("value_list", JSONArray.parseArray(result.get(2)));
            response.setData(jsonObject);
        }catch (Exception e){
            response.setResCode("-1");
            response.setResMsg("error");
            e.printStackTrace();
        }
        return response;
    }

    @RequestMapping(value="/correlation",method={RequestMethod.GET})
    public @ResponseBody
    BasicResponse correlation(@RequestParam ArrayList<String> secuCode, @RequestParam String startDate, @RequestParam String endDate, HttpServletRequest request){
        BasicResponse response = new BasicResponse();
        response.setResCode("1");
        response.setResMsg("success");
        try {
            String[] argv = new String[secuCode.size() + 2];
            argv[0]=startDate;
            argv[1]=endDate;
            System.arraycopy(secuCode.toArray(new String[secuCode.size()]),0,argv,2,secuCode.size());
            ArrayList<String> result=RunPython("correlation.py",argv);
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("secuNames", JSONArray.parseArray(result.get(0)));
            jsonObject.put("chartData", JSONArray.parseArray(result.get(1)));
            response.setData(jsonObject);
        }catch (Exception e){
            response.setResCode("-1");
            response.setResMsg("error");
            e.printStackTrace();
        }
        return response;
    }

    @RequestMapping(value="/regression",method={RequestMethod.GET})
    public @ResponseBody
    BasicResponse regression(@RequestParam String secuCode1, @RequestParam String secuCode2, @RequestParam String startDate, @RequestParam String endDate, HttpServletRequest request){
        BasicResponse response = new BasicResponse();
        response.setResCode("1");
        response.setResMsg("success");
        try {
            ArrayList<String> result = RunPython("regression.py", new String[]{secuCode1, secuCode2, startDate, endDate});
            System.out.println(result.size());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("secuName1",result.get(0));
            jsonObject.put("secuName2",result.get(1));
            jsonObject.put("legendData",JSONArray.parseArray(result.get(2)));
            jsonObject.put("chartData", JSONArray.parseArray(result.get(3)));
            response.setData(jsonObject);
        }catch (Exception e){
            response.setResCode("-1");
            response.setResMsg("error");
            e.printStackTrace();
        }
        return response;
    }

    @RequestMapping(value="/priceCompare",method={RequestMethod.GET})
    public @ResponseBody
    BasicResponse priceCompare(@RequestParam String secuCode1, @RequestParam String secuCode2, @RequestParam String startDate, @RequestParam String endDate, HttpServletRequest request){
        BasicResponse response = new BasicResponse();
        response.setResCode("1");
        response.setResMsg("success");
        try {
            ArrayList<String> result = RunPython("priceCompare.py", new String[]{secuCode1, secuCode2, startDate, endDate});
            System.out.println(result.size());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("secuName1",result.get(0));
            jsonObject.put("secuName2",result.get(1));
            jsonObject.put("chartData",JSONArray.parseArray(result.get(2)));
            response.setData(jsonObject);
        }catch (Exception e){
            response.setResCode("-1");
            response.setResMsg("error");
            e.printStackTrace();
        }
        return response;
    }

    @RequestMapping(value="/season",method={RequestMethod.GET})
    public @ResponseBody
    BasicResponse season(@RequestParam String secuCode, @RequestParam String yearNum, HttpServletRequest request){
        BasicResponse response = new BasicResponse();
        response.setResCode("1");
        response.setResMsg("success");
        try {
            ArrayList<String> result = RunPython("season.py", new String[]{secuCode, yearNum});
            System.out.println(result.size());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("secuName",result.get(0));
            jsonObject.put("chartData",JSONArray.parseArray( result.get(1)));
            response.setData(jsonObject);
        }catch (Exception e){
            response.setResCode("-1");
            response.setResMsg("error");
            e.printStackTrace();
        }
        return response;
    }

    @RequestMapping(value="/history",method={RequestMethod.GET})
    public @ResponseBody
    BasicResponse history(@RequestParam String secuCode, @RequestParam String monthNum, HttpServletRequest request){
        BasicResponse response = new BasicResponse();
        response.setResCode("1");
        response.setResMsg("success");
        try {
            ArrayList<String> result = RunPython("history.py", new String[]{secuCode, monthNum});
            System.out.println(result.size());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("secuName",result.get(0));
            jsonObject.put("window",result.get(1));
            jsonObject.put("chartData",JSONArray.parseArray(result.get(2)));
            response.setData(jsonObject);
        }catch (Exception e){
            response.setResCode("-1");
            response.setResMsg("error");
            e.printStackTrace();
        }
        return response;
    }

    @RequestMapping(value="/historyReview",method={RequestMethod.GET})
    public @ResponseBody
    BasicResponse historyReview(@RequestParam String secuCode, HttpServletRequest request){
        BasicResponse response = new BasicResponse();
        response.setResCode("1");
        response.setResMsg("success");
        try {
            ArrayList<String> result = RunPython("historyReview.py", new String[]{secuCode});
            System.out.println(result.size());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("secuName",result.get(0));
            jsonObject.put("month_ratio_list",JSONArray.parseArray( result.get(1)));
            jsonObject.put("month_stat",JSONArray.parseArray( result.get(2)));
            jsonObject.put("weekday_stat",JSONArray.parseArray( result.get(3)));
            response.setData(jsonObject);
        }catch (Exception e){
            response.setResCode("-1");
            response.setResMsg("error");
            e.printStackTrace();
        }
        return response;
    }

    @RequestMapping(value="/estimate",method={RequestMethod.GET})
    public @ResponseBody
    BasicResponse estimate(@RequestParam String secuCode, HttpServletRequest request){
        BasicResponse response = new BasicResponse();
        response.setResCode("1");
        response.setResMsg("success");
        try {
            String indCode = securityDAO.getIndustry(secuCode);
            ArrayList<String> result = RunPython("estimate.py", new String[]{secuCode,indCode});
            System.out.println(result.size());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("chartData",JSONArray.parseArray( result.get(0)));
            response.setData(jsonObject);
        }catch (Exception e){
            response.setResCode("-1");
            response.setResMsg("error");
            e.printStackTrace();
        }
        return response;
    }

    @RequestMapping(value="/marketEmotion",method={RequestMethod.GET})
    public @ResponseBody
    BasicResponse marketEmotion(@RequestParam String monthNum, HttpServletRequest request){
        BasicResponse response = new BasicResponse();
        response.setResCode("1");
        response.setResMsg("success");
        try {
            ArrayList<String> result = RunPython("marketEmotion.py", new String[]{monthNum});
            System.out.println(result.size());
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("chartData1",JSONArray.parseArray( result.get(0)));
            jsonObject.put("chartData2",JSONArray.parseArray( result.get(1)));
            response.setData(jsonObject);
        }catch (Exception e){
            response.setResCode("-1");
            response.setResMsg("error");
            e.printStackTrace();
        }
        return response;
    }
}
