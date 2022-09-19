package ch.supsi.webapp.web.controller;

import ch.supsi.webapp.web.model.Category;
import ch.supsi.webapp.web.model.Item;
import ch.supsi.webapp.web.model.User;
import ch.supsi.webapp.web.service.ItemService;
import ch.supsi.webapp.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

@Controller
public class WebApplicationController {
    private final ItemService itemService;
    private final UserService userService;

    @Autowired
    public WebApplicationController(ItemService itemService, UserService userService) {
        this.itemService = itemService;
        this.userService = userService;
    }

    public void deleteAll() {
        itemService.deleteAll();
        userService.deleteAll();
    }

    public User saveUser(User user) {
        return userService.saveUser(user);
    }

    public Category saveCategory(String category) {
        return itemService.saveCategory(category);
    }

    public Item saveItem(Item item) {
        return itemService.save(item);
    }
}
