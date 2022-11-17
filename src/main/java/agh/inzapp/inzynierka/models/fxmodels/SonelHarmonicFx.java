package agh.inzapp.inzynierka.models.fxmodels;

import agh.inzapp.inzynierka.models.enums.UniNames;
import javafx.collections.FXCollections;

import java.util.LinkedHashMap;
import java.util.Map;

public class SonelHarmonicFx extends HarmoFx{
	public void init() {
		initFlags();
		initThd();
		initHarmonics();
	}
	private void initFlags() {
		Map<UniNames, String> flags = new LinkedHashMap<>();
		flags.put(UniNames.Flag_E, null);
		flags.put(UniNames.Flag_P, null);
		flags.put(UniNames.Flag_G, null);
		flags.put(UniNames.Flag_T, null);
		flags.put(UniNames.Flag_A, null);
		setFlags(FXCollections.observableMap(flags));
	}

	private void initThd() {
		Map<UniNames, Double> dataMap = new LinkedHashMap<>();
		dataMap.put(UniNames.SONEL_THD_L1, null);
		dataMap.put(UniNames.SONEL_THD_L2, null);
		dataMap.put(UniNames.SONEL_THD_L3, null);

		setThd(FXCollections.observableMap(dataMap));
	}

	private void initHarmonics() {
		Map<UniNames, Double> dataMap = new LinkedHashMap<>();
		dataMap.put(UniNames.SONEL_H1_UL1, null);
		dataMap.put(UniNames.SONEL_H2_UL1, null);
		dataMap.put(UniNames.SONEL_H3_UL1, null);
		dataMap.put(UniNames.SONEL_H4_UL1, null);
		dataMap.put(UniNames.SONEL_H5_UL1, null);
		dataMap.put(UniNames.SONEL_H6_UL1, null);
		dataMap.put(UniNames.SONEL_H7_UL1, null);
		dataMap.put(UniNames.SONEL_H8_UL1, null);
		dataMap.put(UniNames.SONEL_H9_UL1, null);
		dataMap.put(UniNames.SONEL_H10_UL1, null);
		dataMap.put(UniNames.SONEL_H11_UL1, null);
		dataMap.put(UniNames.SONEL_H12_UL1, null);
		dataMap.put(UniNames.SONEL_H13_UL1, null);
		dataMap.put(UniNames.SONEL_H14_UL1, null);
		dataMap.put(UniNames.SONEL_H15_UL1, null);
		dataMap.put(UniNames.SONEL_H16_UL1, null);
		dataMap.put(UniNames.SONEL_H17_UL1, null);
		dataMap.put(UniNames.SONEL_H18_UL1, null);
		dataMap.put(UniNames.SONEL_H19_UL1, null);
		dataMap.put(UniNames.SONEL_H20_UL1, null);
		dataMap.put(UniNames.SONEL_H21_UL1, null);
		dataMap.put(UniNames.SONEL_H22_UL1, null);
		dataMap.put(UniNames.SONEL_H23_UL1, null);
		dataMap.put(UniNames.SONEL_H24_UL1, null);
		dataMap.put(UniNames.SONEL_H25_UL1, null);
		dataMap.put(UniNames.SONEL_H26_UL1, null);
		dataMap.put(UniNames.SONEL_H27_UL1, null);
		dataMap.put(UniNames.SONEL_H28_UL1, null);
		dataMap.put(UniNames.SONEL_H29_UL1, null);
		dataMap.put(UniNames.SONEL_H30_UL1, null);
		dataMap.put(UniNames.SONEL_H31_UL1, null);
		dataMap.put(UniNames.SONEL_H32_UL1, null);
		dataMap.put(UniNames.SONEL_H33_UL1, null);
		dataMap.put(UniNames.SONEL_H34_UL1, null);
		dataMap.put(UniNames.SONEL_H35_UL1, null);
		dataMap.put(UniNames.SONEL_H36_UL1, null);
		dataMap.put(UniNames.SONEL_H37_UL1, null);
		dataMap.put(UniNames.SONEL_H38_UL1, null);
		dataMap.put(UniNames.SONEL_H39_UL1, null);
		dataMap.put(UniNames.SONEL_H40_UL1, null);
		dataMap.put(UniNames.SONEL_H41_UL1, null);
		dataMap.put(UniNames.SONEL_H42_UL1, null);
		dataMap.put(UniNames.SONEL_H43_UL1, null);
		dataMap.put(UniNames.SONEL_H44_UL1, null);
		dataMap.put(UniNames.SONEL_H45_UL1, null);
		dataMap.put(UniNames.SONEL_H46_UL1, null);
		dataMap.put(UniNames.SONEL_H47_UL1, null);
		dataMap.put(UniNames.SONEL_H48_UL1, null);
		dataMap.put(UniNames.SONEL_H49_UL1, null);
		dataMap.put(UniNames.SONEL_H50_UL1, null);
		dataMap.put(UniNames.SONEL_H1_UL2, null);
		dataMap.put(UniNames.SONEL_H2_UL2, null);
		dataMap.put(UniNames.SONEL_H3_UL2, null);
		dataMap.put(UniNames.SONEL_H4_UL2, null);
		dataMap.put(UniNames.SONEL_H5_UL2, null);
		dataMap.put(UniNames.SONEL_H6_UL2, null);
		dataMap.put(UniNames.SONEL_H7_UL2, null);
		dataMap.put(UniNames.SONEL_H8_UL2, null);
		dataMap.put(UniNames.SONEL_H9_UL2, null);
		dataMap.put(UniNames.SONEL_H10_UL2, null);
		dataMap.put(UniNames.SONEL_H11_UL2, null);
		dataMap.put(UniNames.SONEL_H12_UL2, null);
		dataMap.put(UniNames.SONEL_H13_UL2, null);
		dataMap.put(UniNames.SONEL_H14_UL2, null);
		dataMap.put(UniNames.SONEL_H15_UL2, null);
		dataMap.put(UniNames.SONEL_H16_UL2, null);
		dataMap.put(UniNames.SONEL_H17_UL2, null);
		dataMap.put(UniNames.SONEL_H18_UL2, null);
		dataMap.put(UniNames.SONEL_H19_UL2, null);
		dataMap.put(UniNames.SONEL_H20_UL2, null);
		dataMap.put(UniNames.SONEL_H21_UL2, null);
		dataMap.put(UniNames.SONEL_H22_UL2, null);
		dataMap.put(UniNames.SONEL_H23_UL2, null);
		dataMap.put(UniNames.SONEL_H24_UL2, null);
		dataMap.put(UniNames.SONEL_H25_UL2, null);
		dataMap.put(UniNames.SONEL_H26_UL2, null);
		dataMap.put(UniNames.SONEL_H27_UL2, null);
		dataMap.put(UniNames.SONEL_H28_UL2, null);
		dataMap.put(UniNames.SONEL_H29_UL2, null);
		dataMap.put(UniNames.SONEL_H30_UL2, null);
		dataMap.put(UniNames.SONEL_H31_UL2, null);
		dataMap.put(UniNames.SONEL_H32_UL2, null);
		dataMap.put(UniNames.SONEL_H33_UL2, null);
		dataMap.put(UniNames.SONEL_H34_UL2, null);
		dataMap.put(UniNames.SONEL_H35_UL2, null);
		dataMap.put(UniNames.SONEL_H36_UL2, null);
		dataMap.put(UniNames.SONEL_H37_UL2, null);
		dataMap.put(UniNames.SONEL_H38_UL2, null);
		dataMap.put(UniNames.SONEL_H39_UL2, null);
		dataMap.put(UniNames.SONEL_H40_UL2, null);
		dataMap.put(UniNames.SONEL_H41_UL2, null);
		dataMap.put(UniNames.SONEL_H42_UL2, null);
		dataMap.put(UniNames.SONEL_H43_UL2, null);
		dataMap.put(UniNames.SONEL_H44_UL2, null);
		dataMap.put(UniNames.SONEL_H45_UL2, null);
		dataMap.put(UniNames.SONEL_H46_UL2, null);
		dataMap.put(UniNames.SONEL_H47_UL2, null);
		dataMap.put(UniNames.SONEL_H48_UL2, null);
		dataMap.put(UniNames.SONEL_H49_UL2, null);
		dataMap.put(UniNames.SONEL_H50_UL2, null);
		dataMap.put(UniNames.SONEL_H1_UL3, null);
		dataMap.put(UniNames.SONEL_H2_UL3, null);
		dataMap.put(UniNames.SONEL_H3_UL3, null);
		dataMap.put(UniNames.SONEL_H4_UL3, null);
		dataMap.put(UniNames.SONEL_H5_UL3, null);
		dataMap.put(UniNames.SONEL_H6_UL3, null);
		dataMap.put(UniNames.SONEL_H7_UL3, null);
		dataMap.put(UniNames.SONEL_H8_UL3, null);
		dataMap.put(UniNames.SONEL_H9_UL3, null);
		dataMap.put(UniNames.SONEL_H10_UL3, null);
		dataMap.put(UniNames.SONEL_H11_UL3, null);
		dataMap.put(UniNames.SONEL_H12_UL3, null);
		dataMap.put(UniNames.SONEL_H13_UL3, null);
		dataMap.put(UniNames.SONEL_H14_UL3, null);
		dataMap.put(UniNames.SONEL_H15_UL3, null);
		dataMap.put(UniNames.SONEL_H16_UL3, null);
		dataMap.put(UniNames.SONEL_H17_UL3, null);
		dataMap.put(UniNames.SONEL_H18_UL3, null);
		dataMap.put(UniNames.SONEL_H19_UL3, null);
		dataMap.put(UniNames.SONEL_H20_UL3, null);
		dataMap.put(UniNames.SONEL_H21_UL3, null);
		dataMap.put(UniNames.SONEL_H22_UL3, null);
		dataMap.put(UniNames.SONEL_H23_UL3, null);
		dataMap.put(UniNames.SONEL_H24_UL3, null);
		dataMap.put(UniNames.SONEL_H25_UL3, null);
		dataMap.put(UniNames.SONEL_H26_UL3, null);
		dataMap.put(UniNames.SONEL_H27_UL3, null);
		dataMap.put(UniNames.SONEL_H28_UL3, null);
		dataMap.put(UniNames.SONEL_H29_UL3, null);
		dataMap.put(UniNames.SONEL_H30_UL3, null);
		dataMap.put(UniNames.SONEL_H31_UL3, null);
		dataMap.put(UniNames.SONEL_H32_UL3, null);
		dataMap.put(UniNames.SONEL_H33_UL3, null);
		dataMap.put(UniNames.SONEL_H34_UL3, null);
		dataMap.put(UniNames.SONEL_H35_UL3, null);
		dataMap.put(UniNames.SONEL_H36_UL3, null);
		dataMap.put(UniNames.SONEL_H37_UL3, null);
		dataMap.put(UniNames.SONEL_H38_UL3, null);
		dataMap.put(UniNames.SONEL_H39_UL3, null);
		dataMap.put(UniNames.SONEL_H40_UL3, null);
		dataMap.put(UniNames.SONEL_H41_UL3, null);
		dataMap.put(UniNames.SONEL_H42_UL3, null);
		dataMap.put(UniNames.SONEL_H43_UL3, null);
		dataMap.put(UniNames.SONEL_H44_UL3, null);
		dataMap.put(UniNames.SONEL_H45_UL3, null);
		dataMap.put(UniNames.SONEL_H46_UL3, null);
		dataMap.put(UniNames.SONEL_H47_UL3, null);
		dataMap.put(UniNames.SONEL_H48_UL3, null);
		dataMap.put(UniNames.SONEL_H49_UL3, null);
		dataMap.put(UniNames.SONEL_H50_UL3, null);

		setHarmonics(FXCollections.observableMap(dataMap));
	}
}
