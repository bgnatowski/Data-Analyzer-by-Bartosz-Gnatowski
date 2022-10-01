package agh.inzapp.inzynierka.database.dbmodels;

import lombok.*;

import agh.inzapp.inzynierka.utils.enums.UnitaryNames;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity
public class PQDataDb {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@Column(name = "local_date", columnDefinition = "DATE")
	private LocalDate date;
	@Column(name = "local_time", columnDefinition = "TIME")
	private LocalTime time;
	@Column(name = "flag",  columnDefinition = "FLAG")
	private Character flag;

//	@OneToMany
//	@MapKey(name = "columns")
//	private Map<UnitaryNames, PQColumns> columnsNames;
	@OneToMany
	@MapKeyEnumerated(EnumType.STRING)
	private Map<UnitaryNames, PQColumns> columnsNamesIndexMap;

	//	private Map<UnitaryNames, Double> records = new TreeMap<>();

}

