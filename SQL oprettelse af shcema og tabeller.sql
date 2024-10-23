-- Opret schema
CREATE SCHEMA tourist;

-- bruger tourist schema til tourist
USE tourist;

-- Opretter tabellen cities med alle de mulige byer at vælge 
CREATE TABLE cities (
    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    bynavn VARCHAR(255) NOT NULL,
    postnummer INT NOT NULL
);

-- Opretter tabellen touristattraktioner objektet
CREATE TABLE touristattraktioner (
    Id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    Name VARCHAR(255) NOT NULL,
    Description TEXT,
    District VARCHAR(255)
);

-- Opretter tabellen tags som viser alle mulige tags til en attraktion
CREATE TABLE tags (
    id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    tag VARCHAR(255) NOT NULL
);

-- Opretter tabellen touristtags som viser alle tags som bliver brugt til touristattraktion objektet.
CREATE TABLE touristtags ( 
    TagID INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
    TagName VARCHAR(255) NOT NULL
);

-- Opretter tabellen attractiontags og forener de 2 tabeller
CREATE TABLE attractiontags (
    AttractionID INT NOT NULL,
    TagID INT NOT NULL,
    PRIMARY KEY (AttractionID, TagID),
    FOREIGN KEY (AttractionID) REFERENCES touristattraktioner(Id),
    FOREIGN KEY (TagID) REFERENCES tags(id)
);
