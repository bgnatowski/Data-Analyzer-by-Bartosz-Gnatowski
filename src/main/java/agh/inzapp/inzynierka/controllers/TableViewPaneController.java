package agh.inzapp.inzynierka.controllers;

import agh.inzapp.inzynierka.database.DataDb;
import agh.inzapp.inzynierka.database.DataManager;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TableView;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class TableViewPaneController {
	@FXML
	private Tab harmonicsTab;
	@FXML
	private Tab normalTab;
	@FXML
	private TableView<?> harmonicsTableView;
	@FXML
	private TableView<?> normalTableView;

	private List<DataDb> dataList;

	public void initialize(){
		bindings();
		getData();
	}

	private void getData() {
		List<DataDb> all = DataManager.getAll();
		System.out.println("DZIAŁA");
		System.out.println(all.get(0));
	}

	private void bindings() {
	}


}
