package agh.inzapp.inzynierka.models.fxmodels;

import javafx.collections.FXCollections;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.LinkedHashMap;

@Getter
@Setter
public class HarmoFx extends CommonModelFx {
	public HarmoFx() {
		flags.set(FXCollections.observableMap(new LinkedHashMap<>()));
	}
}
