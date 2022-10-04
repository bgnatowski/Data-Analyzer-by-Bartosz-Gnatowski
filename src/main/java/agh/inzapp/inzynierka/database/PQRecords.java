package agh.inzapp.inzynierka.database;

import agh.inzapp.inzynierka.utils.enums.UnitaryNames;
import lombok.*;

import javax.persistence.*;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
@Table(name = "record")
public class PQRecords {
	@Id
	@GeneratedValue
	@Column(name = "id")
	private Long id;
	@Column(name = "record")
	private Double record;
	@Column(name = "unitaryName")
	@Enumerated(EnumType.STRING)
	private UnitaryNames unitaryName;

	public PQRecords(UnitaryNames name, Double record) {
		this.unitaryName = name;
		this.record = record;
	}

}


