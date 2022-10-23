package agh.inzapp.inzynierka.services;

import agh.inzapp.inzynierka.database.models.CommonDbModel;
import agh.inzapp.inzynierka.database.models.DataDb;
import agh.inzapp.inzynierka.exceptions.ApplicationException;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public interface CrudService {
	<T extends CommonDbModel> T add(T dataModel) throws ApplicationException;
	List<? extends CommonDbModel> getAll();


}
