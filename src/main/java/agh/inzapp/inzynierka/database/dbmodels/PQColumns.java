//package agh.inzapp.inzynierka.database.dbmodels;
//
//
////import lombok.*;
//
//import agh.inzapp.inzynierka.utils.enums.UnitaryNames;
//import lombok.*;
//
//import javax.persistence.*;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@AllArgsConstructor
//@ToString
//
//@Entity
//public class PQColumns {
//	@Id
//	private Long id;
//
//	@ManyToOne
//	private PQDataDb pqDataDb;
//
//	@Column
//	private int columnNameIndex;
//
//	@Column(name = "unitaryName")
//	@Enumerated(EnumType.STRING)
//	private UnitaryNames unitaryName;
//
//	public PQColumns(UnitaryNames unitaryName, int columnNameIndex) {
//		this.columnNameIndex = columnNameIndex;
//		this.unitaryName = unitaryName;
//	}
//}
