package agh.inzapp.inzynierka.database.dbmodels;

import agh.inzapp.inzynierka.utils.enums.UnitaryNames;

import java.util.Date;
import java.util.Map;


public interface BaseDataModelDb {
	Map<UnitaryNames, Integer> getColumnsNames();
	Map<UnitaryNames, Double> getRecords();
	void setRecords(Map<UnitaryNames, Double> records);
	void setColumnsNames(Map<UnitaryNames, Integer> columnsNames);
	Date getDate();
	Date getTime();


}
