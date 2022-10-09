package agh.inzapp.inzynierka.models.modelObj;

import agh.inzapp.inzynierka.enums.UniNames;
import lombok.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Getter
@Setter
@AllArgsConstructor

public class PQDataObj extends BaseDataObj {
	private Character flags;
	public PQDataObj() {
		initRecords();
		initAdditionalRecords();
	}

	@Override
	protected void initAdditionalRecords() {
		Map<UniNames, Double> records = getRecords();
		records.put(UniNames.U12_max           , null);
		records.put(UniNames.U23_max           , null);
		records.put(UniNames.U31_max           , null);
		records.put(UniNames.U12_min           , null);
		records.put(UniNames.U23_min           , null);
		records.put(UniNames.U31_min           , null);
		records.put(UniNames.Pst_U12           , null);
		records.put(UniNames.Pst_U23           , null);
		records.put(UniNames.Pst_U31           , null);
		records.put(UniNames.Plt_U12           , null);
		records.put(UniNames.Plt_U23           , null);
		records.put(UniNames.Plt_U31           , null);
		records.put(UniNames.f, null);
		records.put(UniNames.cos_phi           , null);
		records.put(UniNames.tan_phi           , null);
		records.put(UniNames.Unbalanced_Voltage, null);
		records.put(UniNames.Unbalanced_Current, null);
		records.put(UniNames.P_abs             , null);
		records.put(UniNames.PF_total_abs      , null);
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
