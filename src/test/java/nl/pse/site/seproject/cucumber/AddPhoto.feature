Feature: Add Photo
  Scenario: User want to upload a new photo.
    Given that user Maria uploaded a new photo for her blog.
    When Maria uploaded a photo bigger than 3MB and the frontend did not checked this correctly
    Then the backend system must denied the upload action