package agh.inzapp.inzynierka.utils.parsers;

import agh.inzapp.inzynierka.models.enums.UniNames;
import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
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
	}

	static {
//		mapDataNames.put("Date", Date);
//		mapDataNames.put("Time", Time);
//		mapDataNames.put("Flagging", Flag);
//		mapDataNames.put("U12_[V]", U12_avg);
//		mapDataNames.put("U23_[V]", U23_avg);
//		mapDataNames.put("U31_[V]", U31_avg);
//		mapDataNames.put("U12_max_[V]", U12_max);
//		mapDataNames.put("U23_max_[V]", U23_max);
//		mapDataNames.put("U31_max_[V]", U31_max);
//		mapDataNames.put("U12_min_[V]", U12_min);
//		mapDataNames.put("U23_min_[V]", U23_min);
//		mapDataNames.put("U31_min_[V]", U31_min);
//		mapDataNames.put("IL1_[A]", IL1_avg);
//		mapDataNames.put("IL2_[A]", IL2_avg);
//		mapDataNames.put("IL3_[A]", IL3_avg);
//		mapDataNames.put("I_Neutral_[A]", IN_avg);
//		mapDataNames.put("IL1_max_[A]", IL1_max);
//		mapDataNames.put("IL2_max_[A]", IL2_max);
//		mapDataNames.put("IL3_max_[A]", IL3_max);
//		mapDataNames.put("I_Neutral_max_[A]", IN_max);
//		mapDataNames.put("IL1_min_[A]", IL1_min);
//		mapDataNames.put("IL2_min_[A]", IL2_min);
//		mapDataNames.put("IL3_min_[A]", IL3_min);
//		mapDataNames.put("I_Neutral_min_[A]", IN_min);
//		mapDataNames.put("Pst_U12", Pst_U12);
//		mapDataNames.put("Pst_U23", Pst_U23);
//		mapDataNames.put("Pst_U31", Pst_U31);
//		mapDataNames.put("Plt_U12", Plt_U12);
//		mapDataNames.put("Plt_U23", Plt_U23);
//		mapDataNames.put("Plt_U31", Plt_U31);
//		mapDataNames.put("Unbalance_-_Voltage_(_Neg./Pos.)_[%]", Unbalanced_Voltage);
//		mapDataNames.put("Unbalance_-_Current_(_Neg./Pos.)_[%]", Unbalanced_Current);
//		mapDataNames.put("F_[Hz]", f);
//		mapDataNames.put("P_total_min_[W]", P_min);
//		mapDataNames.put("P_total_[W]", P_total);
//		mapDataNames.put("P_total_[W]'_abs", P_abs);
//		mapDataNames.put("P_total_max_[W]", P_max);
//		mapDataNames.put("S_total_min__[VA]", S_min);
//		mapDataNames.put("S_total_[VA]", S_total);
//		mapDataNames.put("S_total_max_[VA]", S_max);
//		mapDataNames.put("PF_total", PF_total);
//		mapDataNames.put("PF_total'_abs", PF_total_abs);
//		mapDataNames.put("cos(phi)", cos_phi);
//		mapDataNames.put("tan(phi)", tan_phi);
//		mapDataNames.put("QV_total_[Var]", Q_total);
//		mapDataNames.put("UL1_[V]", UL1_avg);
//		mapDataNames.put("UL2_[V]", UL2_avg);
//		mapDataNames.put("UL3_[V]", UL3_avg);
//		mapDataNames.put("UL1_max_[V]", UL1_max);
//		mapDataNames.put("UL2_max_[V]", UL2_max);
//		mapDataNames.put("UL3_max_[V]", UL3_max);
//		mapDataNames.put("UL1_min_[V]", UL1_min);
//		mapDataNames.put("UL2_min_[V]", UL2_min);
//		mapDataNames.put("UL3_min_[V]", UL3_min);
//		mapDataNames.put("Pst_UL1", Pst_UL1);
//		mapDataNames.put("Pst_UL2", Pst_UL2);
//		mapDataNames.put("Pst_UL3", Pst_UL3);
	}

	static {
		mapHarmonicNames.put("Date", Date);
		mapHarmonicNames.put("Time", Time);
		mapHarmonicNames.put("Flag_P", Flag_P);
		mapHarmonicNames.put("Flag_G", Flag_G);
		mapHarmonicNames.put("Flag_E", Flag_E);
		mapHarmonicNames.put("Flag_T", Flag_T);
		mapHarmonicNames.put("Flag_A", Flag_A);
		mapHarmonicNames.put("THD U L1 avg [%]", SONEL_THD_L1);
		mapHarmonicNames.put("THD U L2 avg [%]", SONEL_THD_L2);
		mapHarmonicNames.put("THD U L3 avg [%]", SONEL_THD_L3);
		mapHarmonicNames.put("U H 1 L1 avg [V]", SONEL_H1_UL1);
		mapHarmonicNames.put("U H 2 L1 avg [V]", SONEL_H2_UL1);
		mapHarmonicNames.put("U H 3 L1 avg [V]", SONEL_H3_UL1);
		mapHarmonicNames.put("U H 4 L1 avg [V]", SONEL_H4_UL1);
		mapHarmonicNames.put("U H 5 L1 avg [V]", SONEL_H5_UL1);
		mapHarmonicNames.put("U H 6 L1 avg [V]", SONEL_H6_UL1);
		mapHarmonicNames.put("U H 7 L1 avg [V]",  SONEL_H7_UL1);
		mapHarmonicNames.put("U H 8 L1 avg [V]",  SONEL_H8_UL1);
		mapHarmonicNames.put("U H 9 L1 avg [V]",  SONEL_H9_UL1);
		mapHarmonicNames.put("U H 10 L1 avg [V]", SONEL_H10_UL1);
		mapHarmonicNames.put("U H 11 L1 avg [V]", SONEL_H11_UL1);
		mapHarmonicNames.put("U H 12 L1 avg [V]", SONEL_H12_UL1);
		mapHarmonicNames.put("U H 13 L1 avg [V]", SONEL_H13_UL1);
		mapHarmonicNames.put("U H 14 L1 avg [V]", SONEL_H14_UL1);
		mapHarmonicNames.put("U H 15 L1 avg [V]", SONEL_H15_UL1);
		mapHarmonicNames.put("U H 16 L1 avg [V]", SONEL_H16_UL1);
		mapHarmonicNames.put("U H 17 L1 avg [V]", SONEL_H17_UL1);
		mapHarmonicNames.put("U H 18 L1 avg [V]", SONEL_H18_UL1);
		mapHarmonicNames.put("U H 19 L1 avg [V]", SONEL_H19_UL1);
		mapHarmonicNames.put("U H 20 L1 avg [V]", SONEL_H20_UL1);
		mapHarmonicNames.put("U H 21 L1 avg [V]", SONEL_H21_UL1);
		mapHarmonicNames.put("U H 22 L1 avg [V]", SONEL_H22_UL1);
		mapHarmonicNames.put("U H 23 L1 avg [V]", SONEL_H23_UL1);
		mapHarmonicNames.put("U H 24 L1 avg [V]", SONEL_H24_UL1);
		mapHarmonicNames.put("U H 25 L1 avg [V]", SONEL_H25_UL1);
		mapHarmonicNames.put("U H 26 L1 avg [V]", SONEL_H26_UL1);
		mapHarmonicNames.put("U H 27 L1 avg [V]", SONEL_H27_UL1);
		mapHarmonicNames.put("U H 28 L1 avg [V]", SONEL_H28_UL1);
		mapHarmonicNames.put("U H 29 L1 avg [V]", SONEL_H29_UL1);
		mapHarmonicNames.put("U H 30 L1 avg [V]", SONEL_H30_UL1);
		mapHarmonicNames.put("U H 31 L1 avg [V]", SONEL_H31_UL1);
		mapHarmonicNames.put("U H 32 L1 avg [V]", SONEL_H32_UL1);
		mapHarmonicNames.put("U H 33 L1 avg [V]", SONEL_H33_UL1);
		mapHarmonicNames.put("U H 34 L1 avg [V]", SONEL_H34_UL1);
		mapHarmonicNames.put("U H 35 L1 avg [V]", SONEL_H35_UL1);
		mapHarmonicNames.put("U H 36 L1 avg [V]", SONEL_H36_UL1);
		mapHarmonicNames.put("U H 37 L1 avg [V]", SONEL_H37_UL1);
		mapHarmonicNames.put("U H 38 L1 avg [V]", SONEL_H38_UL1);
		mapHarmonicNames.put("U H 39 L1 avg [V]", SONEL_H39_UL1);
		mapHarmonicNames.put("U H 40 L1 avg [V]", SONEL_H40_UL1);
		mapHarmonicNames.put("U H 41 L1 avg [V]", SONEL_H41_UL1);
		mapHarmonicNames.put("U H 42 L1 avg [V]", SONEL_H42_UL1);
		mapHarmonicNames.put("U H 43 L1 avg [V]", SONEL_H43_UL1);
		mapHarmonicNames.put("U H 44 L1 avg [V]", SONEL_H44_UL1);
		mapHarmonicNames.put("U H 45 L1 avg [V]", SONEL_H45_UL1);
		mapHarmonicNames.put("U H 46 L1 avg [V]", SONEL_H46_UL1);
		mapHarmonicNames.put("U H 47 L1 avg [V]", SONEL_H47_UL1);
		mapHarmonicNames.put("U H 48 L1 avg [V]",SONEL_H48_UL1);
		mapHarmonicNames.put("U H 49 L1 avg [V]",SONEL_H49_UL1);
		mapHarmonicNames.put("U H 50 L1 avg [V]",SONEL_H50_UL1);
		mapHarmonicNames.put("U H 1 L2 avg [V]", SONEL_H1_UL2);
		mapHarmonicNames.put("U H 2 L2 avg [V]", SONEL_H2_UL2);
		mapHarmonicNames.put("U H 3 L2 avg [V]", SONEL_H3_UL2);
		mapHarmonicNames.put("U H 4 L2 avg [V]", SONEL_H4_UL2);
		mapHarmonicNames.put("U H 5 L2 avg [V]", SONEL_H5_UL2);
		mapHarmonicNames.put("U H 6 L2 avg [V]", SONEL_H6_UL2);
		mapHarmonicNames.put("U H 7 L2 avg [V]",  SONEL_H7_UL2);
		mapHarmonicNames.put("U H 8 L2 avg [V]",  SONEL_H8_UL2);
		mapHarmonicNames.put("U H 9 L2 avg [V]",  SONEL_H9_UL2);
		mapHarmonicNames.put("U H 10 L2 avg [V]", SONEL_H10_UL2);
		mapHarmonicNames.put("U H 11 L2 avg [V]", SONEL_H11_UL2);
		mapHarmonicNames.put("U H 12 L2 avg [V]", SONEL_H12_UL2);
		mapHarmonicNames.put("U H 13 L2 avg [V]", SONEL_H13_UL2);
		mapHarmonicNames.put("U H 14 L2 avg [V]", SONEL_H14_UL2);
		mapHarmonicNames.put("U H 15 L2 avg [V]", SONEL_H15_UL2);
		mapHarmonicNames.put("U H 16 L2 avg [V]", SONEL_H16_UL2);
		mapHarmonicNames.put("U H 17 L2 avg [V]", SONEL_H17_UL2);
		mapHarmonicNames.put("U H 18 L2 avg [V]", SONEL_H18_UL2);
		mapHarmonicNames.put("U H 19 L2 avg [V]", SONEL_H19_UL2);
		mapHarmonicNames.put("U H 20 L2 avg [V]", SONEL_H20_UL2);
		mapHarmonicNames.put("U H 21 L2 avg [V]", SONEL_H21_UL2);
		mapHarmonicNames.put("U H 22 L2 avg [V]", SONEL_H22_UL2);
		mapHarmonicNames.put("U H 23 L2 avg [V]", SONEL_H23_UL2);
		mapHarmonicNames.put("U H 24 L2 avg [V]", SONEL_H24_UL2);
		mapHarmonicNames.put("U H 25 L2 avg [V]", SONEL_H25_UL2);
		mapHarmonicNames.put("U H 26 L2 avg [V]", SONEL_H26_UL2);
		mapHarmonicNames.put("U H 27 L2 avg [V]", SONEL_H27_UL2);
		mapHarmonicNames.put("U H 28 L2 avg [V]", SONEL_H28_UL2);
		mapHarmonicNames.put("U H 29 L2 avg [V]", SONEL_H29_UL2);
		mapHarmonicNames.put("U H 30 L2 avg [V]", SONEL_H30_UL2);
		mapHarmonicNames.put("U H 31 L2 avg [V]", SONEL_H31_UL2);
		mapHarmonicNames.put("U H 32 L2 avg [V]", SONEL_H32_UL2);
		mapHarmonicNames.put("U H 33 L2 avg [V]", SONEL_H33_UL2);
		mapHarmonicNames.put("U H 34 L2 avg [V]", SONEL_H34_UL2);
		mapHarmonicNames.put("U H 35 L2 avg [V]", SONEL_H35_UL2);
		mapHarmonicNames.put("U H 36 L2 avg [V]", SONEL_H36_UL2);
		mapHarmonicNames.put("U H 37 L2 avg [V]", SONEL_H37_UL2);
		mapHarmonicNames.put("U H 38 L2 avg [V]", SONEL_H38_UL2);
		mapHarmonicNames.put("U H 39 L2 avg [V]", SONEL_H39_UL2);
		mapHarmonicNames.put("U H 40 L2 avg [V]", SONEL_H40_UL2);
		mapHarmonicNames.put("U H 41 L2 avg [V]", SONEL_H41_UL2);
		mapHarmonicNames.put("U H 42 L2 avg [V]", SONEL_H42_UL2);
		mapHarmonicNames.put("U H 43 L2 avg [V]", SONEL_H43_UL2);
		mapHarmonicNames.put("U H 44 L2 avg [V]", SONEL_H44_UL2);
		mapHarmonicNames.put("U H 45 L2 avg [V]", SONEL_H45_UL2);
		mapHarmonicNames.put("U H 46 L2 avg [V]", SONEL_H46_UL2);
		mapHarmonicNames.put("U H 47 L2 avg [V]", SONEL_H47_UL2);
		mapHarmonicNames.put("U H 48 L2 avg [V]",SONEL_H48_UL2);
		mapHarmonicNames.put("U H 49 L2 avg [V]",SONEL_H49_UL2);
		mapHarmonicNames.put("U H 50 L2 avg [V]",SONEL_H50_UL2);
		mapHarmonicNames.put("U H 1 L3 avg [V]", SONEL_H1_UL3);
		mapHarmonicNames.put("U H 2 L3 avg [V]", SONEL_H2_UL3);
		mapHarmonicNames.put("U H 3 L3 avg [V]", SONEL_H3_UL3);
		mapHarmonicNames.put("U H 4 L3 avg [V]", SONEL_H4_UL3);
		mapHarmonicNames.put("U H 5 L3 avg [V]", SONEL_H5_UL3);
		mapHarmonicNames.put("U H 6 L3 avg [V]", SONEL_H6_UL3);
		mapHarmonicNames.put("U H 7 L3 avg [V]",  SONEL_H7_UL3);
		mapHarmonicNames.put("U H 8 L3 avg [V]",  SONEL_H8_UL3);
		mapHarmonicNames.put("U H 9 L3 avg [V]",  SONEL_H9_UL3);
		mapHarmonicNames.put("U H 10 L3 avg [V]", SONEL_H10_UL3);
		mapHarmonicNames.put("U H 11 L3 avg [V]", SONEL_H11_UL3);
		mapHarmonicNames.put("U H 12 L3 avg [V]", SONEL_H12_UL3);
		mapHarmonicNames.put("U H 13 L3 avg [V]", SONEL_H13_UL3);
		mapHarmonicNames.put("U H 14 L3 avg [V]", SONEL_H14_UL3);
		mapHarmonicNames.put("U H 15 L3 avg [V]", SONEL_H15_UL3);
		mapHarmonicNames.put("U H 16 L3 avg [V]", SONEL_H16_UL3);
		mapHarmonicNames.put("U H 17 L3 avg [V]", SONEL_H17_UL3);
		mapHarmonicNames.put("U H 18 L3 avg [V]", SONEL_H18_UL3);
		mapHarmonicNames.put("U H 19 L3 avg [V]", SONEL_H19_UL3);
		mapHarmonicNames.put("U H 20 L3 avg [V]", SONEL_H20_UL3);
		mapHarmonicNames.put("U H 21 L3 avg [V]", SONEL_H21_UL3);
		mapHarmonicNames.put("U H 22 L3 avg [V]", SONEL_H22_UL3);
		mapHarmonicNames.put("U H 23 L3 avg [V]", SONEL_H23_UL3);
		mapHarmonicNames.put("U H 24 L3 avg [V]", SONEL_H24_UL3);
		mapHarmonicNames.put("U H 25 L3 avg [V]", SONEL_H25_UL3);
		mapHarmonicNames.put("U H 26 L3 avg [V]", SONEL_H26_UL3);
		mapHarmonicNames.put("U H 27 L3 avg [V]", SONEL_H27_UL3);
		mapHarmonicNames.put("U H 28 L3 avg [V]", SONEL_H28_UL3);
		mapHarmonicNames.put("U H 29 L3 avg [V]", SONEL_H29_UL3);
		mapHarmonicNames.put("U H 30 L3 avg [V]", SONEL_H30_UL3);
		mapHarmonicNames.put("U H 31 L3 avg [V]", SONEL_H31_UL3);
		mapHarmonicNames.put("U H 32 L3 avg [V]", SONEL_H32_UL3);
		mapHarmonicNames.put("U H 33 L3 avg [V]", SONEL_H33_UL3);
		mapHarmonicNames.put("U H 34 L3 avg [V]", SONEL_H34_UL3);
		mapHarmonicNames.put("U H 35 L3 avg [V]", SONEL_H35_UL3);
		mapHarmonicNames.put("U H 36 L3 avg [V]", SONEL_H36_UL3);
		mapHarmonicNames.put("U H 37 L3 avg [V]", SONEL_H37_UL3);
		mapHarmonicNames.put("U H 38 L3 avg [V]", SONEL_H38_UL3);
		mapHarmonicNames.put("U H 39 L3 avg [V]", SONEL_H39_UL3);
		mapHarmonicNames.put("U H 40 L3 avg [V]", SONEL_H40_UL3);
		mapHarmonicNames.put("U H 41 L3 avg [V]", SONEL_H41_UL3);
		mapHarmonicNames.put("U H 42 L3 avg [V]", SONEL_H42_UL3);
		mapHarmonicNames.put("U H 43 L3 avg [V]", SONEL_H43_UL3);
		mapHarmonicNames.put("U H 44 L3 avg [V]", SONEL_H44_UL3);
		mapHarmonicNames.put("U H 45 L3 avg [V]", SONEL_H45_UL3);
		mapHarmonicNames.put("U H 46 L3 avg [V]", SONEL_H46_UL3);
		mapHarmonicNames.put("U H 47 L3 avg [V]", SONEL_H47_UL3);
		mapHarmonicNames.put("U H 48 L3 avg [V]", SONEL_H48_UL3);
		mapHarmonicNames.put("U H 49 L3 avg [V]", SONEL_H49_UL3);
		mapHarmonicNames.put("U H 50 L3 avg [V]", SONEL_H50_UL3);
	}

	public static List<UniNames> parseNames(List<String> names) {
		List<UniNames> uniNamesList = new ArrayList<>();
		names.forEach(name -> {
			if (mapDataNames.containsKey(name)) {
				uniNamesList.add(mapDataNames.get(name));
			}
		});
		return uniNamesList;
	}

	public static List<UniNames> parseHarmonicsNames(List<String> names) {
		List<UniNames> uniNamesList = new ArrayList<>();
		names.forEach(name -> {
			if (mapHarmonicNames.containsKey(name)) {
				uniNamesList.add(mapHarmonicNames.get(name));
			}
		});
		return uniNamesList;
	}

	public static LocalDate parseDate(String stringDate) throws ApplicationException {
		AtomicReference<LocalDate> parsedDate = new AtomicReference<>();
		final boolean isMatched = dateFormatPatterns.stream()
				.anyMatch(pattern -> {
					DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
					LocalDate parse = LocalDate.parse(stringDate, formatter);
					parsedDate.set(parse);
					return true;
				});
		if (isMatched) return parsedDate.get();
		else throw new ApplicationException("error.parseDate");
	}

	public static LocalTime parseTime(String time) {
		return LocalTime.parse(time);
	}

	public static String parseFlag(String flag) {
		return flag.equals(" ") ? "o" : "x";
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
