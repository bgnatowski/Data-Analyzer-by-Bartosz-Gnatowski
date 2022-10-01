package agh.inzapp.inzynierka.utils.converters;

import agh.inzapp.inzynierka.utils.enums.UnitaryNames;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static agh.inzapp.inzynierka.utils.enums.UnitaryNames.*;

public class PQParser {
	private static Map<String, UnitaryNames> winPQNamesMap = new TreeMap<>();
	private static Map<String, UnitaryNames> winPQHarmonicsNamesMap = new TreeMap<>();
	private static final List<String> dateFormatPatterns = new ArrayList<>();

	static {
		dateFormatPatterns.add("d.MM.yyyy");
		dateFormatPatterns.add("d-MM-yyyy");
		dateFormatPatterns.add("d/MM/yyyy");
		dateFormatPatterns.add("yyyy/MM/d");
	}

	static {
		winPQNamesMap.put("Date", Date);
		winPQNamesMap.put("Time", Time);
		winPQNamesMap.put("Flagging", Flag);
		winPQNamesMap.put("U12_[V]", U12_avg);
		winPQNamesMap.put("U23_[V]", U23_avg);
		winPQNamesMap.put("U31_[V]", U31_avg);
		winPQNamesMap.put("U12_max_[V]", U12_max);
		winPQNamesMap.put("U23_max_[V]", U23_max);
		winPQNamesMap.put("U31_max_[V]", U31_max);
		winPQNamesMap.put("U12_min_[V]", U12_min);
		winPQNamesMap.put("U23_min_[V]", U23_min);
		winPQNamesMap.put("U31_min_[V]", U31_min);
		winPQNamesMap.put("IL1_[A]", IL1_avg);
		winPQNamesMap.put("IL2_[A]", IL2_avg);
		winPQNamesMap.put("IL3_[A]", IL3_avg);
		winPQNamesMap.put("I_Neutral_[A]", IN_avg);
		winPQNamesMap.put("IL1_max_[A]", IL1_max);
		winPQNamesMap.put("IL2_max_[A]", IL2_max);
		winPQNamesMap.put("IL3_max_[A]", IL3_max);
		winPQNamesMap.put("I_Neutral_max_[A]", IN_max);
		winPQNamesMap.put("IL1_min_[A]", IL1_min);
		winPQNamesMap.put("IL2_min_[A]", IL2_min);
		winPQNamesMap.put("IL3_min_[A]", IL3_min);
		winPQNamesMap.put("I_Neutral_min_[A]", IN_min);
		winPQNamesMap.put("Pst_U12", Pst_U12);
		winPQNamesMap.put("Pst_U23", Pst_U23);
		winPQNamesMap.put("Pst_U31", Pst_U31);
		winPQNamesMap.put("Plt_U12", Plt_U12);
		winPQNamesMap.put("Plt_U23", Plt_U23);
		winPQNamesMap.put("Plt_U31", Plt_U31);
		winPQNamesMap.put("Unbalance_-_Voltage_(_Neg./Pos.)_[%]", Unbalanced_Voltage);
		winPQNamesMap.put("Unbalance_-_Current_(_Neg./Pos.)_[%]", Unbalanced_Current);
		winPQNamesMap.put("F_[Hz]", f);
		winPQNamesMap.put("P_total_min_[W]", P_min);
		winPQNamesMap.put("P_total_[W]", P_total);
		winPQNamesMap.put("P_total_[W]'_abs", P_abs);
		winPQNamesMap.put("P_total_max_[W]", P_max);
		winPQNamesMap.put("S_total_min__[VA]", S_min);
		winPQNamesMap.put("S_total_[VA]", S_total);
		winPQNamesMap.put("S_total_max_[VA]", S_max);
		winPQNamesMap.put("PF_total", PF_total);
		winPQNamesMap.put("PF_total'_abs", PF_total_abs);
		winPQNamesMap.put("cos(phi)", cos_phi);
		winPQNamesMap.put("tan(phi)", tan_phi);
		winPQNamesMap.put("QV_total_[Var]", Q_total);
		winPQNamesMap.put("UL1_[V]", UL1_avg);
		winPQNamesMap.put("UL2_[V]", UL2_avg);
		winPQNamesMap.put("UL3_[V]", UL3_avg);
		winPQNamesMap.put("UL1_max_[V]", UL1_max);
		winPQNamesMap.put("UL2_max_[V]", UL2_max);
		winPQNamesMap.put("UL3_max_[V]", UL3_max);
		winPQNamesMap.put("UL1_min_[V]", UL1_min);
		winPQNamesMap.put("UL2_min_[V]", UL2_min);
		winPQNamesMap.put("UL3_min_[V]", UL3_min);
		winPQNamesMap.put("Pst_UL1", Pst_UL1);
		winPQNamesMap.put("Pst_UL2", Pst_UL2);
		winPQNamesMap.put("Pst_UL3", Pst_UL3);
	}

