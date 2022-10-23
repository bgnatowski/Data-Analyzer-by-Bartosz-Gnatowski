package agh.inzapp.inzynierka.database.mappings;

import agh.inzapp.inzynierka.database.models.DataDb;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter

@Entity
@Table(name = "records_mapping")
public class RecordsMapping {
	@EmbeddedId
	private RecordsMappingId id;

	@MapsId("recordsId")
	@ManyToOne(fetch = FetchType.LAZY, optional = false)
	@JoinColumn(name = "records_id", nullable = false)
	private DataDb recordsOwner;

	@Column(name = "record_value")
	private Double recordValue;

	@Override
	public String toString() {
		return "[ " + id.getUniName() + ", " + recordValue + " ]";
	}
}