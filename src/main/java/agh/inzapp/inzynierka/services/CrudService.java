package agh.inzapp.inzynierka.services;

import agh.inzapp.inzynierka.database.models.DataDb;
import org.springframework.stereotype.Service;


import java.util.List;

@Service
public interface CrudService {
	DataDb add(DataDb dataModel);
	List<DataDb> getAll();


}
