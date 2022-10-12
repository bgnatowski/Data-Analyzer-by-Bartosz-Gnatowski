package agh.inzapp.inzynierka.converters;

import agh.inzapp.inzynierka.enums.UniNames;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static agh.inzapp.inzynierka.enums.UniNames.*;

public class PQParser {
	private static final Map<String, UniNames> mapPQDataNames = new TreeMap<>();
	private static final Map<String, UniNames> mapPQHarmonicsNames = new TreeMap<>();
	private static final List<String> dateFormatPatterns = new ArrayList<>();

	static {
		dateFormatPatterns.add("d.MM.yyyy");
		dateFormatPatterns.add("d-MM-yyyy");
		dateFormatPatterns.add("d/MM/yyyy");
		dateFormatPatterns.add("yyyy/MM/d");
	}

	static {
		mapPQDataNames.put("Date", Date);
		mapPQDataNames.put("Time", Time);
		mapPQDataNames.put("Flagging", Flag);
		mapPQDataNames.put("U12_[V]", U12_avg);
		mapPQDataNames.put("U23_[V]", U23_avg);
		mapPQDataNames.put("U31_[V]", U31_avg);
		mapPQDataNames.put("U12_max_[V]", U12_max);
		mapPQDataNames.put("U23_max_[V]", U23_max);
		mapPQDataNames.put("U31_max_[V]", U31_max);
		mapPQDataNames.put("U12_min_[V]", U12_min);
		mapPQDataNames.put("U23_min_[V]", U23_min);
		mapPQDataNames.put("U31_min_[V]", U31_min);
		mapPQDataNames.put("IL1_[A]", IL1_avg);
		mapPQDataNames.put("IL2_[A]", IL2_avg);
		mapPQDataNames.put("IL3_[A]", IL3_avg);
		mapPQDataNames.put("I_Neutral_[A]", IN_avg);
		mapPQDataNames.put("IL1_max_[A]", IL1_max);
		mapPQDataNames.put("IL2_max_[A]", IL2_max);
		mapPQDataNames.put("IL3_max_[A]", IL3_max);
		mapPQDataNames.put("I_Neutral_max_[A]", IN_max);
		mapPQDataNames.put("IL1_min_[A]", IL1_min);
		mapPQDataNames.put("IL2_min_[A]", IL2_min);
		mapPQDataNames.put("IL3_min_[A]", IL3_min);
		mapPQDataNames.put("I_Neutral_min_[A]", IN_min);
		mapPQDataNames.put("Pst_U12", Pst_U12);
		mapPQDataNames.put("Pst_U23", Pst_U23);
		mapPQDataNames.put("Pst_U31", Pst_U31);
		mapPQDataNames.put("Plt_U12", Plt_U12);
		mapPQDataNames.put("Plt_U23", Plt_U23);
		mapPQDataNames.put("Plt_U31", Plt_U31);
		mapPQDataNames.put("Unbalance_-_Voltage_(_Neg./Pos.)_[%]", Unbalanced_Voltage);
		mapPQDataNames.put("Unbalance_-_Current_(_Neg./Pos.)_[%]", Unbalanced_Current);
		mapPQDataNames.put("F_[Hz]", f);
		mapPQDataNames.put("P_total_min_[W]", P_min);
		mapPQDataNames.put("P_total_[W]", P_total);
		mapPQDataNames.put("P_total_[W]'_abs", P_abs);
		mapPQDataNames.put("P_total_max_[W]", P_max);
		mapPQDataNames.put("S_total_min__[VA]", S_min);
		mapPQDataNames.put("S_total_[VA]", S_total);
		mapPQDataNames.put("S_total_max_[VA]", S_max);
		mapPQDataNames.put("PF_total", PF_total);
		mapPQDataNames.put("PF_total'_abs", PF_total_abs);
		mapPQDataNames.put("cos(phi)", cos_phi);
		mapPQDataNames.put("tan(phi)", tan_phi);
		mapPQDataNames.put("QV_total_[Var]", Q_total);
		mapPQDataNames.put("UL1_[V]", UL1_avg);
		mapPQDataNames.put("UL2_[V]", UL2_avg);
		mapPQDataNames.put("UL3_[V]", UL3_avg);
		mapPQDataNames.put("UL1_max_[V]", UL1_max);
		mapPQDataNames.put("UL2_max_[V]", UL2_max);
		mapPQDataNames.put("UL3_max_[V]", UL3_max);
		mapPQDataNames.put("UL1_min_[V]", UL1_min);
		mapPQDataNames.put("UL2_min_[V]", UL2_min);
		mapPQDataNames.put("UL3_min_[V]", UL3_min);
		mapPQDataNames.put("Pst_UL1", Pst_UL1);
		mapPQDataNames.put("Pst_UL2", Pst_UL2);
		mapPQDataNames.put("Pst_UL3", Pst_UL3);
	}

