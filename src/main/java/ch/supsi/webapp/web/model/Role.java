package ch.supsi.webapp.web.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;

@Getter @Setter @AllArgsConstructor @NoArgsConstructor
@Entity
public class Role {
    @Id
    private String name;
}
