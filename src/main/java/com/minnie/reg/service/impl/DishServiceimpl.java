package com.minnie.reg.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minnie.reg.dto.DishDto;
import com.minnie.reg.entity.Dish;
import com.minnie.reg.Filter.mapper.DishMapper;
import com.minnie.reg.entity.DishFlavor;
import com.minnie.reg.service.DishFlavorService;
import com.minnie.reg.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@Service
public class DishServiceimpl extends ServiceImpl<DishMapper, Dish> implements DishService {
    @Autowired
    private DishFlavorService dishFlavorService;

    @Transactional
    @Override
    public void saveWithFlavor(DishDto dishDto) {
        //将菜品数据保存到dish表
        this.save(dishDto);
        //获取dishId
        Long dishId = dishDto.getId();
        //将获取到的dishId赋值给dishFlavor的dishId属性,菜品口味
        List<DishFlavor> flavors = dishDto.getFlavors();
        for (DishFlavor dishFlavor : flavors) {
            dishFlavor.setDishId(dishId);
        }
        //同时将菜品口味数据保存到dish_flavor表
        dishFlavorService.saveBatch(flavors);
    }
}
