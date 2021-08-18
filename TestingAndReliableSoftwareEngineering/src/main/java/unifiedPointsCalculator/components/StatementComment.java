package unifiedPointsCalculator.components;

public class StatementComment {

	private float points;

	public StatementComment(String comment, float points) {
		this.setComment(comment);
		this.points = points;
	}

	/**
	 * @return the points
	 */
	public float getPoints() {
		return points;
	}


	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		// Not implemented for demo code
	}

}
