package agh.inzapp.inzynierka.strategies;


import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;
import agh.inzapp.inzynierka.models.fxmodels.CommonModelFx;
import com.opencsv.CSVParser;
import com.opencsv.CSVParserBuilder;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface CSVStrategy {
	List<? extends CommonModelFx> importCSVFile(String path) throws ApplicationException;
}
