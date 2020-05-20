Feature: Delete Photo
  Scenario: User want to delete one of his photo's because he don't like it anymore.
    Given that user Tino want to delete a photo.
    When Tino only has got one photo in his blog and the frontend did not checked this correctly
    Then the backend system must denied the delete action