	static{
		mapPQHarmonicsNames.put("THD12_[%]",winPQ_THD_12);
		mapPQHarmonicsNames.put("THD23_[%]",winPQ_THD_23);
		mapPQHarmonicsNames.put("THD31_[%]",winPQ_THD_31);
		mapPQHarmonicsNames.put("THDL1_[%]",winPQ_THD_L1);
		mapPQHarmonicsNames.put("THDL2_[%]",winPQ_THD_L2);
		mapPQHarmonicsNames.put("THDL3_[%]",winPQ_THD_L3);
		mapPQHarmonicsNames.put("H1_UL1_[%]",winPQ_H1_UL1);
		mapPQHarmonicsNames.put("H1_UL2_[%]",winPQ_H2_UL1);
		mapPQHarmonicsNames.put("H1_UL3_[%]",winPQ_H3_UL1);
		mapPQHarmonicsNames.put("H2_UL1_[%]",winPQ_H4_UL1);
		mapPQHarmonicsNames.put("H2_UL2_[%]",winPQ_H5_UL1);
		mapPQHarmonicsNames.put("H2_UL3_[%]",winPQ_H6_UL1);
		mapPQHarmonicsNames.put("H3_UL1_[%]",winPQ_H7_UL1);
		mapPQHarmonicsNames.put("H3_UL2_[%]",winPQ_H8_UL1);
		mapPQHarmonicsNames.put("H3_UL3_[%]",winPQ_H9_UL1);
		mapPQHarmonicsNames.put("H4_UL1_[%]",winPQ_H10_UL1);
		mapPQHarmonicsNames.put("H4_UL2_[%]",winPQ_H11_UL1);
		mapPQHarmonicsNames.put("H4_UL3_[%]",winPQ_H12_UL1);
		mapPQHarmonicsNames.put("H5_UL1_[%]",winPQ_H13_UL1);
		mapPQHarmonicsNames.put("H5_UL2_[%]",winPQ_H14_UL1);
		mapPQHarmonicsNames.put("H5_UL3_[%]",winPQ_H15_UL1);
		mapPQHarmonicsNames.put("H6_UL1_[%]",winPQ_H16_UL1);
		mapPQHarmonicsNames.put("H6_UL2_[%]",winPQ_H17_UL1);
		mapPQHarmonicsNames.put("H6_UL3_[%]",winPQ_H18_UL1);
		mapPQHarmonicsNames.put("H7_UL1_[%]",winPQ_H19_UL1);
		mapPQHarmonicsNames.put("H7_UL2_[%]",winPQ_H20_UL1);
		mapPQHarmonicsNames.put("H7_UL3_[%]",winPQ_H21_UL1);
		mapPQHarmonicsNames.put("H8_UL1_[%]",winPQ_H22_UL1);
		mapPQHarmonicsNames.put("H8_UL2_[%]",winPQ_H23_UL1);
		mapPQHarmonicsNames.put("H8_UL3_[%]",winPQ_H24_UL1);
		mapPQHarmonicsNames.put("H9_UL1_[%]",winPQ_H25_UL1);
		mapPQHarmonicsNames.put("H9_UL2_[%]",winPQ_H26_UL1);
		mapPQHarmonicsNames.put("H9_UL3_[%]",winPQ_H27_UL1);
		mapPQHarmonicsNames.put("H10_UL1_[%]",winPQ_H28_UL1);
		mapPQHarmonicsNames.put("H10_UL2_[%]",winPQ_H29_UL1);
		mapPQHarmonicsNames.put("H10_UL3_[%]",winPQ_H30_UL1);
		mapPQHarmonicsNames.put("H11_UL1_[%]",winPQ_H31_UL1);
		mapPQHarmonicsNames.put("H11_UL2_[%]",winPQ_H32_UL1);
		mapPQHarmonicsNames.put("H11_UL3_[%]",winPQ_H33_UL1);
		mapPQHarmonicsNames.put("H12_UL1_[%]",winPQ_H34_UL1);
		mapPQHarmonicsNames.put("H12_UL2_[%]",winPQ_H35_UL1);
		mapPQHarmonicsNames.put("H12_UL3_[%]",winPQ_H36_UL1);
		mapPQHarmonicsNames.put("H13_UL1_[%]",winPQ_H37_UL1);
		mapPQHarmonicsNames.put("H13_UL2_[%]",winPQ_H38_UL1);
		mapPQHarmonicsNames.put("H13_UL3_[%]",winPQ_H39_UL1);
		mapPQHarmonicsNames.put("H14_UL1_[%]",winPQ_H40_UL1);
		mapPQHarmonicsNames.put("H14_UL2_[%]",winPQ_H41_UL1);
		mapPQHarmonicsNames.put("H14_UL3_[%]",winPQ_H42_UL1);
		mapPQHarmonicsNames.put("H15_UL1_[%]",winPQ_H43_UL1);
		mapPQHarmonicsNames.put("H15_UL2_[%]",winPQ_H44_UL1);
		mapPQHarmonicsNames.put("H15_UL3_[%]",winPQ_H45_UL1);
		mapPQHarmonicsNames.put("H16_UL1_[%]",winPQ_H46_UL1);
		mapPQHarmonicsNames.put("H16_UL2_[%]",winPQ_H47_UL1);
		mapPQHarmonicsNames.put("H16_UL3_[%]",winPQ_H48_UL1);
		mapPQHarmonicsNames.put("H17_UL1_[%]",winPQ_H49_UL1);
		mapPQHarmonicsNames.put("H17_UL2_[%]",winPQ_H50_UL1);
		mapPQHarmonicsNames.put("H17_UL3_[%]",winPQ_H1_UL2);
		mapPQHarmonicsNames.put("H18_UL1_[%]",winPQ_H2_UL2);
		mapPQHarmonicsNames.put("H18_UL2_[%]",winPQ_H3_UL2);
		mapPQHarmonicsNames.put("H18_UL3_[%]",winPQ_H4_UL2);
		mapPQHarmonicsNames.put("H19_UL1_[%]",winPQ_H5_UL2);
		mapPQHarmonicsNames.put("H19_UL2_[%]",winPQ_H6_UL2);
		mapPQHarmonicsNames.put("H19_UL3_[%]",winPQ_H7_UL2);
		mapPQHarmonicsNames.put("H20_UL1_[%]",winPQ_H8_UL2);
		mapPQHarmonicsNames.put("H20_UL2_[%]",winPQ_H9_UL2);
		mapPQHarmonicsNames.put("H20_UL3_[%]",winPQ_H10_UL2);
		mapPQHarmonicsNames.put("H21_UL1_[%]",winPQ_H11_UL2);
		mapPQHarmonicsNames.put("H21_UL2_[%]",winPQ_H12_UL2);
		mapPQHarmonicsNames.put("H21_UL3_[%]",winPQ_H13_UL2);
		mapPQHarmonicsNames.put("H22_UL1_[%]",winPQ_H14_UL2);
		mapPQHarmonicsNames.put("H22_UL2_[%]",winPQ_H15_UL2);
		mapPQHarmonicsNames.put("H22_UL3_[%]",winPQ_H16_UL2);
		mapPQHarmonicsNames.put("H23_UL1_[%]",winPQ_H17_UL2);
		mapPQHarmonicsNames.put("H23_UL2_[%]",winPQ_H18_UL2);
		mapPQHarmonicsNames.put("H23_UL3_[%]",winPQ_H19_UL2);
		mapPQHarmonicsNames.put("H24_UL1_[%]",winPQ_H20_UL2);
		mapPQHarmonicsNames.put("H24_UL2_[%]",winPQ_H21_UL2);
		mapPQHarmonicsNames.put("H24_UL3_[%]",winPQ_H22_UL2);
		mapPQHarmonicsNames.put("H25_UL1_[%]",winPQ_H23_UL2);
		mapPQHarmonicsNames.put("H25_UL2_[%]",winPQ_H24_UL2);
		mapPQHarmonicsNames.put("H25_UL3_[%]",winPQ_H25_UL2);
		mapPQHarmonicsNames.put("H26_UL1_[%]",winPQ_H26_UL2);
		mapPQHarmonicsNames.put("H26_UL2_[%]",winPQ_H27_UL2);
		mapPQHarmonicsNames.put("H26_UL3_[%]",winPQ_H28_UL2);
		mapPQHarmonicsNames.put("H27_UL1_[%]",winPQ_H29_UL2);
		mapPQHarmonicsNames.put("H27_UL2_[%]",winPQ_H30_UL2);
		mapPQHarmonicsNames.put("H27_UL3_[%]",winPQ_H31_UL2);
		mapPQHarmonicsNames.put("H28_UL1_[%]",winPQ_H32_UL2);
		mapPQHarmonicsNames.put("H28_UL2_[%]",winPQ_H33_UL2);
		mapPQHarmonicsNames.put("H28_UL3_[%]",winPQ_H34_UL2);
		mapPQHarmonicsNames.put("H29_UL1_[%]",winPQ_H35_UL2);
		mapPQHarmonicsNames.put("H29_UL2_[%]",winPQ_H36_UL2);
		mapPQHarmonicsNames.put("H29_UL3_[%]",winPQ_H37_UL2);
		mapPQHarmonicsNames.put("H30_UL1_[%]",winPQ_H38_UL2);
		mapPQHarmonicsNames.put("H30_UL2_[%]",winPQ_H39_UL2);
		mapPQHarmonicsNames.put("H30_UL3_[%]",winPQ_H40_UL2);
		mapPQHarmonicsNames.put("H31_UL1_[%]",winPQ_H41_UL2);
		mapPQHarmonicsNames.put("H31_UL2_[%]",winPQ_H42_UL2);
		mapPQHarmonicsNames.put("H31_UL3_[%]",winPQ_H43_UL2);
		mapPQHarmonicsNames.put("H32_UL1_[%]",winPQ_H44_UL2);
		mapPQHarmonicsNames.put("H32_UL2_[%]",winPQ_H45_UL2);
		mapPQHarmonicsNames.put("H32_UL3_[%]",winPQ_H46_UL2);
		mapPQHarmonicsNames.put("H33_UL1_[%]",winPQ_H47_UL2);
		mapPQHarmonicsNames.put("H33_UL2_[%]",winPQ_H48_UL2);
		mapPQHarmonicsNames.put("H33_UL3_[%]",winPQ_H49_UL2);
		mapPQHarmonicsNames.put("H34_UL1_[%]",winPQ_H50_UL2);
		mapPQHarmonicsNames.put("H34_UL2_[%]",winPQ_H1_UL3);
		mapPQHarmonicsNames.put("H34_UL3_[%]",winPQ_H2_UL3);
		mapPQHarmonicsNames.put("H35_UL1_[%]",winPQ_H3_UL3);
		mapPQHarmonicsNames.put("H35_UL2_[%]",winPQ_H4_UL3);
		mapPQHarmonicsNames.put("H35_UL3_[%]",winPQ_H5_UL3);
		mapPQHarmonicsNames.put("H36_UL1_[%]",winPQ_H6_UL3);
		mapPQHarmonicsNames.put("H36_UL2_[%]",winPQ_H7_UL3);
		mapPQHarmonicsNames.put("H36_UL3_[%]",winPQ_H8_UL3);
		mapPQHarmonicsNames.put("H37_UL1_[%]",winPQ_H9_UL3);
		mapPQHarmonicsNames.put("H37_UL2_[%]",winPQ_H10_UL3);
		mapPQHarmonicsNames.put("H37_UL3_[%]",winPQ_H11_UL3);
		mapPQHarmonicsNames.put("H38_UL1_[%]",winPQ_H12_UL3);
		mapPQHarmonicsNames.put("H38_UL2_[%]",winPQ_H13_UL3);
		mapPQHarmonicsNames.put("H38_UL3_[%]",winPQ_H14_UL3);
		mapPQHarmonicsNames.put("H39_UL1_[%]",winPQ_H15_UL3);
		mapPQHarmonicsNames.put("H39_UL2_[%]",winPQ_H16_UL3);
		mapPQHarmonicsNames.put("H39_UL3_[%]",winPQ_H17_UL3);
		mapPQHarmonicsNames.put("H40_UL1_[%]",winPQ_H18_UL3);
		mapPQHarmonicsNames.put("H40_UL2_[%]",winPQ_H19_UL3);
		mapPQHarmonicsNames.put("H40_UL3_[%]",winPQ_H20_UL3);
		mapPQHarmonicsNames.put("H41_UL1_[%]",winPQ_H21_UL3);
		mapPQHarmonicsNames.put("H41_UL2_[%]",winPQ_H22_UL3);
		mapPQHarmonicsNames.put("H41_UL3_[%]",winPQ_H23_UL3);
		mapPQHarmonicsNames.put("H42_UL1_[%]",winPQ_H24_UL3);
		mapPQHarmonicsNames.put("H42_UL2_[%]",winPQ_H25_UL3);
		mapPQHarmonicsNames.put("H42_UL3_[%]",winPQ_H26_UL3);
		mapPQHarmonicsNames.put("H43_UL1_[%]",winPQ_H27_UL3);
		mapPQHarmonicsNames.put("H43_UL2_[%]",winPQ_H28_UL3);
		mapPQHarmonicsNames.put("H43_UL3_[%]",winPQ_H29_UL3);
		mapPQHarmonicsNames.put("H44_UL1_[%]",winPQ_H30_UL3);
		mapPQHarmonicsNames.put("H44_UL2_[%]",winPQ_H31_UL3);
		mapPQHarmonicsNames.put("H44_UL3_[%]",winPQ_H32_UL3);
		mapPQHarmonicsNames.put("H45_UL1_[%]",winPQ_H33_UL3);
		mapPQHarmonicsNames.put("H45_UL2_[%]",winPQ_H34_UL3);
		mapPQHarmonicsNames.put("H45_UL3_[%]",winPQ_H35_UL3);
		mapPQHarmonicsNames.put("H46_UL1_[%]",winPQ_H36_UL3);
		mapPQHarmonicsNames.put("H46_UL2_[%]",winPQ_H37_UL3);
		mapPQHarmonicsNames.put("H46_UL3_[%]",winPQ_H38_UL3);
		mapPQHarmonicsNames.put("H47_UL1_[%]",winPQ_H39_UL3);
		mapPQHarmonicsNames.put("H47_UL2_[%]",winPQ_H40_UL3);
		mapPQHarmonicsNames.put("H47_UL3_[%]",winPQ_H41_UL3);
		mapPQHarmonicsNames.put("H48_UL1_[%]",winPQ_H42_UL3);
		mapPQHarmonicsNames.put("H48_UL2_[%]",winPQ_H43_UL3);
		mapPQHarmonicsNames.put("H48_UL3_[%]",winPQ_H44_UL3);
		mapPQHarmonicsNames.put("H49_UL1_[%]",winPQ_H45_UL3);
		mapPQHarmonicsNames.put("H49_UL2_[%]",winPQ_H46_UL3);
		mapPQHarmonicsNames.put("H49_UL3_[%]",winPQ_H47_UL3);
		mapPQHarmonicsNames.put("H50_UL1_[%]",winPQ_H48_UL3);
		mapPQHarmonicsNames.put("H50_UL2_[%]",winPQ_H49_UL3);
		mapPQHarmonicsNames.put("H50_UL3_[%]",winPQ_H50_UL3);
	}
	public static Map<UniNames, Integer> parseNames(List<String> names) {
		return getUnitaryNamesIntegerMap(names, mapPQDataNames);
	}

