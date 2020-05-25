create table Golfer (
	userName text,
	email text unique,
	firstName text,
	lastName text,
	DOB date,
	gender text,
	primary key(userName)
);

create table Course(
	courseID serial,
	courseName text, 
	courseCity text, 
	courseState text,
	primary key(courseID)
);

create table Round(
	datePlayed date, 
	timeStarted time, 
	golferUserName text,
	courseID int, 
	score int,
	primary key(dateplayed, timestarted ),
	foreign key(golferUserName) references Golfer(userName),
	foreign key(courseID) references Course(courseID)
);

create table Tees(
	courseID int,
	teeColor text,
	rating numeric,
	slope int,
	primary key(courseID, teecolor ),
	foreign key(courseID) references Course(courseID)
);
