
create table [User](
	rollNumber nvarchar(10) not null,
	firstName nvarchar(50),
	lastName nvarchar(50),
	phoneNumber nvarchar(10),
	email nvarchar(50),
	address nvarchar(100),
	username nvarchar(50),
	password nvarchar(50) not null,
	primary key(username)
)

create table Class(
   classID nvarchar(10),
   name nvarchar(20),
   inviteCode nvarchar(20) unique,
   primary key(ClassID)
)

create table ClassMember(
	username nvarchar(50) foreign key references [User](username),
	classID nvarchar(10) foreign key references Class(classID),
	role nvarchar(7) not null, 
	check(role in ('Student', 'Teacher'))
)

create table Homework(
	homeworkID nvarchar(10) primary key,
	fileLink nvarchar,
	discription nvarchar,
	classID nvarchar(10) foreign key references Class(classID),
	username nvarchar(50) foreign key references [User](username),
	deadline Date,
)

create table Submission(
	homeworkID nvarchar(10),
	fileLink nvarchar,
	username nvarchar(50) foreign key references [User](username),
	mark int,
	primary key(homeworkID, username)
)

create table MailRequest(
	username nvarchar(50) foreign key references [User](username),
	title nvarchar,
	content nvarchar,
	classID nvarchar(10) foreign key references Class(classID)
)

create table MailResponse(
	username nvarchar(50) foreign key references [User](username),
	title nvarchar,
	content nvarchar,
	classID nvarchar(10) foreign key references Class(classID)
)

