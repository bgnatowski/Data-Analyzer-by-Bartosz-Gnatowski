package agh.inzapp.inzynierka.database.dbmodels;

import agh.inzapp.inzynierka.utils.enums.UnitaryNames;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

import java.util.Date;
import java.util.Map;
import java.util.TreeMap;
@DatabaseTable(tableName = "WINPQDATA")
public class WinPQDataDb implements BaseDataModelDb {
	@DatabaseField(generatedId = true)
	private long id;
	@DatabaseField(columnName = "DATE",canBeNull = false, format = "yyyy-MM-DD")
	private Date date;
	@DatabaseField(columnName = "TIME",canBeNull = false, format = "HH:mm")
	private Date time;
	@DatabaseField(columnName = "FLAG")
	private Character flag;
	private Map<UnitaryNames, Integer> columnsNames = new TreeMap<>();
	private Map<UnitaryNames, Double> records = new TreeMap<>();
	public WinPQDataDb() {
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public Character getFlag() {
		return flag;
	}

	public void setFlag(Character flag) {
		this.flag = flag;
	}

	@Override
	public Map<UnitaryNames, Integer> getColumnsNames() {
		return columnsNames;
	}

	@Override
	public void setColumnsNames(Map<UnitaryNames, Integer> columnsNames) {
		this.columnsNames = columnsNames;
	}

	@Override
	public Map<UnitaryNames, Double> getRecords() {
		return records;
	}

	@Override
	public void setRecords(Map<UnitaryNames, Double> records) {
		this.records = records;
	}
}
