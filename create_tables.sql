CREATE TABLE [User](
    username        VARCHAR(25)     PRIMARY KEY,
    password        CHAR(60)        NOT NULL,
    firstName       NVARCHAR(25)    NOT NULL    DEFAULT 'A',
    lastName        NVARCHAR(25)    NOT NULL    DEFAULT N'Nguyễn',
    phone           CHAR(10)        NOT NULL    DEFAULT 9999999999,
    email           VARCHAR(50)     NOT NULL    DEFAULT 'email@gmail.com',
    address         NVARCHAR(MAX)   NOT NULL    DEFAULT '',
    avatarURL       VARCHAR(MAX),
    role            VARCHAR(5)      NOT NULL    DEFAULT 'USER',
    createdAt       DATETIME        NOT NULL    DEFAULT GETDATE(),
    deleted         BIT             NOT NULL    DEFAULT 0,
    CHECK(role IN ('ADMIN', 'USER'))
)

CREATE TABLE Class(
    classID         INT             IDENTITY(1,1) PRIMARY KEY,
    className       NVARCHAR(50)    NOT NULL,
    inviteCode      CHAR(10)        NOT NULL    UNIQUE,
    coverURL        VARCHAR(MAX),
    createdAt       DATETIME        NOT NULL    DEFAULT GETDATE(),
    deleted         BIT             NOT NULL    DEFAULT 0
)

CREATE TABLE ClassMember(
    classID         INT             FOREIGN KEY REFERENCES Class(classID),
    username        VARCHAR(25)     FOREIGN KEY REFERENCES [User](username),
    role            VARCHAR(7)      NOT NULL,
    createdAt       DATETIME        NOT NULL    DEFAULT GETDATE(),
    deleted         BIT             NOT NULL    DEFAULT 0,
    CHECK(role IN ('STUDENT', 'TEACHER')),
    PRIMARY KEY(username, classID)
)

CREATE TABLE Homework(
    homeworkID      INT             IDENTITY(1,1) PRIMARY KEY,
    classID         INT             FOREIGN KEY REFERENCES Class(classID),
    creator         VARCHAR(25)     FOREIGN KEY REFERENCES [User](username),
    fileLink        VARCHAR(MAX)    NOT NULL,
    description     NVARCHAR(MAX)   NOT NULL,
    deadline        DATETIME,
    createdAt       DATETIME        NOT NULL    DEFAULT GETDATE(),
    deleted         BIT             NOT NULL    DEFAULT 0
)

CREATE TABLE Submission(
    homeworkID      INT             FOREIGN KEY REFERENCES Homework(homeworkID),
    username        VARCHAR(25)     FOREIGN KEY REFERENCES [User](username),
    fileLink        VARCHAR(MAX)    NOT NULL,
    mark            INT,
    createdAt       DATETIME        NOT NULL    DEFAULT GETDATE(),
    deleted         BIT             NOT NULL    DEFAULT 0,
    PRIMARY KEY(homeworkID, username)
)

CREATE TABLE Message(
    messageID       INT             IDENTITY(1,1) PRIMARY KEY,
    classID         INT             FOREIGN KEY REFERENCES Class(classID),
    sender          VARCHAR(25)     FOREIGN KEY REFERENCES [User](username),
    receiver        VARCHAR(25)     FOREIGN KEY REFERENCES [User](username),
    content         NVARCHAR(MAX)   NOT NULL,
    createdAt       DATETIME        NOT NULL    DEFAULT GETDATE(),
    deleted         BIT             NOT NULL    DEFAULT 0
)
