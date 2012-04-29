Story: Close the Incident
As an admin
I want to be able to close a resolved incident
so that I know the engineer is available again

Given the engineer has reported the ACME Factory problem was resolved
And I select the the issue in the issue list
And press Resolved
And enter Engine stuck with the same stuff again as a Root Cause
And enter 2 as Hours Spent
And press Close
Then the status should change to Closed