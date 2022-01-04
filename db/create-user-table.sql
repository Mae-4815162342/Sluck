CREATE TABLE IF NOT EXISTS User
(
    uuid       INT PRIMARY KEY NOT NULL AUTO_INCREMENT ,
    username  VARCHAR(50) NOT NULL,
    password   VARCHAR(250) NOT NULL,
    UNIQUE(uuid),
    UNIQUE(username)
);
CREATE TABLE IF NOT EXISTS Channel
(
    cuid       INT PRIMARY KEY NOT NULL AUTO_INCREMENT ,
    name  VARCHAR(50) NOT NULL,
    adminId     int NOT NULL,
    UNIQUE(cuid),
    UNIQUE(name),
    KEY admin_id (adminId)
);
CREATE TABLE IF NOT EXISTS Message
(
    muid INT NOT NULL,
    text       VARCHAR(140) NOT NULL,
    userId  INT NOT NULL,
    channelId    INT NOT NULL,
    UNIQUE(muid),
    KEY user_id (userId),
    KEY channel_id (channelId)
);