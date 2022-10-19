package agh.inzapp.inzynierka.models.modelObj;

import agh.inzapp.inzynierka.enums.UniNames;
import lombok.*;

import java.util.*;

@NoArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)

public class PQDataObj extends BaseDataObj {
	@Override
	public void init() {
		initCommonRecords();
		initOwnRecords();
		initFlags();
	}
	@Override
	protected void initFlags() {
		Map<UniNames, String> flags = new LinkedHashMap<>();
		flags.put(UniNames.Flag, null);
		setFlags(flags);
	}

	private void initOwnRecords() {
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

	@Override
	public String toString() {
		List<String> allDataToString = new ArrayList<>();
		allDataToString.add(getLocalDateTime().toLocalDate().toString());
		allDataToString.add(getLocalDateTime().toLocalTime().toString());
		allDataToString.add(getFlags().values().toString());

		String s = Arrays.toString(getRecords().values().toArray());
		String data = s.substring(1, s.length()-1);
		allDataToString.add(data);

		return allDataToString.toString();
	}




}
