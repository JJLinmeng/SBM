package com.mumu.studentbankmanagement.Interceptor;

import com.alibaba.fastjson.JSONObject;
import com.mumu.studentbankmanagement.Util.TokenUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * @author hanmingzhi
 * @date 2022/8/14 18:46
 **/

@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(request.getMethod().equals("OPTIONS")){
            response.setStatus(HttpServletResponse.SC_OK);
            return true;
        }
        String token = request.getHeader("token");
        String requestUrl = request.getServletPath();
        if (token!=null){
            boolean result= TokenUtil.checkToken(token); // 验证token
            if (result){
                System.out.println("通过拦截器："+requestUrl);  // 可以输出接口是否通过拦截器
                return true;
            }
        }
        response.setCharacterEncoding("utf-8");
        response.setContentType("application/json; charset=utf-8");
        try {
            JSONObject json=new JSONObject();
            json.put("msg","token认证失败");
            json.put("code","500");
            response.getWriter().append(json.toString());
            System.out.println("认证失败，未通过拦截器："+requestUrl);
        } catch (Exception e) {
            return false;
        }
        /**
         * 还可以在此处检验其他操作
         */
        return false;
    }
}
