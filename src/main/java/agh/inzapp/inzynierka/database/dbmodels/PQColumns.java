package agh.inzapp.inzynierka.database.dbmodels;


//import lombok.*;

import agh.inzapp.inzynierka.utils.enums.UnitaryNames;

import javax.persistence.*;
@Entity
public class PQColumns {
	@Id
	private long id;

	@ManyToOne
	private PQDataDb pqDataDb;

	@Column
	private int columnNameIndex;

	@Column(name = "unitaryName")
	@Enumerated(EnumType.STRING)
	private UnitaryNames unitaryName;

	public PQColumns() {
	}

	public PQColumns(UnitaryNames unitaryName, int columnNameIndex) {
		this.columnNameIndex = columnNameIndex;
		this.unitaryName = unitaryName;
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public PQDataDb getPqDataDb() {
		return pqDataDb;
	}

	public void setPqDataDb(PQDataDb pqDataDb) {
		this.pqDataDb = pqDataDb;
	}

	public int getColumnNameIndex() {
		return columnNameIndex;
	}

	public void setColumnNameIndex(int columnNameIndex) {
		this.columnNameIndex = columnNameIndex;
	}

	public UnitaryNames getUnitaryName() {
		return unitaryName;
	}

	public void setUnitaryName(UnitaryNames unitaryName) {
		this.unitaryName = unitaryName;
	}
}
