package agh.inzapp.inzynierka.controllers;

import agh.inzapp.inzynierka.models.enums.DataType;
import agh.inzapp.inzynierka.models.enums.NumberDisplayType;
import agh.inzapp.inzynierka.models.enums.UniNames;
import agh.inzapp.inzynierka.models.fxmodels.*;
import agh.inzapp.inzynierka.utils.DialogUtils;
import agh.inzapp.inzynierka.utils.SavingUtils;
import agh.inzapp.inzynierka.utils.exceptions.ApplicationException;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Callback;
import org.springframework.stereotype.Controller;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static agh.inzapp.inzynierka.models.enums.NumberDisplayType.NORMAL;
import static agh.inzapp.inzynierka.models.enums.NumberDisplayType.SCIENTIFIC;

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
	private int precisionNormal = 2;
	private int precisionHarmo = 2;
	private String formatHarmo = "f";
	private String formatNormal = "f";
	private ListCommonModelFx modelList;

	@FXML
	public void initialize() {
		try {
			modelList = ListCommonModelFx.getInstance();
			bindNormal();
			bindHarmonics();
			Platform.runLater(new Runnable() {
				@Override public void run() {
					showInfoDialog();
				}
			});
		} catch (ApplicationException e) {
			DialogUtils.errorDialog(e.getMessage());
		}
	}

	private void bindNormal() {
		normalTab.setDisable(false);
		if (modelList != null) {
			displayTypeNorm.getItems().setAll(FXCollections.observableArrayList(NORMAL, SCIENTIFIC));
			displayTypeNorm.getSelectionModel().selectFirst();
			initTableNormal();
		}
	}

	private void bindHarmonics() {
		harmonicsTab.setDisable(false);
		if (!modelList.getModelsFxObservableList().isEmpty()) {
			displayTypeHarmo.getItems().setAll(FXCollections.observableArrayList(NORMAL, SCIENTIFIC));
			displayTypeHarmo.getSelectionModel().selectFirst();
			initTableHarmonics();
		}
	}

	private void initTableNormal() {
		if (!modelList.getModelsFxObservableList().isEmpty()) {
			normalTab.setDisable(false);
			normalTableView.setEditable(true);

			List<TableColumn<CommonModelFx, Object>> tableColumnList = getTableNormal(modelList.getColumStandardNames());
			normalTableView.getColumns().addAll(tableColumnList);
			normalTableView.getItems().addAll(modelList.getModelsFxObservableList());
			changeDisplayTypeNorm();
		}
	}

	private void initTableHarmonics() {
		if (!modelList.getModelsFxObservableList().isEmpty()) {
			harmonicsTab.setDisable(false);
			harmonicsTableView.setEditable(true);

			List<TableColumn<CommonModelFx, Object>> tableColumnList = getTableHarmonics(modelList.getColumHarmonicNames());
			harmonicsTableView.getColumns().addAll(tableColumnList);
			harmonicsTableView.getItems().addAll(modelList.getModelsFxObservableList());
			changeDisplayTypeHarmo();
		}
	}

	private void setRightAlignment(TableColumn<CommonModelFx, Object> tableColumn) {
		tableColumn.setStyle("-fx-alignment: CENTER-RIGHT;");
	}

	private List<TableColumn<CommonModelFx, Object>> getTableNormal(ObservableList<UniNames> columnNames) {
		List<TableColumn<CommonModelFx, Object>> tableColumnList = new ArrayList<>();

		TableColumn<CommonModelFx, Object> idColumn = new TableColumn<>("id");
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnList.add(idColumn);

		columnNames.forEach(uniName -> {
			TableColumn tableColumn;
			switch (uniName) {
				case Flag, Flag_A, Flag_G, Flag_E, Flag_T, Flag_P -> {
					tableColumn = new TableColumn<CommonModelFx, Map<UniNames, String>>(uniName.toString());
					tableColumn.setCellValueFactory(
							(Callback<TableColumn.CellDataFeatures<CommonModelFx, String>, ObservableValue<String>>) dataFxCellDataFeatures ->
									new SimpleStringProperty(dataFxCellDataFeatures.getValue().flagsProperty().getValue().get(uniName)));
					setRightAlignment(tableColumn);
					tableColumnList.add(tableColumn);
				}
				case Date -> {
					tableColumn = new TableColumn<CommonModelFx, LocalDate>(uniName.toString());
					tableColumn.setCellValueFactory(
							(Callback<TableColumn.CellDataFeatures<CommonModelFx, LocalDate>, ObservableValue<LocalDate>>) dataFxCellDataFeatures ->
									new SimpleObjectProperty<>(dataFxCellDataFeatures.getValue().dateProperty().getValue().toLocalDate()));
					setRightAlignment(tableColumn);
					tableColumnList.add(tableColumn);
				}
				case Time -> {
					tableColumn = new TableColumn<CommonModelFx, LocalTime>(uniName.toString());
					tableColumn.setCellValueFactory(
							(Callback<TableColumn.CellDataFeatures<CommonModelFx, LocalTime>, ObservableValue<LocalTime>>) dataFxCellDataFeatures ->
									new SimpleObjectProperty<>(dataFxCellDataFeatures.getValue().dateProperty().getValue().toLocalTime()));
					setRightAlignment(tableColumn);
					tableColumnList.add(tableColumn);
				}
				case THD_L1, THD_L2, THD_L3, THD_12, THD_23, THD_31,
						H1_UL1, H2_UL1, H3_UL1, H4_UL1, H5_UL1, H6_UL1, H7_UL1, H8_UL1, H9_UL1, H10_UL1,
						H11_UL1, H12_UL1, H13_UL1, H14_UL1, H15_UL1, H16_UL1, H17_UL1, H18_UL1, H19_UL1, H20_UL1,
						H21_UL1, H22_UL1, H23_UL1, H24_UL1, H25_UL1, H26_UL1, H27_UL1, H28_UL1, H29_UL1, H30_UL1,
						H31_UL1, H32_UL1, H33_UL1, H34_UL1, H35_UL1, H36_UL1, H37_UL1, H38_UL1, H39_UL1, H40_UL1,
						H41_UL1, H42_UL1, H43_UL1, H44_UL1, H45_UL1, H46_UL1, H47_UL1, H48_UL1, H49_UL1, H50_UL1,
						H1_UL2, H2_UL2, H3_UL2, H4_UL2, H5_UL2, H6_UL2, H7_UL2, H8_UL2, H9_UL2, H10_UL2,
						H11_UL2, H12_UL2, H13_UL2, H14_UL2, H15_UL2, H16_UL2, H17_UL2, H18_UL2, H19_UL2, H20_UL2,
						H21_UL2, H22_UL2, H23_UL2, H24_UL2, H25_UL2, H26_UL2, H27_UL2, H28_UL2, H29_UL2, H30_UL2,
						H31_UL2, H32_UL2, H33_UL2, H34_UL2, H35_UL2, H36_UL2, H37_UL2, H38_UL2, H39_UL2, H40_UL2,
						H41_UL2, H42_UL2, H43_UL2, H44_UL2, H45_UL2, H46_UL2, H47_UL2, H48_UL2, H49_UL2, H50_UL2,
						H1_UL3, H2_UL3, H3_UL3, H4_UL3, H5_UL3, H6_UL3, H7_UL3, H8_UL3, H9_UL3, H10_UL3,
						H11_UL3, H12_UL3, H13_UL3, H14_UL3, H15_UL3, H16_UL3, H17_UL3, H18_UL3, H19_UL3, H20_UL3,
						H21_UL3, H22_UL3, H23_UL3, H24_UL3, H25_UL3, H26_UL3, H27_UL3, H28_UL3, H29_UL3, H30_UL3,
						H31_UL3, H32_UL3, H33_UL3, H34_UL3, H35_UL3, H36_UL3, H37_UL3, H38_UL3, H39_UL3, H40_UL3,
						H41_UL3, H42_UL3, H43_UL3, H44_UL3, H45_UL3, H46_UL3, H47_UL3, H48_UL3, H49_UL3, H50_UL3 -> {

				}
				default -> {
					tableColumn = new TableColumn<CommonModelFx, Double>(uniName + " " + uniName.getUnit());
					tableColumn.setCellValueFactory(
							(Callback<TableColumn.CellDataFeatures<CommonModelFx, Double>, ObservableValue<?>>) cellDataFeatures -> {
								final Optional<Double> aDouble = Optional.ofNullable(cellDataFeatures.getValue().recordsProperty().getValue().get(uniName));
								if (aDouble.isEmpty())
									return new SimpleObjectProperty<Double>(null);
								else {
									return new SimpleDoubleProperty(aDouble.get());
								}
							});
					setRightAlignment(tableColumn);
					tableColumnList.add(tableColumn);
				}
			}
		});
		return tableColumnList;
	}

	private List<TableColumn<CommonModelFx, Object>> getTableHarmonics(ObservableList<UniNames> columnNames) {
		List<TableColumn<CommonModelFx, Object>> tableColumnList = new ArrayList<>();

		TableColumn<CommonModelFx, Object> idColumn = new TableColumn<>("id");
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnList.add(idColumn);

		columnNames.forEach(uniName -> {
			TableColumn tableColumn;
			switch (uniName) {
				case Flag, Flag_A, Flag_G, Flag_E, Flag_T, Flag_P -> {
					tableColumn = new TableColumn<CommonModelFx, Map<UniNames, String>>(uniName.toString());
					tableColumn.setCellValueFactory(
							(Callback<TableColumn.CellDataFeatures<CommonModelFx, String>, ObservableValue<String>>) dataFxCellDataFeatures ->
									new SimpleStringProperty(dataFxCellDataFeatures.getValue().flagsProperty().getValue().get(uniName)));
					setRightAlignment(tableColumn);
					tableColumnList.add(tableColumn);
				}
				case Date -> {
					tableColumn = new TableColumn<CommonModelFx, LocalDate>(uniName.toString());
					tableColumn.setCellValueFactory(
							(Callback<TableColumn.CellDataFeatures<CommonModelFx, LocalDate>, ObservableValue<LocalDate>>) dataFxCellDataFeatures ->
									new SimpleObjectProperty<>(dataFxCellDataFeatures.getValue().dateProperty().getValue().toLocalDate()));
					setRightAlignment(tableColumn);
					tableColumnList.add(tableColumn);
				}
				case Time -> {
					tableColumn = new TableColumn<CommonModelFx, LocalTime>(uniName.toString());
					tableColumn.setCellValueFactory(
							(Callback<TableColumn.CellDataFeatures<CommonModelFx, LocalTime>, ObservableValue<LocalTime>>) dataFxCellDataFeatures ->
									new SimpleObjectProperty<>(dataFxCellDataFeatures.getValue().dateProperty().getValue().toLocalTime()));
					setRightAlignment(tableColumn);
					tableColumnList.add(tableColumn);
				}
				case THD_L1, THD_L2, THD_L3, THD_12, THD_23, THD_31,
						H1_UL1, H2_UL1, H3_UL1, H4_UL1, H5_UL1, H6_UL1, H7_UL1, H8_UL1, H9_UL1, H10_UL1,
						H11_UL1, H12_UL1, H13_UL1, H14_UL1, H15_UL1, H16_UL1, H17_UL1, H18_UL1, H19_UL1, H20_UL1,
						H21_UL1, H22_UL1, H23_UL1, H24_UL1, H25_UL1, H26_UL1, H27_UL1, H28_UL1, H29_UL1, H30_UL1,
						H31_UL1, H32_UL1, H33_UL1, H34_UL1, H35_UL1, H36_UL1, H37_UL1, H38_UL1, H39_UL1, H40_UL1,
						H41_UL1, H42_UL1, H43_UL1, H44_UL1, H45_UL1, H46_UL1, H47_UL1, H48_UL1, H49_UL1, H50_UL1,
						H1_UL2, H2_UL2, H3_UL2, H4_UL2, H5_UL2, H6_UL2, H7_UL2, H8_UL2, H9_UL2, H10_UL2,
						H11_UL2, H12_UL2, H13_UL2, H14_UL2, H15_UL2, H16_UL2, H17_UL2, H18_UL2, H19_UL2, H20_UL2,
						H21_UL2, H22_UL2, H23_UL2, H24_UL2, H25_UL2, H26_UL2, H27_UL2, H28_UL2, H29_UL2, H30_UL2,
						H31_UL2, H32_UL2, H33_UL2, H34_UL2, H35_UL2, H36_UL2, H37_UL2, H38_UL2, H39_UL2, H40_UL2,
						H41_UL2, H42_UL2, H43_UL2, H44_UL2, H45_UL2, H46_UL2, H47_UL2, H48_UL2, H49_UL2, H50_UL2,
						H1_UL3, H2_UL3, H3_UL3, H4_UL3, H5_UL3, H6_UL3, H7_UL3, H8_UL3, H9_UL3, H10_UL3,
						H11_UL3, H12_UL3, H13_UL3, H14_UL3, H15_UL3, H16_UL3, H17_UL3, H18_UL3, H19_UL3, H20_UL3,
						H21_UL3, H22_UL3, H23_UL3, H24_UL3, H25_UL3, H26_UL3, H27_UL3, H28_UL3, H29_UL3, H30_UL3,
						H31_UL3, H32_UL3, H33_UL3, H34_UL3, H35_UL3, H36_UL3, H37_UL3, H38_UL3, H39_UL3, H40_UL3,
						H41_UL3, H42_UL3, H43_UL3, H44_UL3, H45_UL3, H46_UL3, H47_UL3, H48_UL3, H49_UL3, H50_UL3 -> {
					tableColumn = new TableColumn<CommonModelFx, Double>(uniName + " " + uniName.getUnit());
					tableColumn.setCellValueFactory(
							(Callback<TableColumn.CellDataFeatures<CommonModelFx, Double>, ObservableValue<?>>) cellDataFeatures -> {
								final Optional<Double> aDouble = Optional.ofNullable(cellDataFeatures.getValue().harmonicsProperty().getValue().get(uniName));
								if (aDouble.isEmpty())
									return new SimpleObjectProperty<Double>(null);
								else {
									return new SimpleDoubleProperty(aDouble.get());
								}
							});
					setRightAlignment(tableColumn);
					tableColumnList.add(tableColumn);
				}
				default -> {

				}
			}
		});
		return tableColumnList;
	}

	@FXML
	private void changeDisplayTypeNorm() {
		final NumberDisplayType type = displayTypeNorm.getValue();

		switch (type) {
			case NORMAL -> {
				applyNotation(DataType.NORMAL_DATA, "f");
				formatNormal = "f";
			}
			case SCIENTIFIC -> {
				applyNotation(DataType.NORMAL_DATA, "E");
				formatNormal = "E";
			}
		}
	}

	@FXML
	private void changeDisplayTypeHarmo() {
		final NumberDisplayType type = displayTypeHarmo.getValue();

		switch (type) {
			case NORMAL -> {
				applyNotation(DataType.HARMONICS_DATA, "f");
				formatHarmo = "f";
			}
			case SCIENTIFIC -> {
				applyNotation(DataType.HARMONICS_DATA, "E");
				formatHarmo = "E";
			}
		}
	}

	private void applyNotation(DataType dataType, String format) {
		ObservableList<TableColumn<CommonModelFx, ?>> columns = FXCollections.observableArrayList();
		int precision = 0;

		switch (dataType) {
			case NORMAL_DATA -> {
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
				column.setCellFactory(param -> new TableCell() {
					@Override
					protected void updateItem(Object item, boolean empty) {
						super.updateItem(item, empty);
						if (item == null || empty) {
							setText(null);
						} else if (item instanceof Double) {
							setText(String.format("%." + finalPrecision + format, item));
						} else {
							setText(item.toString());
						}
					}
				}));
	}

	@FXML
	private void saveNormalOnAction() {
		final List<String[]> tableViewValues = getNormalValues(normalTableView);
		tableViewValues.add(0, modelList.getColumStandardNamesArray());
		try {
			SavingUtils.saveCSV(tableViewValues);
		} catch (IOException e) {
			DialogUtils.errorDialog("error.save.csv");
		}
	}

	@FXML
	private void saveHarmoOnAction() {
		final List<String[]> tableViewValues = getHarmonicsValues(harmonicsTableView);
		tableViewValues.add(0, modelList.getColumHarmonicNamesArray());
		try {
			SavingUtils.saveCSV(tableViewValues);
		} catch (IOException e) {
			DialogUtils.errorDialog("error.save.csv");
		}
	}


	private List<String[]> getNormalValues(TableView tableView) {
		List<String[]> values = new ArrayList<>();
		ArrayList<String> rowData = new ArrayList<>();
		ObservableList<TableColumn> columns = tableView.getColumns();

		for (int i = 0; i < tableView.getItems().size(); i++) {
			Object row = tableView.getItems().get(i);
			for (TableColumn column : columns) {
				final Object value = column.getCellObservableValue(row).getValue();
				String s;
				if (value instanceof Double) {
					s = String.format("%." + precisionNormal + formatNormal, value);
				} else {
					s = String.valueOf(value);
				}
				rowData.add(s);
			}
			values.add(rowData.toArray(new String[0]));
			rowData.clear();
		}
		return values;
	}

	private List<String[]> getHarmonicsValues(TableView tableView) {
		List<String[]> values = new ArrayList<>();
		ArrayList<String> rowData = new ArrayList<>();
		ObservableList<TableColumn> columns = tableView.getColumns();

		for (int i = 0; i < tableView.getItems().size(); i++) {
			Object row = tableView.getItems().get(i);
			for (TableColumn column : columns) {
				final Object value = column.getCellObservableValue(row).getValue();
				String s;
				if (value instanceof Double) {
					s = String.format("%." + precisionHarmo + formatHarmo, value);
				} else {
					s = String.valueOf(value);
				}
				rowData.add(s);
			}
			values.add(rowData.toArray(new String[0]));
			rowData.clear();
		}
		return values;
	}

	@FXML
	private void infoNormalOnAction() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Statystyki - dane standardowe");
		alert.setHeaderText("Statystyki:");
		TextArea textArea = new TextArea(modelList.getNormalStatistics());
		textArea.setEditable(false);
		alert.getDialogPane().setContent(textArea);
		alert.initStyle(StageStyle.UTILITY);
		alert.showAndWait();
	}

	@FXML
	private void infoHarmoOnAction() {
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setTitle("Statystyki - dane wyższych harmonicznych");
		alert.setHeaderText("Statystyki:");
		TextArea textArea = new TextArea(modelList.getHarmoStatistics());
		textArea.setEditable(false);
		alert.getDialogPane().setContent(textArea);
		alert.initStyle(StageStyle.UTILITY);
		alert.showAndWait();
	}

	private void showInfoDialog(){
		Alert alert = new Alert(AlertType.INFORMATION);
		alert.setWidth(620);
		alert.setHeight(420);
		alert.setTitle("Statystyki - import");
		alert.setHeaderText("Zaimportowano poprawnie.\nStatystyki pomiarów:");
		TextArea textArea = new TextArea(modelList.getImportedStatistics());
		textArea.setPrefColumnCount(40);
		textArea.setPrefRowCount(20);
		textArea.setEditable(false);
		textArea.setWrapText(true);
		textArea.setEditable(false);
		alert.getDialogPane().setContent(textArea);
		alert.initStyle(StageStyle.UTILITY);
		alert.showAndWait();
	}
}
