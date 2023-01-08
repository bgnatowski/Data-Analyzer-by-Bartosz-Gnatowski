package agh.inzapp.inzynierka.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.layout.AnchorPane;
import javafx.scene.web.WebView;

import java.net.URL;
import java.util.Locale;
import java.util.Objects;
import java.util.ResourceBundle;

public class InfoController implements Initializable {
	@FXML
	private AnchorPane ap;

	@Override
	public void initialize(URL url, ResourceBundle resourceBundle) {
		final Locale aDefault = Locale.getDefault();
		String infoResource;
		if(aDefault.getCountry().equals("PL")) infoResource = "/data/infoPl.html";
		else infoResource = "/data/infoEn.html";
		String info = Objects.requireNonNull(this.getClass().getResource(infoResource)).toExternalForm();
		ap.getChildren().add(buildWebView(info));
	}


	private WebView buildWebView(final String url)
	{
		final WebView webView = new WebView();
		webView.setPrefHeight(500);
		webView.setPrefWidth(700);
		webView.getEngine().load(url);
		AnchorPane.setTopAnchor(webView, 0.0);
		AnchorPane.setBottomAnchor(webView, 0.0);
		AnchorPane.setLeftAnchor(webView, 0.0);
		AnchorPane.setRightAnchor(webView, 0.0);
		return webView;
	}
}
