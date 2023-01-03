package agh.inzapp.inzynierka.builders;

import java.util.Map;

public class ReportBuilder {
	private Map<String, Object> reportData;
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
