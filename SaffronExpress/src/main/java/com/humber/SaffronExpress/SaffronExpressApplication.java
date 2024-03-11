package com.humber.SaffronExpress;

import com.humber.SaffronExpress.models.Dish;
import com.humber.SaffronExpress.services.DishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SaffronExpressApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(SaffronExpressApplication.class, args);
	}

	private final DishService dishService;

	@Autowired
    SaffronExpressApplication(DishService dishService){
		this.dishService = dishService;
	}

	@Override
	public void run(String... args) throws Exception {

		dishService.saveDish(new Dish(1,"Pizza","Vegan",12));
		dishService.saveDish(new Dish(2,"Burger","Non-veg",12));
		dishService.saveDish(new Dish(3,"Shwarma","Veg",13));
		dishService.saveDish(new Dish(4,"Ham and Cheese","Veg",12));
		dishService.saveDish(new Dish(5,"Biryani","Non-veg",9));
		dishService.saveDish(new Dish(6,"Fries","Vegan",12));
		dishService.saveDish(new Dish(7,"Salad","Non-veg",13));
		dishService.saveDish(new Dish(8,"Fried Rice","Non-veg",12));
		dishService.saveDish(new Dish(9,"Momo","Vegan",17));
		dishService.saveDish(new Dish(10,"Hot dog","Non-veg",9));
		dishService.saveDish(new Dish(11,"Poutine","Non-veg",12));
		dishService.saveDish(new Dish(12,"Vegies","Veg",17));
	}
}
