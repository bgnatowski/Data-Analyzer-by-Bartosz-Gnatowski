package agh.inzapp.inzynierka.strategies;

import agh.inzapp.inzynierka.models.fxmodels.CommonModelFx;
import agh.inzapp.inzynierka.utils.FxmlUtils;
import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;
import agh.inzapp.inzynierka.utils.parsers.PQParser;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import com.opencsv.exceptions.CsvValidationException;
import javafx.collections.FXCollections;
import org.springframework.stereotype.Component;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class CSVImportPQ extends CSVImportCommon implements CSVStrategy {
	private List<CommonModelFx> dataModels;
	public CSVImportPQ() {
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
	protected void saveModels() throws ApplicationException {
		AtomicLong id = new AtomicLong(0L);
		for (List<String> records : allRecordsList) {
			CommonModelFx model = new CommonModelFx();
			model.setId(id.incrementAndGet());
			model.setColumnNames(FXCollections.observableArrayList(columnsNames));
			setDataInPQModel(records, model);
			model.deleteNone();
			dataModels.add(model);
		}
	}
	@Override
	protected void readFile(String path) throws ApplicationException {
		try (Reader reader = new FileReader(path);
			 CSVReader csvReader = new CSVReaderBuilder(reader)
					 .withSkipLines(0)
					 .withCSVParser(parser)
					 .build()
		) {
			String[] oneLineValues;
			boolean isFirstLineRead = false;

			while ((oneLineValues = csvReader.readNext()) != null) {
				List<String> modelLine = Arrays.asList(oneLineValues);
				if (modelLine.contains("")) break; //bez tego wczytywało +1 wartość
				if (!isFirstLineRead) {
					columnsNames.addAll(PQParser.parseNames(modelLine));
					isFirstLineRead = true;
				} else allRecordsList.add(modelLine);

			}
		} catch (IOException | CsvValidationException e) {
			throw new ApplicationException(FxmlUtils.getNameProperty("error.cannot.read.csv"));
		}
	}
}
