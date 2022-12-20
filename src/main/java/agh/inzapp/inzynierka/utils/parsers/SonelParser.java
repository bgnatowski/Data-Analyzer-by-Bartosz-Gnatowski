package agh.inzapp.inzynierka.utils.parsers;

import agh.inzapp.inzynierka.models.enums.UniNames;
import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;

import static agh.inzapp.inzynierka.models.enums.UniNames.*;
import static agh.inzapp.inzynierka.models.enums.UniNames.SONEL_H50_UL3;

//todo
public class SonelParser {
	private static final Map<String, UniNames> mapDataNames = new LinkedHashMap<>();
	private static final Map<String, UniNames> mapHarmonicNames = new LinkedHashMap<>();
	private static final List<String> dateFormatPatterns = new ArrayList<>();

	static {
		dateFormatPatterns.add("d.MM.yyyy");
		dateFormatPatterns.add("d-MM-yyyy");
		dateFormatPatterns.add("d/MM/yyyy");
		dateFormatPatterns.add("yyyy/MM/d");
		dateFormatPatterns.add("yyyy-MM-d");
	}

	static {
		mapDataNames.put("Date", Date);
		mapDataNames.put("Time (UTC±0)", Time);
		mapDataNames.put("Time (UTC+1)", Time);
		mapDataNames.put("Time (UTC+2)", Time);
		mapDataNames.put("Time (UTC+3)", Time);
		mapDataNames.put("Time (UTC+4)", Time);
		mapDataNames.put("Time (UTC+5)", Time);
		mapDataNames.put("Time (UTC+6)", Time);
		mapDataNames.put("Time (UTC+7)", Time);
		mapDataNames.put("Time (UTC+8)", Time);
		mapDataNames.put("Time (UTC+9)", Time);
		mapDataNames.put("Time (UTC+10)", Time);
		mapDataNames.put("Time (UTC+11)", Time);
		mapDataNames.put("Time (UTC+12)", Time);
		mapDataNames.put("Time (UTC+13)", Time);
		mapDataNames.put("Time (UTC-1)", Time);
		mapDataNames.put("Time (UTC-2)", Time);
		mapDataNames.put("Time (UTC-3)", Time);
		mapDataNames.put("Time (UTC-4)", Time);
		mapDataNames.put("Time (UTC-5)", Time);
		mapDataNames.put("Time (UTC-6)", Time);
		mapDataNames.put("Time (UTC-7)", Time);
		mapDataNames.put("Time (UTC-8)", Time);
		mapDataNames.put("Time (UTC-9)", Time);
		mapDataNames.put("Time (UTC-10)", Time);
		mapDataNames.put("Time (UTC-11)", Time);
		mapDataNames.put("P", Flag_P);
		mapDataNames.put("G", Flag_G);
		mapDataNames.put("E", Flag_E);
		mapDataNames.put("T", Flag_T);
		mapDataNames.put("A", Flag_A);
		mapDataNames.put("U L12 avg [V]", U12_avg); //
		mapDataNames.put("U L23 avg [V]", U23_avg); //
		mapDataNames.put("U L31 avg [V]", U31_avg); //
		mapDataNames.put("U L1 avg [V]", UL1_max); //
		mapDataNames.put("U L2 avg [V]", UL2_max); //
		mapDataNames.put("U L3 avg [V]", UL3_max); //
		mapDataNames.put("U L1 max [V]", UL1_min); //
		mapDataNames.put("U L2 max [V]", UL2_min); //
		mapDataNames.put("U L3 max [V]", UL3_min); //
		mapDataNames.put("U L1 min [V]", UL1_avg); //
		mapDataNames.put("U L2 min [V]", UL2_avg); //
		mapDataNames.put("U L3 min [V]", UL3_avg); //
		mapDataNames.put("I *L1 avg [A]", IL1_avg); //
		mapDataNames.put("I *L2 avg [A]", IL2_avg); //
		mapDataNames.put("I *L3 avg [A]", IL3_avg); //
		mapDataNames.put("I *L1 max [A]", IL1_max); //
		mapDataNames.put("I *L2 max [A]", IL2_max); //
		mapDataNames.put("I *L3 max [A]", IL3_max); //
		mapDataNames.put("I *L1 min [A]", IL1_min); //
		mapDataNames.put("I *L2 min [A]", IL2_min); //
		mapDataNames.put("I *L3 min [A]", IL3_min); //
		mapDataNames.put("I *N avg [A]", IN_avg); //
		mapDataNames.put("I *N max [A]", IN_max); //
		mapDataNames.put("I *N min [A]", IN_min); //
		mapDataNames.put("Pst L1 inst [---]", Pst_UL1); //
		mapDataNames.put("Pst L2 inst [---]", Pst_UL2); //
		mapDataNames.put("Pst L3 inst [---]", Pst_UL3); //
		mapDataNames.put("P Σ avg [kW] ", P_total); //
		mapDataNames.put("P Σ max [kW] ", P_max); //
		mapDataNames.put("P Σ min [kW] ", P_min); //

		//todo uzupełnic uninames
		mapDataNames.put("Plt L1 inst [---]", Plt_L1);
		mapDataNames.put("Plt L2 inst [---]", Plt_L2);
		mapDataNames.put("Plt L3 inst [---]", Plt_L3);
		mapDataNames.put("U N-PE avg [V]", U_NPE_avg);
		mapDataNames.put("U N-PE max [V]", U_NPE_max);
		mapDataNames.put("U N-PE min [V]", U_NPE_min);
		mapDataNames.put("U0 Σ avg [V]", U0_avg_total);
		mapDataNames.put("U0 Σ max [V]", U0_max_total);
		mapDataNames.put("U0 Σ min [V]", U0_min_total);
		mapDataNames.put("U1 Σ avg [V]", U1_avg_total);
		mapDataNames.put("U1 Σ max [V]", U1_max_total);
		mapDataNames.put("U1 Σ min [V]", U1_min_total);
		mapDataNames.put("U2 Σ avg [V]", U2_avg_total);
		mapDataNames.put("U2 Σ max [V]", U2_max_total);
		mapDataNames.put("U2 Σ min [V]", U2_min_total);
		mapDataNames.put("I0 Σ avg [A]", I0_avg_total);
		mapDataNames.put("I0 Σ max [A]", I0_max_total);
		mapDataNames.put("I0 Σ min [A]", I0_min_total);
		mapDataNames.put("I1 Σ avg [A]", I1_avg_total);
		mapDataNames.put("I1 Σ max [A]", I1_max_total);
		mapDataNames.put("I1 Σ min [A]", I1_min_total);
		mapDataNames.put("I2 Σ avg [A]", I2_avg_total);
		mapDataNames.put("I2 Σ max [A]", I2_max_total);
		mapDataNames.put("I2 Σ min [A]", I2_min_total);
		mapDataNames.put("PF L1 avg [---]", PF_L1_avg);
		mapDataNames.put("PF L2 avg [---]", PF_L2_avg);
		mapDataNames.put("PF L3 avg [---]", PF_L3_avg);
		mapDataNames.put("P L1 avg [kW]", P_L1_avg);
		mapDataNames.put("P L2 avg [kW]", P_L2_avg);
		mapDataNames.put("P L3 avg [kW]", P_L3_avg);
		mapDataNames.put("P L1 max [kW]", P_L1_max);
		mapDataNames.put("P L2 max [kW]", P_L2_max);
		mapDataNames.put("P L3 max [kW]", P_L3_max);
		mapDataNames.put("P L1 min [kW]", P_L1_min);
		mapDataNames.put("P L2 min [kW]", P_L2_min);
		mapDataNames.put("P L3 min [kW]", P_L3_min);
		mapDataNames.put("Q1 L1 avg [kvar]", Q_L1_avg);
		mapDataNames.put("Q1 L2 avg [kvar]", Q_L2_avg);
		mapDataNames.put("Q1 L3 avg [kvar]", Q_L3_avg);
		mapDataNames.put("Q1 L1 max [kvar]", Q_L1_max);
		mapDataNames.put("Q1 L2 max [kvar]", Q_L2_max);
		mapDataNames.put("Q1 L3 max [kvar]", Q_L3_max);
		mapDataNames.put("Q1 L1 min [kvar]", Q_L1_min);
		mapDataNames.put("Q1 L2 min [kvar]", Q_L2_min);
		mapDataNames.put("Q1 L3 min [kvar]", Q_L3_min);
		mapDataNames.put("Q1 Σ max [kvar]", Q_total_max);
		mapDataNames.put("Q1 Σ min [kvar]", Q_total_min);
		mapDataNames.put("S L1 avg [kVA]", S_L1_avg);
		mapDataNames.put("S L2 avg [kVA]", S_L2_avg);
		mapDataNames.put("S L3 avg [kVA]", S_L3_avg);
		mapDataNames.put("S L1 max [kVA]", S_L1_max);
		mapDataNames.put("S L2 max [kVA]", S_L2_max);
		mapDataNames.put("S L3 max [kVA]", S_L3_max);
		mapDataNames.put("S L1 min [kVA]", S_L1_min);
		mapDataNames.put("S L2 min [kVA]", S_L2_min);
		mapDataNames.put("S L3 min [kVA]", S_L3_min);
		mapDataNames.put("U0/U1 Σ avg [%]",U0_U1_avg);
		mapDataNames.put("U0/U1 Σ max [%]",U0_U1_max);
		mapDataNames.put("U0/U1 Σ min [%]",U0_U1_min);
		mapDataNames.put("U2/U1 Σ avg [%]",U2_U1_avg);
		mapDataNames.put("U2/U1 Σ max [%]",U2_U1_max);
		mapDataNames.put("U2/U1 Σ min [%]",U2_U1_min);
		mapDataNames.put("I0/I1 Σ avg [%]",I0_I1_avg);
		mapDataNames.put("I0/I1 Σ max [%]",I0_I1_max);
		mapDataNames.put("I0/I1 Σ min [%]",I0_I1_min);
		mapDataNames.put("I2/I1 Σ avg [%]",I2_I1_avg);
		mapDataNames.put("I2/I1 Σ max [%]",I2_I1_max);
		mapDataNames.put("I2/I1 Σ min [%]",I2_I1_min);
	}
	static {
		mapHarmonicNames.put("Date", Date);
		mapHarmonicNames.put("Time (UTC±0)", Time);
		mapHarmonicNames.put("Time (UTC+1)", Time);
		mapHarmonicNames.put("Time (UTC+2)", Time);
		mapHarmonicNames.put("Time (UTC+3)", Time);
		mapHarmonicNames.put("Time (UTC+4)", Time);
		mapHarmonicNames.put("Time (UTC+5)", Time);
		mapHarmonicNames.put("Time (UTC+6)", Time);
		mapHarmonicNames.put("Time (UTC+7)", Time);
		mapHarmonicNames.put("Time (UTC+8)", Time);
		mapHarmonicNames.put("Time (UTC+9)", Time);
		mapHarmonicNames.put("Time (UTC+10)", Time);
		mapHarmonicNames.put("Time (UTC+11)", Time);
		mapHarmonicNames.put("Time (UTC+12)", Time);
		mapHarmonicNames.put("Time (UTC+13)", Time);
		mapHarmonicNames.put("Time (UTC-1)", Time);
		mapHarmonicNames.put("Time (UTC-2)", Time);
		mapHarmonicNames.put("Time (UTC-3)", Time);
		mapHarmonicNames.put("Time (UTC-4)", Time);
		mapHarmonicNames.put("Time (UTC-5)", Time);
		mapHarmonicNames.put("Time (UTC-6)", Time);
		mapHarmonicNames.put("Time (UTC-7)", Time);
		mapHarmonicNames.put("Time (UTC-8)", Time);
		mapHarmonicNames.put("Time (UTC-9)", Time);
		mapHarmonicNames.put("Time (UTC-10)", Time);
		mapHarmonicNames.put("Time (UTC-11)", Time);
		mapHarmonicNames.put("P", Flag_P);
		mapHarmonicNames.put("G", Flag_G);
		mapHarmonicNames.put("E", Flag_E);
		mapHarmonicNames.put("T", Flag_T);
		mapHarmonicNames.put("A", Flag_A);
		mapHarmonicNames.put("THD U L1 avg [%]",  SONEL_THD_L1);
		mapHarmonicNames.put("THD U L2 avg [%]",  SONEL_THD_L2);
		mapHarmonicNames.put("THD U L3 avg [%]",  SONEL_THD_L3);
		mapHarmonicNames.put("U H 1 L1 avg [V]",  SONEL_H1_UL1);
		mapHarmonicNames.put("U H 1 L2 avg [V]",  SONEL_H1_UL2);
		mapHarmonicNames.put("U H 1 L3 avg [V]",  SONEL_H1_UL3);
		mapHarmonicNames.put("U H 2 L1 avg [V]",  SONEL_H2_UL1);
		mapHarmonicNames.put("U H 2 L2 avg [V]",  SONEL_H2_UL2);
		mapHarmonicNames.put("U H 2 L3 avg [V]",  SONEL_H2_UL3);
		mapHarmonicNames.put("U H 3 L1 avg [V]",  SONEL_H3_UL1);
		mapHarmonicNames.put("U H 3 L2 avg [V]",  SONEL_H3_UL2);
		mapHarmonicNames.put("U H 3 L3 avg [V]",  SONEL_H3_UL3);
		mapHarmonicNames.put("U H 4 L1 avg [V]",  SONEL_H4_UL1);
		mapHarmonicNames.put("U H 4 L2 avg [V]",  SONEL_H4_UL2);
		mapHarmonicNames.put("U H 4 L3 avg [V]",  SONEL_H4_UL3);
		mapHarmonicNames.put("U H 5 L1 avg [V]",  SONEL_H5_UL1);
		mapHarmonicNames.put("U H 5 L2 avg [V]",  SONEL_H5_UL2);
		mapHarmonicNames.put("U H 5 L3 avg [V]",  SONEL_H5_UL3);
		mapHarmonicNames.put("U H 6 L1 avg [V]",  SONEL_H6_UL1);
		mapHarmonicNames.put("U H 6 L2 avg [V]",  SONEL_H6_UL2);
		mapHarmonicNames.put("U H 6 L3 avg [V]",  SONEL_H6_UL3);
		mapHarmonicNames.put("U H 7 L1 avg [V]",  SONEL_H7_UL1);
		mapHarmonicNames.put("U H 7 L2 avg [V]",  SONEL_H7_UL2);
		mapHarmonicNames.put("U H 7 L3 avg [V]",  SONEL_H7_UL3);
		mapHarmonicNames.put("U H 8 L1 avg [V]",  SONEL_H8_UL1);
		mapHarmonicNames.put("U H 8 L2 avg [V]",  SONEL_H8_UL2);
		mapHarmonicNames.put("U H 8 L3 avg [V]",  SONEL_H8_UL3);
		mapHarmonicNames.put("U H 9 L1 avg [V]",  SONEL_H9_UL1);
		mapHarmonicNames.put("U H 9 L2 avg [V]",  SONEL_H9_UL2);
		mapHarmonicNames.put("U H 9 L3 avg [V]",  SONEL_H9_UL3);
		mapHarmonicNames.put("U H 10 L1 avg [V]", SONEL_H10_UL1);
		mapHarmonicNames.put("U H 10 L2 avg [V]", SONEL_H10_UL2);
		mapHarmonicNames.put("U H 10 L3 avg [V]", SONEL_H10_UL3);
		mapHarmonicNames.put("U H 11 L1 avg [V]", SONEL_H11_UL1);
		mapHarmonicNames.put("U H 11 L2 avg [V]", SONEL_H11_UL2);
		mapHarmonicNames.put("U H 11 L3 avg [V]", SONEL_H11_UL3);
		mapHarmonicNames.put("U H 12 L1 avg [V]", SONEL_H12_UL1);
		mapHarmonicNames.put("U H 12 L2 avg [V]", SONEL_H12_UL2);
		mapHarmonicNames.put("U H 12 L3 avg [V]", SONEL_H12_UL3);
		mapHarmonicNames.put("U H 13 L1 avg [V]", SONEL_H13_UL1);
		mapHarmonicNames.put("U H 13 L2 avg [V]", SONEL_H13_UL2);
		mapHarmonicNames.put("U H 13 L3 avg [V]", SONEL_H13_UL3);
		mapHarmonicNames.put("U H 14 L1 avg [V]", SONEL_H14_UL1);
		mapHarmonicNames.put("U H 14 L2 avg [V]", SONEL_H14_UL2);
		mapHarmonicNames.put("U H 14 L3 avg [V]", SONEL_H14_UL3);
		mapHarmonicNames.put("U H 15 L1 avg [V]", SONEL_H15_UL1);
		mapHarmonicNames.put("U H 15 L2 avg [V]", SONEL_H15_UL2);
		mapHarmonicNames.put("U H 15 L3 avg [V]", SONEL_H15_UL3);
		mapHarmonicNames.put("U H 16 L1 avg [V]", SONEL_H16_UL1);
		mapHarmonicNames.put("U H 16 L2 avg [V]", SONEL_H16_UL2);
		mapHarmonicNames.put("U H 16 L3 avg [V]", SONEL_H16_UL3);
		mapHarmonicNames.put("U H 17 L1 avg [V]", SONEL_H17_UL1);
		mapHarmonicNames.put("U H 17 L2 avg [V]", SONEL_H17_UL2);
		mapHarmonicNames.put("U H 17 L3 avg [V]", SONEL_H17_UL3);
		mapHarmonicNames.put("U H 18 L1 avg [V]", SONEL_H18_UL1);
		mapHarmonicNames.put("U H 18 L2 avg [V]", SONEL_H18_UL2);
		mapHarmonicNames.put("U H 18 L3 avg [V]", SONEL_H18_UL3);
		mapHarmonicNames.put("U H 19 L1 avg [V]", SONEL_H19_UL1);
		mapHarmonicNames.put("U H 19 L2 avg [V]", SONEL_H19_UL2);
		mapHarmonicNames.put("U H 19 L3 avg [V]", SONEL_H19_UL3);
		mapHarmonicNames.put("U H 20 L1 avg [V]", SONEL_H20_UL1);
		mapHarmonicNames.put("U H 20 L2 avg [V]", SONEL_H20_UL2);
		mapHarmonicNames.put("U H 20 L3 avg [V]", SONEL_H20_UL3);
		mapHarmonicNames.put("U H 21 L1 avg [V]", SONEL_H21_UL1);
		mapHarmonicNames.put("U H 21 L2 avg [V]", SONEL_H21_UL2);
		mapHarmonicNames.put("U H 21 L3 avg [V]", SONEL_H21_UL3);
		mapHarmonicNames.put("U H 22 L1 avg [V]", SONEL_H22_UL1);
		mapHarmonicNames.put("U H 22 L2 avg [V]", SONEL_H22_UL2);
		mapHarmonicNames.put("U H 22 L3 avg [V]", SONEL_H22_UL3);
		mapHarmonicNames.put("U H 23 L1 avg [V]", SONEL_H23_UL1);
		mapHarmonicNames.put("U H 23 L2 avg [V]", SONEL_H23_UL2);
		mapHarmonicNames.put("U H 23 L3 avg [V]", SONEL_H23_UL3);
		mapHarmonicNames.put("U H 24 L1 avg [V]", SONEL_H24_UL1);
		mapHarmonicNames.put("U H 24 L2 avg [V]", SONEL_H24_UL2);
		mapHarmonicNames.put("U H 24 L3 avg [V]", SONEL_H24_UL3);
		mapHarmonicNames.put("U H 25 L1 avg [V]", SONEL_H25_UL1);
		mapHarmonicNames.put("U H 25 L2 avg [V]", SONEL_H25_UL2);
		mapHarmonicNames.put("U H 25 L3 avg [V]", SONEL_H25_UL3);
		mapHarmonicNames.put("U H 26 L1 avg [V]", SONEL_H26_UL1);
		mapHarmonicNames.put("U H 26 L2 avg [V]", SONEL_H26_UL2);
		mapHarmonicNames.put("U H 26 L3 avg [V]", SONEL_H26_UL3);
		mapHarmonicNames.put("U H 27 L1 avg [V]", SONEL_H27_UL1);
		mapHarmonicNames.put("U H 27 L2 avg [V]", SONEL_H27_UL2);
		mapHarmonicNames.put("U H 27 L3 avg [V]", SONEL_H27_UL3);
		mapHarmonicNames.put("U H 28 L1 avg [V]", SONEL_H28_UL1);
		mapHarmonicNames.put("U H 28 L2 avg [V]", SONEL_H28_UL2);
		mapHarmonicNames.put("U H 28 L3 avg [V]", SONEL_H28_UL3);
		mapHarmonicNames.put("U H 29 L1 avg [V]", SONEL_H29_UL1);
		mapHarmonicNames.put("U H 29 L2 avg [V]", SONEL_H29_UL2);
		mapHarmonicNames.put("U H 29 L3 avg [V]", SONEL_H29_UL3);
		mapHarmonicNames.put("U H 30 L1 avg [V]", SONEL_H30_UL1);
		mapHarmonicNames.put("U H 30 L2 avg [V]", SONEL_H30_UL2);
		mapHarmonicNames.put("U H 30 L3 avg [V]", SONEL_H30_UL3);
		mapHarmonicNames.put("U H 31 L1 avg [V]", SONEL_H31_UL1);
		mapHarmonicNames.put("U H 31 L2 avg [V]", SONEL_H31_UL2);
		mapHarmonicNames.put("U H 31 L3 avg [V]", SONEL_H31_UL3);
		mapHarmonicNames.put("U H 32 L1 avg [V]", SONEL_H32_UL1);
		mapHarmonicNames.put("U H 32 L2 avg [V]", SONEL_H32_UL2);
		mapHarmonicNames.put("U H 32 L3 avg [V]", SONEL_H32_UL3);
		mapHarmonicNames.put("U H 33 L1 avg [V]", SONEL_H33_UL1);
		mapHarmonicNames.put("U H 33 L2 avg [V]", SONEL_H33_UL2);
		mapHarmonicNames.put("U H 33 L3 avg [V]", SONEL_H33_UL3);
		mapHarmonicNames.put("U H 34 L1 avg [V]", SONEL_H34_UL1);
		mapHarmonicNames.put("U H 34 L2 avg [V]", SONEL_H34_UL2);
		mapHarmonicNames.put("U H 34 L3 avg [V]", SONEL_H34_UL3);
		mapHarmonicNames.put("U H 35 L1 avg [V]", SONEL_H35_UL1);
		mapHarmonicNames.put("U H 35 L2 avg [V]", SONEL_H35_UL2);
		mapHarmonicNames.put("U H 35 L3 avg [V]", SONEL_H35_UL3);
		mapHarmonicNames.put("U H 36 L1 avg [V]", SONEL_H36_UL1);
		mapHarmonicNames.put("U H 36 L2 avg [V]", SONEL_H36_UL2);
		mapHarmonicNames.put("U H 36 L3 avg [V]", SONEL_H36_UL3);
		mapHarmonicNames.put("U H 37 L1 avg [V]", SONEL_H37_UL1);
		mapHarmonicNames.put("U H 37 L2 avg [V]", SONEL_H37_UL2);
		mapHarmonicNames.put("U H 37 L3 avg [V]", SONEL_H37_UL3);
		mapHarmonicNames.put("U H 38 L1 avg [V]", SONEL_H38_UL1);
		mapHarmonicNames.put("U H 38 L2 avg [V]", SONEL_H38_UL2);
		mapHarmonicNames.put("U H 38 L3 avg [V]", SONEL_H38_UL3);
		mapHarmonicNames.put("U H 39 L1 avg [V]", SONEL_H39_UL1);
		mapHarmonicNames.put("U H 39 L2 avg [V]", SONEL_H39_UL2);
		mapHarmonicNames.put("U H 39 L3 avg [V]", SONEL_H39_UL3);
		mapHarmonicNames.put("U H 40 L1 avg [V]", SONEL_H40_UL1);
		mapHarmonicNames.put("U H 40 L2 avg [V]", SONEL_H40_UL2);
		mapHarmonicNames.put("U H 40 L3 avg [V]", SONEL_H40_UL3);
		mapHarmonicNames.put("U H 41 L1 avg [V]", SONEL_H41_UL1);
		mapHarmonicNames.put("U H 41 L2 avg [V]", SONEL_H41_UL2);
		mapHarmonicNames.put("U H 41 L3 avg [V]", SONEL_H41_UL3);
		mapHarmonicNames.put("U H 42 L1 avg [V]", SONEL_H42_UL1);
		mapHarmonicNames.put("U H 42 L2 avg [V]", SONEL_H42_UL2);
		mapHarmonicNames.put("U H 42 L3 avg [V]", SONEL_H42_UL3);
		mapHarmonicNames.put("U H 43 L1 avg [V]", SONEL_H43_UL1);
		mapHarmonicNames.put("U H 43 L2 avg [V]", SONEL_H43_UL2);
		mapHarmonicNames.put("U H 43 L3 avg [V]", SONEL_H43_UL3);
		mapHarmonicNames.put("U H 44 L1 avg [V]", SONEL_H44_UL1);
		mapHarmonicNames.put("U H 44 L2 avg [V]", SONEL_H44_UL2);
		mapHarmonicNames.put("U H 44 L3 avg [V]", SONEL_H44_UL3);
		mapHarmonicNames.put("U H 45 L1 avg [V]", SONEL_H45_UL1);
		mapHarmonicNames.put("U H 45 L2 avg [V]", SONEL_H45_UL2);
		mapHarmonicNames.put("U H 45 L3 avg [V]", SONEL_H45_UL3);
		mapHarmonicNames.put("U H 46 L1 avg [V]", SONEL_H46_UL1);
		mapHarmonicNames.put("U H 46 L2 avg [V]", SONEL_H46_UL2);
		mapHarmonicNames.put("U H 46 L3 avg [V]", SONEL_H46_UL3);
		mapHarmonicNames.put("U H 47 L1 avg [V]", SONEL_H47_UL1);
		mapHarmonicNames.put("U H 47 L2 avg [V]", SONEL_H47_UL2);
		mapHarmonicNames.put("U H 47 L3 avg [V]", SONEL_H47_UL3);
		mapHarmonicNames.put("U H 48 L1 avg [V]",SONEL_H48_UL1);
		mapHarmonicNames.put("U H 48 L2 avg [V]",SONEL_H48_UL2);
		mapHarmonicNames.put("U H 48 L3 avg [V]",SONEL_H48_UL3);
		mapHarmonicNames.put("U H 49 L1 avg [V]",SONEL_H49_UL1);
		mapHarmonicNames.put("U H 49 L2 avg [V]",SONEL_H49_UL2);
		mapHarmonicNames.put("U H 49 L3 avg [V]",SONEL_H49_UL3);
		mapHarmonicNames.put("U H 50 L1 avg [V]",SONEL_H50_UL1);
		mapHarmonicNames.put("U H 50 L2 avg [V]",SONEL_H50_UL2);
		mapHarmonicNames.put("U H 50 L3 avg [V]",SONEL_H50_UL3);

	}

