package com.humber.SaffronExpress.controllers;

import com.humber.SaffronExpress.models.Dish;
import com.humber.SaffronExpress.services.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/restaurant")
public class DishMVCController {

//    @Autowired
    private final DishService dishService;

    @Autowired
    public DishMVCController(DishService dishService){
        this.dishService = dishService;
    }


    //getting values from app properties and storing it to variable name
    @Value("${restaurant.name}")
    private String name;

    @Value("${page.size}")
    private int pageSize;


    //get mapping - returns home page (/restaurant/home)
    @GetMapping("/home")
    public String home(Model model){
        model.addAttribute("restaurantName",name);
        return "home";
    }

    //get mapping - returns home page (/restaurant/menu)
    // url - /restaurant/menu/1
    @GetMapping("/menu/{pageNo}")
    public String menu(Model model,
                       @RequestParam(required = false) String msg,
                       @RequestParam(required = false) String searchedCategory,
                       @RequestParam(required = false) Double searchedPrice,
                       @PathVariable int pageNo,
                       @RequestParam(required = false) String sortField,
                       @RequestParam(required = false) String sortDirection
    ){

        //search
        if(searchedCategory != null &&  searchedPrice!=null){
            model.addAttribute("dishes", dishService.getFilteredDishes(searchedCategory,searchedPrice));
            model.addAttribute("msg", "Dish filtered successfully!");
            return "menu";
        }

        //null (by default values for sort parameters)
        if(sortField == null) sortField="id";
        if(sortDirection == null) sortDirection="asc";

        //sorted by page no. and page size
        Page<Dish> page = dishService.getPaginatedDishes(pageNo, pageSize, sortField, sortDirection);
        //get all dishes
        model.addAttribute("dishes", page.getContent());
        //pagination infos
        model.addAttribute("totalPages", page.getTotalPages());
        model.addAttribute("currentPage", pageNo);
        model.addAttribute("totalItems", page.getTotalElements());

        //sorting info
        model.addAttribute("sortField", sortField);
        model.addAttribute("sortDir", sortDirection);
        model.addAttribute("reverseSortDir", sortDirection.equals("asc") ? "desc" : "asc");

        //success-failure msg
        model.addAttribute("msg",msg);
        return "menu";
    }

////    add a dish
//// url - /restaurant/add-dish
//    @GetMapping("/add-dish")
//    public String addDish(Model model){
//        model.addAttribute("item", new Dish());
//        return "add-dish";
//    }
//
//    //    save a dish
//// url - /restaurant/post-dish
//    @PostMapping("/post-dish")
//    public String postDish(@ModelAttribute Dish dish, Model model){
//        int saveCode = dishService.saveDish(dish);
//        model.addAttribute("dishes", dishService.getAllDishes());
//        if(saveCode==0) {
//            model.addAttribute("msg", "Dish too cheap. Failed to add!");
//            return "menu";
//        }
//        model.addAttribute("msg", "Dish added successfully!");
//        return "menu";
//    }
//
//
//    //delete api (/restaurant/delete/id=1)
//    @GetMapping("/delete/{id}")
//    public String removeDish(@PathVariable int id){
//        //call the delete service method
//        int deleteStatusCode = dishService.deleteDish(id);
//
//        //successful delete
//        if(deleteStatusCode == 1){
//            return "redirect:/restaurant/menu/1?msg=Dish deleted successfully!";
//        }
//
//        //unsuccessful delete
//        return "redirect:/restaurant/menu/1?msg=Dish was not deleted!";
//    }
//
//    //update api (/restaurant/update/id=1)
//    @GetMapping("/update/{id}")
//    public String updateDish(@PathVariable int id, Model model){
//        Optional<Dish> dishToBeUpdated = dishService.getDishById(id);
//        model.addAttribute("item", dishToBeUpdated.orElse(null));
//        return "add-dish";
//    }
//
////    updates the dish
// //   /restaurant/update
//    @PostMapping("/update")
//    public String updateDish(@ModelAttribute Dish dish){
//        //update dish
//        dishService.updateDish(dish);
//        return "redirect:/restaurant/menu/1?msg=Dish was updated successfully!";
//    }
}
