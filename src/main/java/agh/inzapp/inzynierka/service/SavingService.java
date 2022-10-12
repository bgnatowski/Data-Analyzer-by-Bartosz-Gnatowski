package agh.inzapp.inzynierka.service;

import agh.inzapp.inzynierka.database.DataDb;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;

public interface SavingService {
	<T> T add(T dataModel);
	List<DataDb> getAll();


}
