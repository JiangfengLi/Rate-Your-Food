CREATE DATABASE foodrecipe436;
USE foodrecipe436;

CREATE TABLE User (
	Email VARCHAR(30) NOT NULL, 
	FirstName VARCHAR(15) NOT NULL, 
	LastName VARCHAR(15) NOT NULL, 
	Password VARCHAR(50) NOT NULL, 
	PRIMARY KEY(Email)
);