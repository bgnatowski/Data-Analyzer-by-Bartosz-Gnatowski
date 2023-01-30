package agh.inzapp.inzynierka.utils.parsers;

import agh.inzapp.inzynierka.models.enums.UniNames;
import agh.inzapp.inzynierka.utils.FxmlUtils;
import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;

import java.text.NumberFormat;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;
import java.util.concurrent.atomic.AtomicReference;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static agh.inzapp.inzynierka.models.enums.UniNames.*;
import static agh.inzapp.inzynierka.models.enums.UniNames.tan_phi_L3_Cmin_avg;

public class SonelParser {
	private static final List<String> dateFormatPatterns = new ArrayList<>();

	static {
		dateFormatPatterns.add("d.MM.yyyy");
		dateFormatPatterns.add("d-MM-yyyy");
		dateFormatPatterns.add("d/MM/yyyy");
		dateFormatPatterns.add("yyyy/MM/d");
		dateFormatPatterns.add("yyyy-MM-d");
	}

	public static List<UniNames> parseNames(List<String> names) {
		List<UniNames> uniNamesList = new ArrayList<>();
		names.forEach(uniName -> {
			final String name = uniName.trim();
			final boolean notContainsAnyLine = !name.contains("L1") && !name.contains("L2") && !name.contains("L3");
			// this if-else statement is because char encoding of sum sign so in .exe do not work
			if (matchDate(name)) uniNamesList.add(Date);
			else if (matchTime(name)) uniNamesList.add(Time);
			else if (name.equals("E")) uniNamesList.add(Flag_E);
			else if (name.equals("P")) uniNamesList.add(Flag_P);
			else if (name.equals("G")) uniNamesList.add(Flag_G);
			else if (name.equals("T")) uniNamesList.add(Flag_T);
			else if (name.equals("A")) uniNamesList.add(Flag_A);
			else if (name.startsWith("f")) {
				if (name.contains("L1")) {
					if (matchMin(name)) uniNamesList.add(f_L1_min);
					else if (matchMax(name)) uniNamesList.add(f_L1_max);
					else if (matchAvg(name)) uniNamesList.add(f_L1_avg); else uniNamesList.add(NONE);
				} else if (name.contains("L2")) {
					if (matchMin(name)) uniNamesList.add(f_L2_min);
					else if (matchMax(name)) uniNamesList.add(f_L2_max);
					else if (matchAvg(name)) uniNamesList.add(f_L2_avg); else uniNamesList.add(NONE);
				} else if (name.contains("L3")) {
					if (matchMin(name)) uniNamesList.add(f_L3_min);
					else if (matchMax(name)) uniNamesList.add(f_L3_max);
					else if (matchAvg(name)) uniNamesList.add(f_L3_avg); else uniNamesList.add(NONE);
				} else {
					uniNamesList.add(NONE);
				}
			} else if (name.startsWith("U L")) {
				if (name.contains("L1") && !name.contains("L12")) {
					if (matchMin(name)) uniNamesList.add(UL1_min);
					else if (matchMax(name)) uniNamesList.add(UL1_max);
					else if (matchAvg(name)) uniNamesList.add(UL1_avg); else uniNamesList.add(NONE);
				}else if (name.contains("L2") && !name.contains("L23")) {
					if (matchMin(name)) uniNamesList.add(UL2_min);
					else if (matchMax(name)) uniNamesList.add(UL2_max);
					else if (matchAvg(name)) uniNamesList.add(UL2_avg); else uniNamesList.add(NONE);
				}else if (name.contains("L3") && !name.contains("L31")) {
					if (matchMin(name)) uniNamesList.add(UL3_min);
					else if (matchMax(name)) uniNamesList.add(UL3_max);
					else if (matchAvg(name)) uniNamesList.add(UL3_avg); else uniNamesList.add(NONE);
				}else if (name.contains("L12")) {
					if (matchMin(name)) uniNamesList.add(U12_min);
					else if (matchMax(name)) uniNamesList.add(U12_max);
					else if (matchAvg(name)) uniNamesList.add(U12_avg); else uniNamesList.add(NONE);
				}else if (name.contains("L23")) {
					if (matchMin(name)) uniNamesList.add(U23_min);
					else if (matchMax(name)) uniNamesList.add(U23_max);
					else if (matchAvg(name)) uniNamesList.add(U23_avg); else uniNamesList.add(NONE);
				}else if (name.contains("L31")) {
					if (matchMin(name)) uniNamesList.add(U31_min);
					else if (matchMax(name)) uniNamesList.add(U31_max);
					else if (matchAvg(name)) uniNamesList.add(U31_avg); else uniNamesList.add(NONE);
				} else {
					uniNamesList.add(NONE);
				}
			} else if(name.startsWith("U N-PE")){
				if (matchMin(name)) uniNamesList.add(U_NPE_min);
				else if (matchMax(name)) uniNamesList.add(U_NPE_max);
				else if (matchAvg(name)) uniNamesList.add(U_NPE_avg); else uniNamesList.add(NONE);
			} else if(name.startsWith("I *")){
				if (name.contains("L1")) {
					if (matchMin(name)) uniNamesList.add(IL1_min);
					else if (matchMax(name)) uniNamesList.add(IL1_max);
					else if (matchAvg(name)) uniNamesList.add(IL1_avg); else uniNamesList.add(NONE);
				} else if (name.contains("L2")) {
					if (matchMin(name)) uniNamesList.add(IL2_min);
					else if (matchMax(name)) uniNamesList.add(IL2_max);
					else if (matchAvg(name)) uniNamesList.add(IL2_avg); else uniNamesList.add(NONE);
				} else if (name.contains("L3")) {
					if (matchMin(name)) uniNamesList.add(IL3_min);
					else if (matchMax(name)) uniNamesList.add(IL3_max);
					else if (matchAvg(name)) uniNamesList.add(IL3_avg); else uniNamesList.add(NONE);
				} else if (name.contains("*N")){
					if (matchMin(name)) uniNamesList.add(IN_min);
					else if (matchMax(name)) uniNamesList.add(IN_max);
					else if (matchAvg(name)) uniNamesList.add(IN_avg); else uniNamesList.add(NONE);
				}
			} else if(name.startsWith("Pst")){
				if (name.contains("L1") && !name.contains("L12")){
					uniNamesList.add(Pst_UL1);
				} else if (name.contains("L2") && !name.contains("L23")){
					uniNamesList.add(Pst_UL2);
				} else if (name.contains("L3") && !name.contains("L31")){
					uniNamesList.add(Pst_UL3);
				} else if (name.contains("L12")){
					uniNamesList.add(Pst_U12);
				} else if (name.contains("L23")){
					uniNamesList.add(Pst_U23);
				} else if (name.contains("L31")){
					uniNamesList.add(Pst_U31);
				} else {
					uniNamesList.add(NONE);
				}
			} else if(name.startsWith("Plt")){
				if (name.contains("L1") && !name.contains("L12")){
					uniNamesList.add(Plt_L1);
				} else if (name.contains("L2") && !name.contains("L23")){
					uniNamesList.add(Plt_L2);
				} else if (name.contains("L3") && !name.contains("L31")){
					uniNamesList.add(Plt_L3);
				} else if (name.contains("L12")){
					uniNamesList.add(Plt_U12);
				} else if (name.contains("L23")){
					uniNamesList.add(Plt_U23);
				} else if (name.contains("L31")){
					uniNamesList.add(Plt_U31);
				} else {
					uniNamesList.add(NONE);
				}
			} else if(match("U0/U1", name)){
				if (matchMin(name)) uniNamesList.add(U0_U1_min);
				else if (matchMax(name)) uniNamesList.add(U0_U1_max);
				else if (matchAvg(name)) uniNamesList.add(U0_U1_avg); else uniNamesList.add(NONE);
			} else if(match("U2/U1", name)){
				if (matchMin(name)) uniNamesList.add(U2_U1_min);
				else if (matchMax(name)) uniNamesList.add(U2_U1_max);
				else if (matchAvg(name)) uniNamesList.add(U2_U1_avg); else uniNamesList.add(NONE);
			} else if(match("U0 ", name)){
				if (matchMin(name)) uniNamesList.add(U0_min_total);
				else if (matchMax(name)) uniNamesList.add(U0_max_total);
				else if (matchAvg(name)) uniNamesList.add(U0_avg_total); else uniNamesList.add(NONE);
			} else if(name.startsWith("U1")){
				if (matchMin(name)) uniNamesList.add(U1_min_total);
				else if (matchMax(name)) uniNamesList.add(U1_max_total);
				else if (matchAvg(name)) uniNamesList.add(U1_avg_total); else uniNamesList.add(NONE);
			} else if(match("U2 ", name)){
				if (matchMin(name)) uniNamesList.add(U2_min_total);
				else if (matchMax(name)) uniNamesList.add(U2_max_total);
				else if (matchAvg(name)) uniNamesList.add(U2_avg_total); else uniNamesList.add(NONE);
			}else if(match("I0/I1", name)){
				if (matchMin(name)) uniNamesList.add(I0_I1_min);
				else if (matchMax(name)) uniNamesList.add(I0_I1_max);
				else if (matchAvg(name)) uniNamesList.add(I0_I1_avg); else uniNamesList.add(NONE);
			} else if(match("I2/I1", name)){
				if (matchMin(name)) uniNamesList.add(I2_I1_min);
				else if (matchMax(name)) uniNamesList.add(I2_I1_max);
				else if (matchAvg(name)) uniNamesList.add(I2_I1_avg); else uniNamesList.add(NONE);
			} else if(match("I0 ", name)){
				if (matchMin(name)) uniNamesList.add(I0_min_total);
				else if (matchMax(name)) uniNamesList.add(I0_max_total);
				else if (matchAvg(name)) uniNamesList.add(I0_avg_total); else uniNamesList.add(NONE);
			} else if(name.startsWith("I1")){
				if (matchMin(name)) uniNamesList.add(I1_min_total);
				else if (matchMax(name)) uniNamesList.add(I1_max_total);
				else if (matchAvg(name)) uniNamesList.add(I1_avg_total); else uniNamesList.add(NONE);
			} else if(match("I2 ", name)){
				if (matchMin(name)) uniNamesList.add(I2_min_total);
				else if (matchMax(name)) uniNamesList.add(I2_max_total);
				else if (matchAvg(name)) uniNamesList.add(I2_avg_total); else uniNamesList.add(NONE);
			} else if(name.startsWith("CF U")){
				if (name.contains("L1")) {
					if (matchAvg(name)) uniNamesList.add(CF_U_L1_avg); else uniNamesList.add(NONE);
				} else if (name.contains("L2")) {
					if (matchAvg(name)) uniNamesList.add(CF_U_L2_avg); else uniNamesList.add(NONE);
				} else if (name.contains("L3")) {
					if (matchAvg(name)) uniNamesList.add(CF_U_L3_avg); else uniNamesList.add(NONE);
				} else if (name.contains("N-PE")){
					if (matchAvg(name)) uniNamesList.add(CF_U_NPE_avg); else uniNamesList.add(NONE);
				} else {
					uniNamesList.add(NONE);
				}
			} else if(name.startsWith("CF I")){
				if (name.contains("L1")) {
					if (matchAvg(name)) uniNamesList.add(CF_I_L1_avg); else uniNamesList.add(NONE);
				} else if (name.contains("L2")) {
					if (matchAvg(name)) uniNamesList.add(CF_I_L2_avg); else uniNamesList.add(NONE);
				} else if (name.contains("L3")) {
					if (matchAvg(name)) uniNamesList.add(CF_I_L3_avg); else uniNamesList.add(NONE);
				} else if (name.contains("N")){
					if (matchAvg(name)) uniNamesList.add(CF_I_N_avg); else uniNamesList.add(NONE);
				} else {
					uniNamesList.add(NONE);
				}
			}else if(name.startsWith("THD U")){
				if (name.contains("L1") && !name.contains("L12")){
					if (matchAvg(name)) uniNamesList.add(THD_L1); else uniNamesList.add(NONE);
				}else if (name.contains("L2") && !name.contains("L23")){
					if (matchAvg(name)) uniNamesList.add(THD_L2); else uniNamesList.add(NONE);
				}else if (name.contains("L3") && !name.contains("L31")){
					if (matchAvg(name)) uniNamesList.add(THD_L3); else uniNamesList.add(NONE);
				}else if (name.contains("L12")){
					if (matchAvg(name)) uniNamesList.add(THD_12); else uniNamesList.add(NONE);
				} else if (name.contains("L23")){
					if (matchAvg(name)) uniNamesList.add(THD_23); else uniNamesList.add(NONE);
				} else if (name.contains("L31")){
					if (matchAvg(name)) uniNamesList.add(THD_31); else uniNamesList.add(NONE);
				} else {
					uniNamesList.add(NONE);
				}
			} else if (name.startsWith("U H") && !name.contains("N-PE")) {
				if (matchAvg(name)) {
					int i = Integer.parseInt(name.substring(3, 6).trim());
					switch (i) {
						case 1 -> {
							if (name.contains("L1")) uniNamesList.add(H1_UL1);
							else if (name.contains("L2")) uniNamesList.add(H1_UL2);
							else if (name.contains("L3")) uniNamesList.add(H1_UL3); else uniNamesList.add(NONE);
						}
						case 2 -> {
							if (name.contains("L1")) uniNamesList.add(H2_UL1);
							else if (name.contains("L2")) uniNamesList.add(H2_UL2);
							else if (name.contains("L3")) uniNamesList.add(H2_UL3); else uniNamesList.add(NONE);
						}
						case 3 -> {
							if (name.contains("L1")) uniNamesList.add(H3_UL1);
							else if (name.contains("L2")) uniNamesList.add(H3_UL2);
							else if (name.contains("L3")) uniNamesList.add(H3_UL3); else uniNamesList.add(NONE);
						}
						case 4 -> {
							if (name.contains("L1")) uniNamesList.add(H4_UL1);
							else if (name.contains("L2")) uniNamesList.add(H4_UL2);
							else if (name.contains("L3")) uniNamesList.add(H4_UL3); else uniNamesList.add(NONE);
						}
						case 5 -> {
							if (name.contains("L1")) uniNamesList.add(H5_UL1);
							else if (name.contains("L2")) uniNamesList.add(H5_UL2);
							else if (name.contains("L3")) uniNamesList.add(H5_UL3); else uniNamesList.add(NONE);
						}
						case 6 -> {
							if (name.contains("L1")) uniNamesList.add(H6_UL1);
							else if (name.contains("L2")) uniNamesList.add(H6_UL2);
							else if (name.contains("L3")) uniNamesList.add(H6_UL3); else uniNamesList.add(NONE);
						}
						case 7 -> {
							if (name.contains("L1")) uniNamesList.add(H7_UL1);
							else if (name.contains("L2")) uniNamesList.add(H7_UL2);
							else if (name.contains("L3")) uniNamesList.add(H7_UL3); else uniNamesList.add(NONE);
						}
						case 8 -> {
							if (name.contains("L1")) uniNamesList.add(H8_UL1);
							else if (name.contains("L2")) uniNamesList.add(H8_UL2);
							else if (name.contains("L3")) uniNamesList.add(H8_UL3); else uniNamesList.add(NONE);
						}
						case 9 -> {
							if (name.contains("L1")) uniNamesList.add(H9_UL1);
							else if (name.contains("L2")) uniNamesList.add(H9_UL2);
							else if (name.contains("L3")) uniNamesList.add(H9_UL3); else uniNamesList.add(NONE);
						}
						case 10 -> {
							if (name.contains("L1")) uniNamesList.add(H10_UL1);
							else if (name.contains("L2")) uniNamesList.add(H10_UL2);
							else if (name.contains("L3")) uniNamesList.add(H10_UL3); else uniNamesList.add(NONE);
						}
						case 11 -> {
							if (name.contains("L1")) uniNamesList.add(H11_UL1);
							else if (name.contains("L2")) uniNamesList.add(H11_UL2);
							else if (name.contains("L3")) uniNamesList.add(H11_UL3); else uniNamesList.add(NONE);
						}
						case 12 -> {
							if (name.contains("L1")) uniNamesList.add(H12_UL1);
							else if (name.contains("L2")) uniNamesList.add(H12_UL2);
							else if (name.contains("L3")) uniNamesList.add(H12_UL3); else uniNamesList.add(NONE);
						}
						case 13 -> {
							if (name.contains("L1")) uniNamesList.add(H13_UL1);
							else if (name.contains("L2")) uniNamesList.add(H13_UL2);
							else if (name.contains("L3")) uniNamesList.add(H13_UL3); else uniNamesList.add(NONE);
						}
						case 14 -> {
							if (name.contains("L1")) uniNamesList.add(H14_UL1);
							else if (name.contains("L2")) uniNamesList.add(H14_UL2);
							else if (name.contains("L3")) uniNamesList.add(H14_UL3); else uniNamesList.add(NONE);
						}
						case 15 -> {
							if (name.contains("L1")) uniNamesList.add(H15_UL1);
							else if (name.contains("L2")) uniNamesList.add(H15_UL2);
							else if (name.contains("L3")) uniNamesList.add(H15_UL3); else uniNamesList.add(NONE);
						}
						case 16 -> {
							if (name.contains("L1")) uniNamesList.add(H16_UL1);
							else if (name.contains("L2")) uniNamesList.add(H16_UL2);
							else if (name.contains("L3")) uniNamesList.add(H16_UL3); else uniNamesList.add(NONE);
						}
						case 17 -> {
							if (name.contains("L1")) uniNamesList.add(H17_UL1);
							else if (name.contains("L2")) uniNamesList.add(H17_UL2);
							else if (name.contains("L3")) uniNamesList.add(H17_UL3); else uniNamesList.add(NONE);
						}
						case 18 -> {
							if (name.contains("L1")) uniNamesList.add(H18_UL1);
							else if (name.contains("L2")) uniNamesList.add(H18_UL2);
							else if (name.contains("L3")) uniNamesList.add(H18_UL3); else uniNamesList.add(NONE);
						}
						case 19 -> {
							if (name.contains("L1")) uniNamesList.add(H19_UL1);
							else if (name.contains("L2")) uniNamesList.add(H19_UL2);
							else if (name.contains("L3")) uniNamesList.add(H19_UL3); else uniNamesList.add(NONE);
						}
						case 20 -> {
							if (name.contains("L1")) uniNamesList.add(H20_UL1);
							else if (name.contains("L2")) uniNamesList.add(H20_UL2);
							else if (name.contains("L3")) uniNamesList.add(H20_UL3); else uniNamesList.add(NONE);
						}
						case 21 -> {
							if (name.contains("L1")) uniNamesList.add(H21_UL1);
							else if (name.contains("L2")) uniNamesList.add(H21_UL2);
							else if (name.contains("L3")) uniNamesList.add(H21_UL3); else uniNamesList.add(NONE);
						}
						case 22 -> {
							if (name.contains("L1")) uniNamesList.add(H22_UL1);
							else if (name.contains("L2")) uniNamesList.add(H22_UL2);
							else if (name.contains("L3")) uniNamesList.add(H22_UL3); else uniNamesList.add(NONE);
						}
						case 23 -> {
							if (name.contains("L1")) uniNamesList.add(H23_UL1);
							else if (name.contains("L2")) uniNamesList.add(H23_UL2);
							else if (name.contains("L3")) uniNamesList.add(H23_UL3); else uniNamesList.add(NONE);
						}
						case 24 -> {
							if (name.contains("L1")) uniNamesList.add(H24_UL1);
							else if (name.contains("L2")) uniNamesList.add(H24_UL2);
							else if (name.contains("L3")) uniNamesList.add(H24_UL3); else uniNamesList.add(NONE);
						}
						case 25 -> {
							if (name.contains("L1")) uniNamesList.add(H25_UL1);
							else if (name.contains("L2")) uniNamesList.add(H25_UL2);
							else if (name.contains("L3")) uniNamesList.add(H25_UL3); else uniNamesList.add(NONE);
						}
						case 26 -> {
							if (name.contains("L1")) uniNamesList.add(H26_UL1);
							else if (name.contains("L2")) uniNamesList.add(H26_UL2);
							else if (name.contains("L3")) uniNamesList.add(H26_UL3); else uniNamesList.add(NONE);
						}
						case 27 -> {
							if (name.contains("L1")) uniNamesList.add(H27_UL1);
							else if (name.contains("L2")) uniNamesList.add(H27_UL2);
							else if (name.contains("L3")) uniNamesList.add(H27_UL3); else uniNamesList.add(NONE);
						}
						case 28 -> {
							if (name.contains("L1")) uniNamesList.add(H28_UL1);
							else if (name.contains("L2")) uniNamesList.add(H28_UL2);
							else if (name.contains("L3")) uniNamesList.add(H28_UL3); else uniNamesList.add(NONE);
						}
						case 29 -> {
							if (name.contains("L1")) uniNamesList.add(H29_UL1);
							else if (name.contains("L2")) uniNamesList.add(H29_UL2);
							else if (name.contains("L3")) uniNamesList.add(H29_UL3); else uniNamesList.add(NONE);
						}
						case 30 -> {
							if (name.contains("L1")) uniNamesList.add(H30_UL1);
							else if (name.contains("L2")) uniNamesList.add(H30_UL2);
							else if (name.contains("L3")) uniNamesList.add(H30_UL3); else uniNamesList.add(NONE);
						}
						case 31 -> {
							if (name.contains("L1")) uniNamesList.add(H31_UL1);
							else if (name.contains("L2")) uniNamesList.add(H31_UL2);
							else if (name.contains("L3")) uniNamesList.add(H31_UL3); else uniNamesList.add(NONE);
						}
						case 32 -> {
							if (name.contains("L1")) uniNamesList.add(H32_UL1);
							else if (name.contains("L2")) uniNamesList.add(H32_UL2);
							else if (name.contains("L3")) uniNamesList.add(H32_UL3); else uniNamesList.add(NONE);
						}
						case 33 -> {
							if (name.contains("L1")) uniNamesList.add(H33_UL1);
							else if (name.contains("L2")) uniNamesList.add(H33_UL2);
							else if (name.contains("L3")) uniNamesList.add(H33_UL3); else uniNamesList.add(NONE);
						}
						case 34 -> {
							if (name.contains("L1")) uniNamesList.add(H34_UL1);
							else if (name.contains("L2")) uniNamesList.add(H34_UL2);
							else if (name.contains("L3")) uniNamesList.add(H34_UL3); else uniNamesList.add(NONE);
						}
						case 35 -> {
							if (name.contains("L1")) uniNamesList.add(H35_UL1);
							else if (name.contains("L2")) uniNamesList.add(H35_UL2);
							else if (name.contains("L3")) uniNamesList.add(H35_UL3); else uniNamesList.add(NONE);
						}
						case 36 -> {
							if (name.contains("L1")) uniNamesList.add(H36_UL1);
							else if (name.contains("L2")) uniNamesList.add(H36_UL2);
							else if (name.contains("L3")) uniNamesList.add(H36_UL3); else uniNamesList.add(NONE);
						}
						case 37 -> {
							if (name.contains("L1")) uniNamesList.add(H37_UL1);
							else if (name.contains("L2")) uniNamesList.add(H37_UL2);
							else if (name.contains("L3")) uniNamesList.add(H37_UL3); else uniNamesList.add(NONE);
						}
						case 38 -> {
							if (name.contains("L1")) uniNamesList.add(H38_UL1);
							else if (name.contains("L2")) uniNamesList.add(H38_UL2);
							else if (name.contains("L3")) uniNamesList.add(H38_UL3); else uniNamesList.add(NONE);
						}
						case 39 -> {
							if (name.contains("L1")) uniNamesList.add(H39_UL1);
							else if (name.contains("L2")) uniNamesList.add(H39_UL2);
							else if (name.contains("L3")) uniNamesList.add(H39_UL3); else uniNamesList.add(NONE);
						}
						case 40 -> {
							if (name.contains("L1")) uniNamesList.add(H40_UL1);
							else if (name.contains("L2")) uniNamesList.add(H40_UL2);
							else if (name.contains("L3")) uniNamesList.add(H40_UL3); else uniNamesList.add(NONE);
						}
						case 41 -> {
							if (name.contains("L1")) uniNamesList.add(H41_UL1);
							else if (name.contains("L2")) uniNamesList.add(H41_UL2);
							else if (name.contains("L3")) uniNamesList.add(H41_UL3); else uniNamesList.add(NONE);
						}
						case 42 -> {
							if (name.contains("L1")) uniNamesList.add(H42_UL1);
							else if (name.contains("L2")) uniNamesList.add(H42_UL2);
							else if (name.contains("L3")) uniNamesList.add(H42_UL3); else uniNamesList.add(NONE);
						}
						case 43 -> {
							if (name.contains("L1")) uniNamesList.add(H43_UL1);
							else if (name.contains("L2")) uniNamesList.add(H43_UL2);
							else if (name.contains("L3")) uniNamesList.add(H43_UL3); else uniNamesList.add(NONE);
						}
						case 44 -> {
							if (name.contains("L1")) uniNamesList.add(H44_UL1);
							else if (name.contains("L2")) uniNamesList.add(H44_UL2);
							else if (name.contains("L3")) uniNamesList.add(H44_UL3); else uniNamesList.add(NONE);
						}
						case 45 -> {
							if (name.contains("L1")) uniNamesList.add(H45_UL1);
							else if (name.contains("L2")) uniNamesList.add(H45_UL2);
							else if (name.contains("L3")) uniNamesList.add(H45_UL3); else uniNamesList.add(NONE);
						}
						case 46 -> {
							if (name.contains("L1")) uniNamesList.add(H46_UL1);
							else if (name.contains("L2")) uniNamesList.add(H46_UL2);
							else if (name.contains("L3")) uniNamesList.add(H46_UL3); else uniNamesList.add(NONE);
						}
						case 47 -> {
							if (name.contains("L1")) uniNamesList.add(H47_UL1);
							else if (name.contains("L2")) uniNamesList.add(H47_UL2);
							else if (name.contains("L3")) uniNamesList.add(H47_UL3); else uniNamesList.add(NONE);
						}
						case 48 -> {
							if (name.contains("L1")) uniNamesList.add(H48_UL1);
							else if (name.contains("L2")) uniNamesList.add(H48_UL2);
							else if (name.contains("L3")) uniNamesList.add(H48_UL3); else uniNamesList.add(NONE);
						}
						case 49 -> {
							if (name.contains("L1")) uniNamesList.add(H49_UL1);
							else if (name.contains("L2")) uniNamesList.add(H49_UL2);
							else if (name.contains("L3")) uniNamesList.add(H49_UL3); else uniNamesList.add(NONE);
						}
						case 50 -> {
							if (name.contains("L1")) uniNamesList.add(H50_UL1);
							else if (name.contains("L2")) uniNamesList.add(H50_UL2);
							else if (name.contains("L3")) uniNamesList.add(H50_UL3); else uniNamesList.add(NONE);

						}
					}
				} else {
					uniNamesList.add(NONE);
				}
			} else if (name.startsWith("K ")){
				if (name.contains("L1") && !name.contains("L12")) {
					if (matchAvg(name)) uniNamesList.add(K_L1_avg); else uniNamesList.add(NONE);
				}else if (name.contains("L2") && !name.contains("L23")) {
					if (matchAvg(name)) uniNamesList.add(K_L2_avg); else uniNamesList.add(NONE);
				}else if (name.contains("L3") && !name.contains("L31")) {
					if (matchAvg(name)) uniNamesList.add(K_L3_avg); else uniNamesList.add(NONE);
				}else if (name.contains("N")){
					if (matchAvg(name)) uniNamesList.add(K_N_avg); else uniNamesList.add(NONE);
				}else {
					uniNamesList.add(NONE);
				}
			} else if(match("PF ", name) && !name.contains("H")){
				if (name.contains("L1")) {
					if (matchMin(name)) uniNamesList.add(PF_L1_min);
					else if (matchMax(name)) uniNamesList.add(PF_L1_max);
					else if (matchAvg(name)) uniNamesList.add(PF_L1_avg); else uniNamesList.add(NONE);
				} else if (name.contains("L2")) {
					if (matchMin(name)) uniNamesList.add(PF_L3_min);
					else if (matchMax(name)) uniNamesList.add(PF_L3_max);
					else if (matchAvg(name)) uniNamesList.add(PF_L3_avg); else uniNamesList.add(NONE);
				} else if (name.contains("L3")) {
					if (matchMin(name)) uniNamesList.add(PF_L3_min);
					else if (matchMax(name)) uniNamesList.add(PF_L3_max);
					else if (matchAvg(name)) uniNamesList.add(PF_L3_avg); else uniNamesList.add(NONE);
				} else if (notContainsAnyLine){
					if (matchMin(name)) uniNamesList.add(PF_total_min);
					else if (matchMax(name)) uniNamesList.add(PF_total_max);
					else if (matchAvg(name)) uniNamesList.add(PF_total_avg); else uniNamesList.add(NONE);
				} else {
					uniNamesList.add(NONE);
				}
			} else if(match("cos", name)){
				if (name.contains("L1")) {
					if (matchAvg(name)) uniNamesList.add(cos_phi_L1_avg); else uniNamesList.add(NONE);
				} else if (name.contains("L2")) {
					if (matchAvg(name)) uniNamesList.add(cos_phi_L2_avg); else uniNamesList.add(NONE);
				} else if (name.contains("L3")) {
					if (matchAvg(name)) uniNamesList.add(cos_phi_L3_avg); else uniNamesList.add(NONE);
				} else if (notContainsAnyLine){
					if (matchAvg(name)) uniNamesList.add(cos_phi_total_avg); else uniNamesList.add(NONE);
				} else {
					uniNamesList.add(NONE);
				}
			} else if(match("tan|tg", name)){
				if(name.contains("L+")){
					if (name.contains("L1")) {
						if (matchAvg(name)) uniNamesList.add(tan_phi_L1_Lplus_avg); else uniNamesList.add(NONE);
					} else if (name.contains("L2")) {
						if (matchAvg(name)) uniNamesList.add(tan_phi_L2_Lplus_avg); else uniNamesList.add(NONE);
					} else if (name.contains("L3")) {
						if (matchAvg(name)) uniNamesList.add(tan_phi_L3_Lplus_avg); else uniNamesList.add(NONE);
					} else if (notContainsAnyLine){
						if (matchAvg(name)) uniNamesList.add(tan_phi_total_Lplus_avg); else uniNamesList.add(NONE);
					} else {
						uniNamesList.add(NONE);
					}
				} else if(name.contains("C-")){
					if (name.contains("L1")) {
						if (matchAvg(name)) uniNamesList.add(tan_phi_L1_Cmin_avg); else uniNamesList.add(NONE);
					} else if (name.contains("L2")) {
						if (matchAvg(name)) uniNamesList.add(tan_phi_L2_Cmin_avg); else uniNamesList.add(NONE);
					} else if (name.contains("L3")) {
						if (matchAvg(name)) uniNamesList.add(tan_phi_L3_Cmin_avg); else uniNamesList.add(NONE);
					} else if (notContainsAnyLine){
						if (matchAvg(name)) uniNamesList.add(tan_phi_total_Cmin_avg); else uniNamesList.add(NONE);
					} else {
						uniNamesList.add(NONE);
					}
				} else if(name.contains("L-")){
					if (name.contains("L1")) {
						if (matchAvg(name)) uniNamesList.add(tan_phi_L1_Lmin_avg); else uniNamesList.add(NONE);
					} else if (name.contains("L2")) {
						if (matchAvg(name)) uniNamesList.add(tan_phi_L2_Lmin_avg); else uniNamesList.add(NONE);
					} else if (name.contains("L3")) {
						if (matchAvg(name)) uniNamesList.add(tan_phi_L3_Lmin_avg); else uniNamesList.add(NONE);
					} else if (notContainsAnyLine){
						if (matchAvg(name)) uniNamesList.add(tan_phi_total_Lmin_avg); else uniNamesList.add(NONE);
					} else {
						uniNamesList.add(NONE);
					}
				} else if(name.contains("C+")){
					if (name.contains("L1")) {
						if (matchAvg(name)) uniNamesList.add(tan_phi_L1_Cplus_avg); else uniNamesList.add(NONE);
					} else if (name.contains("L2")) {
						if (matchAvg(name)) uniNamesList.add(tan_phi_L2_Cplus_avg); else uniNamesList.add(NONE);
					} else if (name.contains("L3")) {
						if (matchAvg(name)) uniNamesList.add(tan_phi_L3_Cplus_avg); else uniNamesList.add(NONE);
					} else if (notContainsAnyLine){
						if (matchAvg(name)) uniNamesList.add(tan_phi_total_Cplus_avg); else uniNamesList.add(NONE);
					} else {
						uniNamesList.add(NONE);
					}
				} else {
					uniNamesList.add(NONE);
				}
			} else if (match("P ", name) && !name.startsWith("P H")){
				if (name.contains("L1")) {
					if (matchMin(name)) uniNamesList.add(P_L1_min);
					else if (matchMax(name)) uniNamesList.add(P_L1_max);
					else if (matchAvg(name)) uniNamesList.add(P_L1_avg); else uniNamesList.add(NONE);
				} else if (name.contains("L2")) {
					if (matchMin(name)) uniNamesList.add(P_L2_min);
					else if (matchMax(name)) uniNamesList.add(P_L2_max);
					else if (matchAvg(name)) uniNamesList.add(P_L2_avg); else uniNamesList.add(NONE);
				} else if (name.contains("L3")) {
					if (matchMin(name)) uniNamesList.add(P_L3_min);
					else if (matchMax(name)) uniNamesList.add(P_L3_max);
					else if (matchAvg(name)) uniNamesList.add(P_L3_avg); else uniNamesList.add(NONE);
				} else if (notContainsAnyLine){
					if (matchMin(name)) uniNamesList.add(P_total_min);
					else if (matchMax(name)) uniNamesList.add(P_total_max);
					else if (matchAvg(name)) uniNamesList.add(P_total_avg); else uniNamesList.add(NONE);
				} else {
					uniNamesList.add(NONE);
				}
			} else if (match("P+ ", name) && !name.contains("H")){
				if (name.contains("L1")) {
					if (matchAvg(name)) uniNamesList.add(P_plus_L1_avg); else uniNamesList.add(NONE);
				} else if (name.contains("L2")) {
					if (matchAvg(name)) uniNamesList.add(P_plus_L2_avg); else uniNamesList.add(NONE);
				} else if (name.contains("L3")) {
					if (matchAvg(name)) uniNamesList.add(P_plus_L3_avg); else uniNamesList.add(NONE);
				} else if (notContainsAnyLine){
					if (matchAvg(name)) uniNamesList.add(P_plus_total_avg); else uniNamesList.add(NONE);
				} else {
					uniNamesList.add(NONE);
				}
			} else if (match("P- ", name) && !name.contains("H")){
				if (name.contains("L1")) {
					if (matchAvg(name)) uniNamesList.add(P_min_L1_avg); else uniNamesList.add(NONE);
				} else if (name.contains("L2")) {
					if (matchAvg(name)) uniNamesList.add(P_min_L2_avg); else uniNamesList.add(NONE);
				} else if (name.contains("L3")) {
					if (matchAvg(name)) uniNamesList.add(P_min_L3_avg); else uniNamesList.add(NONE);
				} else if (notContainsAnyLine){
					if (matchAvg(name)) uniNamesList.add(P_min_total_avg); else uniNamesList.add(NONE);
				} else {
					uniNamesList.add(NONE);
				}
			} else if (match("Q1 ", name) && !name.contains("H")){
				if (name.contains("L1")) {
					if (matchMin(name)) uniNamesList.add(Q_L1_min);
					else if (matchMax(name)) uniNamesList.add(Q_L1_max);
					else if (matchAvg(name)) uniNamesList.add(Q_L1_avg); else uniNamesList.add(NONE);
				} else if (name.contains("L2")) {
					if (matchMin(name)) uniNamesList.add(Q_L2_min);
					else if (matchMax(name)) uniNamesList.add(Q_L2_max);
					else if (matchAvg(name)) uniNamesList.add(Q_L2_avg); else uniNamesList.add(NONE);
				} else if (name.contains("L3")) {
					if (matchMin(name)) uniNamesList.add(Q_L3_min);
					else if (matchMax(name)) uniNamesList.add(Q_L3_max);
					else if (matchAvg(name)) uniNamesList.add(Q_L3_avg); else uniNamesList.add(NONE);
				} else if (notContainsAnyLine){
					if (matchMin(name)) uniNamesList.add(Q_total_min);
					else if (matchMax(name)) uniNamesList.add(Q_total_max);
					else if (matchAvg(name)) uniNamesList.add(Q_total_avg); else uniNamesList.add(NONE);
				} else {
					uniNamesList.add(NONE);
				}
			} else if (match("Sn ", name) && !name.contains("H")){
				if (name.contains("L1")) {
					if (matchAvg(name)) uniNamesList.add(Sn_L1_avg); else uniNamesList.add(NONE);
				} else if (name.contains("L2")) {
					if (matchAvg(name)) uniNamesList.add(Sn_L2_avg); else uniNamesList.add(NONE);
				} else if (name.contains("L3")) {
					if (matchAvg(name)) uniNamesList.add(Sn_L3_avg); else uniNamesList.add(NONE);
				} else if (notContainsAnyLine){
					if (matchAvg(name)) uniNamesList.add(Sn_total); else uniNamesList.add(NONE);
				} else {
					uniNamesList.add(NONE);
				}
			} else if (match("S ", name) && !name.contains("H")){
				if (name.contains("L1")) {
					if (matchMin(name)) uniNamesList.add(S_L1_min);
					else if (matchMax(name)) uniNamesList.add(S_L1_max);
					else if (matchAvg(name)) uniNamesList.add(S_L1_avg);  else uniNamesList.add(NONE);
				} else if (name.contains("L2")) {
					if (matchMin(name)) uniNamesList.add(S_L2_min);
					else if (matchMax(name)) uniNamesList.add(S_L2_max);
					else if (matchAvg(name)) uniNamesList.add(S_L2_avg);  else uniNamesList.add(NONE);
				} else if (name.contains("L3")) {
					if (matchMin(name)) uniNamesList.add(S_L3_min);
					else if (matchMax(name)) uniNamesList.add(S_L3_max);
					else if (matchAvg(name)) uniNamesList.add(S_L3_avg);  else uniNamesList.add(NONE);
				} else if (notContainsAnyLine){
					if (matchMin(name)) uniNamesList.add(S_total_min);
					else if (matchMax(name)) uniNamesList.add(S_total_max);
					else if (matchAvg(name)) uniNamesList.add(S_total_avg);  else uniNamesList.add(NONE);
				} else {
					uniNamesList.add(NONE);
				}
			} else {
				uniNamesList.add(NONE);
			}
		});

		return uniNamesList;
	}

