package agh.inzapp.inzynierka.database.models;

import agh.inzapp.inzynierka.models.enums.UniNames;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static javax.persistence.GenerationType.SEQUENCE;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString

@Entity(name = "HarmoDb")
@Table(name = "harmoDb", uniqueConstraints = {
		@UniqueConstraint(name = "harmoDb_unique", columnNames = {"date"})
})
public class HarmoDb implements CommonDbModel{
	@Id
//	@SequenceGenerator(name = "harmoDb_sequence", sequenceName = "harmoDb_sequence", allocationSize = 1)
//	@GeneratedValue(strategy = SEQUENCE, generator = "harmoDb_sequence")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", updatable = false)
	private Long id;
	@Column(name = "date", columnDefinition = "TIMESTAMP", nullable = false)
	private LocalDateTime date;
	@Column(name = "flags")
	private String flags;

	@ElementCollection(fetch = FetchType.EAGER)
	@MapKeyColumn(name = "uni_name")
	@MapKeyEnumerated(EnumType.STRING)
	@Column(name = "harmonics_value")
	@CollectionTable(name = "harmonics_mapping", joinColumns = @JoinColumn(name = "harmonics_id", referencedColumnName = "id"))
	private Map<UniNames, Double> records = new LinkedHashMap<>();

	@ElementCollection(targetClass = UniNames.class, fetch = FetchType.EAGER)
	@JoinTable(name = "columnHarmoNames_mapping", joinColumns = @JoinColumn(name = "columnHarmoNames_id"))
	@Column(name = "columnHarmoName", nullable = false)
	@Enumerated(EnumType.STRING)
	private List<UniNames> columnNames;

}
