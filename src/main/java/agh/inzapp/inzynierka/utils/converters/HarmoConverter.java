package agh.inzapp.inzynierka.utils.converters;

import agh.inzapp.inzynierka.database.models.HarmoDb;
import agh.inzapp.inzynierka.models.fxmodels.HarmoFx;
import javafx.collections.FXCollections;
import org.springframework.transaction.annotation.Transactional;

import static agh.inzapp.inzynierka.utils.converters.DataConverter.convertFlagPatternToMap;
import static agh.inzapp.inzynierka.utils.converters.DataConverter.convertFlagsMapToDb;

@Transactional
public class HarmoConverter {
	public static HarmoFx convertDbToFx(HarmoDb harmoDb) {
		HarmoFx harmoFx = new HarmoFx();
		harmoFx.setId(harmoDb.getId());
		harmoFx.setDate(harmoDb.getDate());
		harmoFx.setFlags(FXCollections.observableMap(convertFlagPatternToMap(harmoDb.getFlags())));
		harmoFx.setColumnNames(FXCollections.observableArrayList(harmoDb.getColumnNames()));
		harmoFx.setRecords(FXCollections.observableMap(harmoDb.getRecords()));

		return harmoFx;
	}
	public static HarmoDb convertFxToDb(HarmoFx harmoFx) {
		HarmoDb harmoDb = new HarmoDb();

		harmoDb.setId(harmoFx.getId());
		harmoDb.setDate(harmoFx.getDate());
		harmoDb.setFlags(convertFlagsMapToDb(harmoFx.getFlags()));
		harmoDb.setColumnNames(harmoFx.getColumnNames());
		harmoDb.setRecords(harmoFx.getRecords());

		return harmoDb;
	}

}
