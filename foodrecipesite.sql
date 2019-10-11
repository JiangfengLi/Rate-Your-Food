CREATE DATABASE foodrecipe436;
USE foodrecipe436;

CREATE TABLE User (
	Email VARCHAR(30) NOT NULL,
	FirstName VARCHAR(15) NOT NULL,
	LastName VARCHAR(15) NOT NULL,
	Password VARCHAR(50) NOT NULL,
	PRIMARY KEY(Email)
);

CREATE TABLE Recipe (
	RecipeName VARCHAR (50),
	Creator VARCHAR(30),
	Difficulty INTEGER,
	Rating INTEGER,
	FOREIGN KEY(Creator) REFERENCES User(Email),
	PRIMARY KEY (RecipeName, Creator),
	CONSTRAINT CHECK(Difficulty >= 1 AND Difficulty <= 5),
	CONSTRAINT CHECK(Rating >= 1 AND Rating <= 5)
);

CREATE TABLE Review (
	Author VARCHAR (30),
	RecipeName VARCHAR (50),
	RecipeCreator VARCHAR (30),
	Text VARCHAR (500),
	Difficulty INTEGER,
	Rating INTEGER,
	FOREIGN KEY(Author) REFERENCES User(Email),
	FOREIGN KEY(RecipeName, RecipeCreator) REFERENCES Recipe(RecipeName,Creator),
	PRIMARY KEY (Author, RecipeName, RecipeCreator),
	CONSTRAINT CHECK(Difficulty >= 1 AND Difficulty <= 5),
	CONSTRAINT CHECK(Rating >= 1 AND Rating <= 5)
);

CREATE TABLE Ingredient (
	Name VARCHAR (30),
	RecipeName VARCHAR (50),
	RecipeCreator VARCHAR (30),
	Amount FLOAT(10),
	Unit VARCHAR (10),
	FOREIGN KEY(RecipeName, RecipeCreator) REFERENCES Recipe(RecipeName,Creator),
	PRIMARY KEY (Name, RecipeName, RecipeCreator),
	CONSTRAINT CHECK (Amount > 0)
);

CREATE TABLE Instruction (
	ID INTEGER AUTO_INCREMENT,
	RecipeName VARCHAR (50),
	RecipeCreator VARCHAR(30),
	Text VARCHAR(100) NOT NULL,
	FOREIGN KEY(RecipeName, RecipeCreator) REFERENCES Recipe(RecipeName,Creator),
	PRIMARY KEY (ID, RecipeName, RecipeCreator)
);


# SOME SAMPLE DATA:
INSERT INTO Recipe VALUES ('myfoods','jack@gmail.com',3,5);
INSERT INTO Review VALUES ('jo@gmail.com','myfoods','jack@gmail.com','blah blah',3,4);
INSERT INTO Ingredient VALUES ('peaches','myfoods','jack@gmail.com',1,'grams');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('myfoods','jack@gmail.com','cook for 5 minutes');
