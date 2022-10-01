package agh.inzapp.inzynierka.database.dbmodels;

import agh.inzapp.inzynierka.utils.enums.UnitaryNames;
import com.j256.ormlite.stmt.query.In;
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
@ToString

@Entity(name = "PQDataDb")
@Table(name = "pqdatadb", uniqueConstraints = {@UniqueConstraint(name = "pqdatadb_unique", columnNames = {"date", "time"})})
public class PQDataDb {
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

	@OneToMany(cascade = CascadeType.ALL)
	@JoinTable(name = "pqdata_records_mapping",
	joinColumns = {@JoinColumn(name ="pqdatadb_id", referencedColumnName = "id")},
	inverseJoinColumns = {@JoinColumn(name="record_id", referencedColumnName = "id")})
	@MapKeyEnumerated(EnumType.STRING)
	private Map<UnitaryNames , PQRecords> records;

	@Transient
	private Map<UnitaryNames, Integer> columnNamesIndexMap;


}

