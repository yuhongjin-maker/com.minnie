package com.minnie.reg.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.minnie.reg.common.R;
import com.minnie.reg.entity.Employee;
import com.minnie.reg.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@Slf4j
@RestController
@RequestMapping("/employee")
public class EmployeeController {
    @Autowired
    private EmployeeService employeeService;


    //前端发送的请求是POST
    @PostMapping("/login")
    public R<Employee> login(HttpServletRequest request, @RequestBody Employee employee){
        /**
         1. 将页面提交的密码password进行md5加密处理
         2. 根据页面提交的用户名username查询数据库
         3. 如果没有查询到则返回登录失败结果
         4. 密码比对，如果不一致则返回登录失败结果
         5. 查看员工状态，如果为已禁用状态，则返回员工已禁用状态
         6. 登录成功，将员工id存入Session并返回登录成功结果
         */

        //1

        String password = employee.getPassword();
        password= DigestUtils.md5DigestAsHex(password.getBytes());

        //2
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(Employee::getUsername, employee.getUsername());
        Employee emp = employeeService.getOne(queryWrapper);

        //3
        if(emp == null){
            return R.error("login failed");
        }

        //4
        if(!emp.getPassword().equals(password)){
            return R.error("login failed");
        }

        //5
        if(emp.getStatus() == 0){
            return R.error("account disabled");
        }

        //6.
        request.getSession().setAttribute("employee",emp.getId());
        return R.success(emp);
    }


    @PostMapping("/logout")
    public R<String> logout(HttpServletRequest request){
        //清理Session中保存的当前员工的id
        request.getSession().removeAttribute("employee");
        return R.success("退出成功");

    }

}
