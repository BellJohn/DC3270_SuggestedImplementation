package unifiedCalculatorFeatureTests;

import static org.junit.Assert.assertEquals;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import unifiedPointsCalculator.components.Grade;
import unifiedPointsCalculator.components.GradeType;
import unifiedPointsCalculator.components.StatementComment;
import unifiedPointsCalculator.components.SubjectType;
import unifiedPointsCalculator.main.UnifiedPointsCalculationResult;
import unifiedPointsCalculator.main.UnifiedPointsCalculator;

public class UnifiedCalculatorStepDefinitions {

	List<Grade> predictedGrades = new ArrayList<>();
	List<Grade> actualGrades = new ArrayList<>();
	List<StatementComment> statementComments = new ArrayList<>();
	UnifiedPointsCalculationResult result;
	static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

	@Given("this collection of Grades")
	public void givenGrades(DataTable table) throws ParseException {

		List<Map<String, String>> rows = table.asMaps(String.class, String.class);

		for (Map<String, String> column : rows) {
			Grade grade = new Grade();
			grade.setSubject(column.get("Subject"));
			grade.setSubjectType(SubjectType.valueOf(column.get("Subject Type").toUpperCase()));
			grade.setGradeType(GradeType.valueOf(column.get("Grade Type").toUpperCase()));
			grade.setVerified(Boolean.parseBoolean(column.get("Verified")));
			grade.setLowerBound(column.get("Lower Bound"));
			grade.setUpperBound(column.get("Upper Bound"));

			grade.setDate(sdf.parse(column.get("Date")));
			if (grade.isActual()) {
				actualGrades.add(grade);
			} else {
				predictedGrades.add(grade);
			}
		}
	}

	@And("this collection of Statement Comments")
	public void givenStatementComments(DataTable table) {
		List<Map<String, String>> rows = table.asMaps(String.class, String.class);

		for (Map<String, String> column : rows) {
			StatementComment comment = new StatementComment(column.get("Comment"), Float.valueOf(column.get("Points")));
			statementComments.add(comment);
		}
	}

	@When("the programme calculates the unified points")
	public void executeCalculation() {
		UnifiedPointsCalculator calculator = new UnifiedPointsCalculator();
		result = calculator.calculateTotalUnifiedPoints(predictedGrades, actualGrades, statementComments);
	}

	@Then("the programme should receive an outcome of {string}")
	public void validateSuccess(String success) {
		boolean expected = true;
		if(success.equalsIgnoreCase("failure")) {
			expected = false;
		}
		assertEquals(expected, result.getOutcome());
	}

	@Then("the programme should receive a score of {float}")
	public void validateScore(float score) {
		assertEquals(score, result.getTotalUnifiedPoints(), 0.1);
	}

	@Then("the programme should receive the following message: {string}")
	public void validateMessage(String message) {
		assertEquals(message, result.getOutcomeMessage());
	}
}
