package unifiedPointsCalculator.components;

public enum GradeType {
	ACTUAL("Actual"), PREDICTED("Predicted"), UNKNOWN("Unknown");

	private String value;

	GradeType(String value) {
		this.value=value;
	}

}
