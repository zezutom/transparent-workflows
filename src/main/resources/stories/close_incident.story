Story: Close the Incident
As an admin
I want to be able to close a resolved incident
so that I know the engineer is available again

Given an assignment exists
When the assignment is resolved as Engine stuck with the same stuff again
Then the incident should be closed as Engine stuck with the same stuff again
And no pending task should be assigned to the engineer