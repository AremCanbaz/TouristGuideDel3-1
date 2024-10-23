INSERT INTO touristattraktioner (Name, Description, District)
VALUES 
('Tivoli', 'A famous amusement park in Copenhagen', 'Copenhagen'),
('Nyhavn', 'Historical waterfront and entertainment district', 'Copenhagen'),
('The Little Mermaid', 'Iconic bronze statue by the waterside', 'Copenhagen');

INSERT INTO touristtags (TagName)
VALUES 
('Historical Site'),
('Amusement Park'),
('Statue'),
('Waterfront'),
('Landmark');

INSERT INTO attractiontags (AttractionID, TagID)
VALUES 
(1, 2),  
(2, 4),  
(3, 1);  
