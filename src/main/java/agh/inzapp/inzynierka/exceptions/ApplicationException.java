package agh.inzapp.inzynierka.exceptions;

import agh.inzapp.inzynierka.utils.DialogUtils;
import agh.inzapp.inzynierka.utils.FxmlUtils;

public class ApplicationException extends Exception {
	public ApplicationException(String message) {
		super(message);
	}
}