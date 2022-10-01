package agh.inzapp.inzynierka.database.dbutils;

import agh.inzapp.inzynierka.database.dbmodels.BaseDataModelDb;
import agh.inzapp.inzynierka.database.dbmodels.WinPQDataDb;
import agh.inzapp.inzynierka.utils.enums.UnitaryNames;
import com.j256.ormlite.jdbc.JdbcConnectionSource;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Map;


public class DbManagerOld {

	private static final Logger LOGGER = LoggerFactory.getLogger(DbManagerOld.class);
//	private static final String JDBC_DRIVER_HD = "jdbc:h2:./app_database";
	private static final String JDBC_DRIVER_HD = "jdbc:h2:./test_db";
	private static final String USER = "admin";
	private static final String PASS = "";

	private static ConnectionSource connectionSource;

	public static void initDatabase(BaseDataModelDb model){
		String statement = createStatementDropTable(model);
		executeStatement(statement);

		statement = createStatementCreateTable(model);
		executeStatement(statement);

		createConnectionSource();
		closeConnectionSource();
	}

	private static String createStatementCreateTable(BaseDataModelDb model) {
		StringBuilder stringBuilder = new StringBuilder();
		if (model instanceof WinPQDataDb){
			WinPQDataDb modelDB = (WinPQDataDb) model;
//		CREATE TABLE MODEL(ID LONG PRIMARY KEY AUTO_INCREMENT,  TIME TIME, DATE DATE, FLAG VARCHAR(1), U12_AVG DOUBLE PRECISION, U12_MAX DOUBLE PRECISION, U12_MIN DOUBLE PRECISION);
			stringBuilder.append("CREATE TABLE ");
			stringBuilder.append(modelDB.getClass().getSimpleName());
			stringBuilder.append("(");
			stringBuilder.append("ID LONG PRIMARY KEY AUTO_INCREMENT, ");
			WinPQDataDb winPQModelObj = (WinPQDataDb) modelDB;
			Map<UnitaryNames, Integer> columns = winPQModelObj.getColumnsNames();
			columns.forEach((name, id) -> {
				if(id == 0){
					stringBuilder.append(name.toString().toUpperCase() + " DATE, ");
				} else if (id == 1) {
					stringBuilder.append(name.toString().toUpperCase() + " TIME, ");
				} else if (id == 2){
					stringBuilder.append(name.toString().toUpperCase() + " VARCHAR(1), ");
				} else {
					stringBuilder.append(name.toString().toUpperCase() + " DOUBLE PRECISION, ");
				}
			});
			stringBuilder.deleteCharAt(stringBuilder.lastIndexOf(","));
			stringBuilder.append(");");
			System.out.println(stringBuilder.toString());
		} else {
			System.out.println("SONEL DB CREATESTATEMENTCREATE");
		}
		return stringBuilder.toString();
	}

	private static void executeStatement(String statementSQL) {
		try (Connection connection = getConnection();
			 // Step 2:Create a statement using connection object
			 Statement statement = connection.createStatement();) {
			// Step 3: Execute the query or update query
			statement.execute(statementSQL);
		} catch (SQLException e) {
			// print SQL exception information
			System.out.println("exception" + e.getMessage());
		}
	}

	private static String createStatementDropTable(BaseDataModelDb model) {
		StringBuilder stringBuilder = new StringBuilder();
		if (model instanceof WinPQDataDb) {
			WinPQDataDb dbModel = (WinPQDataDb) model;
			stringBuilder.append("DROP TABLE IF EXISTS " + model.getClass().getSimpleName() + ";");
		}
		return stringBuilder.toString();
	}

	private static Connection getConnection() {
		Connection connection = null;
		try {
			connection = DriverManager.getConnection(JDBC_DRIVER_HD, USER, PASS);
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return connection;
	}

	private static void createConnectionSource(){
		try {
			connectionSource = new JdbcConnectionSource(JDBC_DRIVER_HD,USER, PASS);
		} catch (SQLException e) {
			LOGGER.warn(e.getMessage());
		}
	}

	public static ConnectionSource getConnectionSource(){
		if(connectionSource == null){
			createConnectionSource();
		}
		return connectionSource;
	}

	public static void closeConnectionSource(){
		if(connectionSource!=null){
			try {
				connectionSource.close();
			} catch (Exception e) {
				LOGGER.warn(e.getMessage());
			}
		}
	}
}