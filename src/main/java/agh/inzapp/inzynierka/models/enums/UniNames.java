package agh.inzapp.inzynierka.models.enums;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static agh.inzapp.inzynierka.utils.FxmlUtils.getInternalizedPropertyByKey;

public enum UniNames{
	// Base
	Date(getInternalizedPropertyByKey("date"), getInternalizedPropertyByKey("unit.date")),
	Time(getInternalizedPropertyByKey("time"), getInternalizedPropertyByKey("unit.time")),
	Flag(getInternalizedPropertyByKey("Flag"), getInternalizedPropertyByKey("unit.none")),
	Flag_P(getInternalizedPropertyByKey("Flag_P"), getInternalizedPropertyByKey("unit.none")),
	Flag_G(getInternalizedPropertyByKey("Flag_G"), getInternalizedPropertyByKey("unit.none")),
	Flag_E(getInternalizedPropertyByKey("Flag_E"), getInternalizedPropertyByKey("unit.none")),
	Flag_T(getInternalizedPropertyByKey("Flag_T"), getInternalizedPropertyByKey("unit.none")),
	Flag_A(getInternalizedPropertyByKey("Flag_A"), getInternalizedPropertyByKey("unit.none")),
	U12_avg(getInternalizedPropertyByKey("U12_avg"), getInternalizedPropertyByKey("unit.V")),
	U23_avg(getInternalizedPropertyByKey("U23_avg"), getInternalizedPropertyByKey("unit.V")),
	U31_avg(getInternalizedPropertyByKey("U31_avg"), getInternalizedPropertyByKey("unit.V")),
	U12_max(getInternalizedPropertyByKey("U12_max"), getInternalizedPropertyByKey("unit.V")),
	U23_max(getInternalizedPropertyByKey("U23_max"), getInternalizedPropertyByKey("unit.V")),
	U31_max(getInternalizedPropertyByKey("U31_max"), getInternalizedPropertyByKey("unit.V")),
	U12_min(getInternalizedPropertyByKey("U12_min"), getInternalizedPropertyByKey("unit.V")),
	U23_min(getInternalizedPropertyByKey("U23_min"), getInternalizedPropertyByKey("unit.V")),
	U31_min(getInternalizedPropertyByKey("U31_min"), getInternalizedPropertyByKey("unit.V")),
	UL1_avg(getInternalizedPropertyByKey("UL1_avg"), getInternalizedPropertyByKey("unit.V")),
	UL2_avg(getInternalizedPropertyByKey("UL2_avg"), getInternalizedPropertyByKey("unit.V")),
	UL3_avg(getInternalizedPropertyByKey("UL3_avg"), getInternalizedPropertyByKey("unit.V")),
	UL1_max(getInternalizedPropertyByKey("UL1_max"), getInternalizedPropertyByKey("unit.V")),
	UL2_max(getInternalizedPropertyByKey("UL2_max"), getInternalizedPropertyByKey("unit.V")),
	UL3_max(getInternalizedPropertyByKey("UL3_max"), getInternalizedPropertyByKey("unit.V")),
	UL1_min(getInternalizedPropertyByKey("UL1_min"), getInternalizedPropertyByKey("unit.V")),
	UL2_min(getInternalizedPropertyByKey("UL2_min"), getInternalizedPropertyByKey("unit.V")),
	UL3_min(getInternalizedPropertyByKey("UL3_min"), getInternalizedPropertyByKey("unit.V")),
	U_NPE_avg(getInternalizedPropertyByKey("U_NPE_avg"), getInternalizedPropertyByKey("unit.V")),
	U_NPE_max(getInternalizedPropertyByKey("U_NPE_max"), getInternalizedPropertyByKey("unit.V")),
	U_NPE_min(getInternalizedPropertyByKey("U_NPE_min"), getInternalizedPropertyByKey("unit.V")),
	IL1_avg(getInternalizedPropertyByKey("IL1_avg"), getInternalizedPropertyByKey("unit.A")),
	IL2_avg(getInternalizedPropertyByKey("IL2_avg"), getInternalizedPropertyByKey("unit.A")),
	IL3_avg(getInternalizedPropertyByKey("IL3_avg"), getInternalizedPropertyByKey("unit.A")),
	IN_avg(getInternalizedPropertyByKey("IN_avg"), getInternalizedPropertyByKey("unit.A")),
	IL1_max(getInternalizedPropertyByKey("IL1_max"), getInternalizedPropertyByKey("unit.A")),
	IL2_max(getInternalizedPropertyByKey("IL2_max"), getInternalizedPropertyByKey("unit.A")),
	IL3_max(getInternalizedPropertyByKey("IL3_max"), getInternalizedPropertyByKey("unit.A")),
	IN_max(getInternalizedPropertyByKey("IN_max"), getInternalizedPropertyByKey("unit.A")),
	IL1_min(getInternalizedPropertyByKey("IL1_min"), getInternalizedPropertyByKey("unit.A")),
	IL2_min(getInternalizedPropertyByKey("IL2_min"), getInternalizedPropertyByKey("unit.A")),
	IL3_min(getInternalizedPropertyByKey("IL3_min"), getInternalizedPropertyByKey("unit.A")),
	IN_min(getInternalizedPropertyByKey("IN_min"), getInternalizedPropertyByKey("unit.A")),
	U0_avg_total(getInternalizedPropertyByKey("U0_avg"), getInternalizedPropertyByKey("unit.V")),
	U1_avg_total(getInternalizedPropertyByKey("U1_avg"), getInternalizedPropertyByKey("unit.V")),
	U2_avg_total(getInternalizedPropertyByKey("U2_avg"), getInternalizedPropertyByKey("unit.V")),
	U0_max_total(getInternalizedPropertyByKey("U0_max"), getInternalizedPropertyByKey("unit.V")),
	U1_max_total(getInternalizedPropertyByKey("U1_max"), getInternalizedPropertyByKey("unit.V")),
	U2_max_total(getInternalizedPropertyByKey("U2_max"), getInternalizedPropertyByKey("unit.V")),
	U0_min_total(getInternalizedPropertyByKey("U0_min"), getInternalizedPropertyByKey("unit.V")),
	U1_min_total(getInternalizedPropertyByKey("U1_min"), getInternalizedPropertyByKey("unit.V")),
	U2_min_total(getInternalizedPropertyByKey("U2_min"), getInternalizedPropertyByKey("unit.V")),
	I0_avg_total(getInternalizedPropertyByKey("I0_avg"), getInternalizedPropertyByKey("unit.A")),
	I1_avg_total(getInternalizedPropertyByKey("I1_avg"), getInternalizedPropertyByKey("unit.A")),
	I2_avg_total(getInternalizedPropertyByKey("I2_avg"), getInternalizedPropertyByKey("unit.A")),
	I0_max_total(getInternalizedPropertyByKey("I0_max"), getInternalizedPropertyByKey("unit.A")),
	I1_max_total(getInternalizedPropertyByKey("I1_max"), getInternalizedPropertyByKey("unit.A")),
	I2_max_total(getInternalizedPropertyByKey("I2_max"), getInternalizedPropertyByKey("unit.A")),
	I0_min_total(getInternalizedPropertyByKey("I0_min"), getInternalizedPropertyByKey("unit.A")),
	I1_min_total(getInternalizedPropertyByKey("I1_min"), getInternalizedPropertyByKey("unit.A")),
	I2_min_total(getInternalizedPropertyByKey("I2_min"), getInternalizedPropertyByKey("unit.A")),
	Pst_U12(getInternalizedPropertyByKey("Pst_U12"), getInternalizedPropertyByKey("unit.none")),
	Pst_U23(getInternalizedPropertyByKey("Pst_U23"), getInternalizedPropertyByKey("unit.none")),
	Pst_U31(getInternalizedPropertyByKey("Pst_U31"), getInternalizedPropertyByKey("unit.none")),
	Plt_U12(getInternalizedPropertyByKey("Plt_U12"), getInternalizedPropertyByKey("unit.none")),
	Plt_U23(getInternalizedPropertyByKey("Plt_U23"), getInternalizedPropertyByKey("unit.none")),
	Plt_U31(getInternalizedPropertyByKey("Plt_U31"), getInternalizedPropertyByKey("unit.none")),
	Pst_UL1(getInternalizedPropertyByKey("Pst_UL1"), getInternalizedPropertyByKey("unit.none")),
	Pst_UL2(getInternalizedPropertyByKey("Pst_UL2"), getInternalizedPropertyByKey("unit.none")),
	Pst_UL3(getInternalizedPropertyByKey("Pst_UL3"), getInternalizedPropertyByKey("unit.none")),
	Plt_L1(getInternalizedPropertyByKey("Plt_L1"), getInternalizedPropertyByKey("unit.percentage")),
	Plt_L2(getInternalizedPropertyByKey("Plt_L2"), getInternalizedPropertyByKey("unit.percentage")),
	Plt_L3(getInternalizedPropertyByKey("Plt_L3"), getInternalizedPropertyByKey("unit.percentage")),
	f(getInternalizedPropertyByKey("f"), getInternalizedPropertyByKey("unit.hz")),
	cos_phi(getInternalizedPropertyByKey("cos_phi"), getInternalizedPropertyByKey("unit.none")),
	tan_phi(getInternalizedPropertyByKey("tan_phi"), getInternalizedPropertyByKey("unit.none")),
	PF_total(getInternalizedPropertyByKey("PF_total"), getInternalizedPropertyByKey("unit.none")),
	PF_total_abs(getInternalizedPropertyByKey("PF_total_abs"), getInternalizedPropertyByKey("unit.none")),
	PF_L1_avg(getInternalizedPropertyByKey("PF_L1_avg"), getInternalizedPropertyByKey("unit.none")),
	PF_L2_avg(getInternalizedPropertyByKey("PF_L2_avg"), getInternalizedPropertyByKey("unit.none")),
	PF_L3_avg(getInternalizedPropertyByKey("PF_L3_avg"), getInternalizedPropertyByKey("unit.none")),
	P_total(getInternalizedPropertyByKey("P_total"), getInternalizedPropertyByKey("unit.kW")),
	P_abs(getInternalizedPropertyByKey("P_abs"), getInternalizedPropertyByKey("unit.kW")),
	P_max(getInternalizedPropertyByKey("P_max"), getInternalizedPropertyByKey("unit.kW")),
	P_min(getInternalizedPropertyByKey("P_min"), getInternalizedPropertyByKey("unit.kW")),
	P_L1_avg(getInternalizedPropertyByKey("P_L1_avg"), getInternalizedPropertyByKey("unit.kW")),
	P_L2_avg(getInternalizedPropertyByKey("P_L2_avg"), getInternalizedPropertyByKey("unit.kW")),
	P_L3_avg(getInternalizedPropertyByKey("P_L3_avg"), getInternalizedPropertyByKey("unit.kW")),
	P_L1_max(getInternalizedPropertyByKey("P_L1_max"), getInternalizedPropertyByKey("unit.kW")),
	P_L2_max(getInternalizedPropertyByKey("P_L2_max"), getInternalizedPropertyByKey("unit.kW")),
	P_L3_max(getInternalizedPropertyByKey("P_L3_max"), getInternalizedPropertyByKey("unit.kW")),
	P_L1_min(getInternalizedPropertyByKey("P_L1_min"), getInternalizedPropertyByKey("unit.kW")),
	P_L2_min(getInternalizedPropertyByKey("P_L2_min"), getInternalizedPropertyByKey("unit.kW")),
	P_L3_min(getInternalizedPropertyByKey("P_L3_min"), getInternalizedPropertyByKey("unit.kW")),
	S_total(getInternalizedPropertyByKey("S_total"), getInternalizedPropertyByKey("unit.kVA")),
	S_max(getInternalizedPropertyByKey("S_max"), getInternalizedPropertyByKey("unit.kVA")),
	S_min(getInternalizedPropertyByKey("S_min"), getInternalizedPropertyByKey("unit.kVA")),
	S_L1_avg(getInternalizedPropertyByKey("S_L1_avg"), getInternalizedPropertyByKey("unit.kVA")),
	S_L2_avg(getInternalizedPropertyByKey("S_L2_avg"), getInternalizedPropertyByKey("unit.kVA")),
	S_L3_avg(getInternalizedPropertyByKey("S_L3_avg"), getInternalizedPropertyByKey("unit.kVA")),
	S_L1_max(getInternalizedPropertyByKey("S_L1_max"), getInternalizedPropertyByKey("unit.kVA")),
	S_L2_max(getInternalizedPropertyByKey("S_L2_max"), getInternalizedPropertyByKey("unit.kVA")),
	S_L3_max(getInternalizedPropertyByKey("S_L3_max"), getInternalizedPropertyByKey("unit.kVA")),
	S_L1_min(getInternalizedPropertyByKey("S_L1_min"), getInternalizedPropertyByKey("unit.kVA")),
	S_L2_min(getInternalizedPropertyByKey("S_L2_min"), getInternalizedPropertyByKey("unit.kVA")),
	S_L3_min(getInternalizedPropertyByKey("S_L3_min"), getInternalizedPropertyByKey("unit.kVA")),
	Q_total(getInternalizedPropertyByKey("Q_total"), getInternalizedPropertyByKey("unit.kVar")),
	Q_total_max(getInternalizedPropertyByKey("Q_max"), getInternalizedPropertyByKey("unit.kVar")),
	Q_total_min(getInternalizedPropertyByKey("Q_min"), getInternalizedPropertyByKey("unit.kVar")),
	Q_L1_avg(getInternalizedPropertyByKey("Q_L1_avg"), getInternalizedPropertyByKey("unit.kVar")),
	Q_L2_avg(getInternalizedPropertyByKey("Q_L2_avg"), getInternalizedPropertyByKey("unit.kVar")),
	Q_L3_avg(getInternalizedPropertyByKey("Q_L3_avg"), getInternalizedPropertyByKey("unit.kVar")),
	Q_L1_max(getInternalizedPropertyByKey("Q_L1_max"), getInternalizedPropertyByKey("unit.kVar")),
	Q_L2_max(getInternalizedPropertyByKey("Q_L2_max"), getInternalizedPropertyByKey("unit.kVar")),
	Q_L3_max(getInternalizedPropertyByKey("Q_L3_max"), getInternalizedPropertyByKey("unit.kVar")),
	Q_L1_min(getInternalizedPropertyByKey("Q_L1_min"), getInternalizedPropertyByKey("unit.kVar")),
	Q_L2_min(getInternalizedPropertyByKey("Q_L2_min"), getInternalizedPropertyByKey("unit.kVar")),
	Q_L3_min(getInternalizedPropertyByKey("Q_L3_min"), getInternalizedPropertyByKey("unit.kVar")),
	U0_U1_avg(getInternalizedPropertyByKey("U0_U1_avg"), getInternalizedPropertyByKey("unit.percentage")),
	U0_U1_min(getInternalizedPropertyByKey("U0_U1_min"), getInternalizedPropertyByKey("unit.percentage")),
	U0_U1_max(getInternalizedPropertyByKey("U0_U1_max"), getInternalizedPropertyByKey("unit.percentage")),
	U2_U1_avg(getInternalizedPropertyByKey("U2_U1_avg"), getInternalizedPropertyByKey("unit.percentage")),
	U2_U1_min(getInternalizedPropertyByKey("U2_U1_min"), getInternalizedPropertyByKey("unit.percentage")),
	U2_U1_max(getInternalizedPropertyByKey("U2_U1_max"), getInternalizedPropertyByKey("unit.percentage")),
	I0_I1_avg(getInternalizedPropertyByKey("I0_I1_avg"), getInternalizedPropertyByKey("unit.percentage")),
	I0_I1_min(getInternalizedPropertyByKey("I0_I1_min"), getInternalizedPropertyByKey("unit.percentage")),
	I0_I1_max(getInternalizedPropertyByKey("I0_I1_max"), getInternalizedPropertyByKey("unit.percentage")),
	I2_I1_avg(getInternalizedPropertyByKey("I2_I1_avg"), getInternalizedPropertyByKey("unit.percentage")),
	I2_I1_min(getInternalizedPropertyByKey("I2_I1_min"), getInternalizedPropertyByKey("unit.percentage")),
	I2_I1_max(getInternalizedPropertyByKey("I2_I1_max"), getInternalizedPropertyByKey("unit.percentage")),

