create table Users (
	userName text,
	password text not null,
	email text unique,
	firstName text,
	lastName text,
	primary key(userName)
);

create table Course(
	courseName text, 
	courseCity text, 
	courseState text,
	primary key(courseName, courseCity, courseState)
);

create table Rounds(
	datePlayed date, 
	timeStarted time, 
	golferUserName text,
	courseName text, 
	courseCity text, 
	courseState text, 
	teeColor text,
	score int,
	primary key(dateplayed, timestarted ),
	foreign key(golferUserName) references Users(userName),
	foreign key(courseName, courseCity, courseState, teeColor) references Tees(courseName, courseCity, courseState, teeColor)
	
);

create table Tees(
	courseName text, 
	courseCity text, 
	courseState text,
	teeColor text,
	rating numeric,
	slope int,
	primary key(courseName, courseCity, courseState, teecolor ),
	foreign key(courseName, courseCity, courseState) references Course(courseName, courseCity, courseState)
);
