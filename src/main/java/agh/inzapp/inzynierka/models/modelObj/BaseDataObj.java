package agh.inzapp.inzynierka.models.modelObj;

import agh.inzapp.inzynierka.enums.UniNames;
import lombok.*;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.TreeMap;

@Getter
@Setter
@ToString
@EqualsAndHashCode

public abstract class BaseDataObj {
	private Long id;
	private Map<UniNames, Integer> columnsNamesIndexMap = new LinkedHashMap<>();
	private Map<UniNames, Double> records;
	private Map<UniNames, String> flags;
	private LocalDateTime localDateTime;

	protected void initRecords() {
		records = new TreeMap<>();
		records.put(UniNames.U12_avg, null);
		records.put(UniNames.U23_avg, null);
		records.put(UniNames.U31_avg, null);
		records.put(UniNames.UL1_avg, null);
		records.put(UniNames.UL2_avg, null);
		records.put(UniNames.UL3_avg, null);
		records.put(UniNames.UL1_max, null);
		records.put(UniNames.UL2_max, null);
		records.put(UniNames.UL3_max, null);
		records.put(UniNames.UL1_min, null);
		records.put(UniNames.UL2_min, null);
		records.put(UniNames.UL3_min, null);
		records.put(UniNames.IL1_avg, null);
		records.put(UniNames.IL2_avg, null);
		records.put(UniNames.IL3_avg, null);
		records.put(UniNames.IL1_max, null);
		records.put(UniNames.IL2_max, null);
		records.put(UniNames.IL3_max, null);
		records.put(UniNames.IL1_min, null);
		records.put(UniNames.IL2_min, null);
		records.put(UniNames.IL3_min, null);
		records.put(UniNames.IN_avg,  null);
		records.put(UniNames.IN_max,  null);
		records.put(UniNames.IN_min,  null);
		records.put(UniNames.Pst_UL1, null);
		records.put(UniNames.Pst_UL2, null);
		records.put(UniNames.Pst_UL3, null);
		records.put(UniNames.P_total, null);
		records.put(UniNames.P_max,   null);
		records.put(UniNames.P_min,   null);
		records.put(UniNames.S_total, null);
		records.put(UniNames.S_max,   null);
		records.put(UniNames.S_min,   null);
		records.put(UniNames.PF_total,null);
		records.put(UniNames.Q_total,null);
	}

	protected abstract void initAdditionalRecords();
	protected abstract void initFlags();


}
