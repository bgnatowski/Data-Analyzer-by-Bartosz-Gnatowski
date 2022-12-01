package agh.inzapp.inzynierka.controllers;

import agh.inzapp.inzynierka.models.ListDataFx;
import agh.inzapp.inzynierka.models.ListHarmoFx;
import agh.inzapp.inzynierka.models.enums.UniNames;
import agh.inzapp.inzynierka.models.fxmodels.DataFx;
import agh.inzapp.inzynierka.models.fxmodels.HarmoFx;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Controller
public class TableViewPaneController {

	@FXML
	private Tab harmonicsTab, normalTab;
	@FXML
	private TableView<HarmoFx> harmonicsTableView;
	@FXML
	private TableView<DataFx> normalTableView;
	private static ListDataFx listDataFx;
	private static ListHarmoFx listHarmoFx;

	@FXML
	public void initialize(){
		listDataFx = ListDataFx.getInstance();
		listHarmoFx = ListHarmoFx.getInstance();
		bindings();
	}
	private void bindings() {
		normalTab.setDisable(false);
		harmonicsTab.setDisable(false);
		if (listDataFx != null) {
			initTableNormal();
		}
		if (listHarmoFx != null) {
			initTableHarmonics();
		}
	}
	private void initTableNormal() {
		if (!listDataFx.getDataFxObservableList().isEmpty()){
			normalTab.setDisable(false);
			normalTableView.setEditable(true);

			final DataFx dataFx = listDataFx.getDataFxList().get(0);
			List<TableColumn<DataFx, ?>> tableColumnList = getTableColumnsNormal(dataFx.getColumnNames());
			normalTableView.getColumns().addAll(tableColumnList);
			normalTableView.getItems().addAll(listDataFx.getDataFxObservableList());
		}
	}
	private static List<TableColumn<DataFx, ?>> getTableColumnsNormal(ObservableList<UniNames> columnNames) {
		List<TableColumn<DataFx, ?>> tableColumnList = new ArrayList<>();

		TableColumn<DataFx, Long> idColumn = new TableColumn<>("id");
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnList.add(idColumn);

		columnNames.forEach(uniName -> {
			TableColumn tableColumn;
			switch (uniName){
				case Flag, Flag_A, Flag_G, Flag_E, Flag_T, Flag_P-> {
					tableColumn = new TableColumn<DataFx, Map<UniNames, String>>(uniName.toString());
					tableColumn.setCellValueFactory(
							(Callback<TableColumn.CellDataFeatures<DataFx, String>, ObservableValue<String>>) dataFxCellDataFeatures ->
									new SimpleStringProperty(dataFxCellDataFeatures.getValue().flagsProperty().getValue().get(uniName)));
				}
				case Date -> {
					tableColumn = new TableColumn<DataFx, LocalDate>(uniName.toString());
					tableColumn.setCellValueFactory(
							(Callback<TableColumn.CellDataFeatures<DataFx, LocalDate>, ObservableValue<LocalDate>>) dataFxCellDataFeatures ->
									new SimpleObjectProperty<>(dataFxCellDataFeatures.getValue().dateProperty().getValue().toLocalDate()));
				}
				case Time -> {
					tableColumn = new TableColumn<DataFx, LocalTime>(uniName.toString());
					tableColumn.setCellValueFactory(
							(Callback<TableColumn.CellDataFeatures<DataFx, LocalTime>, ObservableValue<LocalTime>>) dataFxCellDataFeatures ->
									new SimpleObjectProperty<>(dataFxCellDataFeatures.getValue().dateProperty().getValue().toLocalTime()));
				}
				default ->{
					tableColumn = new TableColumn<DataFx, Double>(uniName + " " + uniName.getUnit());
					tableColumn.setCellValueFactory(
							(Callback<TableColumn.CellDataFeatures<DataFx, Double>, ObservableValue<?>>) cellDataFeatures -> {
						final Optional<Double> aDouble = Optional.ofNullable(cellDataFeatures.getValue().recordsProperty().getValue().get(uniName));
						if (aDouble.isEmpty())
							return new SimpleObjectProperty<Double>(null);
						else{
							return new SimpleDoubleProperty(aDouble.get());
						}
					});
				}

			}
			tableColumnList.add(tableColumn);
		});
		return tableColumnList;
	}
	private void initTableHarmonics() {
		if (!listHarmoFx.getHarmoFxObservableList().isEmpty()){
			harmonicsTab.setDisable(false);
			harmonicsTableView.setEditable(true);

			final HarmoFx harmoFx = listHarmoFx.getHarmoFxList().get(0);
			final ObservableList<UniNames> columnNames = harmoFx.getColumnNames();

			List<TableColumn<HarmoFx, ?>> tableColumnList = getTableColumnsHarmonics(columnNames);
			harmonicsTableView.getColumns().addAll(tableColumnList);
			harmonicsTableView.getItems().addAll(listHarmoFx.getHarmoFxObservableList());
		}
	}

	private static List<TableColumn<HarmoFx, ?>> getTableColumnsHarmonics(ObservableList<UniNames> columnNames) {
		List<TableColumn<HarmoFx, ?>> tableColumnList = new ArrayList<>();

		TableColumn<HarmoFx, Long> idColumn = new TableColumn<>("id");
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnList.add(idColumn);

		columnNames.forEach(uniName -> {
			TableColumn tableColumn;
			switch (uniName){
				case Flag, Flag_A, Flag_G, Flag_E, Flag_T, Flag_P -> {
					tableColumn = new TableColumn<HarmoFx, Map<UniNames, String>>(uniName.toString());
					tableColumn.setCellValueFactory(
							(Callback<TableColumn.CellDataFeatures<HarmoFx, String>, ObservableValue<String>>) dataFxCellDataFeatures ->
									new SimpleStringProperty(dataFxCellDataFeatures.getValue().flagsProperty().getValue().get(uniName)));
				}
				case Date -> {
					tableColumn = new TableColumn<HarmoFx, LocalDate>(uniName.toString());
					tableColumn.setCellValueFactory(
							(Callback<TableColumn.CellDataFeatures<HarmoFx, LocalDate>, ObservableValue<LocalDate>>) dataFxCellDataFeatures ->
									new SimpleObjectProperty<>(dataFxCellDataFeatures.getValue().dateProperty().getValue().toLocalDate()));
				}
				case Time -> {
					tableColumn = new TableColumn<HarmoFx, LocalTime>(uniName.toString());
					tableColumn.setCellValueFactory(
							(Callback<TableColumn.CellDataFeatures<HarmoFx, LocalTime>, ObservableValue<LocalTime>>) dataFxCellDataFeatures ->
									new SimpleObjectProperty<>(dataFxCellDataFeatures.getValue().dateProperty().getValue().toLocalTime()));
				}
				default ->{
					tableColumn = new TableColumn<HarmoFx, Double>(uniName + " " + uniName.getUnit());
					tableColumn.setCellValueFactory(
							(Callback<TableColumn.CellDataFeatures<HarmoFx, Double>, ObservableValue<?>>) dataFxCellDataFeatures ->
									new SimpleDoubleProperty(dataFxCellDataFeatures.getValue().recordsProperty().getValue().get(uniName)));
				}
			}
			tableColumnList.add(tableColumn);
		});
		return tableColumnList;
	}


}