	// HARMONICZNE WINPQ
	THD_12(getInternalizedPropertyByKey("THD_12"), getInternalizedPropertyByKey("unit.percentage")),
	THD_23(getInternalizedPropertyByKey("THD_23"), getInternalizedPropertyByKey("unit.percentage")),
	THD_31(getInternalizedPropertyByKey("THD_31"), getInternalizedPropertyByKey("unit.percentage")),
	THD_L1(getInternalizedPropertyByKey("THD_L1"), getInternalizedPropertyByKey("unit.percentage")),
	THD_L2(getInternalizedPropertyByKey("THD_L2"), getInternalizedPropertyByKey("unit.percentage")),
	THD_L3(getInternalizedPropertyByKey("THD_L3"), getInternalizedPropertyByKey("unit.percentage")),
	H1_UL1(getInternalizedPropertyByKey("H1_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H2_UL1(getInternalizedPropertyByKey("H2_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H3_UL1(getInternalizedPropertyByKey("H3_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H4_UL1(getInternalizedPropertyByKey("H4_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H5_UL1(getInternalizedPropertyByKey("H5_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H6_UL1(getInternalizedPropertyByKey("H6_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H7_UL1(getInternalizedPropertyByKey("H7_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H8_UL1(getInternalizedPropertyByKey("H8_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H9_UL1(getInternalizedPropertyByKey("H9_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H10_UL1(getInternalizedPropertyByKey("H10_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H11_UL1(getInternalizedPropertyByKey("H11_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H12_UL1(getInternalizedPropertyByKey("H12_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H13_UL1(getInternalizedPropertyByKey("H13_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H14_UL1(getInternalizedPropertyByKey("H14_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H15_UL1(getInternalizedPropertyByKey("H15_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H16_UL1(getInternalizedPropertyByKey("H16_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H17_UL1(getInternalizedPropertyByKey("H17_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H18_UL1(getInternalizedPropertyByKey("H18_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H19_UL1(getInternalizedPropertyByKey("H19_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H20_UL1(getInternalizedPropertyByKey("H20_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H21_UL1(getInternalizedPropertyByKey("H21_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H22_UL1(getInternalizedPropertyByKey("H22_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H23_UL1(getInternalizedPropertyByKey("H23_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H24_UL1(getInternalizedPropertyByKey("H24_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H25_UL1(getInternalizedPropertyByKey("H25_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H26_UL1(getInternalizedPropertyByKey("H26_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H27_UL1(getInternalizedPropertyByKey("H27_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H28_UL1(getInternalizedPropertyByKey("H28_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H29_UL1(getInternalizedPropertyByKey("H29_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H30_UL1(getInternalizedPropertyByKey("H30_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H31_UL1(getInternalizedPropertyByKey("H31_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H32_UL1(getInternalizedPropertyByKey("H32_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H33_UL1(getInternalizedPropertyByKey("H33_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H34_UL1(getInternalizedPropertyByKey("H34_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H35_UL1(getInternalizedPropertyByKey("H35_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H36_UL1(getInternalizedPropertyByKey("H36_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H37_UL1(getInternalizedPropertyByKey("H37_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H38_UL1(getInternalizedPropertyByKey("H38_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H39_UL1(getInternalizedPropertyByKey("H39_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H40_UL1(getInternalizedPropertyByKey("H40_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H41_UL1(getInternalizedPropertyByKey("H41_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H42_UL1(getInternalizedPropertyByKey("H42_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H43_UL1(getInternalizedPropertyByKey("H43_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H44_UL1(getInternalizedPropertyByKey("H44_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H45_UL1(getInternalizedPropertyByKey("H45_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H46_UL1(getInternalizedPropertyByKey("H46_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H47_UL1(getInternalizedPropertyByKey("H47_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H48_UL1(getInternalizedPropertyByKey("H48_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H49_UL1(getInternalizedPropertyByKey("H49_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H50_UL1(getInternalizedPropertyByKey("H50_UL1"), getInternalizedPropertyByKey("unit.percentage")),
	H1_UL2(getInternalizedPropertyByKey("H1_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H2_UL2(getInternalizedPropertyByKey("H2_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H3_UL2(getInternalizedPropertyByKey("H3_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H4_UL2(getInternalizedPropertyByKey("H4_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H5_UL2(getInternalizedPropertyByKey("H5_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H6_UL2(getInternalizedPropertyByKey("H6_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H7_UL2(getInternalizedPropertyByKey("H7_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H8_UL2(getInternalizedPropertyByKey("H8_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H9_UL2(getInternalizedPropertyByKey("H9_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H10_UL2(getInternalizedPropertyByKey("H10_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H11_UL2(getInternalizedPropertyByKey("H11_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H12_UL2(getInternalizedPropertyByKey("H12_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H13_UL2(getInternalizedPropertyByKey("H13_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H14_UL2(getInternalizedPropertyByKey("H14_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H15_UL2(getInternalizedPropertyByKey("H15_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H16_UL2(getInternalizedPropertyByKey("H16_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H17_UL2(getInternalizedPropertyByKey("H17_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H18_UL2(getInternalizedPropertyByKey("H18_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H19_UL2(getInternalizedPropertyByKey("H19_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H20_UL2(getInternalizedPropertyByKey("H20_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H21_UL2(getInternalizedPropertyByKey("H21_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H22_UL2(getInternalizedPropertyByKey("H22_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H23_UL2(getInternalizedPropertyByKey("H23_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H24_UL2(getInternalizedPropertyByKey("H24_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H25_UL2(getInternalizedPropertyByKey("H25_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H26_UL2(getInternalizedPropertyByKey("H26_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H27_UL2(getInternalizedPropertyByKey("H27_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H28_UL2(getInternalizedPropertyByKey("H28_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H29_UL2(getInternalizedPropertyByKey("H29_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H30_UL2(getInternalizedPropertyByKey("H30_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H31_UL2(getInternalizedPropertyByKey("H31_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H32_UL2(getInternalizedPropertyByKey("H32_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H33_UL2(getInternalizedPropertyByKey("H33_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H34_UL2(getInternalizedPropertyByKey("H34_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H35_UL2(getInternalizedPropertyByKey("H35_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H36_UL2(getInternalizedPropertyByKey("H36_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H37_UL2(getInternalizedPropertyByKey("H37_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H38_UL2(getInternalizedPropertyByKey("H38_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H39_UL2(getInternalizedPropertyByKey("H39_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H40_UL2(getInternalizedPropertyByKey("H40_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H41_UL2(getInternalizedPropertyByKey("H41_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H42_UL2(getInternalizedPropertyByKey("H42_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H43_UL2(getInternalizedPropertyByKey("H43_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H44_UL2(getInternalizedPropertyByKey("H44_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H45_UL2(getInternalizedPropertyByKey("H45_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H46_UL2(getInternalizedPropertyByKey("H46_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H47_UL2(getInternalizedPropertyByKey("H47_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H48_UL2(getInternalizedPropertyByKey("H48_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H49_UL2(getInternalizedPropertyByKey("H49_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H50_UL2(getInternalizedPropertyByKey("H50_UL2"), getInternalizedPropertyByKey("unit.percentage")),
	H1_UL3(getInternalizedPropertyByKey("H1_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H2_UL3(getInternalizedPropertyByKey("H2_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H3_UL3(getInternalizedPropertyByKey("H3_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H4_UL3(getInternalizedPropertyByKey("H4_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H5_UL3(getInternalizedPropertyByKey("H5_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H6_UL3(getInternalizedPropertyByKey("H6_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H7_UL3(getInternalizedPropertyByKey("H7_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H8_UL3(getInternalizedPropertyByKey("H8_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H9_UL3(getInternalizedPropertyByKey("H9_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H10_UL3(getInternalizedPropertyByKey("H10_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H11_UL3(getInternalizedPropertyByKey("H11_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H12_UL3(getInternalizedPropertyByKey("H12_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H13_UL3(getInternalizedPropertyByKey("H13_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H14_UL3(getInternalizedPropertyByKey("H14_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H15_UL3(getInternalizedPropertyByKey("H15_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H16_UL3(getInternalizedPropertyByKey("H16_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H17_UL3(getInternalizedPropertyByKey("H17_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H18_UL3(getInternalizedPropertyByKey("H18_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H19_UL3(getInternalizedPropertyByKey("H19_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H20_UL3(getInternalizedPropertyByKey("H20_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H21_UL3(getInternalizedPropertyByKey("H21_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H22_UL3(getInternalizedPropertyByKey("H22_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H23_UL3(getInternalizedPropertyByKey("H23_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H24_UL3(getInternalizedPropertyByKey("H24_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H25_UL3(getInternalizedPropertyByKey("H25_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H26_UL3(getInternalizedPropertyByKey("H26_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H27_UL3(getInternalizedPropertyByKey("H27_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H28_UL3(getInternalizedPropertyByKey("H28_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H29_UL3(getInternalizedPropertyByKey("H29_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H30_UL3(getInternalizedPropertyByKey("H30_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H31_UL3(getInternalizedPropertyByKey("H31_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H32_UL3(getInternalizedPropertyByKey("H32_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H33_UL3(getInternalizedPropertyByKey("H33_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H34_UL3(getInternalizedPropertyByKey("H34_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H35_UL3(getInternalizedPropertyByKey("H35_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H36_UL3(getInternalizedPropertyByKey("H36_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H37_UL3(getInternalizedPropertyByKey("H37_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H38_UL3(getInternalizedPropertyByKey("H38_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H39_UL3(getInternalizedPropertyByKey("H39_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H40_UL3(getInternalizedPropertyByKey("H40_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H41_UL3(getInternalizedPropertyByKey("H41_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H42_UL3(getInternalizedPropertyByKey("H42_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H43_UL3(getInternalizedPropertyByKey("H43_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H44_UL3(getInternalizedPropertyByKey("H44_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H45_UL3(getInternalizedPropertyByKey("H45_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H46_UL3(getInternalizedPropertyByKey("H46_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H47_UL3(getInternalizedPropertyByKey("H47_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H48_UL3(getInternalizedPropertyByKey("H48_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H49_UL3(getInternalizedPropertyByKey("H49_UL3"), getInternalizedPropertyByKey("unit.percentage")),
	H50_UL3(getInternalizedPropertyByKey("H50_UL3"), getInternalizedPropertyByKey("unit.percentage")),


	//sonel added
	f_L1_min(					getInternalizedPropertyByKey("f.L1.min"),getInternalizedPropertyByKey("unit.hz")),
	f_L1_max(					getInternalizedPropertyByKey("f.L1.max"),getInternalizedPropertyByKey("unit.hz")),
	f_L1_avg(					getInternalizedPropertyByKey("f.L1.avg"),getInternalizedPropertyByKey("unit.hz")),
	f_L2_min(					getInternalizedPropertyByKey("f.L2.min"),getInternalizedPropertyByKey("unit.hz")),
	f_L2_max(					getInternalizedPropertyByKey("f.L2.max"),getInternalizedPropertyByKey("unit.hz")),
	f_L2_avg(					getInternalizedPropertyByKey("f.L2.avg"),getInternalizedPropertyByKey("unit.hz")),
	f_L3_min(					getInternalizedPropertyByKey("f.L3.min"),getInternalizedPropertyByKey("unit.hz")),
	f_L3_max(					getInternalizedPropertyByKey("f.L3.max"),getInternalizedPropertyByKey("unit.hz")),
	f_L3_avg(					getInternalizedPropertyByKey("f.L3.avg"),getInternalizedPropertyByKey("unit.hz")),
	K_L1_avg(					getInternalizedPropertyByKey("K.L1.avg"), getInternalizedPropertyByKey("unit.none")),
	K_L2_avg(					getInternalizedPropertyByKey("K.L2.avg"), getInternalizedPropertyByKey("unit.none")),
	K_L3_avg(					getInternalizedPropertyByKey("K.L3.avg"), getInternalizedPropertyByKey("unit.none")),
	K_n_avg(					getInternalizedPropertyByKey("K.n.avg"), getInternalizedPropertyByKey("unit.none")),
	CF_U_L1_avg(				getInternalizedPropertyByKey("CF.U.L1.avg"), getInternalizedPropertyByKey("unit.none")),
	CF_U_L2_avg(				getInternalizedPropertyByKey("CF.U.L2.avg"), getInternalizedPropertyByKey("unit.none")),
	CF_U_L3_avg(				getInternalizedPropertyByKey("CF.U.L3.avg"), getInternalizedPropertyByKey("unit.none")),
	CF_U_NPE_avg(				getInternalizedPropertyByKey("CF.U.NPE.avg"), getInternalizedPropertyByKey("unit.none")),
	CF_I_L1_avg(				getInternalizedPropertyByKey("CF.I.L1.avg"), getInternalizedPropertyByKey("unit.none")),
	CF_I_L2_avg(				getInternalizedPropertyByKey("CF.I.L2.avg"), getInternalizedPropertyByKey("unit.none")),
	CF_I_L3_avg(				getInternalizedPropertyByKey("CF.I.L3.avg"), getInternalizedPropertyByKey("unit.none")),
	CF_I_N_avg(					getInternalizedPropertyByKey("CF.I.N.avg"), getInternalizedPropertyByKey("unit.none")),
	PF_L1_min(					getInternalizedPropertyByKey("PF.L1.min"), getInternalizedPropertyByKey("unit.none")),
	PF_L2_min(					getInternalizedPropertyByKey("PF.L2.min"), getInternalizedPropertyByKey("unit.none")),
	PF_L3_min(					getInternalizedPropertyByKey("PF.L3.min"), getInternalizedPropertyByKey("unit.none")),
	PF_L1_max(					getInternalizedPropertyByKey("PF.L1.max"), getInternalizedPropertyByKey("unit.none")),
	PF_L2_max(					getInternalizedPropertyByKey("PF.L2.max"), getInternalizedPropertyByKey("unit.none")),
	PF_L3_max(					getInternalizedPropertyByKey("PF.L3.max"), getInternalizedPropertyByKey("unit.none")),
	PF_total_avg(				getInternalizedPropertyByKey("PF.total.avg"), getInternalizedPropertyByKey("unit.none")),
	PF_total_min(				getInternalizedPropertyByKey("PF.total.min"), getInternalizedPropertyByKey("unit.none")),
	PF_total_max(				getInternalizedPropertyByKey("PF.total.max"), getInternalizedPropertyByKey("unit.none")),
	cos_phi_L1_avg(				getInternalizedPropertyByKey("cos.phi.L1.avg"), getInternalizedPropertyByKey("unit.none")),
	cos_phi_L2_avg(				getInternalizedPropertyByKey("cos.phi.L2.avg"), getInternalizedPropertyByKey("unit.none")),
	cos_phi_L3_avg(				getInternalizedPropertyByKey("cos.phi.L3.avg"), getInternalizedPropertyByKey("unit.none")),
	cos_phi_total_avg(			getInternalizedPropertyByKey("cos.phi.total.avg"), getInternalizedPropertyByKey("unit.none")),
	tan_phi_L1_Lplus_avg(		getInternalizedPropertyByKey("tan.phi.L1.Lplus.avg"), getInternalizedPropertyByKey("unit.none")),
	tan_phi_L2_Lplus_avg(		getInternalizedPropertyByKey("tan.phi.L2.Lplus.avg"), getInternalizedPropertyByKey("unit.none")),
	tan_phi_L3_Lplus_avg(		getInternalizedPropertyByKey("tan.phi.L3.Lplus.avg"), getInternalizedPropertyByKey("unit.none")),
	tan_phi_total_Lplus_avg(	getInternalizedPropertyByKey("tan.phi.total.Lplus.avg"), getInternalizedPropertyByKey("unit.none")),
	tan_phi_L1_Lmin_avg(		getInternalizedPropertyByKey("tan.phi.L1.Lmin.avg"), getInternalizedPropertyByKey("unit.none")),
	tan_phi_L2_Lmin_avg(		getInternalizedPropertyByKey("tan.phi.L2.Lmin.avg"), getInternalizedPropertyByKey("unit.none")),
	tan_phi_L3_Lmin_avg(		getInternalizedPropertyByKey("tan.phi.L3.Lmin.avg"), getInternalizedPropertyByKey("unit.none")),
	tan_phi_total_Lmin_avg(		getInternalizedPropertyByKey("tan.phi.total.Lmin.avg"), getInternalizedPropertyByKey("unit.none")),
	tan_phi_L1_Cmin_avg(		getInternalizedPropertyByKey("tan.phi.L1.Cmin.avg"), getInternalizedPropertyByKey("unit.none")),
	tan_phi_L2_Cmin_avg(		getInternalizedPropertyByKey("tan.phi.L2.Cmin.avg"), getInternalizedPropertyByKey("unit.none")),
	tan_phi_L3_Cmin_avg(		getInternalizedPropertyByKey("tan.phi.L3.Cmin.avg"), getInternalizedPropertyByKey("unit.none")),
	tan_phi_total_Cmin_avg(		getInternalizedPropertyByKey("tan.phi.total.Cmin.avg"), getInternalizedPropertyByKey("unit.none")),
	tan_phi_L1_Cplus_avg(		getInternalizedPropertyByKey("tan.phi.L1.Cplus.avg"), getInternalizedPropertyByKey("unit.none")),
	tan_phi_L2_Cplus_avg(		getInternalizedPropertyByKey("tan.phi.L2.Cplus.avg"), getInternalizedPropertyByKey("unit.none")),
	tan_phi_L3_Cplus_avg(		getInternalizedPropertyByKey("tan.phi.L3.Cplus.avg"), getInternalizedPropertyByKey("unit.none")),
	tan_phi_total_Cplus_avg(	getInternalizedPropertyByKey("tan.phi.total.Cplus.avg"), getInternalizedPropertyByKey("unit.none")),
	P_plus_L1_avg(				getInternalizedPropertyByKey("P.plus.L1.avg"), getInternalizedPropertyByKey("unit.kW")),
	P_plus_L2_avg(				getInternalizedPropertyByKey("P.plus.L2.avg"), getInternalizedPropertyByKey("unit.kW")),
	P_plus_L3_avg(				getInternalizedPropertyByKey("P.plus.L3.avg"), getInternalizedPropertyByKey("unit.kW")),
	P_plus_total_avg(			getInternalizedPropertyByKey("P.plus.total.avg"), getInternalizedPropertyByKey("unit.kW")),
	P_min_L1_avg(				getInternalizedPropertyByKey("P.min.L1.avg"), getInternalizedPropertyByKey("unit.kW")),
	P_min_L2_avg(				getInternalizedPropertyByKey("P.min.L2.avg"), getInternalizedPropertyByKey("unit.kW")),
	P_min_L3_avg(				getInternalizedPropertyByKey("P.min.L3.avg"), getInternalizedPropertyByKey("unit.kW")),
	P_min_total_avg(			getInternalizedPropertyByKey("P.min.total.avg"), getInternalizedPropertyByKey("unit.kW")),
	P_total_min(				getInternalizedPropertyByKey("P.total.min"), getInternalizedPropertyByKey("unit.kW")),
	P_total_max(				getInternalizedPropertyByKey("P.total.max"), getInternalizedPropertyByKey("unit.kW")),
	P_total_avg(				getInternalizedPropertyByKey("P.total.avg"), getInternalizedPropertyByKey("unit.kW")),
	Q_total_avg(				getInternalizedPropertyByKey("Q.total.avg"), getInternalizedPropertyByKey("unit.kVar")),
	Sn_L1_avg(					getInternalizedPropertyByKey("Sn.L1.avg"), getInternalizedPropertyByKey("unit.kVA")),
	Sn_L2_avg(					getInternalizedPropertyByKey("Sn.L2.avg"), getInternalizedPropertyByKey("unit.kVA")),
	Sn_L3_avg(					getInternalizedPropertyByKey("Sn.L3.avg"), getInternalizedPropertyByKey("unit.kVA")),
	Sn_total(					getInternalizedPropertyByKey("Sn.total"), getInternalizedPropertyByKey("unit.kVA"));
	private final String uniName;
	private final String unit;

	private static final List<UniNames> L1 = new ArrayList<>(Arrays.asList(
			H2_UL1, H3_UL1, H4_UL1, H5_UL1, H6_UL1, H7_UL1, H8_UL1, H9_UL1, H10_UL1,
			H11_UL1, H12_UL1, H13_UL1, H14_UL1, H15_UL1, H16_UL1, H17_UL1, H18_UL1, H19_UL1, H20_UL1,
			H21_UL1, H22_UL1, H23_UL1, H24_UL1, H25_UL1, H26_UL1, H27_UL1, H28_UL1, H29_UL1, H30_UL1,
			H31_UL1, H32_UL1, H33_UL1, H34_UL1, H35_UL1, H36_UL1, H37_UL1, H38_UL1, H39_UL1, H40_UL1,
			H41_UL1, H42_UL1, H43_UL1, H44_UL1, H45_UL1, H46_UL1, H47_UL1, H48_UL1, H49_UL1, H50_UL1));
	
	private static List<UniNames> L2 = new ArrayList<>(Arrays.asList(
			H2_UL2, H3_UL2, H4_UL2, H5_UL2, H6_UL2, H7_UL2, H8_UL2, H9_UL2, H10_UL2,
			H11_UL2, H12_UL2, H13_UL2, H14_UL2, H15_UL2, H16_UL2, H17_UL2, H18_UL2, H19_UL2, H20_UL2,
			H21_UL2, H22_UL2, H23_UL2, H24_UL2, H25_UL2, H26_UL2, H27_UL2, H28_UL2, H29_UL2, H30_UL2,
			H31_UL2, H32_UL2, H33_UL2, H34_UL2, H35_UL2, H36_UL2, H37_UL2, H38_UL2, H39_UL2, H40_UL2,
			H41_UL2, H42_UL2, H43_UL2, H44_UL2, H45_UL2, H46_UL2, H47_UL2, H48_UL2, H49_UL2, H50_UL2));
	private static List<UniNames> L3 = new ArrayList<>(Arrays.asList(
			H2_UL3, H3_UL3, H4_UL3, H5_UL3, H6_UL3, H7_UL3, H8_UL3, H9_UL3, H10_UL3,
			H11_UL3, H12_UL3, H13_UL3, H14_UL3, H15_UL3, H16_UL3, H17_UL3, H18_UL3, H19_UL3, H20_UL3,
			H21_UL3, H22_UL3, H23_UL3, H24_UL3, H25_UL3, H26_UL3, H27_UL3, H28_UL3, H29_UL3, H30_UL3,
			H31_UL3, H32_UL3, H33_UL3, H34_UL3, H35_UL3, H36_UL3, H37_UL3, H38_UL3, H39_UL3, H40_UL3,
			H41_UL3, H42_UL3, H43_UL3, H44_UL3, H45_UL3, H46_UL3, H47_UL3, H48_UL3, H49_UL3, H50_UL3));

	private static final List<UniNames> Q = new ArrayList<>(Arrays.asList(
			Q_L1_avg, Q_L2_avg, Q_L3_avg, Q_L1_max, Q_L2_max, Q_L3_max, Q_L1_min, Q_L2_min, Q_L3_min, Q_total, Q_total_avg, Q_total_min, Q_total_max
	));

	private static final List<UniNames> S = new ArrayList<>(Arrays.asList(
			S_total, Sn_total, S_L1_avg, S_L2_avg, S_L3_avg, Sn_L3_avg, Sn_L2_avg, Sn_L2_avg, S_L1_min, S_L1_max, S_L2_min, S_L2_max, S_L3_min, S_L3_max
	));

	private static final List<UniNames> P = new ArrayList<>(Arrays.asList(
			P_total, P_total_avg, P_total_max, P_total_min, P_L1_avg, P_L2_avg, P_L3_avg, P_L1_min, P_L2_min, P_L3_min, P_L1_max, P_L2_max, P_L3_max
	));

	private static final List<UniNames> HARMONICS = new ArrayList<>(Arrays.asList(THD_L1, THD_L2, THD_L3, THD_12, THD_23, THD_31,
			H1_UL1, H2_UL1, H3_UL1, H4_UL1, H5_UL1, H6_UL1, H7_UL1, H8_UL1, H9_UL1, H10_UL1,
			H11_UL1, H12_UL1, H13_UL1, H14_UL1, H15_UL1, H16_UL1, H17_UL1, H18_UL1, H19_UL1, H20_UL1,
			H21_UL1, H22_UL1, H23_UL1, H24_UL1, H25_UL1, H26_UL1, H27_UL1, H28_UL1, H29_UL1, H30_UL1,
			H31_UL1, H32_UL1, H33_UL1, H34_UL1, H35_UL1, H36_UL1, H37_UL1, H38_UL1, H39_UL1, H40_UL1,
			H41_UL1, H42_UL1, H43_UL1, H44_UL1, H45_UL1, H46_UL1, H47_UL1, H48_UL1, H49_UL1, H50_UL1,
			H1_UL2, H2_UL2, H3_UL2, H4_UL2, H5_UL2, H6_UL2, H7_UL2, H8_UL2, H9_UL2, H10_UL2,
			H11_UL2, H12_UL2, H13_UL2, H14_UL2, H15_UL2, H16_UL2, H17_UL2, H18_UL2, H19_UL2, H20_UL2,
			H21_UL2, H22_UL2, H23_UL2, H24_UL2, H25_UL2, H26_UL2, H27_UL2, H28_UL2, H29_UL2, H30_UL2,
			H31_UL2, H32_UL2, H33_UL2, H34_UL2, H35_UL2, H36_UL2, H37_UL2, H38_UL2, H39_UL2, H40_UL2,
			H41_UL2, H42_UL2, H43_UL2, H44_UL2, H45_UL2, H46_UL2, H47_UL2, H48_UL2, H49_UL2, H50_UL2,
			H1_UL3, H2_UL3, H3_UL3, H4_UL3, H5_UL3, H6_UL3, H7_UL3, H8_UL3, H9_UL3, H10_UL3,
			H11_UL3, H12_UL3, H13_UL3, H14_UL3, H15_UL3, H16_UL3, H17_UL3, H18_UL3, H19_UL3, H20_UL3,
			H21_UL3, H22_UL3, H23_UL3, H24_UL3, H25_UL3, H26_UL3, H27_UL3, H28_UL3, H29_UL3, H30_UL3,
			H31_UL3, H32_UL3, H33_UL3, H34_UL3, H35_UL3, H36_UL3, H37_UL3, H38_UL3, H39_UL3, H40_UL3,
			H41_UL3, H42_UL3, H43_UL3, H44_UL3, H45_UL3, H46_UL3, H47_UL3, H48_UL3, H49_UL3, H50_UL3));

	private static final List<UniNames> HARMONICS_ENOUGH = new ArrayList<>(Arrays.asList(
			THD_L1, THD_L2, THD_L3,
			H1_UL1, H2_UL1, H3_UL1, H4_UL1, H5_UL1, H6_UL1, H7_UL1, H8_UL1, H9_UL1, H10_UL1,
			H11_UL1, H12_UL1, H13_UL1, H14_UL1, H15_UL1, H16_UL1, H17_UL1, H18_UL1, H19_UL1, H20_UL1,
			H21_UL1, H22_UL1, H23_UL1, H24_UL1, H25_UL1,
			H1_UL2, H2_UL2, H3_UL2, H4_UL2, H5_UL2, H6_UL2, H7_UL2, H8_UL2, H9_UL2, H10_UL2,
			H11_UL2, H12_UL2, H13_UL2, H14_UL2, H15_UL2, H16_UL2, H17_UL2, H18_UL2, H19_UL2, H20_UL2,
			H21_UL2, H22_UL2, H23_UL2, H24_UL2, H25_UL2,
			H1_UL3, H2_UL3, H3_UL3, H4_UL3, H5_UL3, H6_UL3, H7_UL3, H8_UL3, H9_UL3, H10_UL3,
			H11_UL3, H12_UL3, H13_UL3, H14_UL3, H15_UL3, H16_UL3, H17_UL3, H18_UL3, H19_UL3, H20_UL3,
			H21_UL3, H22_UL3, H23_UL3, H24_UL3, H25_UL3));

	private static final List<UniNames> NORMAL_ENOUGH = new ArrayList<>(Arrays.asList(
			UL1_avg, UL2_avg, UL3_avg, U2_U1_avg, Plt_L1, Plt_L2, Plt_L3));
	UniNames(String uniName, String unit) {
		this.uniName = uniName;
		this.unit = unit;
	}

	public String getUnit() {
		return unit;
	}

	public String getUniName() { return uniName;}
	@Override
	public String toString() {
		return this.uniName;
	}

	public static List<UniNames> getPowerLineHarmonicNames(int i) throws IllegalStateException {
		List<UniNames> list;
		switch (i){
			case 1 -> list = L1;
			case 2 -> list = L2;
			case 3 -> list = L3;
			default -> throw new IllegalStateException("Unexpected value: " + i);
		}
		return list;
	}

	public static List<UniNames> getL1HarmoNames()  {
		return L1;
	}

	public static List<UniNames> getL2HarmoNames()  {
		return L2;
	}

	public static List<UniNames> getL3HarmoNames()  {
		return L3;
	}

	public static List<UniNames> getQ(){return Q;}

	public static List<UniNames> getS(){return S;}
	public static List<UniNames> getP(){return P;}
	public static List<UniNames> getHarmonics(){return HARMONICS;}
	public static List<UniNames> getHarmonicsEnough(){return HARMONICS_ENOUGH;}
	public static List<UniNames> getNormalEnough() {
		return NORMAL_ENOUGH;
	}
}
