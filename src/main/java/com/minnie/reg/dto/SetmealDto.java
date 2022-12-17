package com.minnie.reg.dto;

import com.minnie.reg.entity.Setmeal;
import com.minnie.reg.entity.SetmealDish;
import lombok.Data;

import java.util.List;

@Data
public class SetmealDto extends Setmeal {

    private List<SetmealDish> setmealDishes;

    private String categoryName;
}
