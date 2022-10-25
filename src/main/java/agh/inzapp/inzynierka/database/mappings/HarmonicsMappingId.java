package agh.inzapp.inzynierka.database.mappings;

import agh.inzapp.inzynierka.models.enums.UniNames;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter

@Embeddable
public class HarmonicsMappingId implements Serializable {
	private static final long serialVersionUID = 6257191391108915418L;
	@Column(name = "harmonics_id", nullable = false)
	private Long harmonicsId;

	@Column(name = "uni_name", nullable = false)
	@Enumerated(EnumType.STRING)
	private UniNames uniName;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		HarmonicsMappingId entity = (HarmonicsMappingId) o;
		return Objects.equals(this.uniName, entity.uniName) &&
				Objects.equals(this.harmonicsId, entity.harmonicsId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(uniName, harmonicsId);
	}

}