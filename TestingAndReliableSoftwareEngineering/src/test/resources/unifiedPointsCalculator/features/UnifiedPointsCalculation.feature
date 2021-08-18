Feature: Unified Points Calculation
  
  Title: System calculates an Applicants unified points score

  Scenario: Correct Midpoint Calculation
    # This proves that the midpoint of two grades is correctly calculated to give the right unified points without rounding.
    Given this collection of Grades
      | Subject | Subject Type | Grade Type | Verified | Lower Bound | Upper Bound | Date       |
      | French  | Vocational   | Predicted  | false    | P           | M           | 2021-01-02 |
    When the programme calculates the unified points
    Then the programme should receive an outcome of "success"
    And the programme should receive a score of 17.5
    And the programme should receive the following message: "Unified Points calculated successfully. Value was 17.5"

  Scenario: Duplicate Predicteds
    # This proves that in the event of duplicate subject grades, the better score is retained.
    Given this collection of Grades
      | Subject | Subject Type | Grade Type | Verified | Lower Bound | Upper Bound | Date       |
      | French  | Academic     | Predicted  | false    | A           | A           | 2021-01-02 |
      | French  | Academic     | Predicted  | false    | D           | D           | 2021-01-02 |
    When the programme calculates the unified points
    Then the programme should receive an outcome of "success"
    And the programme should receive a score of 50
    And the programme should receive the following message: "Unified Points calculated successfully. Value was 50.0"

  Scenario: Personal Statement Comment Sum, less than bound
    # This proves that the system correctly presents an error message when the sum of personal statement points is less than the boundary value of -10
    Given this collection of Grades
      | Subject | Subject Type | Grade Type | Verified | Lower Bound | Upper Bound | Date |
    And this collection of Statement Comments
      | Comment   | Points |
      | Comment 1 |     -2 |
      | Comment 2 |     -2 |
      | Comment 3 |     -2 |
      | Comment 4 |     -2 |
      | Comment 5 |     -2 |
      | Comment 6 |     -1 |
    When the programme calculates the unified points
    Then the programme should receive an outcome of "failure"
    And the programme should receive a score of -11
    And the programme should receive the following message: "Invalid number of Personal Statement Points. Value must be between -10 and 10. Value was -11.0"

  Scenario: Personal Statement Comment Sum, greater than bound
    # This proves that the system correctly presents an error message when the sum of personal statement points is greater than the boundary value of +10
    Given this collection of Grades
      | Subject | Subject Type | Grade Type | Verified | Lower Bound | Upper Bound | Date |
    And this collection of Statement Comments
      | Comment   | Points |
      | Comment 1 |      2 |
      | Comment 2 |      2 |
      | Comment 3 |      2 |
      | Comment 4 |      2 |
      | Comment 5 |      2 |
      | Comment 6 |      1 |
    When the programme calculates the unified points
    Then the programme should receive an outcome of "failure"
    And the programme should receive a score of 11
    And the programme should receive the following message: "Invalid number of Personal Statement Points. Value must be between -10 and 10. Value was 11.0"

  Scenario: Full Suite Correct Calculation
    # This proves that the system correctly calculates grade points and personal statement points to give the correct unified points
    Given this collection of Grades
      | Subject  | Subject Type | Grade Type | Verified | Lower Bound | Upper Bound | Date       |
      | English  | Academic     | Actual     | true     | C           | A           | 2021-01-01 |
      | French   | Academic     | Actual     | true     | C           | A           | 2021-01-02 |
      | Woodwork | Academic     | Actual     | true     | D           | D           | 2021-01-03 |
      | Latin    | Academic     | Actual     | true     | A*          | A*          | 2021-01-04 |
    And this collection of Statement Comments
      | Comment             | Points |
      | A comment           |      2 |
      | A different commend |     -1 |
      | A third comment     |   -0.5 |
    When the programme calculates the unified points
    Then the programme should receive an outcome of "success"
    And the programme should receive a score of 140.5
    And the programme should receive the following message: "Unified Points calculated successfully. Value was 140.5"

  Scenario: Older Predicted
    # This proves that the system correctly handles predicted grades being older than newer actuals
    Given this collection of Grades
      | Subject | Subject Type | Grade Type | Verified | Lower Bound | Upper Bound | Date       |
      | French  | Academic     | Actual     | true     | E           | E           | 2021-01-02 |
      | French  | Academic     | Predicted  | false    | A*          | A*          | 2021-01-01 |
    When the programme calculates the unified points
    Then the programme should receive an outcome of "success"
    And the programme should receive a score of 10
    And the programme should receive the following message: "Unified Points calculated successfully. Value was 10.0"

  Scenario: Newer, worse Predicted
    # This proves that the system correctly handles predicted grades with a worse score being newer than actuals
    Given this collection of Grades
      | Subject | Subject Type | Grade Type | Verified | Lower Bound | Upper Bound | Date       |
      | French  | Academic     | Actual     | true     | A*          | A*          | 2021-01-01 |
      | French  | Academic     | Predicted  | false    | E           | E           | 2021-01-02 |
    When the programme calculates the unified points
    Then the programme should receive an outcome of "success"
    And the programme should receive a score of 60
    And the programme should receive the following message: "Unified Points calculated successfully. Value was 60.0"

  Scenario: Newer, better Predicted
    # This proves that the system correctly handles predicted grades with a better score being newer than actuals
    Given this collection of Grades
      | Subject | Subject Type | Grade Type | Verified | Lower Bound | Upper Bound | Date       |
      | French  | Academic     | Actual     | true     | E           | E           | 2021-01-01 |
      | French  | Academic     | Predicted  | false    | A*          | A*          | 2021-01-02 |
    When the programme calculates the unified points
    Then the programme should receive an outcome of "success"
    And the programme should receive a score of 60
    And the programme should receive the following message: "Unified Points calculated successfully. Value was 60.0"

  Scenario: Duplicate subject with different score
    # This proves that the system correctly handles duplicate predicted grades with a different scores
    Given this collection of Grades
      | Subject | Subject Type | Grade Type | Verified | Lower Bound | Upper Bound | Date       |
      | French  | Academic     | Predicted  | false    | E           | E           | 2021-01-01 |
      | French  | Academic     | Predicted  | false    | A*          | A*          | 2021-01-01 |
    When the programme calculates the unified points
    Then the programme should receive an outcome of "success"
    And the programme should receive a score of 60
    And the programme should receive the following message: "Unified Points calculated successfully. Value was 60.0"
