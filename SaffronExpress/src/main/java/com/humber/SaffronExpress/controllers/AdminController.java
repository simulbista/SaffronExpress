package com.humber.SaffronExpress.controllers;

import com.humber.SaffronExpress.models.Dish;
import com.humber.SaffronExpress.services.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@Controller
@RequestMapping("/restaurant/admin")
public class AdminController {

    private final DishService dishService;

    @Autowired
    public AdminController(DishService dishService){
        this.dishService = dishService;
    }

    //    add a dish
// url - /restaurant/add-dish
    @GetMapping("/add-dish")
    public String addDish(Model model){
        model.addAttribute("item", new Dish());
        return "add-dish";
    }

    //    save a dish
// url - /restaurant/post-dish
    @PostMapping("/post-dish")
    public String postDish(@ModelAttribute Dish dish, Model model){
        int saveCode = dishService.saveDish(dish);
        model.addAttribute("dishes", dishService.getAllDishes());
        if(saveCode==0) {
            model.addAttribute("msg", "Dish too cheap. Failed to add!");
            return "menu";
        }
        model.addAttribute("msg", "Dish added successfully!");
        return "menu";
    }


    //delete api (/restaurant/delete/id=1)
    @GetMapping("/delete/{id}")
    public String removeDish(@PathVariable int id){
        //call the delete service method
        int deleteStatusCode = dishService.deleteDish(id);

        //successful delete
        if(deleteStatusCode == 1){
            return "redirect:/restaurant/menu/1?msg=Dish deleted successfully!";
        }

        //unsuccessful delete
        return "redirect:/restaurant/menu/1?msg=Dish was not deleted!";
    }

    //update api (/restaurant/update/id=1)
    @GetMapping("/update/{id}")
    public String updateDish(@PathVariable int id, Model model){
        Optional<Dish> dishToBeUpdated = dishService.getDishById(id);
        model.addAttribute("item", dishToBeUpdated.orElse(null));
        return "add-dish";
    }

    //    updates the dish
    //   /restaurant/update
    @PostMapping("/update")
    public String updateDish(@ModelAttribute Dish dish){
        //update dish
        dishService.updateDish(dish);
        return "redirect:/restaurant/menu/1?msg=Dish was updated successfully!";
    }
}