	public static Map<UniNames, Integer> parseHarmonicsNames(List<String> names) {
		return getUnitaryNamesIntegerMap(names, mapPQHarmonicsNames);
	}

	private static Map<UniNames, Integer> getUnitaryNamesIntegerMap(List<String> names, Map<String, UniNames> parsingMap ) {
		Map<UniNames, Integer> unitaryNamesMap = new TreeMap<>();

		AtomicInteger i = new AtomicInteger(0);
		names.forEach(element -> {
			if (parsingMap.get(element) != null){
				unitaryNamesMap.put(parsingMap.get(element), i.getAndIncrement());
			}
		});

		return unitaryNamesMap;
	}

	public static LocalDate parseDate(String stringDate) {
		AtomicReference<LocalDate> parsedDate = new AtomicReference<>();
		dateFormatPatterns.stream()
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
		return parsedDate.get();
	}

	public static LocalTime parseTime(String time) {
		return LocalTime.parse(time);
	}

	public static String parseFlag(String flag) {
		return flag.equals("X") ? flag : null;
	}

	public static double parseDouble(String record, UniNames unitaryName) throws ParseException {
		double d;
		if(record.contains(",")){
			NumberFormat format = NumberFormat.getInstance(Locale.FRENCH);
			Number number = format.parse(record);
			d = number.doubleValue();
		} else{
			d = Double.parseDouble(record);
		}

		if (unitaryName.equals(P_total) || unitaryName.equals(P_abs) || unitaryName.equals(P_max) ||
				unitaryName.equals(P_min) || unitaryName.equals(S_total) || unitaryName.equals(S_max) ||
				unitaryName.equals(S_min) || unitaryName.equals(Q_total)){
			d = d / 1000;
		}

		return d;
	}
}
