package com.minnie.reg.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minnie.reg.common.R;
import com.minnie.reg.entity.Employee;
import com.minnie.reg.service.EmployeeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.util.DigestUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.time.LocalDateTime;

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
        return R.success("exit successfully");

    }

    //新增员工
    @PostMapping
    public R<String> save(HttpServletRequest request,@RequestBody Employee employee){
        log.info("新增员工，员工信息：{}",employee.toString());

        //设置初始密码,需要用m5加密处理
        employee.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes()));

        //employee.setCreateTime(LocalDateTime.now());
        //employee.setUpdateTime(LocalDateTime.now());

        //获得当前登录用户id
        //Long empId =(Long) request.getSession().getAttribute("employee");

        //employee.setCreateUser(empId);
        //employee.setUpdateUser(empId);

        employeeService.save(employee);

        return R.success("Successfully added employees");

    }

    /*
    员工信息分页查询
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize,String name){
        log.info("page:{},page_size:{},name:{}",page,pageSize,name);
        //构造分页构造器
        Page pageinfo = new Page(page, pageSize);

        //构造条件构造器
        LambdaQueryWrapper<Employee> queryWrapper = new LambdaQueryWrapper();
        //添加过滤条件
        if(name !=null){
            queryWrapper.like(Employee::getName,name);
        }
        //添加排除条件
        queryWrapper.orderByDesc(Employee::getUpdateTime);

        //执行查询
        employeeService.page(pageinfo,queryWrapper);
        return R.success(pageinfo);

    }

    //启用，禁用员工账号
    //根据id修改员工信息
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Employee employee){
        //Long empId = (Long) request.getSession().getAttribute("employee");
        //employee.setUpdateTime(LocalDateTime.now());
        //employee.setUpdateUser(empId);


        employeeService.updateById(employee);

        return R.success("Employee information modified successfully");
    }

    //根据id查询员工信息
    @GetMapping("/{id}")
    public R<Employee> getById(@ PathVariable Long id){
        log.info("根据id查找");
        Employee employee  = employeeService.getById(id);
        if(employee !=null){
            return R.success(employee);

        }else{
            return R.error("没有查询到");
        }

    }



}
