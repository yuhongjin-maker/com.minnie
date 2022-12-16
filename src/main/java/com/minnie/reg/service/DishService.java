package com.minnie.reg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minnie.reg.dto.DishDto;
import com.minnie.reg.entity.Dish;


public interface DishService extends IService<Dish> {
    void saveWithFlavor(DishDto dishDto);
}
