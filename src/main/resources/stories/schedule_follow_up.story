Story: Schedule a Follow-up
As an admin
I want to be able to schedule a follow-up activity
so that the pending incident can be resolved

Given the engineer failed to resolve the issue
And I select the issue in the issue list
And press Schedule Follow Up
And enter The engine has to be replaced as Findings
And enter Monday by 10am as Due On
and press Schedule
Then the status should remain as Assigned
And I should see a new email in the admin mailbox
And the subject should be ACME Factory: Monday by 10am
And the text should be: Findings: The engine has to be replaced
		