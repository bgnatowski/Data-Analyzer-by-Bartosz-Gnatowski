package agh.inzapp.inzynierka.controllers;

import agh.inzapp.inzynierka.entities.BaseDataDb;
import agh.inzapp.inzynierka.managers.PQDataManager;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class TableViewPaneController {
	@FXML
	private Tab harmonicsTab;
	@FXML
	private Tab normalTab;
	@FXML
	private TableView<?> harmonicsTableView;
	@FXML
	private TableView<?> normalTableView;

	private List<? extends BaseDataDb> dataList;

	public void initialize(){
		bindings();
		getData();
	}

	private void getData() {
//		final List<? extends BaseDataDb> all = PQDataManager.getAll();
//		System.out.println("DZIAŁA");
	}

	private void bindings() {
	}


}
