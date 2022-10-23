package agh.inzapp.inzynierka.database.mappings;

import agh.inzapp.inzynierka.enums.UniNames;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter

@Embeddable
public class ThdMappingId implements Serializable {
	private static final long serialVersionUID = 3914794163513803575L;
	@Column(name = "thd_id", nullable = false)
	private Long thdId;

	@Column(name = "uni_name", nullable = false)
	@Enumerated(EnumType.STRING)
	private UniNames uniName;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		ThdMappingId entity = (ThdMappingId) o;
		return Objects.equals(this.uniName, entity.uniName) &&
				Objects.equals(this.thdId, entity.thdId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(uniName, thdId);
	}

}