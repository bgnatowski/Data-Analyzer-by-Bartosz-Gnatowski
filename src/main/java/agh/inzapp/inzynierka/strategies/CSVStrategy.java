package agh.inzapp.inzynierka.strategies;


import agh.inzapp.inzynierka.models.fxmodels.CommonModelFx;
import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;
import org.springframework.stereotype.Component;

import java.util.List;
@Component
public interface CSVStrategy {
	List<CommonModelFx> importCSVFile(String path) throws ApplicationException;
}
