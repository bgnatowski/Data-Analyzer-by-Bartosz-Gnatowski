package agh.inzapp.inzynierka.models.enums;

public enum AnalyzersModels {
	PQBox50("PQ-Box 50"),
	PQBox100("PQ-Box 100"),
	PQBox150("PQ-Box 150"),
	PQBox200("PQ-Box 200"),
	PQBox300("PQ-Box 300"),
	PQM700("Sonel PQM-700"),
	PQM707("Sonel PQM-707"),
	PQM710("Sonel PQM-710"),
	PQM711("Sonel PQM-711");
	private String name;
	AnalyzersModels(String name) {
		this.name = name;
	}

	@Override
	public String toString() {
		return name;
	}
}
