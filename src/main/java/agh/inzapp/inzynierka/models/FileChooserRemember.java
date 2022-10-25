package agh.inzapp.inzynierka.models;

import agh.inzapp.inzynierka.utils.FxmlUtils;
import javafx.beans.property.SimpleObjectProperty;
import javafx.stage.FileChooser;
import javafx.stage.Window;

import java.io.File;
import java.util.List;

public class FileChooserRemember {
	private static FileChooser instance;
	private static SimpleObjectProperty<File> lastKnownDirectoryProperty = new SimpleObjectProperty<>();

	private FileChooserRemember(){};

	private static FileChooser getInstance(){
		if(instance == null) {
			instance = new FileChooser();
			instance.setInitialDirectory(new File(System.getProperty("user.dir")));
			instance.initialDirectoryProperty().bindBidirectional(lastKnownDirectoryProperty);
			//Set the FileExtensions you want to allow
			instance.getExtensionFilters().clear();
			final FileChooser.ExtensionFilter extensionFilter = new FileChooser.ExtensionFilter(
					FxmlUtils.getResourceBundle().getString("fileChooser.extension"), "*.csv");
			instance.getExtensionFilters().setAll(extensionFilter);
		}
		return instance;
	}

	public static List<File> showOpenMultipleDialog(){
		return showOpenMultipleDialog(null);
	}

	public static List<File> showOpenMultipleDialog(Window ownerWindow){
		List<File> chosenFiles = getInstance().showOpenMultipleDialog(ownerWindow);
		if(chosenFiles != null){
			//Set the property to the directory of the chosenFile so the fileChooser will open here next
			lastKnownDirectoryProperty.setValue(chosenFiles.stream().findFirst().get().getParentFile());
		}
		return chosenFiles;
	}

	public static File showOpenDialog(){
		return showOpenDialog(null);
	}

	public static File showOpenDialog(Window ownerWindow){
		File chosenFile = getInstance().showOpenDialog(ownerWindow);
		if(chosenFile != null){
			//Set the property to the directory of the chosenFile so the fileChooser will open here next
			lastKnownDirectoryProperty.setValue(chosenFile.getParentFile());
		}
		return chosenFile;
	}

	public static File showSaveDialog(){
		return showSaveDialog(null);
	}

	public static File showSaveDialog(Window ownerWindow){
		File chosenFile = getInstance().showSaveDialog(ownerWindow);
		if(chosenFile != null){
			//Set the property to the directory of the chosenFile so the fileChooser will open here next
			lastKnownDirectoryProperty.setValue(chosenFile.getParentFile());
		}
		return chosenFile;
	}

}