	static{
		winPQHarmonicsNamesMap.put("THD12_[%]",winPQ_THD_12);
		winPQHarmonicsNamesMap.put("THD23_[%]",winPQ_THD_23);
		winPQHarmonicsNamesMap.put("THD31_[%]",winPQ_THD_31);
		winPQHarmonicsNamesMap.put("THDL1_[%]",winPQ_THD_L1);
		winPQHarmonicsNamesMap.put("THDL2_[%]",winPQ_THD_L2);
		winPQHarmonicsNamesMap.put("THDL3_[%]",winPQ_THD_L3);
		winPQHarmonicsNamesMap.put("H1_UL1_[%]",winPQ_H1_UL1);
		winPQHarmonicsNamesMap.put("H1_UL2_[%]",winPQ_H2_UL1);
		winPQHarmonicsNamesMap.put("H1_UL3_[%]",winPQ_H3_UL1);
		winPQHarmonicsNamesMap.put("H2_UL1_[%]",winPQ_H4_UL1);
		winPQHarmonicsNamesMap.put("H2_UL2_[%]",winPQ_H5_UL1);
		winPQHarmonicsNamesMap.put("H2_UL3_[%]",winPQ_H6_UL1);
		winPQHarmonicsNamesMap.put("H3_UL1_[%]",winPQ_H7_UL1);
		winPQHarmonicsNamesMap.put("H3_UL2_[%]",winPQ_H8_UL1);
		winPQHarmonicsNamesMap.put("H3_UL3_[%]",winPQ_H9_UL1);
		winPQHarmonicsNamesMap.put("H4_UL1_[%]",winPQ_H10_UL1);
		winPQHarmonicsNamesMap.put("H4_UL2_[%]",winPQ_H11_UL1);
		winPQHarmonicsNamesMap.put("H4_UL3_[%]",winPQ_H12_UL1);
		winPQHarmonicsNamesMap.put("H5_UL1_[%]",winPQ_H13_UL1);
		winPQHarmonicsNamesMap.put("H5_UL2_[%]",winPQ_H14_UL1);
		winPQHarmonicsNamesMap.put("H5_UL3_[%]",winPQ_H15_UL1);
		winPQHarmonicsNamesMap.put("H6_UL1_[%]",winPQ_H16_UL1);
		winPQHarmonicsNamesMap.put("H6_UL2_[%]",winPQ_H17_UL1);
		winPQHarmonicsNamesMap.put("H6_UL3_[%]",winPQ_H18_UL1);
		winPQHarmonicsNamesMap.put("H7_UL1_[%]",winPQ_H19_UL1);
		winPQHarmonicsNamesMap.put("H7_UL2_[%]",winPQ_H20_UL1);
		winPQHarmonicsNamesMap.put("H7_UL3_[%]",winPQ_H21_UL1);
		winPQHarmonicsNamesMap.put("H8_UL1_[%]",winPQ_H22_UL1);
		winPQHarmonicsNamesMap.put("H8_UL2_[%]",winPQ_H23_UL1);
		winPQHarmonicsNamesMap.put("H8_UL3_[%]",winPQ_H24_UL1);
		winPQHarmonicsNamesMap.put("H9_UL1_[%]",winPQ_H25_UL1);
		winPQHarmonicsNamesMap.put("H9_UL2_[%]",winPQ_H26_UL1);
		winPQHarmonicsNamesMap.put("H9_UL3_[%]",winPQ_H27_UL1);
		winPQHarmonicsNamesMap.put("H10_UL1_[%]",winPQ_H28_UL1);
		winPQHarmonicsNamesMap.put("H10_UL2_[%]",winPQ_H29_UL1);
		winPQHarmonicsNamesMap.put("H10_UL3_[%]",winPQ_H30_UL1);
		winPQHarmonicsNamesMap.put("H11_UL1_[%]",winPQ_H31_UL1);
		winPQHarmonicsNamesMap.put("H11_UL2_[%]",winPQ_H32_UL1);
		winPQHarmonicsNamesMap.put("H11_UL3_[%]",winPQ_H33_UL1);
		winPQHarmonicsNamesMap.put("H12_UL1_[%]",winPQ_H34_UL1);
		winPQHarmonicsNamesMap.put("H12_UL2_[%]",winPQ_H35_UL1);
		winPQHarmonicsNamesMap.put("H12_UL3_[%]",winPQ_H36_UL1);
		winPQHarmonicsNamesMap.put("H13_UL1_[%]",winPQ_H37_UL1);
		winPQHarmonicsNamesMap.put("H13_UL2_[%]",winPQ_H38_UL1);
		winPQHarmonicsNamesMap.put("H13_UL3_[%]",winPQ_H39_UL1);
		winPQHarmonicsNamesMap.put("H14_UL1_[%]",winPQ_H40_UL1);
		winPQHarmonicsNamesMap.put("H14_UL2_[%]",winPQ_H41_UL1);
		winPQHarmonicsNamesMap.put("H14_UL3_[%]",winPQ_H42_UL1);
		winPQHarmonicsNamesMap.put("H15_UL1_[%]",winPQ_H43_UL1);
		winPQHarmonicsNamesMap.put("H15_UL2_[%]",winPQ_H44_UL1);
		winPQHarmonicsNamesMap.put("H15_UL3_[%]",winPQ_H45_UL1);
		winPQHarmonicsNamesMap.put("H16_UL1_[%]",winPQ_H46_UL1);
		winPQHarmonicsNamesMap.put("H16_UL2_[%]",winPQ_H47_UL1);
		winPQHarmonicsNamesMap.put("H16_UL3_[%]",winPQ_H48_UL1);
		winPQHarmonicsNamesMap.put("H17_UL1_[%]",winPQ_H49_UL1);
		winPQHarmonicsNamesMap.put("H17_UL2_[%]",winPQ_H50_UL1);
		winPQHarmonicsNamesMap.put("H17_UL3_[%]",winPQ_H1_UL2);
		winPQHarmonicsNamesMap.put("H18_UL1_[%]",winPQ_H2_UL2);
		winPQHarmonicsNamesMap.put("H18_UL2_[%]",winPQ_H3_UL2);
		winPQHarmonicsNamesMap.put("H18_UL3_[%]",winPQ_H4_UL2);
		winPQHarmonicsNamesMap.put("H19_UL1_[%]",winPQ_H5_UL2);
		winPQHarmonicsNamesMap.put("H19_UL2_[%]",winPQ_H6_UL2);
		winPQHarmonicsNamesMap.put("H19_UL3_[%]",winPQ_H7_UL2);
		winPQHarmonicsNamesMap.put("H20_UL1_[%]",winPQ_H8_UL2);
		winPQHarmonicsNamesMap.put("H20_UL2_[%]",winPQ_H9_UL2);
		winPQHarmonicsNamesMap.put("H20_UL3_[%]",winPQ_H10_UL2);
		winPQHarmonicsNamesMap.put("H21_UL1_[%]",winPQ_H11_UL2);
		winPQHarmonicsNamesMap.put("H21_UL2_[%]",winPQ_H12_UL2);
		winPQHarmonicsNamesMap.put("H21_UL3_[%]",winPQ_H13_UL2);
		winPQHarmonicsNamesMap.put("H22_UL1_[%]",winPQ_H14_UL2);
		winPQHarmonicsNamesMap.put("H22_UL2_[%]",winPQ_H15_UL2);
		winPQHarmonicsNamesMap.put("H22_UL3_[%]",winPQ_H16_UL2);
		winPQHarmonicsNamesMap.put("H23_UL1_[%]",winPQ_H17_UL2);
		winPQHarmonicsNamesMap.put("H23_UL2_[%]",winPQ_H18_UL2);
		winPQHarmonicsNamesMap.put("H23_UL3_[%]",winPQ_H19_UL2);
		winPQHarmonicsNamesMap.put("H24_UL1_[%]",winPQ_H20_UL2);
		winPQHarmonicsNamesMap.put("H24_UL2_[%]",winPQ_H21_UL2);
		winPQHarmonicsNamesMap.put("H24_UL3_[%]",winPQ_H22_UL2);
		winPQHarmonicsNamesMap.put("H25_UL1_[%]",winPQ_H23_UL2);
		winPQHarmonicsNamesMap.put("H25_UL2_[%]",winPQ_H24_UL2);
		winPQHarmonicsNamesMap.put("H25_UL3_[%]",winPQ_H25_UL2);
		winPQHarmonicsNamesMap.put("H26_UL1_[%]",winPQ_H26_UL2);
		winPQHarmonicsNamesMap.put("H26_UL2_[%]",winPQ_H27_UL2);
		winPQHarmonicsNamesMap.put("H26_UL3_[%]",winPQ_H28_UL2);
		winPQHarmonicsNamesMap.put("H27_UL1_[%]",winPQ_H29_UL2);
		winPQHarmonicsNamesMap.put("H27_UL2_[%]",winPQ_H30_UL2);
		winPQHarmonicsNamesMap.put("H27_UL3_[%]",winPQ_H31_UL2);
		winPQHarmonicsNamesMap.put("H28_UL1_[%]",winPQ_H32_UL2);
		winPQHarmonicsNamesMap.put("H28_UL2_[%]",winPQ_H33_UL2);
		winPQHarmonicsNamesMap.put("H28_UL3_[%]",winPQ_H34_UL2);
		winPQHarmonicsNamesMap.put("H29_UL1_[%]",winPQ_H35_UL2);
		winPQHarmonicsNamesMap.put("H29_UL2_[%]",winPQ_H36_UL2);
		winPQHarmonicsNamesMap.put("H29_UL3_[%]",winPQ_H37_UL2);
		winPQHarmonicsNamesMap.put("H30_UL1_[%]",winPQ_H38_UL2);
		winPQHarmonicsNamesMap.put("H30_UL2_[%]",winPQ_H39_UL2);
		winPQHarmonicsNamesMap.put("H30_UL3_[%]",winPQ_H40_UL2);
		winPQHarmonicsNamesMap.put("H31_UL1_[%]",winPQ_H41_UL2);
		winPQHarmonicsNamesMap.put("H31_UL2_[%]",winPQ_H42_UL2);
		winPQHarmonicsNamesMap.put("H31_UL3_[%]",winPQ_H43_UL2);
		winPQHarmonicsNamesMap.put("H32_UL1_[%]",winPQ_H44_UL2);
		winPQHarmonicsNamesMap.put("H32_UL2_[%]",winPQ_H45_UL2);
		winPQHarmonicsNamesMap.put("H32_UL3_[%]",winPQ_H46_UL2);
		winPQHarmonicsNamesMap.put("H33_UL1_[%]",winPQ_H47_UL2);
		winPQHarmonicsNamesMap.put("H33_UL2_[%]",winPQ_H48_UL2);
		winPQHarmonicsNamesMap.put("H33_UL3_[%]",winPQ_H49_UL2);
		winPQHarmonicsNamesMap.put("H34_UL1_[%]",winPQ_H50_UL2);
		winPQHarmonicsNamesMap.put("H34_UL2_[%]",winPQ_H1_UL3);
		winPQHarmonicsNamesMap.put("H34_UL3_[%]",winPQ_H2_UL3);
		winPQHarmonicsNamesMap.put("H35_UL1_[%]",winPQ_H3_UL3);
		winPQHarmonicsNamesMap.put("H35_UL2_[%]",winPQ_H4_UL3);
		winPQHarmonicsNamesMap.put("H35_UL3_[%]",winPQ_H5_UL3);
		winPQHarmonicsNamesMap.put("H36_UL1_[%]",winPQ_H6_UL3);
		winPQHarmonicsNamesMap.put("H36_UL2_[%]",winPQ_H7_UL3);
		winPQHarmonicsNamesMap.put("H36_UL3_[%]",winPQ_H8_UL3);
		winPQHarmonicsNamesMap.put("H37_UL1_[%]",winPQ_H9_UL3);
		winPQHarmonicsNamesMap.put("H37_UL2_[%]",winPQ_H10_UL3);
		winPQHarmonicsNamesMap.put("H37_UL3_[%]",winPQ_H11_UL3);
		winPQHarmonicsNamesMap.put("H38_UL1_[%]",winPQ_H12_UL3);
		winPQHarmonicsNamesMap.put("H38_UL2_[%]",winPQ_H13_UL3);
		winPQHarmonicsNamesMap.put("H38_UL3_[%]",winPQ_H14_UL3);
		winPQHarmonicsNamesMap.put("H39_UL1_[%]",winPQ_H15_UL3);
		winPQHarmonicsNamesMap.put("H39_UL2_[%]",winPQ_H16_UL3);
		winPQHarmonicsNamesMap.put("H39_UL3_[%]",winPQ_H17_UL3);
		winPQHarmonicsNamesMap.put("H40_UL1_[%]",winPQ_H18_UL3);
		winPQHarmonicsNamesMap.put("H40_UL2_[%]",winPQ_H19_UL3);
		winPQHarmonicsNamesMap.put("H40_UL3_[%]",winPQ_H20_UL3);
		winPQHarmonicsNamesMap.put("H41_UL1_[%]",winPQ_H21_UL3);
		winPQHarmonicsNamesMap.put("H41_UL2_[%]",winPQ_H22_UL3);
		winPQHarmonicsNamesMap.put("H41_UL3_[%]",winPQ_H23_UL3);
		winPQHarmonicsNamesMap.put("H42_UL1_[%]",winPQ_H24_UL3);
		winPQHarmonicsNamesMap.put("H42_UL2_[%]",winPQ_H25_UL3);
		winPQHarmonicsNamesMap.put("H42_UL3_[%]",winPQ_H26_UL3);
		winPQHarmonicsNamesMap.put("H43_UL1_[%]",winPQ_H27_UL3);
		winPQHarmonicsNamesMap.put("H43_UL2_[%]",winPQ_H28_UL3);
		winPQHarmonicsNamesMap.put("H43_UL3_[%]",winPQ_H29_UL3);
		winPQHarmonicsNamesMap.put("H44_UL1_[%]",winPQ_H30_UL3);
		winPQHarmonicsNamesMap.put("H44_UL2_[%]",winPQ_H31_UL3);
		winPQHarmonicsNamesMap.put("H44_UL3_[%]",winPQ_H32_UL3);
		winPQHarmonicsNamesMap.put("H45_UL1_[%]",winPQ_H33_UL3);
		winPQHarmonicsNamesMap.put("H45_UL2_[%]",winPQ_H34_UL3);
		winPQHarmonicsNamesMap.put("H45_UL3_[%]",winPQ_H35_UL3);
		winPQHarmonicsNamesMap.put("H46_UL1_[%]",winPQ_H36_UL3);
		winPQHarmonicsNamesMap.put("H46_UL2_[%]",winPQ_H37_UL3);
		winPQHarmonicsNamesMap.put("H46_UL3_[%]",winPQ_H38_UL3);
		winPQHarmonicsNamesMap.put("H47_UL1_[%]",winPQ_H39_UL3);
		winPQHarmonicsNamesMap.put("H47_UL2_[%]",winPQ_H40_UL3);
		winPQHarmonicsNamesMap.put("H47_UL3_[%]",winPQ_H41_UL3);
		winPQHarmonicsNamesMap.put("H48_UL1_[%]",winPQ_H42_UL3);
		winPQHarmonicsNamesMap.put("H48_UL2_[%]",winPQ_H43_UL3);
		winPQHarmonicsNamesMap.put("H48_UL3_[%]",winPQ_H44_UL3);
		winPQHarmonicsNamesMap.put("H49_UL1_[%]",winPQ_H45_UL3);
		winPQHarmonicsNamesMap.put("H49_UL2_[%]",winPQ_H46_UL3);
		winPQHarmonicsNamesMap.put("H49_UL3_[%]",winPQ_H47_UL3);
		winPQHarmonicsNamesMap.put("H50_UL1_[%]",winPQ_H48_UL3);
		winPQHarmonicsNamesMap.put("H50_UL2_[%]",winPQ_H49_UL3);
		winPQHarmonicsNamesMap.put("H50_UL3_[%]",winPQ_H50_UL3);
	}
	public static Map<UnitaryNames, Integer> parseNames(List<String> names) {
		return getUnitaryNamesIntegerMap(names, winPQNamesMap);
	}

	public static Map<UnitaryNames, Integer> parseHarmonicsNames(List<String> names) {
		return getUnitaryNamesIntegerMap(names, winPQHarmonicsNamesMap);
	}

	private static Map<UnitaryNames, Integer> getUnitaryNamesIntegerMap(List<String> names, Map<String, UnitaryNames> parsingMap ) {
		Map<UnitaryNames, Integer> unitaryNamesMap = new TreeMap<>();

		AtomicInteger i = new AtomicInteger(0);
		names.stream().forEach(element -> {
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

	public static char parseFlag(String flag) {
		return flag.equals("X") ? 'x' : 'o';
	}

	public static double parseDouble(String record, UnitaryNames unitaryName) throws ParseException {
		double d = 0.0;
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
