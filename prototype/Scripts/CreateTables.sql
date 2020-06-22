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
	datePlayed timestamp with time zone, 
	golferUserName text,
	courseName text, 
	courseCity text, 
	courseState text, 
	teeColor text,
	score int,
	differential numeric,
	primary key(golferUserName, datePlayed),
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

create trigger calculateDifferential before update or insert on Rounds
for each row execute function trg_calculatedDifferential();
	
	
create or replace function trg_calculatedDifferential()
returns trigger as 
$func$
begin
	raise info 'Hello';
	new.differential := (new.score - getCourseRating(new.courseName, new.courseCity, new.courseState, new.teeColor)) * 113/getCourseSlope(new.courseName, new.courseCity, new.courseState, new.teeColor);
		
return new;
end;
$func$ language plpgsql;
	
create function getCourseRating(_courseName text,  _courseCity text,_courseState text, _teeColor text)
returns numeric as $_rating$
declare
	_rating numeric;
begin
	select rating into _rating 
	from course natural join tees 
	where courseName = _courseName and courseState = _courseState and coursecity = _courseCity and teecolor = _teeColor;
	return _rating; 
end;
$_rating$ language plpgsql;

create function getCourseSlope(_courseName text,  _courseCity text,_courseState text, _teeColor text)
returns numeric as $_slope$
declare
	_slope numeric;
begin
	select slope into _slope 
	from course natural join tees 
	where courseName = _courseName and courseState = _courseState and coursecity = _courseCity and teecolor = _teeColor;
	return _slope; 
end;
$_slope$ language plpgsql;

create or replace function getHandicapIndex(_userName text)
returns numeric as $handicapIndex$
declare
	handicapIndex numeric;
	roundCount int;
	n int;
begin
	roundCount := (select count(score) from Rounds where golferusername = _userName);
	if roundCount < 10 then
		n = 1;
	elseif roundCount >= 10 and roundCount < 20 then 
		n = 5;
	else
		n = 10;
	end if;

	raise info '%', n;

	handicapIndex := (select avg(differential)
						from ( select * 
							from Rounds as r1
							where r1.golferusername = _userName
							order by r1.differential asc 
							limit n) as r2);
	return 0.96 * handicapIndex;
end;
$handicapIndex$ language plpgsql;



