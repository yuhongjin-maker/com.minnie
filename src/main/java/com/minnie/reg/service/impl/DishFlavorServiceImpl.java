package com.minnie.reg.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minnie.reg.Filter.mapper.DishFlavorMapper;
import com.minnie.reg.entity.DishFlavor;
import com.minnie.reg.service.DishFlavorService;
import org.springframework.stereotype.Service;

@Service
public class DishFlavorServiceImpl extends ServiceImpl<DishFlavorMapper, DishFlavor> implements DishFlavorService {
}
