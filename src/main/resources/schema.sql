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
    sponsorid INT,
    eventid INT,
    PRIMARY KEY (sponsorid, eventId),
    FOREIGN KEY (sponsorid) REFERENCES sponsor(id),
    FOREIGN KEY (eventId) REFERENCES event(id)
);
