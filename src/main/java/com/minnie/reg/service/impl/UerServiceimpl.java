package com.minnie.reg.service.impl;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minnie.reg.Filter.mapper.SetmealMapper;
import com.minnie.reg.Filter.mapper.UserMapper;
import com.minnie.reg.entity.Setmeal;
import com.minnie.reg.entity.User;
import com.minnie.reg.service.SetmealService;
import com.minnie.reg.service.UserService;
import org.springframework.stereotype.Service;

@Service
public class UerServiceimpl extends ServiceImpl<UserMapper, User> implements UserService {

}
