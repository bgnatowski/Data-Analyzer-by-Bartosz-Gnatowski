package agh.inzapp.inzynierka.database.dbmodels;

import agh.inzapp.inzynierka.utils.enums.UnitaryNames;

import javax.persistence.*;

@Entity
public class PQData {
	@Id
	private long id;
	@ManyToOne
	private PQDataDb pqDataDb;

	@Column(name = "unitaryNames")
	@Enumerated(EnumType.STRING)
	private UnitaryNames unitaryNames;

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

	public UnitaryNames getUnitaryNames() {
		return unitaryNames;
	}

	public void setUnitaryNames(UnitaryNames unitaryNames) {
		this.unitaryNames = unitaryNames;
	}
}


