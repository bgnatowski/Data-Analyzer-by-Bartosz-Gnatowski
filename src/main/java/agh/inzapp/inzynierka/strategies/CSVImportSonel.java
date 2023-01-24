package agh.inzapp.inzynierka.strategies;

import agh.inzapp.inzynierka.models.enums.UniNames;
import agh.inzapp.inzynierka.models.fxmodels.CommonModelFx;
import agh.inzapp.inzynierka.models.fxmodels.DataFx;
import agh.inzapp.inzynierka.utils.DialogUtils;
import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;
import agh.inzapp.inzynierka.utils.parsers.SonelParser;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

public class CSVImportSonel extends CSVImportCommon implements CSVStrategy {
	private static final int SKIP_INFO_LINES = 11;
	private List<CommonModelFx> dataModels;

	public CSVImportSonel() {
		allRecordsList = new ArrayList<>();
		columnsNames = new ArrayList<>();
		pst1 = new ArrayList<>();
		pst2 = new ArrayList<>();
		pst3 = new ArrayList<>();
	}

	@Override
	public List<CommonModelFx> importCSVFile(String path) throws ApplicationException {
		dataModels = new ArrayList<>();
		readFile(path);
		saveModels();
		return dataModels;
	}

	@Override
	protected void saveModels() {
		AtomicLong id = new AtomicLong(0L);
		allRecordsList.forEach(records ->{
			CommonModelFx model = new CommonModelFx();
 			model.setId(id.incrementAndGet());
			model.setColumnNames(FXCollections.observableArrayList(columnsNames));
			try {
				setDataInSonelModel(records, model);
			} catch (ApplicationException e) {
				DialogUtils.errorDialog(e.getMessage());
				throw new RuntimeException(e);
			}
			dataModels.add(model);
		});
	}
	@Override
	protected void readFile(String path) throws ApplicationException {
		try (Reader reader = new FileReader(path);
			 CSVReader csvReader = new CSVReaderBuilder(reader)
					 .withSkipLines(SKIP_INFO_LINES)
					 .withCSVParser(parser)
					 .build()
		) {
			String[] oneLineValues;
			boolean isFirstLineRead = false;

			while ((oneLineValues = csvReader.readNext()) != null) {
				List<String> modelLine = Arrays.asList(oneLineValues);
				if (!isFirstLineRead) {
					columnsNames.addAll(SonelParser.parseNames(modelLine));
					isFirstLineRead = true;
				} else allRecordsList.add(modelLine);
			}
		} catch (IOException | CsvValidationException e) {
			throw new ApplicationException(e.getMessage());
		}
	}
}
