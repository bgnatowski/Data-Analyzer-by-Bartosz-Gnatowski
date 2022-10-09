package agh.inzapp.inzynierka.entities;

import agh.inzapp.inzynierka.enums.UniNames;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Map;

import static javax.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity(name = "PQDataDb")
@Table(name = "pqdatadb", uniqueConstraints = {@UniqueConstraint(name = "pqdatadb_unique", columnNames = {"date", "time"})})
//@Table(name = "pqdatadb")
public class PQDataDb implements BaseDataDb {
	@Id
	@SequenceGenerator(name = "pqdatadb_sequence", sequenceName = "pqdatadb_sequence", allocationSize = 1)
	@GeneratedValue(strategy = SEQUENCE, generator = "pqdatadb_sequence")
	@Column(name = "id", updatable = false)
	private Long id;

	@Column(name = "date", columnDefinition = "DATE", nullable = false)
	private LocalDate date;

	@Column(name = "time", columnDefinition = "TIME", nullable = false)
	private LocalTime time;

	@Column(name = "flag")
	private Character flag;
	//DZIALA NA NAZWY PO PL -> TRZEBA KONWERTOWAC -> PIERWSZE ODPALENIE DZIALA ALE JAKIES BLEDY WYWALA WTF
	@ElementCollection
	@CollectionTable(name = "pqdatadb_record_mapping",
	joinColumns = {@JoinColumn(name = "pqdatadb_id", referencedColumnName = "id")})
	@MapKeyColumn(name = "uni_name")
	@Column(name = "value")
	private Map<String, Double> records;

//	@ElementCollection
//	@CollectionTable(name = "pqdatadb_record_mapping",
//			joinColumns = {@JoinColumn(name = "pqdatadb_id", referencedColumnName = "id")})
//	@MapKeyColumn(name = "records_name")
//	@Column(name = "record")
//	private Map<UnitaryNames, Double> pqrecordsRecordMap;

//	@ElementCollection
//	@CollectionTable(name = "pqdatadb_record_mapping",
//			joinColumns = {@JoinColumn(name = "pqdatadb_id", referencedColumnName = "id")})
//	@MapKeyEnumerated(EnumType.STRING)
//	@Column(name = "record")
//	private Map<UnitaryNames, Double> pqrecordsRecordMap;

//	@ElementCollection
//	@JoinTable(name="RECORDS_NAME_VALUE", joinColumns=@JoinColumn(name="ID"))
//	@MapKeyEnumerated (EnumType.STRING)
//	@Column(name="NAME")
//	private Map<UnitaryNames, Double> recordsNameValue = new TreeMap<>();

//	@OneToMany(cascade = CascadeType.ALL)
//	@JoinTable(name = "pqdatadb_pqrecords_mapping",
//	joinColumns = {@JoinColumn(name ="pqdatadb_id", referencedColumnName = "id")},
//	inverseJoinColumns = {@JoinColumn(name="pqrecords_id", referencedColumnName = "id")})
//	@MapKeyEnumerated(EnumType.STRING)
//	private Map<UnitaryNames , PQRecords> records;

//	@ElementCollection
//	@JoinTable(name="pqrecords", joinColumns=@JoinColumn(name="id"))
//	@MapKeyColumn (name="id")
//	@Column(name="VALUE")
//	private Map<UnitaryNames, Double> records = new TreeMap<>();

	@Transient
	private Map<UniNames, Integer> columnNamesIndexMap;

	@Override
	public String toString() {
		return "PQDataDb{" +
				"id=" + id +
				", date=" + date +
				", time=" + time +
				", flag=" + flag +
				", records=" + records +
				", columnNamesIndexMap=" + columnNamesIndexMap +
				'}';
	}
}

