package com.foodsystem.food_order_app;

import com.foodsystem.food_order_app.model.Food;
import com.foodsystem.food_order_app.repository.FoodRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class FoodOrderAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(FoodOrderAppApplication.class, args);
	}

	@Bean
	public CommandLineRunner dataLoader(FoodRepository repo) {
		return args -> {
			// اگر دیتابیس خالی بود، این غذاها را اضافه کن
			if (repo.count() == 0) {
				repo.save(new Food(null, "چلو کباب کوبیده", "۲ سیخ کباب ۱۰۰ گرمی به همراه برنج ایرانی", 185000.0, true));
				repo.save(new Food(null, "جوجه کباب زعفرانی", "جوجه کباب بدون استخوان با دورچین", 155000.0, true));
				repo.save(new Food(null, "زرشک پلو با مرغ", "ران مرغ سرخ شده به همراه زرشک پلو", 140000.0, true));
				System.out.println(">>>>> داده‌های اولیه با موفقیت به دیتابیس اضافه شدند! <<<<<");
			} else {
				System.out.println(">>>>> دیتابیس قبلاً پر شده است. <<<<<");
			}
		};
	}
}