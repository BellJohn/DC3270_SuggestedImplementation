package unifiedPointsCalculator.main;

/**
 * @author John
 *
 */
public class UnifiedPointsCalculationResult {

	private boolean outcome;
	private String outcomeMessage;
	private float totalUnifiedPoints;

	public void setOutcome(boolean outcome) {
		this.outcome = outcome;
	}

	public void setOutcomeMessage(String message) {
		this.outcomeMessage = message;
		
	}

	public void setTotalUnifiedPoints(float points) {
		this.totalUnifiedPoints = points;
	}

	/**
	 * @return the outcome
	 */
	public boolean getOutcome() {
		return outcome;
	}

	/**
	 * @return the outcomeMessage
	 */
	public String getOutcomeMessage() {
		return outcomeMessage;
	}

	/**
	 * @return the totalUnifiedPoints
	 */
	public float getTotalUnifiedPoints() {
		return totalUnifiedPoints;
	}


}
