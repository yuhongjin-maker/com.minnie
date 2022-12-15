package com.minnie.reg.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minnie.reg.entity.Dish;
import com.minnie.reg.mapper.DishMapper;
import com.minnie.reg.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class DishServiceimpl extends ServiceImpl<DishMapper, Dish> implements DishService {
}
