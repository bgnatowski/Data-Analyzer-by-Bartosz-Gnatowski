package agh.inzapp.inzynierka.service;

import agh.inzapp.inzynierka.database.DataDb;
import org.springframework.stereotype.Service;


import java.io.Serial;
import java.util.List;

@Service
public interface CrudService {
	DataDb add(DataDb dataModel);
	List<DataDb> getAll();


}
