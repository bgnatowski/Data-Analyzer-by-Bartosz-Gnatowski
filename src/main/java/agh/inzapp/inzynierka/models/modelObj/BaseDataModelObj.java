package agh.inzapp.inzynierka.models.modelObj;

import agh.inzapp.inzynierka.utils.enums.UnitaryNames;

import java.time.LocalDateTime;
import java.util.Map;
import java.util.TreeMap;

public abstract class BaseDataModelObj {
	private Map<UnitaryNames, Integer> columnsNames = new TreeMap<>();
	private Map<UnitaryNames, Double> records = new TreeMap<>();
	private LocalDateTime localDateTime;

	protected void initRecords() {
		records.put(UnitaryNames.U12_avg, null);
		records.put(UnitaryNames.U23_avg, null);
		records.put(UnitaryNames.U31_avg, null);
		records.put(UnitaryNames.UL1_avg, null);
		records.put(UnitaryNames.UL2_avg, null);
		records.put(UnitaryNames.UL3_avg, null);
		records.put(UnitaryNames.UL1_max, null);
		records.put(UnitaryNames.UL2_max, null);
		records.put(UnitaryNames.UL3_max, null);
		records.put(UnitaryNames.UL1_min, null);
		records.put(UnitaryNames.UL2_min, null);
		records.put(UnitaryNames.UL3_min, null);
		records.put(UnitaryNames.IL1_avg, null);
		records.put(UnitaryNames.IL2_avg, null);
		records.put(UnitaryNames.IL3_avg, null);
		records.put(UnitaryNames.IL1_max, null);
		records.put(UnitaryNames.IL2_max, null);
		records.put(UnitaryNames.IL3_max, null);
		records.put(UnitaryNames.IL1_min, null);
		records.put(UnitaryNames.IL2_min, null);
		records.put(UnitaryNames.IL3_min, null);
		records.put(UnitaryNames.IN_avg,  null);
		records.put(UnitaryNames.IN_max,  null);
		records.put(UnitaryNames.IN_min,  null);
		records.put(UnitaryNames.Pst_UL1, null);
		records.put(UnitaryNames.Pst_UL2, null);
		records.put(UnitaryNames.Pst_UL3, null);
		records.put(UnitaryNames.P_total, null);
		records.put(UnitaryNames.P_max,   null);
		records.put(UnitaryNames.P_min,   null);
		records.put(UnitaryNames.S_total, null);
		records.put(UnitaryNames.S_max,   null);
		records.put(UnitaryNames.S_min,   null);
		records.put(UnitaryNames.PF_total,null);
		records.put(UnitaryNames.Q_total,null);
	}

	protected abstract void initAdditionalRecords();

	public Map<UnitaryNames, Double> getRecords() {
		return records;
	}

	public void setRecords(Map<UnitaryNames, Double> records) {
		this.records = records;
	}

	public Map<UnitaryNames, Integer> getColumnsNames() {
		return columnsNames;
	}

	public void setColumnsNames(Map<UnitaryNames, Integer> columnsNames) {
		this.columnsNames = columnsNames;
	}

	public LocalDateTime getLocalDateTime() {
		return localDateTime;
	}

	public void setLocalDateTime(LocalDateTime date) {
		this.localDateTime = date;
	}


}
