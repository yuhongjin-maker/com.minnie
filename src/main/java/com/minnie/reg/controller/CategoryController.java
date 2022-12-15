package com.minnie.reg.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.minnie.reg.common.R;
import com.minnie.reg.entity.Category;
import com.minnie.reg.entity.Employee;
import com.minnie.reg.service.CategoryService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

//分类管理
@Slf4j
@RestController
@RequestMapping("/category")

public class CategoryController {
    @Autowired
    private CategoryService categoryService;
    //新增分类

    @PostMapping
    public R<String> save(@RequestBody Category category){
        log.info("新增菜品分类：{}",category.toString());

        categoryService.save(category);

        return R.success("Successfully added food menu");
    }

    /*
    员工信息分页查询
     */
    @GetMapping("/page")
    public R<Page> page(int page, int pageSize){
        log.info("page:{},page_size:{}",page,pageSize);
        //构造分页构造器
        Page<Category> pageinfo = new Page<>(page,pageSize);

        //构造条件构造器
        LambdaQueryWrapper<Category> queryWrapper = new LambdaQueryWrapper();

        //添加排序条件
        queryWrapper.orderByAsc(Category::getSort);

        //执行查询
        categoryService.page(pageinfo,queryWrapper);

        return R.success(pageinfo);

    }

    //根据id删除分类
    @DeleteMapping
    public R<String> delet(Long ids ){
        log.info("删除分类，id为:{}",ids);
        categoryService.remove(ids);
        return R.success("分类信息删除成功");
    }

    //更新菜品信息
    @PutMapping
    public R<String> update(HttpServletRequest request, @RequestBody Category category){
        //Long empId = (Long) request.getSession().getAttribute("employee");
        //employee.setUpdateTime(LocalDateTime.now());
        //employee.setUpdateUser(empId)
        log.info("修改分类信息{}",category);

        categoryService.updateById(category);

        return R.success("修改分类信息成功");

    }


    @GetMapping("/list")
    public R <List<Category>> list(Category category){

        //条件构造器
        LambdaQueryWrapper<Category> queryWrapper  = new LambdaQueryWrapper<>();

        //添加条件
        queryWrapper.eq(category.getType()!= null, Category::getType, category.getType());

        //添加排序条件
        queryWrapper.orderByAsc(Category::getSort).orderByDesc(Category::getUpdateTime);

        List<Category> list = categoryService.list(queryWrapper);

        return R.success(list);

    }
}
