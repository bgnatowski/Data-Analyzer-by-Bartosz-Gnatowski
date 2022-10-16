package agh.inzapp.inzynierka.database;

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
	private DataDb records_owner;

	@Column(name = "record_value")
	private Double value;

	@Override
	public String toString() {
		return "uni_name: " + id.getUniName() + ", value: " + value;
	}
}