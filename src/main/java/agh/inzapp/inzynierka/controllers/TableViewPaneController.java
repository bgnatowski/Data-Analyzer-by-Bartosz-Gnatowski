package agh.inzapp.inzynierka.controllers;

import agh.inzapp.inzynierka.models.enums.NumberDisplayType;
import agh.inzapp.inzynierka.models.enums.UniNames;
import agh.inzapp.inzynierka.models.fxmodels.DataFx;
import agh.inzapp.inzynierka.models.fxmodels.HarmoFx;
import agh.inzapp.inzynierka.models.fxmodels.ListDataFx;
import agh.inzapp.inzynierka.models.fxmodels.ListHarmoFx;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.springframework.stereotype.Controller;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static agh.inzapp.inzynierka.models.enums.NumberDisplayType.*;

@Controller
public class TableViewPaneController {

	@FXML
	private Tab harmonicsTab, normalTab;
	@FXML
	private TableView<HarmoFx> harmonicsTableView;
	@FXML
	private TableView<DataFx> normalTableView;
	@FXML
	private Button leftShiftPrecisionButtonNorm, rightShiftPrecisionButtonNorm,
			leftShiftPrecisionButtonHarmo, rightShiftPrecisionButtonHarmo;
	@FXML
	private ComboBox<NumberDisplayType> displayTypeNorm, displayTypeHarmo;
	private static ListDataFx listDataFx;
	private static ListHarmoFx listHarmoFx;

	@FXML
	public void initialize(){
		listDataFx = ListDataFx.getInstance();
		listHarmoFx = ListHarmoFx.getInstance();
		bindings();
	}
	private void bindings() {
		bindNormal();
		bindHarmonics();
	}

	private void bindHarmonics() {
		harmonicsTab.setDisable(false);
		if (listHarmoFx != null) {
			initTableHarmonics();
			displayTypeHarmo.getItems().setAll(FXCollections.observableArrayList(NORMAL, SCIENTIFIC));
			displayTypeHarmo.getSelectionModel().selectFirst();
		}
	}

	private void bindNormal() {
		normalTab.setDisable(false);
		if (listDataFx != null) {
			initTableNormal();
			displayTypeNorm.getItems().setAll(FXCollections.observableArrayList(NORMAL, SCIENTIFIC));
			displayTypeHarmo.getSelectionModel().selectFirst();
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

	private static void setRightAlignment(TableColumn tableColumn) {
		tableColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
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
					setRightAlignment(tableColumn);
				}
				case Date -> {
					tableColumn = new TableColumn<DataFx, LocalDate>(uniName.toString());
					tableColumn.setCellValueFactory(
							(Callback<TableColumn.CellDataFeatures<DataFx, LocalDate>, ObservableValue<LocalDate>>) dataFxCellDataFeatures ->
									new SimpleObjectProperty<>(dataFxCellDataFeatures.getValue().dateProperty().getValue().toLocalDate()));
					setRightAlignment(tableColumn);
				}
				case Time -> {
					tableColumn = new TableColumn<DataFx, LocalTime>(uniName.toString());
					tableColumn.setCellValueFactory(
							(Callback<TableColumn.CellDataFeatures<DataFx, LocalTime>, ObservableValue<LocalTime>>) dataFxCellDataFeatures ->
									new SimpleObjectProperty<>(dataFxCellDataFeatures.getValue().dateProperty().getValue().toLocalTime()));
					setRightAlignment(tableColumn);
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
					tableColumn.setCellFactory(c -> new TableCell<HarmoFx, Double>(){
						@Override
						protected void updateItem(Double balance, boolean empty) {
							super.updateItem(balance, empty);
							if(balance == null || empty){
								setText(null);
							}else{
								setText(String.format("%.2f", balance.doubleValue()));
							}
						}
					});
					setRightAlignment(tableColumn);
				}

			}
			tableColumnList.add(tableColumn);
		});
		return tableColumnList;
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
					setRightAlignment(tableColumn);
				}
				case Date -> {
					tableColumn = new TableColumn<HarmoFx, LocalDate>(uniName.toString());
					tableColumn.setCellValueFactory(
							(Callback<TableColumn.CellDataFeatures<HarmoFx, LocalDate>, ObservableValue<LocalDate>>) dataFxCellDataFeatures ->
									new SimpleObjectProperty<>(dataFxCellDataFeatures.getValue().dateProperty().getValue().toLocalDate()));
					setRightAlignment(tableColumn);
				}
				case Time -> {
					tableColumn = new TableColumn<HarmoFx, LocalTime>(uniName.toString());
					tableColumn.setCellValueFactory(
							(Callback<TableColumn.CellDataFeatures<HarmoFx, LocalTime>, ObservableValue<LocalTime>>) dataFxCellDataFeatures ->
									new SimpleObjectProperty<>(dataFxCellDataFeatures.getValue().dateProperty().getValue().toLocalTime()));
					setRightAlignment(tableColumn);
				}
				default ->{
					tableColumn = new TableColumn<HarmoFx, Double>(uniName + " " + uniName.getUnit());
					tableColumn.setCellValueFactory(
							(Callback<TableColumn.CellDataFeatures<HarmoFx, Double>, ObservableValue<?>>) dataFxCellDataFeatures ->
									new SimpleDoubleProperty(dataFxCellDataFeatures.getValue().recordsProperty().getValue().get(uniName)));
					tableColumn.setCellFactory(c -> new TableCell<HarmoFx, Double>(){
						@Override
						protected void updateItem(Double balance, boolean empty) {
							super.updateItem(balance, empty);
							if(balance == null || empty){
								setText(null);
							}else{
								setText(String.format("%.2f", balance.doubleValue()));
							}
						}
					});
					setRightAlignment(tableColumn);
				}
			}
			tableColumnList.add(tableColumn);
		});
		return tableColumnList;
	}

	@FXML
	private void changeDisplayTypeNorm() {
		final NumberDisplayType type = displayTypeNorm.getValue();

		switch (type){
			case NORMAL -> {}
			case SCIENTIFIC -> applyScientificNotationOnNormal();
		}
	}

	private void applyScientificNotationOnNormal() {
		final ObservableList<TableColumn<DataFx, ?>> columns = normalTableView.getColumns();
//		columns.forEach(column ->{
//			column.setCellFactory(param -> new TableCell<DataFx, Object>(){
//				@Override
//				protected void updateItem(Object item, boolean b) {
//					super.updateItem(co);
//				}
//			});
//		});
	}

	@FXML
	private void leftShiftPrecisionNorm() {
	}

	@FXML
	private void rightShiftPrecisionNorm() {
	}

	@FXML
	private void changeDisplayTypeHarmo() {
	}

	@FXML
	private void leftShiftPrecisionHarmo() {
	}

	@FXML
	private void rightShiftPrecisionHarmo() {
	}
}
