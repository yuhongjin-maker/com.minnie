package com.minnie.reg.mapper;

import com.minnie.reg.entity.Employee;
import org.apache.ibatis.annotations.Mapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;


@Mapper
public interface EmployeeMapper extends BaseMapper<Employee> {
}
