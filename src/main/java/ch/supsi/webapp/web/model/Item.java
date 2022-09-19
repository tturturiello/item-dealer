package ch.supsi.webapp.web.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.hibernate.Hibernate;

import javax.persistence.*;
import java.util.Date;
import java.util.Objects;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
@Entity
public class Item {

	@Id
	@GeneratedValue
	private int id;

	@Builder.Default
	private String title = "";

	@Builder.Default
	@Column(columnDefinition = "TEXT")
	private String description = "";

	@Builder.Default
	private Date date = new Date();

	@Builder.Default
	@ManyToOne
	private Category category = new Category();

	@Builder.Default
	@ManyToOne
	private User author = new User();

	@Builder.Default
	private ItemType type = ItemType.DEMAND;

	@Lob
	@JsonIgnore
	@Builder.Default
	private byte[] image = new byte[0];

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		Item item = (Item) o;
		return Objects.equals(id, item.id);
	}

	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 37)
				.append( id )
				.append( title )
				.append( description )
				.append( date )
				.append( category )
				.append( author )
				.append( type )
				.append( image )
				.toHashCode();
	}
}
