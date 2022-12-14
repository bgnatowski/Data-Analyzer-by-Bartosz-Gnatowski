package agh.inzapp.inzynierka.controllers;

import agh.inzapp.inzynierka.models.enums.DataType;
import agh.inzapp.inzynierka.models.enums.NumberDisplayType;
import agh.inzapp.inzynierka.models.enums.UniNames;
import agh.inzapp.inzynierka.models.fxmodels.*;
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
public class TableViewController {

	@FXML
	private Tab harmonicsTab, normalTab;
	@FXML
	private TableView<CommonModelFx> harmonicsTableView;
	@FXML
	private TableView<CommonModelFx> normalTableView;
	@FXML
	private ComboBox<NumberDisplayType> displayTypeNorm, displayTypeHarmo;
	private ListDataFx listDataFx;
	private ListHarmoFx listHarmoFx;
	private int precisionNormal = 2;
	private int precisionHarmo = 2;

	@FXML
	public void initialize(){
		listDataFx = ListDataFx.getInstance();
		listHarmoFx = ListHarmoFx.getInstance();
		bindNormal();
		bindHarmonics();
	}
	private void bindHarmonics() {
		harmonicsTab.setDisable(false);
		if (listHarmoFx != null) {
			displayTypeHarmo.getItems().setAll(FXCollections.observableArrayList(NORMAL, SCIENTIFIC));
			displayTypeHarmo.getSelectionModel().selectFirst();
			initTableHarmonics();
		}
	}

	private void bindNormal() {
		normalTab.setDisable(false);
		if (listDataFx != null) {
			displayTypeNorm.getItems().setAll(FXCollections.observableArrayList(NORMAL, SCIENTIFIC));
			displayTypeNorm.getSelectionModel().selectFirst();
			initTableNormal();
		}
	}

	private void initTableNormal() {
		if (!listDataFx.getDataFxObservableList().isEmpty()){
			normalTab.setDisable(false);
			normalTableView.setEditable(true);

			final DataFx dataFx = listDataFx.getDataFxList().get(0);
			List<TableColumn<CommonModelFx, Object>> tableColumnList = getTableColumns(dataFx.getColumnNames());
			normalTableView.getColumns().addAll(tableColumnList);
			normalTableView.getItems().addAll(listDataFx.getDataFxObservableList());
			changeDisplayTypeNorm();
		}
	}
	private void initTableHarmonics() {
		if (!listHarmoFx.getHarmoFxObservableList().isEmpty()){
			harmonicsTab.setDisable(false);
			harmonicsTableView.setEditable(true);

			final HarmoFx harmoFx = listHarmoFx.getHarmoFxList().get(0);
			final ObservableList<UniNames> columnNames = harmoFx.getColumnNames();

			List<TableColumn<CommonModelFx, Object>> tableColumnList = getTableColumns(columnNames);
			harmonicsTableView.getColumns().addAll(tableColumnList);
			harmonicsTableView.getItems().addAll(listHarmoFx.getHarmoFxObservableList());
			changeDisplayTypeHarmo();
		}
	}

	private void setRightAlignment(TableColumn<CommonModelFx, Object> tableColumn) {
		tableColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
	}

