package agh.inzapp.inzynierka.controllers;

import agh.inzapp.inzynierka.enums.UniNames;
import agh.inzapp.inzynierka.models.modelFx.DataFx;
import agh.inzapp.inzynierka.models.modelFx.ListDataFx;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.MapValueFactory;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Callback;
import org.springframework.data.util.Optionals;

import javax.swing.text.html.Option;
import javax.xml.crypto.Data;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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

		final DataFx dataFx = listDataFx.getDataFxList().get(0);
		final ObservableList<UniNames> columnNames = dataFx.getColumnNames();

		List<TableColumn<DataFx, ?>> tableColumnList = new ArrayList<>();

		TableColumn<DataFx, Long> idColumn = new TableColumn<>("id");
		idColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
		tableColumnList.add(idColumn);

		columnNames.forEach(uniName -> {
			TableColumn tableColumn;
			switch (uniName){
				case Flag:
				case Flag_A:
				case Flag_G:
				case Flag_E:
				case Flag_T:
				case Flag_P:
					tableColumn = new TableColumn<DataFx, Map<UniNames, String>>(uniName.toString());
					tableColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures<DataFx, String>, ObservableValue<String>>) dataFxCellDataFeatures -> new SimpleStringProperty(dataFxCellDataFeatures.getValue().flagsProperty().getValue().get(uniName)));
					break;
				case Date:
					tableColumn = new TableColumn<DataFx, LocalDate>(uniName.toString());
					tableColumn.setCellValueFactory(new PropertyValueFactory<>("date"));
					break;
				case Time:
					tableColumn = new TableColumn<DataFx, LocalTime>(uniName.toString());
					tableColumn.setCellValueFactory(new PropertyValueFactory<>("time"));
					break;
				default:
					tableColumn = new TableColumn<DataFx, Double>(uniName + " " + uniName.getUnit());
					tableColumn.setCellValueFactory((Callback<TableColumn.CellDataFeatures<DataFx, Double>, ObservableValue>) cellDataFeatures -> {
						final Optional<Double> aDouble = Optional.ofNullable(cellDataFeatures.getValue().recordsProperty().getValue().get(uniName));
						if (aDouble.isEmpty())
							return new SimpleObjectProperty(null);
						else{
							return new SimpleDoubleProperty(aDouble.get());
						}

					});
			}
			tableColumnList.add(tableColumn);

		});
		normalTableView.getColumns().addAll(tableColumnList);
	}

	private void bindings() {
		normalTableView.getItems().addAll(listDataFx.getDataFxObservableList());
//		for (TableColumn<DataFx, ?> dataFxTableColumn : normalTableView.getColumns()) {
//			dataFxTableColumn.setCellValueFactory(cellData -> cellData.getValue().dateProperty());
//		}
	}

}
