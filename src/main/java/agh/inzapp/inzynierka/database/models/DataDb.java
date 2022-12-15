package agh.inzapp.inzynierka.database.models;

import agh.inzapp.inzynierka.models.enums.UniNames;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static javax.persistence.GenerationType.IDENTITY;
import static javax.persistence.GenerationType.SEQUENCE;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity(name = "DataDb")
@Table(name = "dataDb")
public class DataDb implements CommonDbModel{
	@Id
	@SequenceGenerator(name = "data_db_sequence", sequenceName = "data_db_sequence", allocationSize = 1000, initialValue = 1)
	@GeneratedValue(strategy = SEQUENCE, generator = "data_db_sequence")
	@Column(name = "id")
	private Long id;
	@Column(name = "date", columnDefinition = "TIMESTAMP", nullable = false)
	private LocalDateTime date;
	@Column(name = "flags")
	private String flags;

	@ElementCollection(fetch = FetchType.EAGER)
	@MapKeyColumn(name = "uni_name")
	@MapKeyEnumerated(EnumType.STRING)
	@Column(name = "record_value")
	@CollectionTable(name = "records_mapping", joinColumns = @JoinColumn(name = "records_id", referencedColumnName = "id"))
	private Map<UniNames, Double> records = new LinkedHashMap<>();

	@ElementCollection(targetClass = UniNames.class, fetch = FetchType.EAGER)
	@JoinTable(name = "columnNames_mapping", joinColumns = @JoinColumn(name = "columnName_id"))
	@Column(name = "columnName", nullable = false)
	@Enumerated(EnumType.STRING)
	private List<UniNames> columnNames;
}


