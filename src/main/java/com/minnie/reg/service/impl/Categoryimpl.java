package com.minnie.reg.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.minnie.reg.entity.Category;
import com.minnie.reg.mapper.CategoryMapper;
import com.minnie.reg.service.CategoryService;
import org.springframework.stereotype.Service;

@Service
public class Categoryimpl extends ServiceImpl<CategoryMapper, Category> implements CategoryService {


}

