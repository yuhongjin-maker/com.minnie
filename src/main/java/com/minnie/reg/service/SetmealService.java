package com.minnie.reg.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.minnie.reg.dto.SetmealDto;
import com.minnie.reg.entity.Setmeal;

import java.util.List;

public interface SetmealService extends IService<Setmeal> {
    //新增套餐同时需要保存套餐和菜品的关联关系
    public void saveWithDish(SetmealDto setmealDto);

    void removeWithDish(List<Long> ids);
}
