

create table [User](
	firstName nvarchar(50) not null,
	lastName nvarchar(50) not null,
	phoneNumber nvarchar(10) not null,
	email nvarchar(50) not null,
	address nvarchar(100) not null,
	username nvarchar(50),
	password nvarchar(50) not null,
	primary key(username)
)

create table Class(
   classID int identity(1,1),
   className nvarchar(20) not null,
   inviteCode nvarchar(20) not null,
   primary key(ClassID)
)

create table ClassMember(
	classID int foreign key references Class(classID),
	username nvarchar(50) foreign key references [User](username),
	role nvarchar(7) not null, 
	check(role in ('Student', 'Teacher')),
	primary key(username, classID)
)

create table Homework(
	homeworkID int identity(1,1) primary key,
	classID int foreign key references Class(classID),
	username nvarchar(50) foreign key references [User](username),
	fileLink nvarchar not null,
	discription nvarchar not null,
	deadline Date not null
)

create table Submission(
	homeworkID int identity(1,1) references Homework(HomeworkID),
	username nvarchar(50) foreign key references [User](username),
	fileLink nvarchar not null,
	mark int not null,
	primary key(homeworkID, username)
)

create table Mail(
	messageID int identity(1,1) not null,
	classID int foreign key references Class(classID),
	usernameFrom nvarchar(50) foreign key references [User](username),
	usernameTo nvarchar(50) foreign key references [User](username),
	content nvarchar not null, 
)