	private List<TableColumn<CommonModelFx, Object>> getTableColumns(ObservableList<UniNames> columnNames) {
		List<TableColumn<CommonModelFx, Object>> tableColumnList = new ArrayList<>();

		TableColumn<CommonModelFx, Object> idColumn = new TableColumn<>("id");
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnList.add(idColumn);

		columnNames.forEach(uniName -> {
			TableColumn tableColumn;
			switch (uniName){
				case Flag, Flag_A, Flag_G, Flag_E, Flag_T, Flag_P-> {
					tableColumn = new TableColumn<CommonModelFx, Map<UniNames, String>>(uniName.toString());
					tableColumn.setCellValueFactory(
							(Callback<TableColumn.CellDataFeatures<CommonModelFx, String>, ObservableValue<String>>) dataFxCellDataFeatures ->
									new SimpleStringProperty(dataFxCellDataFeatures.getValue().flagsProperty().getValue().get(uniName)));
					setRightAlignment(tableColumn);
				}
				case Date -> {
					tableColumn = new TableColumn<CommonModelFx, LocalDate>(uniName.toString());
					tableColumn.setCellValueFactory(
							(Callback<TableColumn.CellDataFeatures<CommonModelFx, LocalDate>, ObservableValue<LocalDate>>) dataFxCellDataFeatures ->
									new SimpleObjectProperty<>(dataFxCellDataFeatures.getValue().dateProperty().getValue().toLocalDate()));
					setRightAlignment(tableColumn);
				}
				case Time -> {
					tableColumn = new TableColumn<DataFx, LocalTime>(uniName.toString());
					tableColumn.setCellValueFactory(
							(Callback<TableColumn.CellDataFeatures<CommonModelFx, LocalTime>, ObservableValue<LocalTime>>) dataFxCellDataFeatures ->
									new SimpleObjectProperty<>(dataFxCellDataFeatures.getValue().dateProperty().getValue().toLocalTime()));
					setRightAlignment(tableColumn);
				}
				default ->{
					tableColumn = new TableColumn<CommonModelFx, Double>(uniName + " " + uniName.getUnit());
					tableColumn.setCellValueFactory(
							(Callback<TableColumn.CellDataFeatures<CommonModelFx, Double>, ObservableValue<?>>) cellDataFeatures -> {
								final Optional<Double> aDouble = Optional.ofNullable(cellDataFeatures.getValue().recordsProperty().getValue().get(uniName));
								if (aDouble.isEmpty())
									return new SimpleObjectProperty<Double>(null);
								else{
									return new SimpleDoubleProperty(aDouble.get());
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
			case NORMAL -> applyNotation(DataType.NORMAL_DATA, "f");
			case SCIENTIFIC -> applyNotation(DataType.NORMAL_DATA, "E");
		}
	}

	@FXML
	private void changeDisplayTypeHarmo() {
		final NumberDisplayType type = displayTypeHarmo.getValue();

		switch (type){
			case NORMAL -> applyNotation(DataType.HARMONICS_DATA, "f");
			case SCIENTIFIC -> applyNotation(DataType.HARMONICS_DATA, "E");
		}
	}

	private void applyNotation(DataType dataType, String format) {
		ObservableList<TableColumn<CommonModelFx, ?>> columns = FXCollections.observableArrayList();
		int precision = 0;

		switch (dataType){
			case NORMAL_DATA ->{
				columns = normalTableView.getColumns();
				precision = precisionNormal;
			}
			case HARMONICS_DATA -> {
				columns = harmonicsTableView.getColumns();
				precision = precisionHarmo;
			}
		}

		int finalPrecision = precision;
		columns.forEach(column ->
			column.setCellFactory(param -> new TableCell(){
				@Override
				protected void updateItem(Object item, boolean empty) {
					super.updateItem(item, empty);
					if(item == null || empty){
						setText(null);
					}else if(item instanceof Double){
						setText(String.format("%."+ finalPrecision +format, ((Double) item).doubleValue()));
					} else {
						setText(item.toString());
					}
				}
			}));
	}

	@FXML
	private void leftShiftPrecisionNorm() {
		precisionNormal--;
		changeDisplayTypeNorm();
	}

	@FXML
	private void rightShiftPrecisionNorm() {
		precisionNormal++;
		changeDisplayTypeNorm();
	}

	@FXML
	private void leftShiftPrecisionHarmo() {
		precisionHarmo--;
		changeDisplayTypeHarmo();
	}

	@FXML
	private void rightShiftPrecisionHarmo() {
		precisionHarmo++;
		changeDisplayTypeHarmo();
	}
}
