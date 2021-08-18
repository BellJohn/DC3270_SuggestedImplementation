package unifiedPointsCalculator.main;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import unifiedPointsCalculator.components.AcademicGrade;
import unifiedPointsCalculator.components.Grade;
import unifiedPointsCalculator.components.StatementComment;
import unifiedPointsCalculator.components.SubjectType;
import unifiedPointsCalculator.components.VocationalGrade;

public class UnifiedPointsCalculator {

	/**
	 * Calculates the total Unified Points based on the qualities in the
	 * specification
	 * 
	 * @param predictedGrades
	 * @param actualGrades
	 * @param personalStatementComments
	 * @return
	 */
	public UnifiedPointsCalculationResult calculateTotalUnifiedPoints(List<Grade> predictedGrades,
			List<Grade> actualGrades, List<StatementComment> personalStatementComments) {

		List<Grade> workingPredictedGrades = calculateNumericalValue(predictedGrades);
		List<Grade> workingActualGrades = calculateNumericalValue(actualGrades);

		removeDuplicateGrades(workingPredictedGrades);
		removeDuplicateGrades(workingActualGrades);

		List<Grade> finalGrades = consolidateActualAndPredicted(workingPredictedGrades, workingActualGrades);
		float totalGradePoints = getTopThreeGradesPoints(finalGrades);
		float totalStatementPoints = calculateStatmentPoints(personalStatementComments);

		UnifiedPointsCalculationResult returnResult = new UnifiedPointsCalculationResult();
		returnResult.setTotalUnifiedPoints(totalGradePoints + totalStatementPoints);

		if (totalStatementPoints < -10 || totalStatementPoints > 10) {
			returnResult.setOutcome(false);
			returnResult.setOutcomeMessage(String.format(
					"Invalid number of Personal Statement Points. Value must be between -10 and 10. Value was %s",
					totalStatementPoints));
			return returnResult;
		}

		returnResult.setOutcome(true);
		returnResult.setOutcomeMessage(String.format("Unified Points calculated successfully. Value was %s",
				(totalGradePoints + totalStatementPoints)));
		
		return returnResult;
	}

	/**
	 * Sums up the available points in the Personal Statement Comments
	 * 
	 * @param personalStatementComments
	 */
	private float calculateStatmentPoints(List<StatementComment> personalStatementComments) {
		float totalSum = 0f;
		for (StatementComment statementComment : personalStatementComments) {
			totalSum += statementComment.getPoints();
		}
		return totalSum;
	}

	/**
	 * Returns the total points of the top three available grades
	 * 
	 * @param finalGrades
	 */
	private float getTopThreeGradesPoints(List<Grade> finalGrades) {
		Collections.sort(finalGrades);
		float points = 0f;
		for (int i = 0; i < finalGrades.size() && i < 3; i++) {
			points += finalGrades.get(i).getPoints();
		}
		return points;
	}

	/**
	 * Returns a consolidated list of grades where in the event that a predicted and
	 * actual grade apply to the same subject, one needs to be removed. If the
	 * predicted grade has the same date, or an earlier date, as the actual grade,
	 * the predicted grade is removed. Otherwise, the lowest-scoring of the two
	 * grades is removed.
	 * 
	 * @param workingPredictedGrades
	 * @param workingActualGrades
	 */
	private List<Grade> consolidateActualAndPredicted(List<Grade> workingPredictedGrades,
			List<Grade> workingActualGrades) {
		Map<String, Grade> finalGrades = new HashMap<>();

		for (Grade grade : workingPredictedGrades) {
			finalGrades.put(grade.getSubject(), grade);
		}

		for (Grade grade : workingActualGrades) {
			String subject = grade.getSubject();
			if (!finalGrades.containsKey(subject)) {
				// We don't already have this grade, put it straight in.
				finalGrades.put(subject, grade);
			} else if (!finalGrades.get(subject).getDate().after(grade.getDate())) {
				// else if the predicted is BEFORE or the same as the actual grade's date
				finalGrades.put(subject, grade);
			} else {
				// otherwise the predicted is NEWER than our actual so take the highest score
				if (grade.getPoints() >= finalGrades.get(subject).getPoints()) {
					finalGrades.put(subject, grade);
				}
			}
		}
		return Arrays.asList(finalGrades.values().toArray(new Grade[0]));
	}

	/**
	 * Removes grades from the list where the same subject appears multiple times.
	 * In that event, the one with higher points is retained
	 * 
	 * @param workingGrades
	 */
	private void removeDuplicateGrades(List<Grade> workingGrades) {
		Map<String, Grade> subjectVsGrade = new HashMap<>();
		for (Grade grade : workingGrades) {
			String subject = grade.getSubject();
			// If we haven't encountered this subject yet OR the grade from the loop is
			// BETTER than what is already in the map
			if (!subjectVsGrade.containsKey(subject) || grade.getPoints() >= subjectVsGrade.get(subject).getPoints()) {
				subjectVsGrade.put(subject, grade);
			}
		}
		workingGrades.clear();
		workingGrades.addAll(subjectVsGrade.values());
	}

	/**
	 * Cycles through the provided list and assigns each grade it's numerical
	 * equivalent
	 * 
	 * @param workingGrades
	 */
	private List<Grade> calculateNumericalValue(List<Grade> workingGrades) {
		for (Grade grade : workingGrades) {
			if (grade.getSubjectType().equals(SubjectType.ACADEMIC)) {
				short lowerPoints = getGradePointsAcademic(grade.getLowerBound());
				short upperPoints = getGradePointsAcademic(grade.getUpperBound());
				grade.setPoints((lowerPoints + upperPoints) / 2f);
			} else {
				short lowerPoints = getGradePointsVocational(grade.getLowerBound());
				short upperPoints = getGradePointsVocational(grade.getUpperBound());
				grade.setPoints((lowerPoints + upperPoints) / 2f);
			}

			if (grade.isActual()) {
				grade.setPoints(grade.getPoints() * Boolean.compare(grade.isVerified(), false));
			}
		}
		return workingGrades;
	}

	/**
	 * Returns the mapping of grade lettering to the numerical value for academic
	 * grades
	 * 
	 * @param gradeLetter
	 */
	private short getGradePointsAcademic(String gradeLetter) {
		return (short) AcademicGrade.valueOfString(gradeLetter).getPoints();
	}

	/**
	 * Returns the mapping of grade lettering to the numerical value for academic
	 * grades
	 * 
	 * @param gradeLetter
	 */
	private short getGradePointsVocational(String gradeLetter) {
		return (short) VocationalGrade.valueOfString(gradeLetter).getPoints();
	}
}


