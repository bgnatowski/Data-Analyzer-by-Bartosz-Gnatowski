package agh.inzapp.inzynierka.database.dao;

import agh.inzapp.inzynierka.database.dbmodels.BaseDataModelDb;
import agh.inzapp.inzynierka.database.dbutils.DbManagerOld;
import agh.inzapp.inzynierka.utils.FxmlUtils;
import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;
import com.j256.ormlite.dao.Dao;
import com.j256.ormlite.dao.DaoManager;
import com.j256.ormlite.logger.Logger;
import com.j256.ormlite.logger.LoggerFactory;
import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.support.ConnectionSource;

import java.sql.SQLException;
import java.util.List;

public abstract class CommonDao {
	private static final Logger LOGGER = LoggerFactory.getLogger(CommonDao.class);
	protected final ConnectionSource connectionSource;

	protected CommonDao() {
		this.connectionSource = DbManagerOld.getConnectionSource();
	}
	public <T extends BaseDataModelDb, I> void createOrUpdate(BaseDataModelDb baseDataModelDb) throws ApplicationException {
		Dao<T, I> dao = getDao((Class<T>) baseDataModelDb.getClass());
		try {
			dao.createOrUpdate((T) baseDataModelDb);
		} catch (SQLException e) {
			LOGGER.warn(e.getCause().getMessage());
			throw new ApplicationException(FxmlUtils.getInternalizedPropertyByKey("error.create.update"));
		} finally {
			closeDbConnection();
		}
	}

	public <T extends BaseDataModelDb, I> Dao<T, I> getDao(Class<T> cls) throws ApplicationException {
		try {
			return DaoManager.createDao(connectionSource, cls);
		} catch (SQLException e) {
			LOGGER.warn(e.getCause().getMessage());
			throw new ApplicationException(FxmlUtils.getResourceBundle().getString("error.get.dao"));
		} finally {
			closeDbConnection();
		}
	}

	public void closeDbConnection() throws ApplicationException {
		try {
			connectionSource.close();
		} catch (Exception e) {
			throw new ApplicationException(FxmlUtils.getResourceBundle().getString("error.get.dao"));
		}
	}


	public <T extends BaseDataModelDb, I> void refresh(BaseDataModelDb baseDataModelDb) throws ApplicationException {
		try {
			Dao<T, I> dao = getDao((Class<T>) baseDataModelDb.getClass());
			dao.refresh((T) baseDataModelDb);
		} catch (SQLException e) {
			LOGGER.warn(e.getCause().getMessage());
			throw new ApplicationException(FxmlUtils.getResourceBundle().getString("error.refresh"));
		} finally {
			closeDbConnection();
		}
	}

	public <T extends BaseDataModelDb, I> void delete(BaseDataModelDb baseDataModelDb) throws ApplicationException {
		try {
			Dao<T, I> dao = getDao((Class<T>) baseDataModelDb.getClass());
			dao.delete((T) baseDataModelDb);
		} catch (SQLException e) {
			LOGGER.warn(e.getCause().getMessage());
			throw new ApplicationException(FxmlUtils.getResourceBundle().getString("error.delete"));
		} finally {
			closeDbConnection();
		}
	}
	public<T extends BaseDataModelDb,I> void deleteById(Class<T>cls, Integer id) throws ApplicationException {
		try {
			Dao<T, I> dao = getDao(cls);
			dao.deleteById((I) id);
		} catch (SQLException e) {
			LOGGER.warn(e.getCause().getMessage());
			throw new ApplicationException(FxmlUtils.getResourceBundle().getString("error.delete"));
		} finally {
			closeDbConnection();
		}
	}

	public<T extends BaseDataModelDb,I> T findById(Class<T>cls, Integer id) throws ApplicationException {
		try {
			Dao<T, I> dao = getDao(cls);
			return dao.queryForId((I) id);
		} catch (SQLException e) {
			LOGGER.warn(e.getCause().getMessage());
			throw new ApplicationException(FxmlUtils.getResourceBundle().getString("error.not.found"));
		} finally {
			closeDbConnection();
		}
	}
	public <T extends BaseDataModelDb, I> List<T> queryForAll(Class<T> cls) throws ApplicationException {
		try {
			Dao<T, I> dao = getDao(cls);
			return dao.queryForAll();
		} catch (SQLException e) {
			LOGGER.warn(e.getCause().getMessage());
			throw new ApplicationException(FxmlUtils.getResourceBundle().getString("error.not.found.all"));
		} finally {
			closeDbConnection();
		}
	}

	public <T extends BaseDataModelDb, I> QueryBuilder<T, I> getQueryBuilder(Class<T> cls) throws ApplicationException {
		Dao<T, I> dao = getDao(cls);
		return dao.queryBuilder();
	}

}
