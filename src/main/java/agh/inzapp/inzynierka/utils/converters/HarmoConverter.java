package agh.inzapp.inzynierka.utils.converters;

import agh.inzapp.inzynierka.database.models.HarmoDb;
import agh.inzapp.inzynierka.models.fxmodels.HarmoFx;
import javafx.collections.FXCollections;
import org.springframework.transaction.annotation.Transactional;

import static agh.inzapp.inzynierka.utils.converters.DataConverter.convertFlagPatternToMap;
import static agh.inzapp.inzynierka.utils.converters.DataConverter.convertFlagsMapToDb;

@Transactional
public class HarmoConverter {
	public static HarmoFx convertDbToFx(HarmoDb dataDb) {
		HarmoFx harmoFx = new HarmoFx();
		harmoFx.setId(dataDb.getId());
		harmoFx.setDate(dataDb.getDate());
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
		harmoDb.setFlags(convertFlagsMapToDb(harmoFx.getFlags()));
		harmoDb.setColumnNames(harmoFx.getColumnHarmonicNames());
		harmoDb.setHarmonics(harmoFx.getHarmonics());
		harmoDb.setThd(harmoFx.getThd());

		return harmoDb;
	}

}
