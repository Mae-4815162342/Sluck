CREATE TABLE IF NOT EXISTS User
(
    uuid       INT PRIMARY KEY NOT NULL AUTO_INCREMENT ,
    username  VARCHAR(50) NOT NULL,
    password   VARCHAR(250) NOT NULL,
    UNIQUE(uuid),
);
CREATE TABLE IF NOT EXISTS Channel
(
    cuid       INT PRIMARY KEY NOT NULL AUTO_INCREMENT ,
    name  VARCHAR(50) NOT NULL,
    admin     int NOT NULL,
    UNIQUE(cuid),
    FOREIGN(admin) REFERENCES User(uuid)
);
CREATE TABLE IF NOT EXISTS Message
(
    muid INT PRIMARY KEY NOT NULL AUTO_INCREMENT,
    text       VARCHAR(140) NOT NULL,
    userId  INT NOT NULL,
    channelId    INT NOT NULL,
    UNIQUE(muid),
    FOREIGN(userId) REFERENCES User(uuid),
    FOREIGN(channelId) references Channel(cuid)
);