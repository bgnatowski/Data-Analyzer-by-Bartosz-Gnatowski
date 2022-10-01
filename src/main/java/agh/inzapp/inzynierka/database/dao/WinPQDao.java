package agh.inzapp.inzynierka.database.dao;


import agh.inzapp.inzynierka.database.dbmodels.BaseDataModelDb;
import agh.inzapp.inzynierka.database.dbmodels.WinPQDataDb;
import agh.inzapp.inzynierka.database.dbutils.DbManager;
import agh.inzapp.inzynierka.utils.converters.DateConverter;
import agh.inzapp.inzynierka.utils.enums.UnitaryNames;
import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;

import java.sql.*;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

public class WinPQDao extends CommonDao {
	private StringBuilder stringBuilder;
	private Map<UnitaryNames, Integer> mapping = new TreeMap<>();


	public WinPQDao() {
		super();
	}

	public void createOrUpdateAll(List<WinPQDataDb> dataModelDb) throws ApplicationException {
		int batchSize = 20;
		Connection connection = DbManager.getConnection();

		try {
			connection.setAutoCommit(false);

			WinPQDataDb model = dataModelDb.get(0);
			createMapping(model);
			String sql = getCreateTableStatement(model);
			PreparedStatement statement = connection.prepareStatement(sql);

			dataModelDb.forEach(modelDb -> {
				int count = 0;
				modelDb.getColumnsNames().forEach((name, id) -> {try {
						if (id == 0) {
							statement.setDate(mapping.get(name), Date.valueOf(DateConverter.convertToLocalDate(modelDb.getDate())));
//							System.out.println(mapping.get(name) + " " + name + " " + modelDb.getLocalDateTime().toLocalDate());
						} else if (id == 1) {
							statement.setTime(mapping.get(name), Time.valueOf(DateConverter.convertToLocalTime(modelDb.getTime())));
//							System.out.println(mapping.get(name) + " " + name + " " + modelDb.getLocalDateTime().toLocalTime());
						} else if (id == 2) {
							statement.setString(mapping.get(name), String.valueOf(modelDb.getFlag()));
//							System.out.println(mapping.get(name) + " " + name + " " + modelDb.getFlag());
						} else {
							final Double aDouble = modelDb.getRecords().get(name);
//							System.out.println(mapping.get(name) + " " + name + " " + aDouble);
							if (aDouble != null){
								statement.setDouble(mapping.get(name), aDouble);
							} else{
								statement.setNull(mapping.get(name), Types.DOUBLE);
							}
						}
					} catch (SQLException e) {
						System.out.println(e.getMessage()); //TODO exception dialog
					}
				});
				try {
					statement.addBatch();
					if (count % batchSize == 0) {
						statement.executeBatch();
					}
				} catch (SQLException e) {
					System.out.println(e.getMessage()); //TODO exception dialog
				}
			});
			statement.executeBatch();
			connection.commit();
			connection.close();
		} catch (SQLException e) {
			System.out.println(e.getMessage()); //TODO exception dialog
		}
	}
	@Override
	public <T extends BaseDataModelDb, I> void createOrUpdate(BaseDataModelDb model) throws ApplicationException {
//		System.out.println(model);
		super.createOrUpdate(model);
		if (model instanceof WinPQDataDb) {
			WinPQDataDb finalInnerModel = (WinPQDataDb) model;
			Map<UnitaryNames, Double> records = finalInnerModel.getRecords();
			records.forEach((columnName, value) -> {
				stringBuilder = new StringBuilder();
				stringBuilder.append("UPDATE " + model.getClass().getSimpleName());
				stringBuilder.append(" SET " + columnName + " = " + value);
				stringBuilder.append(" WHERE DATE = '" + DateConverter.convertToLocalDate(finalInnerModel.getDate()) + "'");
				stringBuilder.append(" AND TIME = '" + DateConverter.convertToLocalTime(finalInnerModel.getTime()) + "';");
//			System.out.println(stringBuilder.toString());
				try {
					this.getDao(WinPQDataDb.class).executeRaw(stringBuilder.toString());
				} catch (SQLException e) {
					throw new RuntimeException(e);
				} catch (ApplicationException e) {
					throw new RuntimeException(e);
				}
			});
		}
	}

	private String getCreateTableStatement(WinPQDataDb modelDb) {
		stringBuilder = new StringBuilder();
		// " INSERT INTO <TABLENAME BY CLASS NAME> ( "
		stringBuilder.append("INSERT INTO ").append(modelDb.getClass().getSimpleName()).append(" (");
		// pętla wypełniająca: " NAME , "
		modelDb.getColumnsNames().forEach((name, value) -> {
			stringBuilder.append(name.toString().toUpperCase() + ", ");
		});
		// delete last " , "
		stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
		// " ) VALUES ( "
		stringBuilder.append(") VALUES (");
		// pętla wypełniająca " ?, "
		for (int i = 0; i < modelDb.getColumnsNames().size(); i++) {
			stringBuilder.append("?, ");
		}
		// delete last " , "
		stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
		// " ); "
		stringBuilder.append(");");
		return stringBuilder.toString();
	}

	private void createMapping(WinPQDataDb model) {
		AtomicInteger index = new AtomicInteger(1);
		model.getColumnsNames().forEach((name, value) -> {
//			System.out.println(name + " " + value);
			mapping.put(name, index.getAndIncrement());
		});
	}
}
