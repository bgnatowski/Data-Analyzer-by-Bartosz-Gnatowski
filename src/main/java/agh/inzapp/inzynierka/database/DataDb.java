package agh.inzapp.inzynierka.database;

import agh.inzapp.inzynierka.enums.UniNames;
import lombok.*;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static javax.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
//@ToString(exclude = {"records", "flags"})
//@EqualsAndHashCode(exclude = {"records", "flags"})

@Entity(name = "DataDb")
@Table(name = "dataDb", uniqueConstraints = {
		@UniqueConstraint(name = "dataDb_unique", columnNames = {"date", "time"})
})
public class DataDb {
	@Id
	@SequenceGenerator(name = "dataDb_sequence", sequenceName = "dataDb_sequence", allocationSize = 1)
	@GeneratedValue(strategy = SEQUENCE, generator = "dataDb_sequence")
	@Column(name = "id", updatable = false)
	private Long id;
	@Column(name = "date", columnDefinition = "DATE", nullable = false)
	private LocalDate date;
	@Column(name = "time", columnDefinition = "TIME", nullable = false)
	private LocalTime time;

	@ElementCollection
	@MapKeyColumn(name="flags")
	@Column(name="value")
	@CollectionTable(name="datadb_flags_mapping", joinColumns=@JoinColumn(name="flags_id", referencedColumnName = "id"))
	@ToString.Exclude
	private Map<String, String> flags;

	@ElementCollection
	@MapKeyColumn(name = "uni_name")
	@Column(name = "records")
	@CollectionTable(name = "datadb_records_mapping", joinColumns = @JoinColumn(name = "records_id", referencedColumnName = "id"))
	@ToString.Exclude
	private Map<String, Double> records;


	@Transient
	private Map<UniNames, Integer> columnNamesIndexMap;

}


