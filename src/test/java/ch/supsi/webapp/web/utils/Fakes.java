package ch.supsi.webapp.web.utils;

import ch.supsi.webapp.web.model.*;

public abstract class Fakes {
    public static Item item(int id) {
        User admin = new User("admin", "admin", new Role("ROLE_ADMIN"), "Amministratore", "Unico");
        return Item.builder().id(id).author(admin).build();
    }
}
