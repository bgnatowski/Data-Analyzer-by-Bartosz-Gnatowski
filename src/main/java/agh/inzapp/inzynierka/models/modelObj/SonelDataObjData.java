package agh.inzapp.inzynierka.models.modelObj;

import agh.inzapp.inzynierka.utils.enums.UnitaryNames;

import java.util.Map;
import java.util.TreeMap;

public class SonelDataObjData extends BaseDataModelObj {
	private Map<UnitaryNames,Character> flags;
	public SonelDataObjData() {
		initRecords();
		initAdditionalRecords();
		initFlags();
	}
	@Override
	protected void initAdditionalRecords() {
		Map<UnitaryNames, Double> records = getRecords();
		records.put(UnitaryNames.Plt_L2      ,null);
		records.put(UnitaryNames.Plt_L1      ,null);
		records.put(UnitaryNames.Plt_L3      ,null);
		records.put(UnitaryNames.U_NPE_avg   ,null);
		records.put(UnitaryNames.U_NPE_max   ,null);
		records.put(UnitaryNames.U_NPE_min   ,null);
		records.put(UnitaryNames.U0_avg      ,null);
		records.put(UnitaryNames.U0_max      ,null);
		records.put(UnitaryNames.U0_min      ,null);
		records.put(UnitaryNames.U1_avg      ,null);
		records.put(UnitaryNames.U1_max      ,null);
		records.put(UnitaryNames.U1_min      ,null);
		records.put(UnitaryNames.U2_avg      ,null);
		records.put(UnitaryNames.U2_max      ,null);
		records.put(UnitaryNames.U2_min      ,null);
		records.put(UnitaryNames.I0_avg      ,null);
		records.put(UnitaryNames.I0_max      ,null);
		records.put(UnitaryNames.I0_min      ,null);
		records.put(UnitaryNames.I1_avg      ,null);
		records.put(UnitaryNames.I1_max      ,null);
		records.put(UnitaryNames.I1_min      ,null);
		records.put(UnitaryNames.I2_avg      ,null);
		records.put(UnitaryNames.I2_min      ,null);
		records.put(UnitaryNames.PF_L1_avg   ,null);
		records.put(UnitaryNames.PF_L2_avg   ,null);
		records.put(UnitaryNames.PF_L3_avg   ,null);
		records.put(UnitaryNames.P_L1_avg    ,null);
		records.put(UnitaryNames.I2_max      ,null);
		records.put(UnitaryNames.P_L2_avg    ,null);
		records.put(UnitaryNames.P_L3_avg    ,null);
		records.put(UnitaryNames.P_L1_max    ,null);
		records.put(UnitaryNames.P_L2_max    ,null);
		records.put(UnitaryNames.P_L3_max    ,null);
		records.put(UnitaryNames.P_L1_min    ,null);
		records.put(UnitaryNames.P_L2_min    ,null);
		records.put(UnitaryNames.P_L3_min    ,null);
		records.put(UnitaryNames.Q_L1_avg   ,null);
		records.put(UnitaryNames.Q_L2_avg   ,null);
		records.put(UnitaryNames.Q_L3_avg   ,null);
		records.put(UnitaryNames.Q_L1_max   ,null);
		records.put(UnitaryNames.Q_L2_max   ,null);
		records.put(UnitaryNames.Q_L3_max   ,null);
		records.put(UnitaryNames.Q_L1_min   ,null);
		records.put(UnitaryNames.Q_L2_min   ,null);
		records.put(UnitaryNames.Q_L3_min   ,null);
		records.put(UnitaryNames.Q_max,null);
		records.put(UnitaryNames.Q_min,null);
		records.put(UnitaryNames.S_L1_avg    ,null);
		records.put(UnitaryNames.S_L2_avg    ,null);
		records.put(UnitaryNames.S_L3_avg    ,null);
		records.put(UnitaryNames.S_L1_max    ,null);
		records.put(UnitaryNames.S_L2_max    ,null);
		records.put(UnitaryNames.S_L3_max    ,null);
		records.put(UnitaryNames.S_L1_min    ,null);
		records.put(UnitaryNames.S_L2_min    ,null);
		records.put(UnitaryNames.S_L3_min    ,null);
		records.put(UnitaryNames.U0_U1_avg   ,null);
		records.put(UnitaryNames.U0_U1_min   ,null);
		records.put(UnitaryNames.U0_U1_max   ,null);
		records.put(UnitaryNames.U2_U1_avg   ,null);
		records.put(UnitaryNames.U2_U1_min   ,null);
		records.put(UnitaryNames.U2_U1_max   ,null);
		records.put(UnitaryNames.I0_I1_avg   ,null);
		records.put(UnitaryNames.I0_I1_min   ,null);
		records.put(UnitaryNames.I0_I1_max   ,null);
		records.put(UnitaryNames.I2_I1_avg   ,null);
		records.put(UnitaryNames.I2_I1_min   ,null);
		records.put(UnitaryNames.I2_I1_max   ,null);
	}
	private void initFlags() {
		flags = new TreeMap<>();
		flags.put(UnitaryNames.Flag_P, 'o');
		flags.put(UnitaryNames.Flag_G, 'o');
		flags.put(UnitaryNames.Flag_E, 'o');
		flags.put(UnitaryNames.Flag_T, 'o');
		flags.put(UnitaryNames.Flag_A, 'o');
	}

	public Map<UnitaryNames, Character> getFlags() {
		return flags;
	}

	public void setFlags(Map<UnitaryNames, Character> flags) {
		this.flags = flags;
	}
}
