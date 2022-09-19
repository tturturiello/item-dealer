package ch.supsi.webapp.web;

import ch.supsi.webapp.web.controller.WebApplicationController;
import ch.supsi.webapp.web.model.*;
import org.aspectj.util.FileUtil;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.webjars.NotFoundException;

import java.util.Objects;

@SpringBootApplication
public class WebApplication {

	public static void main(String[] args) {
		SpringApplication.run(WebApplication.class, args);
	}

	@Bean
	public CommandLineRunner demo(WebApplicationController webApplicationController) {
		return (args -> {
			webApplicationController.deleteAll();

			User admin = User.builder()
					.username("admin")
					.password("admin")
					.role(new Role("ROLE_ADMIN"))
					.build();

			User user = User.builder()
					.username("t1murt")
					.password("1234")
					.firstname("Timothy")
					.lastname("Turturiello")
					.build();

			webApplicationController.saveUser(admin);
			webApplicationController.saveUser(user);
			Category booksCategory = webApplicationController.saveCategory("Books");
			Category foodCategory = webApplicationController.saveCategory("Food");
			Category techCategory = webApplicationController.saveCategory("Tech");

			Item item0 = Item.builder()
					.title("Roasting Dish, 50 Year Anniversary Edition, 5 Litres")
					.type(ItemType.SUPPLY)
					.author(admin)
					.category(foodCategory)
					.description("The item is new.")
					.image(FileUtil.readAsByteArray(Objects.requireNonNull(this.getClass()
							.getResourceAsStream("/images/roasting.jpg"))))
					.build();

			Item item1 = Item.builder()
					.author(user)
					.category(booksCategory)
					.type(ItemType.DEMAND)
					.title("I'm looking for this book, help!")
					.description("Test Driven Development: By Example by Beck (Paperback)")
					.image(FileUtil.readAsByteArray(Objects.requireNonNull(this.getClass()
							.getResourceAsStream("/images/book.jpg"))))
					.build();

			Item item2 = Item.builder()
					.author(admin)
					.category(techCategory)
					.type(ItemType.SUPPLY)
					.title("LENOVO THINKPAD T480 14\" BUSINESS LIGHT GAMING i5-8350U 16GB RAM 512GB SSD WIN11")
					.description("The Laptop is refurbished, tested, and in good working order. " +
							"You'll receive the item in the exact same condition as the one in the picture. " +
							"Please carefully see all pictures prior to purchase. " +
							"The Laptop Comes with the charger")
					.image(FileUtil.readAsByteArray(Objects.requireNonNull(this.getClass()
							.getResourceAsStream("/images/laptop.jpg"))))
					.build();

			webApplicationController.saveItem(item0);
			webApplicationController.saveItem(item1);
			webApplicationController.saveItem(item2);
		});
	}

}
