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

public class PQParser {
	private static final Map<String, UniNames> mapDataNames = new LinkedHashMap<>();
	private static final Map<String, UniNames> mapHarmonicNames = new LinkedHashMap<>();
	private static final List<String> dateFormatPatterns = new ArrayList<>();
	public static final String DECIMAL_SEPARATOR = ",";

	static {
		dateFormatPatterns.add("d.MM.yyyy");
		dateFormatPatterns.add("d-MM-yyyy");
		dateFormatPatterns.add("d/MM/yyyy");
		dateFormatPatterns.add("yyyy/MM/d");
	}

	static {
		mapDataNames.put("Date", Date);
		mapDataNames.put("Time", Time);
		mapDataNames.put("Flagging", Flag);
		mapDataNames.put("U12_[V]", U12_avg);
		mapDataNames.put("U23_[V]", U23_avg);
		mapDataNames.put("U31_[V]", U31_avg);
		mapDataNames.put("U12_max_[V]", U12_max);
		mapDataNames.put("U23_max_[V]", U23_max);
		mapDataNames.put("U31_max_[V]", U31_max);
		mapDataNames.put("U12_min_[V]", U12_min);
		mapDataNames.put("U23_min_[V]", U23_min);
		mapDataNames.put("U31_min_[V]", U31_min);
		mapDataNames.put("IL1_[A]", IL1_avg);
		mapDataNames.put("IL2_[A]", IL2_avg);
		mapDataNames.put("IL3_[A]", IL3_avg);
		mapDataNames.put("I_Neutral_[A]", IN_avg);
		mapDataNames.put("IL1_max_[A]", IL1_max);
		mapDataNames.put("IL2_max_[A]", IL2_max);
		mapDataNames.put("IL3_max_[A]", IL3_max);
		mapDataNames.put("I_Neutral_max_[A]", IN_max);
		mapDataNames.put("IL1_min_[A]", IL1_min);
		mapDataNames.put("IL2_min_[A]", IL2_min);
		mapDataNames.put("IL3_min_[A]", IL3_min);
		mapDataNames.put("I_Neutral_min_[A]", IN_min);
		mapDataNames.put("Pst_U12", Pst_U12);
		mapDataNames.put("Pst_U23", Pst_U23);
		mapDataNames.put("Pst_U31", Pst_U31);
		mapDataNames.put("Plt_U12", Plt_U12);
		mapDataNames.put("Plt_U23", Plt_U23);
		mapDataNames.put("Plt_U31", Plt_U31);
		mapDataNames.put("Unbalance_-_Voltage_(_Neg./Pos.)_[%]", Unbalanced_Voltage);
		mapDataNames.put("Unbalance_-_Current_(_Neg./Pos.)_[%]", Unbalanced_Current);
		mapDataNames.put("F_[Hz]", f);
		mapDataNames.put("P_total_min_[W]", P_min);
		mapDataNames.put("P_total_[W]", P_total);
		mapDataNames.put("P_total_[W]'_abs", P_abs);
		mapDataNames.put("P_total_max_[W]", P_max);
		mapDataNames.put("S_total_min__[VA]", S_min);
		mapDataNames.put("S_total_[VA]", S_total);
		mapDataNames.put("S_total_max_[VA]", S_max);
		mapDataNames.put("PF_total", PF_total);
		mapDataNames.put("PF_total'_abs", PF_total_abs);
		mapDataNames.put("cos(phi)", cos_phi);
		mapDataNames.put("tan(phi)", tan_phi);
		mapDataNames.put("QV_total_[Var]", Q_total);
		mapDataNames.put("UL1_[V]", UL1_avg);
		mapDataNames.put("UL2_[V]", UL2_avg);
		mapDataNames.put("UL3_[V]", UL3_avg);
		mapDataNames.put("UL1_max_[V]", UL1_max);
		mapDataNames.put("UL2_max_[V]", UL2_max);
		mapDataNames.put("UL3_max_[V]", UL3_max);
		mapDataNames.put("UL1_min_[V]", UL1_min);
		mapDataNames.put("UL2_min_[V]", UL2_min);
		mapDataNames.put("UL3_min_[V]", UL3_min);
		mapDataNames.put("Pst_UL1", Pst_UL1);
		mapDataNames.put("Pst_UL2", Pst_UL2);
		mapDataNames.put("Pst_UL3", Pst_UL3);
	}

