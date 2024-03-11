package com.humber.SaffronExpress.services;

import com.humber.SaffronExpress.models.Dish;
import com.humber.SaffronExpress.repositories.DishRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DishService {

    private final DishRepository dishRepository;

//    Constructor injection (instead of field injection for immutability)
    @Autowired
    public DishService(DishRepository dishRepository) {
        this.dishRepository = dishRepository;
    }

    public int saveDish(Dish dish){

        if(dish.getPrice()>8){
            dishRepository.save(dish);
            return 1;
        }
        return 0;
    }

    public List<Dish> getAllDishes(){
        return dishRepository.findAll();
    }

    public List<Dish> getFilteredDishes(String searchedCategory, Double searchedPrice ){
        return dishRepository.findByCategoryAndPrice(searchedCategory,searchedPrice);
    }

    public Page<Dish> getPaginatedDishes(int pageNo, int pageSize,String sortField, String sortDirection){

        Sort sort = sortDirection.equalsIgnoreCase(Sort.Direction.ASC.name())?Sort.by(sortField).ascending() : Sort.by(sortField).descending();

        Pageable pageable = PageRequest.of(pageNo-1,pageSize,sort);
        return dishRepository.findAll(pageable);
    }

    //delete dish method
    public int deleteDish(int id){
        if(dishRepository.existsById(id)){
            dishRepository.deleteById(id);
            return 1;
        }
        return 0;
    }

    //get dish by id
    public Optional<Dish> getDishById(int id){
        Optional<Dish> dishToBeUpdated = dishRepository.findById(id);
        return dishToBeUpdated;
    }

    //update dish
    public void updateDish(Dish dish){
        dishRepository.save(dish);
    }
}
