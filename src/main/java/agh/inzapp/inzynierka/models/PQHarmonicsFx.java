package agh.inzapp.inzynierka.models;

import agh.inzapp.inzynierka.enums.UniNames;
import javafx.collections.FXCollections;

import java.util.LinkedHashMap;
import java.util.Map;

public class PQHarmonicsFx extends HarmoFx{
	public void init() {
		initFlags();
		initThd();
		initHarmonics();
	}
	private void initFlags() {
		Map<UniNames, String> flags = new LinkedHashMap<>();
		flags.put(UniNames.Flag, null);
		setFlags(FXCollections.observableMap(flags));
	}
	private void initThd() {
		Map<UniNames, Double> dataMap = new LinkedHashMap<>();
		dataMap.put(UniNames.PQ_H1_UL1, null);
		dataMap.put(UniNames.PQ_H2_UL1, null);
		dataMap.put(UniNames.PQ_H3_UL1, null);
		dataMap.put(UniNames.PQ_H4_UL1, null);
		dataMap.put(UniNames.PQ_H5_UL1, null);
		dataMap.put(UniNames.PQ_H6_UL1, null);
		dataMap.put(UniNames.PQ_H7_UL1, null);
		dataMap.put(UniNames.PQ_H8_UL1, null);
		dataMap.put(UniNames.PQ_H9_UL1, null);
		dataMap.put(UniNames.PQ_H10_UL1, null);
		dataMap.put(UniNames.PQ_H11_UL1, null);
		dataMap.put(UniNames.PQ_H12_UL1, null);
		dataMap.put(UniNames.PQ_H13_UL1, null);
		dataMap.put(UniNames.PQ_H14_UL1, null);
		dataMap.put(UniNames.PQ_H15_UL1, null);
		dataMap.put(UniNames.PQ_H16_UL1, null);
		dataMap.put(UniNames.PQ_H17_UL1, null);
		dataMap.put(UniNames.PQ_H18_UL1, null);
		dataMap.put(UniNames.PQ_H19_UL1, null);
		dataMap.put(UniNames.PQ_H20_UL1, null);
		dataMap.put(UniNames.PQ_H21_UL1, null);
		dataMap.put(UniNames.PQ_H22_UL1, null);
		dataMap.put(UniNames.PQ_H23_UL1, null);
		dataMap.put(UniNames.PQ_H24_UL1, null);
		dataMap.put(UniNames.PQ_H25_UL1, null);
		dataMap.put(UniNames.PQ_H26_UL1, null);
		dataMap.put(UniNames.PQ_H27_UL1, null);
		dataMap.put(UniNames.PQ_H28_UL1, null);
		dataMap.put(UniNames.PQ_H29_UL1, null);
		dataMap.put(UniNames.PQ_H30_UL1, null);
		dataMap.put(UniNames.PQ_H31_UL1, null);
		dataMap.put(UniNames.PQ_H32_UL1, null);
		dataMap.put(UniNames.PQ_H33_UL1, null);
		dataMap.put(UniNames.PQ_H34_UL1, null);
		dataMap.put(UniNames.PQ_H35_UL1, null);
		dataMap.put(UniNames.PQ_H36_UL1, null);
		dataMap.put(UniNames.PQ_H37_UL1, null);
		dataMap.put(UniNames.PQ_H38_UL1, null);
		dataMap.put(UniNames.PQ_H39_UL1, null);
		dataMap.put(UniNames.PQ_H40_UL1, null);
		dataMap.put(UniNames.PQ_H41_UL1, null);
		dataMap.put(UniNames.PQ_H42_UL1, null);
		dataMap.put(UniNames.PQ_H43_UL1, null);
		dataMap.put(UniNames.PQ_H44_UL1, null);
		dataMap.put(UniNames.PQ_H45_UL1, null);
		dataMap.put(UniNames.PQ_H46_UL1, null);
		dataMap.put(UniNames.PQ_H47_UL1, null);
		dataMap.put(UniNames.PQ_H48_UL1, null);
		dataMap.put(UniNames.PQ_H49_UL1, null);
		dataMap.put(UniNames.PQ_H50_UL1, null);

		dataMap.put(UniNames.PQ_H1_UL2, null);
		dataMap.put(UniNames.PQ_H2_UL2, null);
		dataMap.put(UniNames.PQ_H3_UL2, null);
		dataMap.put(UniNames.PQ_H4_UL2, null);
		dataMap.put(UniNames.PQ_H5_UL2, null);
		dataMap.put(UniNames.PQ_H6_UL2, null);
		dataMap.put(UniNames.PQ_H7_UL2, null);
		dataMap.put(UniNames.PQ_H8_UL2, null);
		dataMap.put(UniNames.PQ_H9_UL2, null);
		dataMap.put(UniNames.PQ_H10_UL2, null);
		dataMap.put(UniNames.PQ_H11_UL2, null);
		dataMap.put(UniNames.PQ_H12_UL2, null);
		dataMap.put(UniNames.PQ_H13_UL2, null);
		dataMap.put(UniNames.PQ_H14_UL2, null);
		dataMap.put(UniNames.PQ_H15_UL2, null);
		dataMap.put(UniNames.PQ_H16_UL2, null);
		dataMap.put(UniNames.PQ_H17_UL2, null);
		dataMap.put(UniNames.PQ_H18_UL2, null);
		dataMap.put(UniNames.PQ_H19_UL2, null);
		dataMap.put(UniNames.PQ_H20_UL2, null);
		dataMap.put(UniNames.PQ_H21_UL2, null);
		dataMap.put(UniNames.PQ_H22_UL2, null);
		dataMap.put(UniNames.PQ_H23_UL2, null);
		dataMap.put(UniNames.PQ_H24_UL2, null);
		dataMap.put(UniNames.PQ_H25_UL2, null);
		dataMap.put(UniNames.PQ_H26_UL2, null);
		dataMap.put(UniNames.PQ_H27_UL2, null);
		dataMap.put(UniNames.PQ_H28_UL2, null);
		dataMap.put(UniNames.PQ_H29_UL2, null);
		dataMap.put(UniNames.PQ_H30_UL2, null);
		dataMap.put(UniNames.PQ_H31_UL2, null);
		dataMap.put(UniNames.PQ_H32_UL2, null);
		dataMap.put(UniNames.PQ_H33_UL2, null);
		dataMap.put(UniNames.PQ_H34_UL2, null);
		dataMap.put(UniNames.PQ_H35_UL2, null);
		dataMap.put(UniNames.PQ_H36_UL2, null);
		dataMap.put(UniNames.PQ_H37_UL2, null);
		dataMap.put(UniNames.PQ_H38_UL2, null);
		dataMap.put(UniNames.PQ_H39_UL2, null);
		dataMap.put(UniNames.PQ_H40_UL2, null);
		dataMap.put(UniNames.PQ_H41_UL2, null);
		dataMap.put(UniNames.PQ_H42_UL2, null);
		dataMap.put(UniNames.PQ_H43_UL2, null);
		dataMap.put(UniNames.PQ_H44_UL2, null);
		dataMap.put(UniNames.PQ_H45_UL2, null);
		dataMap.put(UniNames.PQ_H46_UL2, null);
		dataMap.put(UniNames.PQ_H47_UL2, null);
		dataMap.put(UniNames.PQ_H48_UL2, null);
		dataMap.put(UniNames.PQ_H49_UL2, null);
		dataMap.put(UniNames.PQ_H50_UL2, null);

		dataMap.put(UniNames.PQ_H1_UL3, null);
		dataMap.put(UniNames.PQ_H2_UL3, null);
		dataMap.put(UniNames.PQ_H3_UL3, null);
		dataMap.put(UniNames.PQ_H4_UL3, null);
		dataMap.put(UniNames.PQ_H5_UL3, null);
		dataMap.put(UniNames.PQ_H6_UL3, null);
		dataMap.put(UniNames.PQ_H7_UL3, null);
		dataMap.put(UniNames.PQ_H8_UL3, null);
		dataMap.put(UniNames.PQ_H9_UL3, null);
		dataMap.put(UniNames.PQ_H10_UL3, null);
		dataMap.put(UniNames.PQ_H11_UL3, null);
		dataMap.put(UniNames.PQ_H12_UL3, null);
		dataMap.put(UniNames.PQ_H13_UL3, null);
		dataMap.put(UniNames.PQ_H14_UL3, null);
		dataMap.put(UniNames.PQ_H15_UL3, null);
		dataMap.put(UniNames.PQ_H16_UL3, null);
		dataMap.put(UniNames.PQ_H17_UL3, null);
		dataMap.put(UniNames.PQ_H18_UL3, null);
		dataMap.put(UniNames.PQ_H19_UL3, null);
		dataMap.put(UniNames.PQ_H20_UL3, null);
		dataMap.put(UniNames.PQ_H21_UL3, null);
		dataMap.put(UniNames.PQ_H22_UL3, null);
		dataMap.put(UniNames.PQ_H23_UL3, null);
		dataMap.put(UniNames.PQ_H24_UL3, null);
		dataMap.put(UniNames.PQ_H25_UL3, null);
		dataMap.put(UniNames.PQ_H26_UL3, null);
		dataMap.put(UniNames.PQ_H27_UL3, null);
		dataMap.put(UniNames.PQ_H28_UL3, null);
		dataMap.put(UniNames.PQ_H29_UL3, null);
		dataMap.put(UniNames.PQ_H30_UL3, null);
		dataMap.put(UniNames.PQ_H31_UL3, null);
		dataMap.put(UniNames.PQ_H32_UL3, null);
		dataMap.put(UniNames.PQ_H33_UL3, null);
		dataMap.put(UniNames.PQ_H34_UL3, null);
		dataMap.put(UniNames.PQ_H35_UL3, null);
		dataMap.put(UniNames.PQ_H36_UL3, null);
		dataMap.put(UniNames.PQ_H37_UL3, null);
		dataMap.put(UniNames.PQ_H38_UL3, null);
		dataMap.put(UniNames.PQ_H39_UL3, null);
		dataMap.put(UniNames.PQ_H40_UL3, null);
		dataMap.put(UniNames.PQ_H41_UL3, null);
		dataMap.put(UniNames.PQ_H42_UL3, null);
		dataMap.put(UniNames.PQ_H43_UL3, null);
		dataMap.put(UniNames.PQ_H44_UL3, null);
		dataMap.put(UniNames.PQ_H45_UL3, null);
		dataMap.put(UniNames.PQ_H46_UL3, null);
		dataMap.put(UniNames.PQ_H47_UL3, null);
		dataMap.put(UniNames.PQ_H48_UL3, null);
		dataMap.put(UniNames.PQ_H49_UL3, null);
		dataMap.put(UniNames.PQ_H50_UL3, null);

		setHarmonics(FXCollections.observableMap(dataMap));
	}

	private void initHarmonics() {
		Map<UniNames, Double> dataMap = new LinkedHashMap<>();
		dataMap.put(UniNames.PQ_THD_12, null);
		dataMap.put(UniNames.PQ_THD_23, null);
		dataMap.put(UniNames.PQ_THD_31, null);
		dataMap.put(UniNames.PQ_THD_L1, null);
		dataMap.put(UniNames.PQ_THD_L2, null);
		dataMap.put(UniNames.PQ_THD_L3, null);

		setThd(FXCollections.observableMap(dataMap));
	}

}
