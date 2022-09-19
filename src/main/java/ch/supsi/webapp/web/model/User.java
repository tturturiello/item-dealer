package ch.supsi.webapp.web.model;

import lombok.*;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Builder
@Entity
public class User {
    @Id
    @Builder.Default
    private String username = "anonymous";
    @Builder.Default
    private String password = "";
    @ManyToOne(cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    @Builder.Default
    private Role role = new Role();
    @Builder.Default
    private String firstname = "";
    @Builder.Default
    private String lastname = "";

    public User(String username) {
        this.username = username;
    }
}