	static {
		mapHarmonicNames.put("Date", Date);
		mapHarmonicNames.put("Time", Time);
		mapHarmonicNames.put("Flagging", Flag);
		mapHarmonicNames.put("THD12_[%]", PQ_THD_12);
		mapHarmonicNames.put("THD23_[%]", PQ_THD_23);
		mapHarmonicNames.put("THD31_[%]", PQ_THD_31);
		mapHarmonicNames.put("THDL1_[%]", PQ_THD_L1);
		mapHarmonicNames.put("THDL2_[%]", PQ_THD_L2);
		mapHarmonicNames.put("THDL3_[%]", PQ_THD_L3);
		mapHarmonicNames.put("H1_UL1_[%]", PQ_H1_UL1);
		mapHarmonicNames.put("H1_UL2_[%]", PQ_H1_UL2);
		mapHarmonicNames.put("H1_UL3_[%]", PQ_H1_UL3);
		mapHarmonicNames.put("H2_UL1_[%]", PQ_H2_UL1);
		mapHarmonicNames.put("H2_UL2_[%]", PQ_H2_UL2);
		mapHarmonicNames.put("H2_UL3_[%]", PQ_H2_UL3);
		mapHarmonicNames.put("H3_UL1_[%]", PQ_H3_UL1);
		mapHarmonicNames.put("H3_UL2_[%]", PQ_H3_UL2);
		mapHarmonicNames.put("H3_UL3_[%]", PQ_H3_UL3);
		mapHarmonicNames.put("H4_UL1_[%]", PQ_H4_UL1);
		mapHarmonicNames.put("H4_UL2_[%]", PQ_H4_UL2);
		mapHarmonicNames.put("H4_UL3_[%]", PQ_H4_UL3);
		mapHarmonicNames.put("H5_UL1_[%]", PQ_H5_UL1);
		mapHarmonicNames.put("H5_UL2_[%]", PQ_H5_UL2);
		mapHarmonicNames.put("H5_UL3_[%]", PQ_H5_UL3);
		mapHarmonicNames.put("H6_UL1_[%]", PQ_H6_UL1);
		mapHarmonicNames.put("H6_UL2_[%]", PQ_H6_UL2);
		mapHarmonicNames.put("H6_UL3_[%]", PQ_H6_UL3);
		mapHarmonicNames.put("H7_UL1_[%]", PQ_H7_UL1);
		mapHarmonicNames.put("H7_UL2_[%]", PQ_H7_UL2);
		mapHarmonicNames.put("H7_UL3_[%]", PQ_H7_UL3);
		mapHarmonicNames.put("H8_UL1_[%]", PQ_H8_UL1);
		mapHarmonicNames.put("H8_UL2_[%]", PQ_H8_UL2);
		mapHarmonicNames.put("H8_UL3_[%]", PQ_H8_UL3);
		mapHarmonicNames.put("H9_UL1_[%]", PQ_H9_UL1);
		mapHarmonicNames.put("H9_UL2_[%]", PQ_H9_UL2);
		mapHarmonicNames.put("H9_UL3_[%]", PQ_H9_UL3);
		mapHarmonicNames.put("H10_UL1_[%]", PQ_H10_UL1);
		mapHarmonicNames.put("H10_UL2_[%]", PQ_H10_UL2);
		mapHarmonicNames.put("H10_UL3_[%]", PQ_H10_UL3);
		mapHarmonicNames.put("H11_UL1_[%]", PQ_H11_UL1);
		mapHarmonicNames.put("H11_UL2_[%]", PQ_H11_UL2);
		mapHarmonicNames.put("H11_UL3_[%]", PQ_H11_UL3);
		mapHarmonicNames.put("H12_UL1_[%]", PQ_H12_UL1);
		mapHarmonicNames.put("H12_UL2_[%]", PQ_H12_UL2);
		mapHarmonicNames.put("H12_UL3_[%]", PQ_H12_UL3);
		mapHarmonicNames.put("H13_UL1_[%]", PQ_H13_UL1);
		mapHarmonicNames.put("H13_UL2_[%]", PQ_H13_UL2);
		mapHarmonicNames.put("H13_UL3_[%]", PQ_H13_UL3);
		mapHarmonicNames.put("H14_UL1_[%]", PQ_H14_UL1);
		mapHarmonicNames.put("H14_UL2_[%]", PQ_H14_UL2);
		mapHarmonicNames.put("H14_UL3_[%]", PQ_H14_UL3);
		mapHarmonicNames.put("H15_UL1_[%]", PQ_H15_UL1);
		mapHarmonicNames.put("H15_UL2_[%]", PQ_H15_UL2);
		mapHarmonicNames.put("H15_UL3_[%]", PQ_H15_UL3);
		mapHarmonicNames.put("H16_UL1_[%]", PQ_H16_UL1);
		mapHarmonicNames.put("H16_UL2_[%]", PQ_H16_UL2);
		mapHarmonicNames.put("H16_UL3_[%]", PQ_H16_UL3);
		mapHarmonicNames.put("H17_UL1_[%]", PQ_H17_UL1);
		mapHarmonicNames.put("H17_UL2_[%]", PQ_H17_UL2);
		mapHarmonicNames.put("H17_UL3_[%]", PQ_H17_UL3);
		mapHarmonicNames.put("H18_UL1_[%]", PQ_H18_UL1);
		mapHarmonicNames.put("H18_UL2_[%]", PQ_H18_UL2);
		mapHarmonicNames.put("H18_UL3_[%]", PQ_H18_UL3);
		mapHarmonicNames.put("H19_UL1_[%]", PQ_H19_UL1);
		mapHarmonicNames.put("H19_UL2_[%]", PQ_H19_UL2);
		mapHarmonicNames.put("H19_UL3_[%]", PQ_H19_UL3);
		mapHarmonicNames.put("H20_UL1_[%]", PQ_H20_UL1);
		mapHarmonicNames.put("H20_UL2_[%]", PQ_H20_UL2);
		mapHarmonicNames.put("H20_UL3_[%]", PQ_H20_UL3);
		mapHarmonicNames.put("H21_UL1_[%]", PQ_H21_UL1);
		mapHarmonicNames.put("H21_UL2_[%]", PQ_H21_UL2);
		mapHarmonicNames.put("H21_UL3_[%]", PQ_H21_UL3);
		mapHarmonicNames.put("H22_UL1_[%]", PQ_H22_UL1);
		mapHarmonicNames.put("H22_UL2_[%]", PQ_H22_UL2);
		mapHarmonicNames.put("H22_UL3_[%]", PQ_H22_UL3);
		mapHarmonicNames.put("H23_UL1_[%]", PQ_H23_UL1);
		mapHarmonicNames.put("H23_UL2_[%]", PQ_H23_UL2);
		mapHarmonicNames.put("H23_UL3_[%]", PQ_H23_UL3);
		mapHarmonicNames.put("H24_UL1_[%]", PQ_H24_UL1);
		mapHarmonicNames.put("H24_UL2_[%]", PQ_H24_UL2);
		mapHarmonicNames.put("H24_UL3_[%]", PQ_H24_UL3);
		mapHarmonicNames.put("H25_UL1_[%]", PQ_H25_UL1);
		mapHarmonicNames.put("H25_UL2_[%]", PQ_H25_UL2);
		mapHarmonicNames.put("H25_UL3_[%]", PQ_H25_UL3);
		mapHarmonicNames.put("H26_UL1_[%]", PQ_H26_UL1);
		mapHarmonicNames.put("H26_UL2_[%]", PQ_H26_UL2);
		mapHarmonicNames.put("H26_UL3_[%]", PQ_H26_UL3);
		mapHarmonicNames.put("H27_UL1_[%]", PQ_H27_UL1);
		mapHarmonicNames.put("H27_UL2_[%]", PQ_H27_UL2);
		mapHarmonicNames.put("H27_UL3_[%]", PQ_H27_UL3);
		mapHarmonicNames.put("H28_UL1_[%]", PQ_H28_UL1);
		mapHarmonicNames.put("H28_UL2_[%]", PQ_H28_UL2);
		mapHarmonicNames.put("H28_UL3_[%]", PQ_H28_UL3);
		mapHarmonicNames.put("H29_UL1_[%]", PQ_H29_UL1);
		mapHarmonicNames.put("H29_UL2_[%]", PQ_H29_UL2);
		mapHarmonicNames.put("H29_UL3_[%]", PQ_H29_UL3);
		mapHarmonicNames.put("H30_UL1_[%]", PQ_H30_UL1);
		mapHarmonicNames.put("H30_UL2_[%]", PQ_H30_UL2);
		mapHarmonicNames.put("H30_UL3_[%]", PQ_H30_UL3);
		mapHarmonicNames.put("H31_UL1_[%]", PQ_H31_UL1);
		mapHarmonicNames.put("H31_UL2_[%]", PQ_H31_UL2);
		mapHarmonicNames.put("H31_UL3_[%]", PQ_H31_UL3);
		mapHarmonicNames.put("H32_UL1_[%]", PQ_H32_UL1);
		mapHarmonicNames.put("H32_UL2_[%]", PQ_H32_UL2);
		mapHarmonicNames.put("H32_UL3_[%]", PQ_H32_UL3);
		mapHarmonicNames.put("H33_UL1_[%]", PQ_H33_UL1);
		mapHarmonicNames.put("H33_UL2_[%]", PQ_H33_UL2);
		mapHarmonicNames.put("H33_UL3_[%]", PQ_H33_UL3);
		mapHarmonicNames.put("H34_UL1_[%]", PQ_H34_UL1);
		mapHarmonicNames.put("H34_UL2_[%]", PQ_H34_UL2);
		mapHarmonicNames.put("H34_UL3_[%]", PQ_H34_UL3);
		mapHarmonicNames.put("H35_UL1_[%]", PQ_H35_UL1);
		mapHarmonicNames.put("H35_UL2_[%]", PQ_H35_UL2);
		mapHarmonicNames.put("H35_UL3_[%]", PQ_H35_UL3);
		mapHarmonicNames.put("H36_UL1_[%]", PQ_H36_UL1);
		mapHarmonicNames.put("H36_UL2_[%]", PQ_H36_UL2);
		mapHarmonicNames.put("H36_UL3_[%]", PQ_H36_UL3);
		mapHarmonicNames.put("H37_UL1_[%]", PQ_H37_UL1);
		mapHarmonicNames.put("H37_UL2_[%]", PQ_H37_UL2);
		mapHarmonicNames.put("H37_UL3_[%]", PQ_H37_UL3);
		mapHarmonicNames.put("H38_UL1_[%]", PQ_H38_UL1);
		mapHarmonicNames.put("H38_UL2_[%]", PQ_H38_UL2);
		mapHarmonicNames.put("H38_UL3_[%]", PQ_H38_UL3);
		mapHarmonicNames.put("H39_UL1_[%]", PQ_H39_UL1);
		mapHarmonicNames.put("H39_UL2_[%]", PQ_H39_UL2);
		mapHarmonicNames.put("H39_UL3_[%]", PQ_H39_UL3);
		mapHarmonicNames.put("H40_UL1_[%]", PQ_H40_UL1);
		mapHarmonicNames.put("H40_UL2_[%]", PQ_H40_UL2);
		mapHarmonicNames.put("H40_UL3_[%]", PQ_H40_UL3);
		mapHarmonicNames.put("H41_UL1_[%]", PQ_H41_UL1);
		mapHarmonicNames.put("H41_UL2_[%]", PQ_H41_UL2);
		mapHarmonicNames.put("H41_UL3_[%]", PQ_H41_UL3);
		mapHarmonicNames.put("H42_UL1_[%]", PQ_H42_UL1);
		mapHarmonicNames.put("H42_UL2_[%]", PQ_H42_UL2);
		mapHarmonicNames.put("H42_UL3_[%]", PQ_H42_UL3);
		mapHarmonicNames.put("H43_UL1_[%]", PQ_H43_UL1);
		mapHarmonicNames.put("H43_UL2_[%]", PQ_H43_UL2);
		mapHarmonicNames.put("H43_UL3_[%]", PQ_H43_UL3);
		mapHarmonicNames.put("H44_UL1_[%]", PQ_H44_UL1);
		mapHarmonicNames.put("H44_UL2_[%]", PQ_H44_UL2);
		mapHarmonicNames.put("H44_UL3_[%]", PQ_H44_UL3);
		mapHarmonicNames.put("H45_UL1_[%]", PQ_H45_UL1);
		mapHarmonicNames.put("H45_UL2_[%]", PQ_H45_UL2);
		mapHarmonicNames.put("H45_UL3_[%]", PQ_H45_UL3);
		mapHarmonicNames.put("H46_UL1_[%]", PQ_H46_UL1);
		mapHarmonicNames.put("H46_UL2_[%]", PQ_H46_UL2);
		mapHarmonicNames.put("H46_UL3_[%]", PQ_H46_UL3);
		mapHarmonicNames.put("H47_UL1_[%]", PQ_H47_UL1);
		mapHarmonicNames.put("H47_UL2_[%]", PQ_H47_UL2);
		mapHarmonicNames.put("H47_UL3_[%]", PQ_H47_UL3);
		mapHarmonicNames.put("H48_UL1_[%]", PQ_H48_UL1);
		mapHarmonicNames.put("H48_UL2_[%]", PQ_H48_UL2);
		mapHarmonicNames.put("H48_UL3_[%]", PQ_H48_UL3);
		mapHarmonicNames.put("H49_UL1_[%]", PQ_H49_UL1);
		mapHarmonicNames.put("H49_UL2_[%]", PQ_H49_UL2);
		mapHarmonicNames.put("H49_UL3_[%]", PQ_H49_UL3);
		mapHarmonicNames.put("H50_UL1_[%]", PQ_H50_UL1);
		mapHarmonicNames.put("H50_UL2_[%]", PQ_H50_UL2);
		mapHarmonicNames.put("H50_UL3_[%]", PQ_H50_UL3);
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
		return LocalTime.parse(time);
	}
	public static String parseFlag(String flag) {
		return flag.trim().isEmpty() ? " " : "x";
	}

	public static double parseDouble(String record, UniNames unitaryName) throws ParseException {
		double d;
		if (record.contains(DECIMAL_SEPARATOR)) {
			NumberFormat format = NumberFormat.getInstance(Locale.FRENCH);
			Number number = format.parse(record);
			d = number.doubleValue();
		} else {
			d = Double.parseDouble(record);
		}

		switch (unitaryName){
			case P_total, P_abs, P_max, P_min, S_total, S_max, S_min, Q_total -> d = d / 1000;
		}

		return d;
	}
}
