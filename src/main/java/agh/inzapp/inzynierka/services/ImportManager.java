package agh.inzapp.inzynierka.services;

import agh.inzapp.inzynierka.database.DataManager;
import agh.inzapp.inzynierka.models.enums.Analysers;
import agh.inzapp.inzynierka.models.ListDataFx;
import agh.inzapp.inzynierka.models.ListHarmoFx;
import agh.inzapp.inzynierka.strategies.CSVImportPQ;
import agh.inzapp.inzynierka.strategies.CSVImportPQHarmonics;
import agh.inzapp.inzynierka.strategies.CSVImportSonel;
import agh.inzapp.inzynierka.strategies.CSVImportSonelHarmonics;
import agh.inzapp.inzynierka.utils.CSVUtils;
import org.springframework.stereotype.Component;

@Component
public class ImportManager {
	public void saveNormal(Analysers analyser) {
		switch (analyser){
			case PQbox ->{
				DataManager.saveAll(CSVUtils.importNormalDataList(new CSVImportPQ()));
				ListDataFx.getInstance().init();
			}
			case Sonel -> {
				DataManager.saveAll(CSVUtils.importNormalDataList(new CSVImportSonel()));
				ListDataFx.getInstance().init();
			}
		}
	}
	public void saveHarmonics(Analysers analyser) {
		switch (analyser){
			case PQbox ->{
				DataManager.saveAll(CSVUtils.importHarmonicsDataList(new CSVImportPQHarmonics()));
				ListHarmoFx.getInstance().init();
			}
			case Sonel -> {
				DataManager.saveAll(CSVUtils.importHarmonicsDataList(new CSVImportSonelHarmonics()));
				ListHarmoFx.getInstance().init();
			}
		}
	}

	public void saveBoth(Analysers analyser) {
		switch (analyser){
			case PQbox ->{
				DataManager.saveAll(CSVUtils.importNormalDataList(new CSVImportPQ()));
				ListDataFx.getInstance().init();
				DataManager.saveAll(CSVUtils.importHarmonicsDataList(new CSVImportPQHarmonics()));
				ListHarmoFx.getInstance().init();
			}
			case Sonel -> {
				DataManager.saveAll(CSVUtils.importNormalDataList(new CSVImportSonel()));
				ListDataFx.getInstance().init();
				DataManager.saveAll(CSVUtils.importHarmonicsDataList(new CSVImportSonelHarmonics()));
				ListHarmoFx.getInstance().init();
			}
		}
	}
}
