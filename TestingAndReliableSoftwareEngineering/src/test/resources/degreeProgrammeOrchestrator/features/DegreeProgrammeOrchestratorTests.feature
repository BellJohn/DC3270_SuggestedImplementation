Feature: Degree Programme Manager Creation / Edition / Deletion
  
  Title: Tests to prove the functionality 1 from the brief.

  Scenario: Creation of a new Degree Programme with BVA: Min -1 applied to degreeCode
    # This shows that when DegreeCode is below the acceptable bounds and programmeTitle and description are both nominal values that the creation will be prevented
    Given a Degree Programme is requested with degreeCode '12', programmeTitle 'ABCDEFGHIJKLMNOPQRSTUVWXY' and description 'ABCDEFGHIJKLMNOPQRSTUVWXYABCDEFGHIJKLMNOPQRSTUVWXYABCDEFGHIJKLMNOPQRSTUVWXYABCDEFGHIJKLMNOPQRSTUVWXYABCDEFGHIJKLMNOPQRSTUVWXY123'
    When the degreeProgrammeOrchestrator attempts to create a new degreeProgramme
    Then the degreeProgrammeOrchestrator should return 'false'

    
    
    