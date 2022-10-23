package agh.inzapp.inzynierka.database.models;

import agh.inzapp.inzynierka.enums.UniNames;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static javax.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity(name = "DataDb")
@Table(name = "dataDb", uniqueConstraints = {
		@UniqueConstraint(name = "dataDb_unique", columnNames = {"date", "time"})
})
public class DataDb implements CommonDbModel{
	@Id
	@SequenceGenerator(name = "dataDb_sequence", sequenceName = "dataDb_sequence", allocationSize = 1)
	@GeneratedValue(strategy = SEQUENCE, generator = "dataDb_sequence")
	@Column(name = "id", updatable = false)
	private Long id;
	@Column(name = "date", columnDefinition = "DATE", nullable = false)
	private LocalDate date;
	@Column(name = "time", columnDefinition = "TIME", nullable = false)
	private LocalTime time;
	@Column(name = "flags")
	private String flags;

	@ElementCollection
	@MapKeyColumn(name = "uni_name")
	@MapKeyEnumerated(EnumType.STRING)
	@Column(name = "record_value")
	@CollectionTable(name = "records_mapping", joinColumns = @JoinColumn(name = "records_id", referencedColumnName = "id"))
//	@ToString.Exclude
	private Map<UniNames, Double> records = new LinkedHashMap<>();

	@ElementCollection(targetClass = UniNames.class, fetch = FetchType.EAGER)
	@JoinTable(name = "columnNames_mapping", joinColumns = @JoinColumn(name = "columnName_id"))
	@Column(name = "columnName", nullable = false)
	@Enumerated(EnumType.STRING)
	private List<UniNames> columnNames;

}


