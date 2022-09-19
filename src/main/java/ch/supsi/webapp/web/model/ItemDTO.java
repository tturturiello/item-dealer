package ch.supsi.webapp.web.model;

import lombok.Builder;

import java.util.Date;

@Builder
public class ItemDTO {
    private int id;
    private String title;
    private String description;
    private Date date;
    private String category;
    private String author;

    public static ItemDTO mapToDTO(Item item){
        return ItemDTO.builder()
                .id( item.getId() )
                .title( item.getTitle() )
                .description( item.getDescription() )
                .date( item.getDate() )
                .author( item.getAuthor().getUsername() )
                .category( item.getCategory().getName() )
                .build();
    }
}
