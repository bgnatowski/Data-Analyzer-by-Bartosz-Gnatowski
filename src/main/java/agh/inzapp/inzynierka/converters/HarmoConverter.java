package agh.inzapp.inzynierka.converters;

import agh.inzapp.inzynierka.database.models.DataDb;
import agh.inzapp.inzynierka.database.models.HarmoDb;
import agh.inzapp.inzynierka.enums.UniNames;
import agh.inzapp.inzynierka.models.DataFx;
import agh.inzapp.inzynierka.models.HarmoFx;
import javafx.collections.FXCollections;
import org.springframework.transaction.annotation.Transactional;

import java.util.LinkedHashMap;
import java.util.Map;

import static agh.inzapp.inzynierka.converters.DataConverter.convertFlagPatternToMap;
import static agh.inzapp.inzynierka.converters.DataConverter.convertFlagsMapToDb;

@Transactional
public class HarmoConverter {
	public static HarmoFx convertDbToFx(HarmoDb dataDb) {
		HarmoFx harmoFx = new HarmoFx();
		harmoFx.setId(dataDb.getId());
		harmoFx.setDate(dataDb.getDate());
		harmoFx.setTime(dataDb.getTime());
		harmoFx.setFlags(FXCollections.observableMap(convertFlagPatternToMap(dataDb.getFlags())));
		harmoFx.setColumnHarmonicNames(FXCollections.observableArrayList(dataDb.getColumnNames()));
		harmoFx.setHarmonics(FXCollections.observableMap(dataDb.getHarmonics()));
		harmoFx.setThd(FXCollections.observableMap(dataDb.getThd()));

		return harmoFx;
	}
	public static HarmoDb convertFxToDb(HarmoFx harmoFx) {
		HarmoDb harmoDb = new HarmoDb();

		harmoDb.setId(harmoFx.getId());
		harmoDb.setDate(harmoFx.getDate());
		harmoDb.setTime(harmoFx.getTime());
		harmoDb.setFlags(convertFlagsMapToDb(harmoFx.getFlags()));
		harmoDb.setColumnNames(harmoFx.getColumnHarmonicNames());
		harmoDb.setHarmonics(harmoFx.getHarmonics());
		harmoDb.setThd(harmoFx.getThd());

		return harmoDb;
	}

}