package agh.inzapp.inzynierka;

import agh.inzapp.inzynierka.enums.UniNames;

import java.util.*;

public class TestMainForSonel {
	public static void main(String[] args) {
		Map<UniNames, String> flagMap = new LinkedHashMap<>();
		flagMap.put(UniNames.Flag_T, "4");
		flagMap.put(UniNames.Flag_P, "3");
		flagMap.put(UniNames.Flag_A, "5");
		flagMap.put(UniNames.Flag_E, "1");
		flagMap.put(UniNames.Flag_G, "2");
		final String s = convertFlagsMapToDb(flagMap);
		final String[] split = s.split("\\|");
		Arrays.asList(split).forEach(flaga -> {
			if (!flaga.equals("|"))
				System.out.println(flaga);
		});


	}

	public static String convertFlagsMapToDb(Map<UniNames, String> flags) {
		StringBuilder sb = new StringBuilder();
		flags.forEach((name, flag) -> sb.append(flag).append("|"));
		return sb.toString();
	}
}
