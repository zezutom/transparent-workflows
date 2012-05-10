Story: Assign an Engineer to the Reported Incident
As an admin
I want to be able to assign an engineer to the reported incident
so that the reported incident can be resolved

Given an unassigned incident exists
When the incident is assigned to Joe
Then the incident should be assigned to Joe
And a new task should be created for Joe
And the task should be due in 2 hours
And the task description should be Major Problem by ACME Factory: Engine stopped working