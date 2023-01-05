package agh.inzapp.inzynierka.builders;

import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class ReportBuilder {
	private final Map<String, Object> reportData;
	public ReportBuilder(Map<String, Object> reportData) {
		this.reportData = reportData;
	}
	public void put(String tag, Object object){
		reportData.put(tag, object);
	}

	public Map<String, Object> getReportResult(){
		return reportData;
	}
}
