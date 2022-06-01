create database StudentManagement

create table [User](
	RollNumber nvarchar(10) not null,
	FirstName nvarchar(50),
	LastName nvarchar(50),
	PhoneNumber nvarchar(10),
	Email nvarchar(50),
	Address nvarchar(100),
	UserName nvarchar(50),
	Password nvarchar(50) not null,
	primary key(Username)
)

create table Class(
   ClassID nvarchar(10),
   Name nvarchar(20),
   InviteCode nvarchar(20) unique,
   primary key(ClassID)
)

create table ClassMember(
	Username nvarchar(50) foreign key references [User](Username),
	ClassID nvarchar(10) foreign key references Class(ClassID),
	Role nvarchar(7) not null, 
	check(Role in ('Student', 'Teacher'))
)

create table Homework(
	HomeworkID nvarchar(10) primary key,
	FileLink nvarchar,
	Discription nvarchar,
	ClassID nvarchar(10) foreign key references Class(ClassID),
	Username nvarchar(50) foreign key references [User](Username),
	Deadline Date,
)

create table Submission(
	HomeworkID nvarchar(10),
	FileLink nvarchar,
	Username nvarchar(50) foreign key references [User](Username),
	Mark int,
	primary key(HomeworkID, Username)
)

create table MailRequest(
	Username nvarchar(50) foreign key references [User](Username),
	Title nvarchar,
	Content nvarchar,
	ClassID nvarchar(10) foreign key references Class(ClassID)
)

create table MailResponse(
	Username nvarchar(50) foreign key references [User](Username),
	Title nvarchar,
	Content nvarchar,
	ClassID nvarchar(10) foreign key references Class(ClassID)
)

drop database StudentManagement