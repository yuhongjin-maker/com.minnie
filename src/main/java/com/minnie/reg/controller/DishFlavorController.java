package com.minnie.reg.controller;


import com.minnie.reg.service.DishFlavorService;
import com.minnie.reg.service.DishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

//菜品管理
@Slf4j
@RestController
@RequestMapping("/dish")
public class DishFlavorController {
    @Autowired
    private DishService dishService;

    @Autowired
    private DishFlavorService dishFlavorService;


}
