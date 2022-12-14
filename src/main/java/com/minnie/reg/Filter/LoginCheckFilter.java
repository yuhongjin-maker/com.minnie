package com.minnie.reg.Filter;

import com.alibaba.fastjson.JSON;
import com.minnie.reg.common.BaseContext;
import com.minnie.reg.common.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.AntPathMatcher;

import javax.servlet.Filter;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


//检查用户是否已经完成登录
@WebFilter(filterName = "loginCheckFilter",urlPatterns = "/*")
@Slf4j
public class LoginCheckFilter implements Filter {
    //路径匹配器，支持通配符
    public static final AntPathMatcher PATH_MATCHER = new AntPathMatcher();


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        /*
        1. 获取本次请求的URI
        2. 获取本次请求是否需要处理
        3. 如果不需要处理，则直接放行
        4. 判断登录状态，如果已登录，则直接放行
        5. 如果未登录则返回未登录结果,通过输出流方式向客户端页面响应数据

         */
        //1
        String requestURI = request.getRequestURI(); // /backend/index.html
        log.info("拦截到请求:{}",requestURI);

        String[] urls = new String[]{
                "/employee/login",
                "/employee/logout",
                "/backend/**",
                "/front/**"
        };

        //2
        boolean check = check(urls, requestURI);

        //3
        log.info("本次请求不需要处理:{}",requestURI);
        if (check) {
            filterChain.doFilter(request, response);
            return;

        }

        //4


        if (request.getSession().getAttribute("employee") != null) {
            log.info("用户已登录，用户id:{}",request.getSession().getAttribute("employee"));

            Long empId = (Long) request.getSession().getAttribute("employee");
            BaseContext.setCurrentId(empId);

            filterChain.doFilter(request, response);
            return;
        }

        log.info("用户未登录");

        //5
        response.getWriter().write(JSON.toJSONString(R.error("NOTLOGIN")));

    }

    //2
    //路径匹配，检查本次请求是否需要放行
    public boolean check(String[] urls, String requestURI){
        for(String url: urls) {
            boolean match = PATH_MATCHER.match(url, requestURI);
            if (match) {
                return true;
            }
        }
        return false;
    }

}
