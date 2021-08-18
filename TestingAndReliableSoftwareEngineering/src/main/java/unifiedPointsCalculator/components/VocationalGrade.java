package unifiedPointsCalculator.components;

public enum VocationalGrade {
	DISTINCTION_STAR("D*", 60), DISTINCTION("D", 45), MERIT("M", 25), PASS("P", 10), U("U", 0), UNKNOWN("Unknown", 0);

	private String value;
	private int points;

	VocationalGrade(String value, int points) {
		this.value = value;
		this.points = points;
	}


	/**
	 * @return the points
	 */
	public int getPoints() {
		return points;
	}

	public static VocationalGrade valueOfString(String valueToTest) {
		VocationalGrade returnType = null;
		for (VocationalGrade type : VocationalGrade.values()) {
			if (type.value.equalsIgnoreCase(valueToTest)) {
				returnType = type;
			}
		}
		return returnType;
	}

}
