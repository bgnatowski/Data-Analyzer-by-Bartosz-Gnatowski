package agh.inzapp.inzynierka.models.modelObj;

import agh.inzapp.inzynierka.utils.enums.UnitaryNames;
import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor
@EqualsAndHashCode

public class PQDataObj extends BaseDataModelObj {
	private Character flags;
	public PQDataObj() {
		initRecords();
		initAdditionalRecords();
	}

	@Override
	protected void initAdditionalRecords() {
		Map<UnitaryNames, Double> records = getRecords();
		records.put(UnitaryNames.U12_max           , null);
		records.put(UnitaryNames.U23_max           , null);
		records.put(UnitaryNames.U31_max           , null);
		records.put(UnitaryNames.U12_min           , null);
		records.put(UnitaryNames.U23_min           , null);
		records.put(UnitaryNames.U31_min           , null);
		records.put(UnitaryNames.Pst_U12           , null);
		records.put(UnitaryNames.Pst_U23           , null);
		records.put(UnitaryNames.Pst_U31           , null);
		records.put(UnitaryNames.Plt_U12           , null);
		records.put(UnitaryNames.Plt_U23           , null);
		records.put(UnitaryNames.Plt_U31           , null);
		records.put(UnitaryNames.f, null);
		records.put(UnitaryNames.cos_phi           , null);
		records.put(UnitaryNames.tan_phi           , null);
		records.put(UnitaryNames.Unbalanced_Voltage, null);
		records.put(UnitaryNames.Unbalanced_Current, null);
		records.put(UnitaryNames.P_abs             , null);
		records.put(UnitaryNames.PF_total_abs      , null);
		setRecords(records);
	}

	public Character getFlag() {
		return flags;
	}

	public void setFlags(Character flags) {
		this.flags = flags;
	}

	@Override
	public String toString() {
		List<String> allDataToString = new ArrayList<>();
		allDataToString.add(getLocalDateTime().toLocalDate().toString());
		allDataToString.add(getLocalDateTime().toLocalTime().toString());
		allDataToString.add(getFlag().toString());

		String s = Arrays.toString(getRecords().values().toArray());
		String data = s.substring(1, s.length()-1);
		allDataToString.add(data);

		return allDataToString.toString();
	}


}
