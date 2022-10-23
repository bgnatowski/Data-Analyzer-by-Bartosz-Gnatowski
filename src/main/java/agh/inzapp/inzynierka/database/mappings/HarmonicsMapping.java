package agh.inzapp.inzynierka.database.mappings;

import agh.inzapp.inzynierka.database.models.HarmoDb;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
@Getter
@Setter

@Entity
@Table(name = "harmonics_mapping")
public class HarmonicsMapping {
	@EmbeddedId
	private HarmonicsMappingId id;

	@MapsId("harmonicsId")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "harmonics_id", nullable = false)
	private HarmoDb owner;

	@Column(name = "harmonics_value")
	private Double harmonicValue;

	@Override
	public String toString() {
		return "[ " + id.getUniName() + ", " + harmonicValue + " ]";
	}
}