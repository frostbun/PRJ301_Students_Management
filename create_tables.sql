CREATE TABLE [User](
    username        VARCHAR(25)     PRIMARY KEY,
    password        CHAR(64)        NOT NULL,
    firstName       NVARCHAR(25)    NOT NULL    DEFAULT 'A',
    lastName        NVARCHAR(25)    NOT NULL    DEFAULT 'Nguyễn',
    phone           CHAR(10)        NOT NULL    DEFAULT 9999999999,
    email           VARCHAR(50)     NOT NULL    DEFAULT 'email@gmail.com',
    address         NVARCHAR        NOT NULL    DEFAULT '',
    avatarURL       VARCHAR         NOT NULL    DEFAULT '',
    createdAt       DATETIME        NOT NULL    DEFAULT GETDATE()
)

CREATE TABLE Class(
    classID         INT             IDENTITY(1,1) PRIMARY KEY,
    className       NVARCHAR(50)    NOT NULL,
    inviteCode      CHAR(10)        NOT NULL    UNIQUE,
    coverURL        VARCHAR         NOT NULL    DEFAULT '',
    createdAt       DATETIME        NOT NULL    DEFAULT GETDATE()
)

CREATE TABLE ClassMember(
    classID         INT             FOREIGN KEY REFERENCES Class(classID),
    username        VARCHAR(25)     FOREIGN KEY REFERENCES [User](username),
    role            VARCHAR(7)      NOT NULL,
    createdAt       DATETIME        NOT NULL    DEFAULT GETDATE(),
    CHECK(role IN ('STUDENT', 'TEACHER')),
    PRIMARY KEY(username, classID)
)

CREATE TABLE Homework(
    homeworkID      INT             IDENTITY(1,1) PRIMARY KEY,
    classID         INT             FOREIGN KEY REFERENCES Class(classID),
    creator         VARCHAR(25)     FOREIGN KEY REFERENCES [User](username),
    fileLink        VARCHAR         NOT NULL,
    description     NVARCHAR        NOT NULL,
    deadline        DATETIME,
    createdAt       DATETIME        NOT NULL    DEFAULT GETDATE()
)

CREATE TABLE Submission(
    homeworkID      INT             FOREIGN KEY REFERENCES Homework(homeworkID),
    username        VARCHAR(25)     FOREIGN KEY REFERENCES [User](username),
    fileLink        VARCHAR         NOT NULL,
    mark            INT,
    createdAt       DATETIME        NOT NULL    DEFAULT GETDATE(),
    PRIMARY KEY(homeworkID, username)
)

CREATE TABLE Message(
    messageID       INT             IDENTITY(1,1) PRIMARY KEY,
    classID         INT             FOREIGN KEY REFERENCES Class(classID),
    sender          VARCHAR(25)     FOREIGN KEY REFERENCES [User](username),
    receiver        VARCHAR(25)     FOREIGN KEY REFERENCES [User](username),
    content         NVARCHAR        NOT NULL,
    createdAt       DATETIME        NOT NULL    DEFAULT GETDATE()
)
