//package agh.inzapp.inzynierka.models.modelFx;
//
//import agh.inzapp.inzynierka.database.dao.WinPQDao;
//import agh.inzapp.inzynierka.database.dbmodels.WinPQDataDb;
//import agh.inzapp.inzynierka.models.modelObj.WinPQDataObj;
//import agh.inzapp.inzynierka.utils.converters.ConverterWinPQData;
//import agh.inzapp.inzynierka.exceptions.ApplicationException;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//
//import java.util.ArrayList;
//import java.util.List;
//
//public class ListModel {
//	private ObservableList<WinPQDataObj> winPQDataFxObservableList = FXCollections.observableArrayList();
//	private List<WinPQDataObj> winPQFxList = new ArrayList<>();
//
//	public void init() {
//		WinPQDao dao = new WinPQDao();
//		List<WinPQDataDb> data = null;
//		try {
//			data = dao.queryForAll(WinPQDataDb.class);
//		} catch (ApplicationException e) {
//			System.out.println("error: " + this.getClass().getSimpleName() + ": " + e.getMessage());
//			throw new RuntimeException(e);
//		}
//		winPQFxList.clear();
//		data.forEach(model -> {
//			winPQFxList.add(ConverterWinPQData.convertToObj(model));
//		});
//		winPQDataFxObservableList.setAll(winPQFxList);
//	}
//
//	public ObservableList<WinPQDataObj> getWinPQDataFxObservableList() {
//		return winPQDataFxObservableList;
//	}
//
//	public void setWinPQDataFxObservableList(ObservableList<WinPQDataObj> winPQDataFxObservableList) {
//		this.winPQDataFxObservableList = winPQDataFxObservableList;
//	}
//
//	public List<WinPQDataObj> getWinPQFxList() {
//		return winPQFxList;
//	}
//
//	public void setWinPQFxList(List<WinPQDataObj> winPQFxList) {
//		this.winPQFxList = winPQFxList;
//	}
//}
