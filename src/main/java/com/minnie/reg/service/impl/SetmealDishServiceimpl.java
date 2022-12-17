package com.minnie.reg.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minnie.reg.Filter.mapper.SetmealDishMapper;
import com.minnie.reg.dto.SetmealDto;
import com.minnie.reg.entity.SetmealDish;
import com.minnie.reg.service.SetmealDishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Slf4j
public class SetmealDishServiceimpl extends ServiceImpl<SetmealDishMapper, SetmealDish> implements SetmealDishService {

}
