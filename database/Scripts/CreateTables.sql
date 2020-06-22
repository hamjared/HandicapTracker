

create table Golfer (
	userName text,
	password text not null,
	email citext NOT null unique,
	firstName text,
	lastName text,
	primary key(userName)
);

create table Course(
	courseId serial primary key,
	courseName text not null, 
	courseCity text not null, 
	courseState text not null,
	numHoles numeric not null check (numHoles = 9 or numHoles = 18),
	unique(courseName, courseCity, courseState)
);

create table Tees(
	courseId int,
	teeColor text,
	rating numeric not null check (rating > 0),
	slope int not null check (slope > 0),
	par int not null check(par > 0),
	yardage int not null check (yardage > 0),
	primary key(courseId, teecolor ),
	foreign key(courseId) references Course(courseId)
);

create table Rounds(
	datePlayed timestamp with time zone, 
	golferUserName text,
	courseId int, 
	teeColor text,
	score int not null check (score > 0),
	differential numeric,
	primary key(golferUserName, datePlayed),
	foreign key(golferUserName) references Golfer(userName),
	foreign key(courseId, teeColor) references Tees(courseId, teeColor)
	
);

