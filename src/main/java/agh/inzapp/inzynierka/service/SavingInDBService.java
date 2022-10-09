package agh.inzapp.inzynierka.service;

import agh.inzapp.inzynierka.entities.BaseDataDb;

import java.util.List;

public interface SavingInDBService {
	<T extends BaseDataDb> T add(T dataModel);
	List<? extends BaseDataDb> getAll();


}
