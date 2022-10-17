package agh.inzapp.inzynierka.database;

import agh.inzapp.inzynierka.enums.UniNames;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.Hibernate;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.io.Serial;
import java.io.Serializable;
import java.util.Objects;

@Setter
@Getter

@Embeddable
public class RecordsMappingId implements Serializable {
	@Serial
	private static final long serialVersionUID = 1128836653557708349L;
	@Column(name = "records_id", nullable = false)
	private Long recordsId;

	@Column(name = "uni_name", nullable = false)
	@Enumerated(EnumType.STRING)
	private UniNames uniName;

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
		RecordsMappingId entity = (RecordsMappingId) o;
		return Objects.equals(this.uniName, entity.uniName) &&
				Objects.equals(this.recordsId, entity.recordsId);
	}

	@Override
	public int hashCode() {
		return Objects.hash(uniName, recordsId);
	}

}