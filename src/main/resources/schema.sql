drop table incidents if exists;
create table incidents (
    id bigint generated by default as identity,
    description varchar(255),
    deadline timestamp,
    status varchar(255),
    severity varchar(255),
    created timestamp,
    created_by varchar(255),
    assigned_to varchar(255),
    primary key (id)
);

drop table incident_outputs if exists;
create table incident_outputs (
  id bigint generated by default as identity,
  resolved bit,
  description varchar(255),
  incident_id bigint,
  primary key (id)
);

alter table incident_outputs add foreign key (incident_id) references incidents(id);

insert into incidents(id, description, status, severity, created, created_by) values (1, 'Engine stopped working', 'SUBMITTED', 'MAJOR', '2012-04-24 10:00:05', 'ACME Factory');
insert into incidents(id, description, status, severity, created, created_by) values (2, 'Machine will not start', 'SUBMITTED', 'MAJOR', '2012-04-24 11:00:00', 'ACME Factory');