package ch.supsi.webapp.web.controller;

import ch.supsi.webapp.web.model.Item;
import ch.supsi.webapp.web.model.ItemDTO;
import ch.supsi.webapp.web.model.ItemType;
import ch.supsi.webapp.web.service.ItemService;
import ch.supsi.webapp.web.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Controller
public class RESTController {
    private final ItemService itemService;
    private final UserService userService;

    @Autowired
    public RESTController(ItemService itemService, UserService userService) {
        this.itemService = itemService;
        this.userService = userService;
    }

    @GetMapping("/")
    public String index(Model model){
        model.addAttribute("supplyItems", itemService.findAll(ItemType.SUPPLY));
        model.addAttribute("demandItems", itemService.findAll(ItemType.DEMAND));
        return "index";
    }

    @GetMapping("/item/{id}")
    public String detail(@PathVariable int id, Model model){
        model.addAttribute("item", itemService.get(id));
        return "detail";
    }

    @GetMapping("/item/new")
    public String newPost(Model model){
        model.addAttribute("item", new Item());
        model.addAttribute("categories", itemService.getCategories());
        model.addAttribute("authors", userService.getAuthors());
        return "edit";
    }

    @PostMapping("/item/new")
    public String post(Item item, @RequestParam("file") MultipartFile file) throws IOException {
        User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        var author = userService.findUserByUsername( user.getUsername() );
        item.setAuthor( author );
        item.setDate( new Date() );
        if( !file.isEmpty() )
            item.setImage( file.getBytes() );
        itemService.save( item );
        return "redirect:/";
    }

    @GetMapping(value = "/item/{id}/image", produces = MediaType.IMAGE_JPEG_VALUE)
    @ResponseBody
    public byte[] image(@PathVariable int id) {
        return itemService.get( id ).getImage();
    }

    @GetMapping("/item/{id}/edit")
    public String edit(@PathVariable int id, Model model){
        model.addAttribute("item", itemService.get(id));
        model.addAttribute("categories", itemService.getCategories());
        model.addAttribute("authors", userService.getAuthors());
        return "edit";
    }

    @PostMapping("/item/{id}/edit")
    public String put(@PathVariable int id, Item newItem, @RequestParam("file") MultipartFile file) throws IOException {
        Item item = itemService.get(id);
        item.setType( newItem.getType() );
        item.setTitle( newItem.getTitle() );
        item.setCategory( newItem.getCategory() );
        item.setDescription( newItem.getDescription() );
        if( !file.isEmpty() )
            item.setImage( file.getBytes() );
        itemService.put( item );
        return "redirect:/item/{id}";
    }

    @GetMapping(value = "/item/{id}/delete")
    public String delete(@PathVariable int id){
        itemService.delete(id);
        return "redirect:/";
    }

    @GetMapping(value = "/filter/{category}")
    public String filter(@PathVariable String category, Model model){
        model.addAttribute("supplyItems", itemService.searchByCategoryAndType(category, ItemType.SUPPLY));
        model.addAttribute("demandItems", itemService.searchByCategoryAndType(category, ItemType.DEMAND));
        return "index";
    }

    @GetMapping(value = "/login")
    public String login(){
        return "login";
    }

    @GetMapping(value = "/register")
    public String register(){
        return "register";
    }

    @PostMapping(value = "/register")
    public String register(ch.supsi.webapp.web.model.User user){
        userService.saveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/item/search")
    @ResponseBody
    public List<ItemDTO> search(@RequestParam("q") String search){
        return itemService
                .list( search )
                .stream()
                .map( ItemDTO::mapToDTO )
                .collect( Collectors.toList() );
    }
}
