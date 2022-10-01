//package agh.inzapp.inzynierka.models.modelObj;
//
//import agh.inzapp.inzynierka.utils.enums.UnitaryNames;
//
//import java.util.Map;
//import java.util.TreeMap;
//
//public class WinPQHarmonicsObjDataObj extends WinPQDataObj {
//	private Map<UnitaryNames, Integer> columnsHarmonicsNames = new TreeMap<>();
//	private Map<UnitaryNames, Double> thdData = new TreeMap<>();
//	private Map<UnitaryNames, Double> harmonicsL1Data = new TreeMap<>();
//	private Map<UnitaryNames, Double> harmonicsL2Data = new TreeMap<>();
//	private Map<UnitaryNames, Double> harmonicsL3Data = new TreeMap<>();
//
//	public WinPQHarmonicsObjDataObj() {
//		super();
//		initAdditionalRecords();
//	}
//
//	@Override
//	protected void initAdditionalRecords() {
//		initThdMap();
//		initL1Map();
//		initL2Map();
//		initL3Map();
//	}
//
//	private void initThdMap() {
//		Map<UnitaryNames, Double> dataMap = new TreeMap<>();
//		dataMap.put(UnitaryNames.winPQ_THD_12, null);
//		dataMap.put(UnitaryNames.winPQ_THD_23, null);
//		dataMap.put(UnitaryNames.winPQ_THD_31, null);
//		dataMap.put(UnitaryNames.winPQ_THD_L1, null);
//		dataMap.put(UnitaryNames.winPQ_THD_L2, null);
//		dataMap.put(UnitaryNames.winPQ_THD_L3, null);
//		setThdData(dataMap);
//	}
//
//	private void initL1Map() {
//		Map<UnitaryNames, Double> dataMap = new TreeMap<>();
//		dataMap.put(UnitaryNames.winPQ_H1_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H2_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H3_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H4_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H5_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H6_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H7_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H8_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H9_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H10_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H11_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H12_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H13_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H14_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H15_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H16_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H17_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H18_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H19_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H20_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H21_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H22_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H23_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H24_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H25_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H26_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H27_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H28_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H29_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H30_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H31_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H32_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H33_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H34_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H35_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H36_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H37_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H38_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H39_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H40_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H41_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H42_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H43_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H44_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H45_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H46_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H47_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H48_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H49_UL1, null);
//		dataMap.put(UnitaryNames.winPQ_H50_UL1, null);
//		setHarmonicsL1Data(dataMap);
//	}
//
//	private void initL2Map() {
//		Map<UnitaryNames, Double> dataMap = new TreeMap<>();
//		dataMap.put(UnitaryNames.winPQ_H1_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H2_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H3_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H4_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H5_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H6_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H7_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H8_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H9_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H10_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H11_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H12_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H13_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H14_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H15_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H16_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H17_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H18_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H19_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H20_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H21_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H22_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H23_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H24_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H25_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H26_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H27_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H28_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H29_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H30_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H31_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H32_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H33_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H34_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H35_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H36_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H37_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H38_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H39_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H40_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H41_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H42_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H43_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H44_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H45_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H46_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H47_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H48_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H49_UL2, null);
//		dataMap.put(UnitaryNames.winPQ_H50_UL2, null);
//		setHarmonicsL2Data(dataMap);
//	}
//
//	private void initL3Map() {
//		Map<UnitaryNames, Double> dataMap = new TreeMap<>();
//		dataMap.put(UnitaryNames.winPQ_H1_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H2_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H3_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H4_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H5_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H6_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H7_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H8_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H9_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H10_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H11_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H12_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H13_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H14_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H15_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H16_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H17_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H18_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H19_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H20_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H21_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H22_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H23_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H24_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H25_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H26_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H27_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H28_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H29_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H30_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H31_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H32_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H33_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H34_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H35_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H36_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H37_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H38_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H39_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H40_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H41_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H42_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H43_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H44_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H45_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H46_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H47_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H48_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H49_UL3, null);
//		dataMap.put(UnitaryNames.winPQ_H50_UL3, null);
//		setHarmonicsL3Data(dataMap);
//	}
//
//	public Map<UnitaryNames, Double> getThdData() {
//		return thdData;
//	}
//
//	public void setThdData(Map<UnitaryNames, Double> thdData) {
//		this.thdData = thdData;
//	}
//
//	public Map<UnitaryNames, Double> getHarmonicsL1Data() {
//		return harmonicsL1Data;
//	}
//
//	public void setHarmonicsL1Data(Map<UnitaryNames, Double> harmonicsL1Data) {
//		this.harmonicsL1Data = harmonicsL1Data;
//	}
//
//	public Map<UnitaryNames, Double> getHarmonicsL2Data() {
//		return harmonicsL2Data;
//	}
//
//	public void setHarmonicsL2Data(Map<UnitaryNames, Double> harmonicsL2Data) {
//		this.harmonicsL2Data = harmonicsL2Data;
//	}
//
//	public Map<UnitaryNames, Double> getHarmonicsL3Data() {
//		return harmonicsL3Data;
//	}
//
//	public void setHarmonicsL3Data(Map<UnitaryNames, Double> harmonicsL3Data) {
//		this.harmonicsL3Data = harmonicsL3Data;
//	}
//
//	public Map<UnitaryNames, Integer> getColumnsHarmonicsNames() {
//		return columnsHarmonicsNames;
//	}
//
//	public void setColumnsHarmonicsNames(Map<UnitaryNames, Integer> columnsHarmonicsNames) {
//		this.columnsHarmonicsNames = columnsHarmonicsNames;
//	}
//
//}
