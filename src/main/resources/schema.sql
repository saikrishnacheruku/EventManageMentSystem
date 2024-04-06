CREATE TABLE sponsor (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name TEXT,
    industry TEXT
);
CREATE TABLE event (
    id INT AUTO_INCREMENT PRIMARY KEY,
    name TEXT,
    date TEXT
);
CREATE TABLE event_sponsor (
    sponsorId INT,
    eventId INT,
    PRIMARY KEY (sponsorId, eventId),
    FOREIGN KEY (sponsorId) REFERENCES sponsor(id),
    FOREIGN KEY (eventId) REFERENCES event(id)
);
