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

@Entity(name = "HataDb")
@Table(name = "harmoDb", uniqueConstraints = {
		@UniqueConstraint(name = "harmoDb_unique", columnNames = {"date", "time"})
})
public class HarmoDb implements CommonDbModel{
	@Id
	@SequenceGenerator(name = "harmoDb_sequence", sequenceName = "harmoDb_sequence", allocationSize = 1)
	@GeneratedValue(strategy = SEQUENCE, generator = "harmoDb_sequence")
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
	@Column(name = "harmonics_value")
	@CollectionTable(name = "harmonics_mapping", joinColumns = @JoinColumn(name = "harmonics_id", referencedColumnName = "id"))
//	@ToString.Exclude
	private Map<UniNames, Double> harmonics = new LinkedHashMap<>();

	@ElementCollection
	@MapKeyColumn(name = "uni_name")
	@MapKeyEnumerated(EnumType.STRING)
	@Column(name = "thd_value")
	@CollectionTable(name = "thd_mapping", joinColumns = @JoinColumn(name = "thd_id", referencedColumnName = "id"))
//	@ToString.Exclude
	private Map<UniNames, Double> thd = new LinkedHashMap<>();

	@ElementCollection(targetClass = UniNames.class, fetch = FetchType.EAGER)
	@JoinTable(name = "columnHarmoNames_mapping", joinColumns = @JoinColumn(name = "columnHarmoNames_id"))
	@Column(name = "columnHarmoName", nullable = false)
	@Enumerated(EnumType.STRING)
	private List<UniNames> columnNames;

}