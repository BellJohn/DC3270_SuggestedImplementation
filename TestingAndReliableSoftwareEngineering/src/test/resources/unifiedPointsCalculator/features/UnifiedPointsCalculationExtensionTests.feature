Feature: Unified Points Calculation
  
  Title: Further Functional Tests

  Scenario: Two different Actuals which are not verified
  # This shows the situation when scores should be zeroed as the grades are actuals but not verified
    Given this collection of Grades
      | Subject | Subject Type | Grade Type | Verified | Lower Bound | Upper Bound | Date       |
      | English | Academic     | Actual     | false    | C           | A           | 2021-01-01 |
      | French  | Academic     | Actual     | false    | C           | A           | 2021-01-02 |
    When the programme calculates the unified points
    Then the programme should receive an outcome of "success"
    And the programme should receive a score of 0
    And the programme should receive the following message: "Unified Points calculated successfully. Value was 0.0"
  
    Scenario: Top Three Scores Only
    # This shows that the system is correctly summing up just the top three scores
    Given this collection of Grades
      | Subject   | Subject Type | Grade Type | Verified | Lower Bound | Upper Bound | Date       |
      | English   | Academic     | Predicted  | false    | C           | A           | 2021-01-01 |
      | French    | Academic     | Predicted  | false    | C           | A           | 2021-01-02 |
      | Woodwork  | Vocational   | Predicted  | false    | D           | D           | 2021-01-03 |
      | Food Tech | Vocational   | Predicted  | false    | P           | P           | 2021-01-03 |
    When the programme calculates the unified points
    Then the programme should receive an outcome of "success"
    And the programme should receive a score of 125
    And the programme should receive the following message: "Unified Points calculated successfully. Value was 125.0"
   
