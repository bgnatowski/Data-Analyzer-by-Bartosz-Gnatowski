package agh.inzapp.inzynierka.services;

import agh.inzapp.inzynierka.database.DataManager;
import agh.inzapp.inzynierka.enums.Analysers;
import agh.inzapp.inzynierka.models.ListDataFx;
import agh.inzapp.inzynierka.models.ListHarmoFx;
import agh.inzapp.inzynierka.strategies.CSVImportPQ;
import agh.inzapp.inzynierka.strategies.CSVImportPQHarmonics;
import agh.inzapp.inzynierka.utils.CSVUtils;

public class ImportManager {
	public static void saveNormal(Analysers analyser) {
		switch (analyser){
			case PQbox ->{
				DataManager.saveAll(CSVUtils.getNormalDataList(new CSVImportPQ()));
				ListDataFx.getInstance().init();
			}
			case Sonel -> {

			}
		}
	}

	public static void saveHarmonics(Analysers analyser) {
		switch (analyser){
			case PQbox ->{
				DataManager.saveAll(CSVUtils.getHarmonicDataList(new CSVImportPQHarmonics()));//todo
				ListHarmoFx.getInstance().init();
			}
			case Sonel -> {

			}
		}
	}

	public static void saveBoth(Analysers analyser) {
		switch (analyser){
			case PQbox ->{
				DataManager.saveAll(CSVUtils.getNormalDataList(new CSVImportPQ()));
				ListDataFx.getInstance().init();
				DataManager.saveAll(CSVUtils.getHarmonicDataList(new CSVImportPQHarmonics()));
				ListHarmoFx.getInstance().init();
			}
			case Sonel -> {

			}
		}
	}
}
