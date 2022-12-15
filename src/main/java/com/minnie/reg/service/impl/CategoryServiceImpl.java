package com.minnie.reg.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minnie.reg.common.CustomException;
import com.minnie.reg.entity.Category;
import com.minnie.reg.entity.Dish;
import com.minnie.reg.entity.Setmeal;
import com.minnie.reg.mapper.CategoryMapper;
import com.minnie.reg.service.CategoryService;
import com.minnie.reg.service.DishService;
import com.minnie.reg.service.SetmealService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CategoryServiceImpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {

    @Autowired
    private DishService dishService;

    @Autowired
    private SetmealService setmealService;

    /*
    根据id删除分类，删除之前进行判断
     */
    @Override
    public void remove(Long ids) {
        LambdaQueryWrapper<Dish> dishLambdaQueryWrapper = new LambdaQueryWrapper<>();
        //添加查询条件，根据分类id进行查询
        dishLambdaQueryWrapper.eq(Dish::getCategoryId,ids);
        int count = dishService.count(dishLambdaQueryWrapper);

        //查询当前分类是否关联了菜品，如果已经关联，抛出一个业务异常
        if(count > 0){
            //已经关联菜品抛出异常
            throw new CustomException("当前分类下关联了菜品，不能删除");
        }

        //查询当前分类是否关联了套餐，如果已经关联，抛出一个业务异常
        LambdaQueryWrapper<Setmeal> setmealLambdaQueryWrapper = new LambdaQueryWrapper<>();

        setmealLambdaQueryWrapper.eq(Setmeal::getCategoryId,ids);
        int count1=setmealService.count(setmealLambdaQueryWrapper);

        if(count1 >0){
            //已经关联套餐抛出异常
            throw new CustomException("当前分类下关联了套餐，不能删除");
        }
        //正常删除分类

        super.removeById(ids);
    }
}