	private static boolean match(String regex, String name) {
		Pattern p = Pattern.compile(regex);
		Matcher m = p.matcher(name);
		return m.find();
	}

	private static boolean matchDate(String trimmed) {
		Pattern p1 = Pattern.compile("Dat.");
		Matcher m1 = p1.matcher(trimmed);
		return m1.find();
	}

	private static boolean matchTime(String trimmed) {
		Pattern p1 = Pattern.compile("Time.*|Czas.*");
		Matcher m1 = p1.matcher(trimmed);
		return m1.find();
	}

	private static boolean matchMin(String trimmed) {
		Pattern p1 = Pattern.compile("min(\\. [0-9]+ [a-z]+)? \\[");
		Matcher m1 = p1.matcher(trimmed);
		Pattern p2 = Pattern.compile(".+avg.");
		Matcher m2 = p2.matcher(trimmed);
		Pattern p3 = Pattern.compile(".+red.");
		Matcher m3 = p3.matcher(trimmed);
		Pattern p4 = Pattern.compile(".+ma.");
		Matcher m4 = p4.matcher(trimmed);
		return m1.find() && (!m2.find() && !m3.find() && !m4.find());
	}
	private static boolean matchAvg(String trimmed) {
		Pattern p1 = Pattern.compile(".+avg.");
		Matcher m1 = p1.matcher(trimmed);
		Pattern p2 = Pattern.compile(".+red.");
		Matcher m2 = p2.matcher(trimmed);
		return m1.find() || m2.find();
	}
	private static boolean matchMax(String trimmed) {
		Pattern p = Pattern.compile(".+ma.");
		Matcher m = p.matcher(trimmed);
		return m.find();
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
		else throw new ApplicationException(FxmlUtils.getInternalizedPropertyByKey("error.parse.model"));
	}

	public static LocalTime parseTime(String time) {
		final LocalTime parse = LocalTime.parse(time);
		return LocalTime.of(parse.getHour(), parse.getMinute());
	}

	public static String parseFlag(String flag) {
		String trimmed = flag.trim();
		return trimmed.isEmpty() ? " " : trimmed;
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
