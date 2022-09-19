package ch.supsi.webapp.web.repository;

import ch.supsi.webapp.web.model.Item;
import ch.supsi.webapp.web.model.ItemType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ItemRepository extends JpaRepository<Item, Integer>, JpaSpecificationExecutor<Item> {
    List<Item> findTop3ByTypeOrderByDateDesc(ItemType type);
    List<Item> findTop3ByCategoryNameIgnoreCaseAndTypeOrderByDateDesc(String category, ItemType type);
    List<Item> findTop5ByTitleContainingIgnoreCaseOrDescriptionContainingIgnoreCaseOrCategoryNameContainingIgnoreCaseOrderByDateDesc(String title, String description, String category);
}