	public static List<UniNames> parseNames(List<String> names) {
		List<UniNames> uniNamesList = new ArrayList<>();
		names.forEach(name -> {
			if (mapDataNames.containsKey(name.trim())) {
				uniNamesList.add(mapDataNames.get(name.trim()));
			}
		});
		return uniNamesList;
	}

	public static List<UniNames> parseHarmonicsNames(List<String> names) {
		List<UniNames> uniNamesList = new ArrayList<>();
		names.forEach(name -> {
			if (mapHarmonicNames.containsKey(name.trim())) {
				uniNamesList.add(mapHarmonicNames.get(name.trim()));
			}
		});
		return uniNamesList;
	}

	public static LocalDate parseDate(String stringDate) throws ApplicationException {
		AtomicReference<LocalDate> parsedDate = new AtomicReference<>();
		final boolean isMatched = dateFormatPatterns.stream()
				.anyMatch(pattern -> {
					try {
						DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
						LocalDate parse = LocalDate.parse(stringDate, formatter);
						parsedDate.set(parse);
						return true;
					} catch (DateTimeParseException e) {
						return false;
					}
				});
		if (isMatched) return parsedDate.get();
		else throw new ApplicationException("error.parseDate");
	}

	public static LocalTime parseTime(String time) {
		final LocalTime parse = LocalTime.parse(time);
		return LocalTime.of(parse.getHour(), parse.getMinute());
	}

	public static String parseFlag(String flag) {
		return flag.trim().isEmpty() ? " " : "x";
	}

	public static double parseDouble(String record) throws ParseException {
		double d;
		if (record.contains(",")) {
			NumberFormat format = NumberFormat.getInstance(Locale.FRENCH);
			Number number = format.parse(record);
			d = number.doubleValue();
		} else {
			d = Double.parseDouble(record);
		}
		return d;
	}
}
