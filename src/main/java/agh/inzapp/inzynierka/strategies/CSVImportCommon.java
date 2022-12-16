package agh.inzapp.inzynierka.strategies;

import agh.inzapp.inzynierka.models.enums.UniNames;
import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;

import java.util.ArrayList;
import java.util.List;

public abstract class CSVImportCommon {
	protected CSVParser parser = new CSVParserBuilder()
			.withSeparator(';')
			.withQuoteChar('\'')
			.withIgnoreQuotations(false)
			.build();
	protected List<List<String>> allRecordsList = new ArrayList<>();
	protected List<UniNames> columnsNames = new ArrayList<>();

	abstract protected void saveModels();
	abstract protected void readFile(String path) throws ApplicationException;
}
