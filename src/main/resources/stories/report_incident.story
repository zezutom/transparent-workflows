Story: Report Incident
As a customer
I want to be able to report an incident so that it can be resolved as quickly as possible
so that I can mitigate unforeseen outages

Given a customer called ACME Factory is logged in
When an incident described as Engine stopped working is submitted as a MAJOR problem
Then the new incident should be described as Engine stopped working
And the new incident should be classified as MAJOR
And the new incident should be bound to ACME Factory
And the submission time should be the current time
And the status should be SUBMITTED
And a new email should be sent to admin@crashtracker.com, subject: ACME Factory: New Incident Report, message: Major Problem: Engine stopped working

