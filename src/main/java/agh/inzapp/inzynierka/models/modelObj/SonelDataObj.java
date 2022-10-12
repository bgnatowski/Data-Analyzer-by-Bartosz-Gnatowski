package agh.inzapp.inzynierka.models.modelObj;

import agh.inzapp.inzynierka.enums.UniNames;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.Map;
import java.util.TreeMap;
@Getter
@Setter
@ToString
@EqualsAndHashCode

public class SonelDataObj extends BaseDataObj {
	public SonelDataObj() {
		initRecords();
		initAdditionalRecords();
		initFlags();
	}
	@Override
	protected void initAdditionalRecords() {
		Map<UniNames, Double> records = getRecords();
		records.put(UniNames.Plt_L2      ,null);
		records.put(UniNames.Plt_L1      ,null);
		records.put(UniNames.Plt_L3      ,null);
		records.put(UniNames.U_NPE_avg   ,null);
		records.put(UniNames.U_NPE_max   ,null);
		records.put(UniNames.U_NPE_min   ,null);
		records.put(UniNames.U0_avg      ,null);
		records.put(UniNames.U0_max      ,null);
		records.put(UniNames.U0_min      ,null);
		records.put(UniNames.U1_avg      ,null);
		records.put(UniNames.U1_max      ,null);
		records.put(UniNames.U1_min      ,null);
		records.put(UniNames.U2_avg      ,null);
		records.put(UniNames.U2_max      ,null);
		records.put(UniNames.U2_min      ,null);
		records.put(UniNames.I0_avg      ,null);
		records.put(UniNames.I0_max      ,null);
		records.put(UniNames.I0_min      ,null);
		records.put(UniNames.I1_avg      ,null);
		records.put(UniNames.I1_max      ,null);
		records.put(UniNames.I1_min      ,null);
		records.put(UniNames.I2_avg      ,null);
		records.put(UniNames.I2_min      ,null);
		records.put(UniNames.PF_L1_avg   ,null);
		records.put(UniNames.PF_L2_avg   ,null);
		records.put(UniNames.PF_L3_avg   ,null);
		records.put(UniNames.P_L1_avg    ,null);
		records.put(UniNames.I2_max      ,null);
		records.put(UniNames.P_L2_avg    ,null);
		records.put(UniNames.P_L3_avg    ,null);
		records.put(UniNames.P_L1_max    ,null);
		records.put(UniNames.P_L2_max    ,null);
		records.put(UniNames.P_L3_max    ,null);
		records.put(UniNames.P_L1_min    ,null);
		records.put(UniNames.P_L2_min    ,null);
		records.put(UniNames.P_L3_min    ,null);
		records.put(UniNames.Q_L1_avg   ,null);
		records.put(UniNames.Q_L2_avg   ,null);
		records.put(UniNames.Q_L3_avg   ,null);
		records.put(UniNames.Q_L1_max   ,null);
		records.put(UniNames.Q_L2_max   ,null);
		records.put(UniNames.Q_L3_max   ,null);
		records.put(UniNames.Q_L1_min   ,null);
		records.put(UniNames.Q_L2_min   ,null);
		records.put(UniNames.Q_L3_min   ,null);
		records.put(UniNames.Q_max,null);
		records.put(UniNames.Q_min,null);
		records.put(UniNames.S_L1_avg    ,null);
		records.put(UniNames.S_L2_avg    ,null);
		records.put(UniNames.S_L3_avg    ,null);
		records.put(UniNames.S_L1_max    ,null);
		records.put(UniNames.S_L2_max    ,null);
		records.put(UniNames.S_L3_max    ,null);
		records.put(UniNames.S_L1_min    ,null);
		records.put(UniNames.S_L2_min    ,null);
		records.put(UniNames.S_L3_min    ,null);
		records.put(UniNames.U0_U1_avg   ,null);
		records.put(UniNames.U0_U1_min   ,null);
		records.put(UniNames.U0_U1_max   ,null);
		records.put(UniNames.U2_U1_avg   ,null);
		records.put(UniNames.U2_U1_min   ,null);
		records.put(UniNames.U2_U1_max   ,null);
		records.put(UniNames.I0_I1_avg   ,null);
		records.put(UniNames.I0_I1_min   ,null);
		records.put(UniNames.I0_I1_max   ,null);
		records.put(UniNames.I2_I1_avg   ,null);
		records.put(UniNames.I2_I1_min   ,null);
		records.put(UniNames.I2_I1_max   ,null);
	}
	@Override
	protected void initFlags() {
		Map<UniNames, String> flags = new TreeMap<>();
		flags.put(UniNames.Flag_P, null);
		flags.put(UniNames.Flag_G, null);
		flags.put(UniNames.Flag_E, null);
		flags.put(UniNames.Flag_T, null);
		flags.put(UniNames.Flag_A, null);
		setFlags(flags);
	}
}
