package com.minnie.reg.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minnie.reg.entity.Employee;
import com.minnie.reg.mapper.EmployeeMapper;
import com.minnie.reg.service.EmployeeService;
import org.springframework.stereotype.Service;

@Service

public class Employeeimpl extends ServiceImpl<EmployeeMapper, Employee> implements EmployeeService {
}
