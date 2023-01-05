package agh.inzapp.inzynierka.models.fxmodels;

import javafx.collections.FXCollections;
import lombok.Getter;
import lombok.Setter;

import java.util.LinkedHashMap;

@Getter
@Setter
public class DataFx extends CommonModelFx {

	public DataFx() {
		flags.set(FXCollections.observableMap(new LinkedHashMap<>()));
	}
}
