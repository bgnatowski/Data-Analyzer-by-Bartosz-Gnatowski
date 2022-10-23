package agh.inzapp.inzynierka.database.mappings;

import agh.inzapp.inzynierka.database.models.HarmoDb;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter

@Entity
@Table(name = "thd_mapping")
public class ThdMapping {
	@EmbeddedId
	private ThdMappingId id;

	@MapsId("thdId")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "thd_id", nullable = false)
	private HarmoDb owner;

	@Column(name = "thd_value")
	private Double thdValue;
	@Override
	public String toString() {
		return "[ " + id.getUniName() + ", " + thdValue + " ]";
	}

}