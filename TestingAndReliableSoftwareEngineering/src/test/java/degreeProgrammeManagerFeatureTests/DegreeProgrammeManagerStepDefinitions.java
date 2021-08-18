package degreeProgrammeManagerFeatureTests;

import static org.junit.Assert.assertEquals;

import degreeProgrammeOrchestrator.main.DegreeProgrammeOrchestrator;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class DegreeProgrammeManagerStepDefinitions {
	DegreeProgrammeOrchestrator orchestrator;
	private String degreeCode;
	private String programmeTitle;
	private String description;

	boolean result = false;

	/**
	 * Prepare the three possible input variables
	 * 
	 * @param degreeCode
	 * @param programmeTitle
	 * @param description
	 */
	@Given("a Degree Programme is requested with degreeCode {string}, programmeTitle {string} and description {string}")
	public void constructDegreeProgrammeOrchestrator(String degreeCode, String programmeTitle, String description) {
		this.degreeCode = degreeCode;
		this.programmeTitle = programmeTitle;
		this.description = description;
	}

	
	/**
	 * Matches the CREATION sub-feature
	 */
	@When("the degreeProgrammeOrchestrator attempts to create a new degreeProgramme")
	public void attemptCreation() {
		result = new DegreeProgrammeOrchestrator().createProgramme(degreeCode, programmeTitle, description);
	}

	/**
	 * Verify boolean outcome of the action
	 * 
	 * @param outcome
	 */
	@Then("the degreeProgrammeOrchestrator should return {}")
	public void assertResult(Boolean outcome) {
		assertEquals(result, outcome);
	}

	/**
	 * Matches the EDITION sub-feature
	 */
	@When("the degreeProgrammeOrchestrator attempts to edit an existing degreeProgramme")
	public void attemptEdition() {
		result = new DegreeProgrammeOrchestrator().editProgramme(degreeCode, programmeTitle, description);
	}

	/**
	 * Matches the DELETION sub-feature
	 */
	@When("the degreeProgrammeOrchestrator attempts to create a new degreeProgramme")
	public void attemptDeletion() {
		result = new DegreeProgrammeOrchestrator().deleteProgramme(degreeCode);
	}

}



