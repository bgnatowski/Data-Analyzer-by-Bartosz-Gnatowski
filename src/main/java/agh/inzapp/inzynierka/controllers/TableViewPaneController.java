package agh.inzapp.inzynierka.controllers;

import agh.inzapp.inzynierka.database.DataDb;
import agh.inzapp.inzynierka.enums.UniNames;
import agh.inzapp.inzynierka.models.modelFx.DataFx;
import agh.inzapp.inzynierka.models.modelFx.ListDataFx;
import com.sun.javafx.collections.ObservableMapWrapper;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

//@Component
public class TableViewPaneController {

	@FXML
	private Tab harmonicsTab;

	@FXML
	private TableView<?> harmonicsTableView;

	@FXML
	private Tab normalTab;

	@FXML
	private TableView<DataFx> normalTableView;

	private ListDataFx listDataFx;

	@FXML
	public void initialize(){
		listDataFx = new ListDataFx();
		listDataFx.init();
		initTable();
		bindings();
	}

	private void initTable() {
		normalTableView.setEditable(true);
		List<TableColumn<DataFx, ?>> tableColumnList = new ArrayList<>();
		tableColumnList.add(new TableColumn<DataFx, String>("Flag"));
		tableColumnList.add(new TableColumn<DataFx, LocalDate>("Date"));
		tableColumnList.add(new TableColumn<DataFx, LocalTime>("Time"));
		tableColumnList.add(new TableColumn<DataFx, Double>("Var1"));
		tableColumnList.add(new TableColumn<DataFx, Double>("Var2"));
		tableColumnList.add(new TableColumn<DataFx, Double>("Var3"));
		tableColumnList.add(new TableColumn<DataFx, Double>("Var4"));
		tableColumnList.add(new TableColumn<DataFx, Double>("Var5"));
		tableColumnList.add(new TableColumn<DataFx, Double>("Var6"));
		normalTableView.getColumns().addAll(tableColumnList);
	}


	private void bindings() {
//		List<TableColumn<DataFx, ?>> tableColumnList = new ArrayList<>();
//		tableColumnList.add(new TableColumn<DataFx, String>("Flag"));
//		tableColumnList.add(new TableColumn<DataFx, LocalDate>("Date"));
//		tableColumnList.add(new TableColumn<DataFx, LocalTime>("Time"));
//		tableColumnList.add(new TableColumn<DataFx, Double>("Var1"));
//		tableColumnList.add(new TableColumn<DataFx, Double>("Var2"));
//		tableColumnList.add(new TableColumn<DataFx, Double>("Var3"));
//		tableColumnList.add(new TableColumn<DataFx, Double>("Var4"));
//		tableColumnList.add(new TableColumn<DataFx, Double>("Var5"));
//		tableColumnList.add(new TableColumn<DataFx, Double>("Var6"));
//		normalTableView.getColumns().setAll((TableColumn<DataFx, ?>) tableColumnList);
//		normalTableView.setItems(listDataFx.getDataFxObservableList());

//		testColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
	}

}
