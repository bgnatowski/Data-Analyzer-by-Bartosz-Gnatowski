package agh.inzapp.inzynierka.strategies;

import agh.inzapp.inzynierka.exceptions.ApplicationException;
import agh.inzapp.inzynierka.models.DataFx;
import agh.inzapp.inzynierka.models.PQHarmonicsFx;

import java.util.ArrayList;
import java.util.List;

public class CSVImportPQHarmonics implements CSVStrategy {
	@Override
	public List<DataFx> importCSVFile(String... path) throws ApplicationException {
		List<DataFx> list = new ArrayList<>();
		list.add(new PQHarmonicsFx());
		list.add(new PQHarmonicsFx());
		list.add(new PQHarmonicsFx());
		return list;
	}
}
