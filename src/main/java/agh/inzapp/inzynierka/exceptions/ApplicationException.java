package agh.inzapp.inzynierka.exceptions;

import agh.inzapp.inzynierka.utils.DialogUtils;
import agh.inzapp.inzynierka.utils.FxmlUtils;

public class ApplicationException extends Exception {
	public ApplicationException(String message) {
		super(message);
	}
	public static void printDialog(String errorMessage, Class<? extends Exception> aClass, String bundleResourceKey) {
		StringBuilder sb = new StringBuilder();
		final String internalizedPropertyByKey = FxmlUtils.getInternalizedPropertyByKey(bundleResourceKey);
		sb.append(errorMessage)
				.append(". Caused in class: ")
				.append(aClass.getSimpleName())
				.append(". ")
				.append(internalizedPropertyByKey);
		DialogUtils.errorDialog(sb.toString());
	}
}