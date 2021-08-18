package unifiedPointsCalculator.components;

public enum AcademicGrade {
	ASTAR("A*", 60), A("A", 50), B("B", 40), C("C", 30), D("D", 20), E("E", 10), U("U", 0);

	private String value;
	private int points;

	AcademicGrade(String value, int points) {
		this.value = value;
		this.points = points;
	}

	/**
	 * @return the points
	 */
	public int getPoints() {
		return points;
	}

	public static AcademicGrade valueOfString(String valueToTest) {
		AcademicGrade returnType = null;
		for (AcademicGrade type : AcademicGrade.values()) {
			if (type.value.equalsIgnoreCase(valueToTest)) {
				returnType = type;
			}
		}
		return returnType;
	}

}
