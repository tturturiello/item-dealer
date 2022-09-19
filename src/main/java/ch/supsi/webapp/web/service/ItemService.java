package ch.supsi.webapp.web.service;


import ch.supsi.webapp.web.model.Category;
import ch.supsi.webapp.web.model.Item;
import ch.supsi.webapp.web.model.ItemType;
import ch.supsi.webapp.web.repository.CategoryRepository;
import ch.supsi.webapp.web.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ItemService {
    private final ItemRepository itemRepository;
    private final CategoryRepository categoryRepository;

    @Autowired
    public ItemService(ItemRepository itemRepository, CategoryRepository categoryRepository) {
        this.itemRepository = itemRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Item> findAll(){
        return itemRepository.findAll();
    }

    public List<Item> findAll(ItemType type) {
        return itemRepository.findTop3ByTypeOrderByDateDesc(type);
    }

    public Item get(int id) {
        return itemRepository.findById( id ).orElseGet( Item::new );
    }

    public Item save(Item p){
        return itemRepository.save(p);
    }

    public Item put(Item p) {
        return itemRepository.save(p);
    }

    public boolean exists(int id) {
        return itemRepository.existsById(id);
    }

    public boolean delete(int id){
        itemRepository.deleteById(id);
        return !itemRepository.existsById(id);
    }

    public List<Category> getCategories(){
        return  categoryRepository.findAll();
    }

    public List<Item> searchByCategoryAndType(String category, ItemType type) {
        return itemRepository.findTop3ByCategoryNameIgnoreCaseAndTypeOrderByDateDesc(category, type);
    }

    public List<Item> list(String search) {
        return itemRepository.findTop5ByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrCategoryNameContainingIgnoreCaseOrderByDateDesc(search, search, search);
    }

    public Category saveCategory(String category) {
        return categoryRepository
                .findById(category)
                .orElseGet(() -> categoryRepository.save(new Category(category)));
    }

    public void deleteAll() {
        if (!itemRepository.findAll().isEmpty())
            itemRepository.deleteAll();
        if (!categoryRepository.findAll().isEmpty())
            categoryRepository.deleteAll();
    }
}
