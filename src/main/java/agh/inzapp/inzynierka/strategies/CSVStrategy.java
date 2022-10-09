package agh.inzapp.inzynierka.strategies;


import agh.inzapp.inzynierka.models.modelObj.BaseDataObj;
import agh.inzapp.inzynierka.exceptions.ApplicationException;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface CSVStrategy {
	CSVParser parser = new CSVParserBuilder()
			.withSeparator(';')
			.withQuoteChar('\'')
			.withIgnoreQuotations(false)
			.build();
	List<BaseDataObj> importCSVFile(String... path) throws ApplicationException;
}
