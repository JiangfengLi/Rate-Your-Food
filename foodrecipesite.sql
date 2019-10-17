CREATE DATABASE foodrecipe436;
USE foodrecipe436;

DROP TABLE Instruction;
DROP TABLE Ingredient;
DROP TABLE Review;
DROP TABLE Recipe;
DROP TABLE User;


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
	Unit VARCHAR (20),
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


# INSERT SAMPLE DATA:

# USER DATA
# password encrypted version of 'jack'
INSERT INTO User VALUES('jack@gmail.com','jack','jack','0mXzfRPHTa40SIZ2gPWoJw==');
# password encrypted version of 'john'
INSERT INTO User VALUES('john@gmail.com','john','jacks','z9/AA0XRBSK/uI8heorA6Q==');
# password encrypted version of 'joeyboy'
INSERT INTO User VALUES('joe@gmail.com','joe','johnny','Z8okvgI5JiCGyYJFteaGRw==');
# password encrypted version of 'eliza'
INSERT INTO User VALUES('elizasmith@yahoo.com','eliza','smith','yPfTGWqwikDosG+nwsx5Pw==');
# password encrypted version of 'championship'
INSERT INTO User VALUES('hotsoccermomma@hotmail.com','sally','sue','TKtrbh2idppc4NJNhx03Jg==');


# RECIPES
INSERT INTO Recipe VALUES ('myfoods','jack@gmail.com',3,5);
INSERT INTO Recipe VALUES ('grandma pams potato dumplings','jack@gmail.com',4,3);
INSERT INTO Recipe VALUES ('hot cheez-it with cheez-wip','jack@gmail.com',2,5);

INSERT INTO Recipe VALUES ('steak with butter','john@gmail.com',3,4);
INSERT INTO Recipe VALUES ('meat with more meat','john@gmail.com',4,3);
INSERT INTO Recipe VALUES ('salami salad','john@gmail.com',4,2);

INSERT INTO Recipe VALUES ('joey bag o donuts','joe@gmail.com',3,4);
INSERT INTO Recipe VALUES ('joey joes doey doughs','joe@gmail.com',5,5);
INSERT INTO Recipe VALUES ('joeys jonesin special','joe@gmail.com',4,5);

INSERT INTO Recipe VALUES ('carne asada with balsamic drizzle','elizasmith@yahoo.com',3,4);
INSERT INTO Recipe VALUES ('calabacitas with oaxacan cheese','elizasmith@yahoo.com',4,4);
INSERT INTO Recipe VALUES ('southwestern brunch omelette','elizasmith@yahoo.com',3,5);

INSERT INTO Recipe VALUES ('hot mommas hot taziki casserole','hotsoccermomma@hotmail.com',3,3);
INSERT INTO Recipe VALUES ('orange slices for camp','hotsoccermomma@hotmail.com',4,2);
INSERT INTO Recipe VALUES ('kitchen sink chicken dinner','hotsoccermomma@hotmail.com',2,2);



# REVIEWS
INSERT INTO Review VALUES ('jack@gmail.com','carne asada with balsamic drizzle','elizasmith@yahoo.com','where da cheez at?',5,2);
INSERT INTO Review VALUES ('jack@gmail.com','orange slices for camp','hotsoccermomma@hotmail.com','needs mo cheez.',3,3);
INSERT INTO Review VALUES ('jack@gmail.com','salami salad','john@gmail.com','i would like to see some cheez in this...',4,4);
INSERT INTO Review VALUES ('jack@gmail.com','calabacitas with oaxacan cheese','elizasmith@yahoo.com','now thats what im talkin about!',5,5);
INSERT INTO Review VALUES ('jack@gmail.com','kitchen sink chicken dinner','hotsoccermomma@hotmail.com','i feel like some cheez would really open up the flavors.',3,4);

INSERT INTO Review VALUES ('john@gmail.com','joey bag o donuts','joe@gmail.com','love the steaky flavors!',3,5);
INSERT INTO Review VALUES ('john@gmail.com','joey joes doey doughs','joe@gmail.com','my man joey does it again!',3,5);
INSERT INTO Review VALUES ('john@gmail.com','joeys jonesin special','joe@gmail.com','jooeyyyyy!!!!!!!!',3,5);
INSERT INTO Review VALUES ('john@gmail.com','carne asada with balsamic drizzle','elizasmith@yahoo.com','love tha meat!',5,5);
INSERT INTO Review VALUES ('john@gmail.com','southwestern brunch omelette','elizasmith@yahoo.com','a light start to a meat-filled day.',3,4);

INSERT INTO Review VALUES ('joe@gmail.com','hot mommas hot taziki casserole','hotsoccermomma@hotmail.com','joey approves.',4,4);
INSERT INTO Review VALUES ('joe@gmail.com','hot cheez-it with cheez-wip','jack@gmail.com','joey likes it.',4,4);
INSERT INTO Review VALUES ('joe@gmail.com','grandma pams potato dumplings','jack@gmail.com','joey get down with some of grandmas dumplins!',5,5);
INSERT INTO Review VALUES ('joe@gmail.com','meat with more meat','john@gmail.com','joeyboy gives two thumbs up!',4,5);
INSERT INTO Review VALUES ('joe@gmail.com','steak with butter','john@gmail.com','one of joeys favorite meat eats!',3,5);

INSERT INTO Review VALUES ('elizasmith@yahoo.com','steak with butter','john@gmail.com','a lighter touch on the butter would reveal the finer qualities of the steak.',2,3);
INSERT INTO Review VALUES ('elizasmith@yahoo.com','kitchen sink chicken dinner','hotsoccermomma@hotmail.com','I find that a squeeze of lemon can really brighten the flavor of old left-overs.',2,2);
INSERT INTO Review VALUES ('elizasmith@yahoo.com','orange slices for camp','hotsoccermomma@hotmail.com','Not inspired.',2,1);
INSERT INTO Review VALUES ('elizasmith@yahoo.com','hot cheez-it with cheez-wip','jack@gmail.com','I dont know where to begin.',1,1);
INSERT INTO Review VALUES ('elizasmith@yahoo.com','joeys jonesin special','joe@gmail.com','Another triumph by our well-known curator of the culinary, Monsieur Joey.',3,5);

INSERT INTO Review VALUES ('hotsoccermomma@hotmail.com','southwestern brunch omelette','elizasmith@yahoo.com','A low-stress way to feed the kids on Sunday!',2,4);
INSERT INTO Review VALUES ('hotsoccermomma@hotmail.com','joey joes doey doughs','joe@gmail.com','It IS doughy, but also delectable.',3,4);
INSERT INTO Review VALUES ('hotsoccermomma@hotmail.com','salami salad','john@gmail.com','It sounds strange, but it aint bad.',3,3);
INSERT INTO Review VALUES ('hotsoccermomma@hotmail.com','hot cheez-it with cheez-wip','jack@gmail.com','Disgusting.',3,1);
INSERT INTO Review VALUES ('hotsoccermomma@hotmail.com','myfoods','jack@gmail.com','Actually, not bad. The kids will eat it.',2,3);



# INGREDIENTS
INSERT INTO Ingredient VALUES ('peaches','myfoods','jack@gmail.com',4,'peaches');
INSERT INTO Ingredient VALUES ('apples','myfoods','jack@gmail.com',2,'apples');
INSERT INTO Ingredient VALUES ('sugar','myfoods','jack@gmail.com',3,'tablespoons');
INSERT INTO Ingredient VALUES ('dough','myfoods','jack@gmail.com',.25,'pounds');
INSERT INTO Ingredient VALUES ('molasses','myfoods','jack@gmail.com',2,'tablespoons');

INSERT INTO Ingredient VALUES ('potatoes','grandma pams potato dumplings','jack@gmail.com',12,'potatoes');
INSERT INTO Ingredient VALUES ('pastry','grandma pams potato dumplings','jack@gmail.com',.5,'pounds');
INSERT INTO Ingredient VALUES ('carrots','grandma pams potato dumplings','jack@gmail.com',.5,'pounds');
INSERT INTO Ingredient VALUES ('shallots','grandma pams potato dumplings','jack@gmail.com',4,'shallots');
INSERT INTO Ingredient VALUES ('beef','grandma pams potato dumplings','jack@gmail.com',.5,'pounds');

INSERT INTO Ingredient VALUES ('cheez-its','hot cheez-it with cheez-wip','jack@gmail.com',1,'bag');
INSERT INTO Ingredient VALUES ('cheez-wip','hot cheez-it with cheez-wip','jack@gmail.com',4,'cans');


INSERT INTO Ingredient VALUES ('12 oz rib-eye','steak with butter','john@gmail.com',4,'steaks');
INSERT INTO Ingredient VALUES ('butter','steak with butter','john@gmail.com',2,'sticks');
INSERT INTO Ingredient VALUES ('salt','steak with butter','john@gmail.com',1,'tablespoon');
INSERT INTO Ingredient VALUES ('pepper','steak with butter','john@gmail.com',1,'teaspoon');

INSERT INTO Ingredient VALUES ('steak','meat with more meat','john@gmail.com',2,'steaks');
INSERT INTO Ingredient VALUES ('pork chops','meat with more meat','john@gmail.com',2,'pork chops');
INSERT INTO Ingredient VALUES ('chicken legs','meat with more meat','john@gmail.com',6,'legs');
INSERT INTO Ingredient VALUES ('chicken wings','meat with more meat','john@gmail.com',6,'wings');
INSERT INTO Ingredient VALUES ('chicken liver','meat with more meat','john@gmail.com',5,'livers');
INSERT INTO Ingredient VALUES ('chicken heart','meat with more meat','john@gmail.com',2,'hearts');
INSERT INTO Ingredient VALUES ('chicken soul','meat with more meat','john@gmail.com',1,'soul');

INSERT INTO Ingredient VALUES ('salami','salami salad','john@gmail.com',.5,'pounds');
INSERT INTO Ingredient VALUES ('pepperoni','salami salad','john@gmail.com',.5,'pounds');
INSERT INTO Ingredient VALUES ('prosciutto','salami salad','john@gmail.com',.5,'pounds');
INSERT INTO Ingredient VALUES ('pear','salami salad','john@gmail.com',2,'pears');
INSERT INTO Ingredient VALUES ('apple','salami salad','john@gmail.com',2,'apples');
INSERT INTO Ingredient VALUES ('burrata','salami salad','john@gmail.com',.3,'pounds');
INSERT INTO Ingredient VALUES ('cheddar cheese','salami salad','john@gmail.com',.3,'pounds');
INSERT INTO Ingredient VALUES ('feta cheese','salami salad','john@gmail.com',.3,'pounds');

INSERT INTO Ingredient VALUES ('tortillas','joey bag o donuts','joe@gmail.com',12,'tortillas');
INSERT INTO Ingredient VALUES ('black or pinto beans','joey bag o donuts','joe@gmail.com',1,'pound');
INSERT INTO Ingredient VALUES ('pepper jack cheese','joey bag o donuts','joe@gmail.com',.5,'pounds');
INSERT INTO Ingredient VALUES ('carne asada','joey bag o donuts','joe@gmail.com',.5,'pounds');
INSERT INTO Ingredient VALUES ('rice','joey bag o donuts','joe@gmail.com',1,'pound');
INSERT INTO Ingredient VALUES ('cilantro','joey bag o donuts','joe@gmail.com',.2,'pounds');
INSERT INTO Ingredient VALUES ('tomato','joey bag o donuts','joe@gmail.com',3,'tomatoes');
INSERT INTO Ingredient VALUES ('serrano peppers','joey bag o donuts','joe@gmail.com',3,'peppers');

INSERT INTO Ingredient VALUES ('mixed grains','joey joes doey doughs','joe@gmail.com',1,'pound');
INSERT INTO Ingredient VALUES ('flour','joey joes doey doughs','joe@gmail.com',.25,'pounds');
INSERT INTO Ingredient VALUES ('yeast','joey joes doey doughs','joe@gmail.com',1,'bag');
INSERT INTO Ingredient VALUES ('sugar','joey joes doey doughs','joe@gmail.com',2,'tablespoons');

INSERT INTO Ingredient VALUES ('dough','joeys jonesin special','joe@gmail.com',1,'pound');
INSERT INTO Ingredient VALUES ('cheddar cheese','joeys jonesin special','joe@gmail.com',.5,'pounds');
INSERT INTO Ingredient VALUES ('butter','joeys jonesin special','joe@gmail.com',.3,'sticks');
INSERT INTO Ingredient VALUES ('beef','joeys jonesin special','joe@gmail.com',.35,'pounds');
INSERT INTO Ingredient VALUES ('taziki','joeys jonesin special','joe@gmail.com',.5,'pounds');


INSERT INTO Ingredient VALUES ('carne asada','carne asada with balsamic drizzle','elizasmith@yahoo.com',.5,'pounds');
INSERT INTO Ingredient VALUES ('balsamic vinegrette','carne asada with balsamic drizzle','elizasmith@yahoo.com',1,'tablespoon');
INSERT INTO Ingredient VALUES ('arugula','carne asada with balsamic drizzle','elizasmith@yahoo.com',.2,'pounds');
INSERT INTO Ingredient VALUES ('gorganzola cheese','carne asada with balsamic drizzle','elizasmith@yahoo.com',.5,'pounds');
INSERT INTO Ingredient VALUES ('salt','carne asada with balsamic drizzle','elizasmith@yahoo.com',1,'teaspoon');
INSERT INTO Ingredient VALUES ('pepper','carne asada with balsamic drizzle','elizasmith@yahoo.com',1,'teaspoon');

INSERT INTO Ingredient VALUES ('oaxacan cheese','calabacitas with oaxacan cheese','elizasmith@yahoo.com',1,'pound');
INSERT INTO Ingredient VALUES ('squash','calabacitas with oaxacan cheese','elizasmith@yahoo.com',3,'squashes');
INSERT INTO Ingredient VALUES ('salt','calabacitas with oaxacan cheese','elizasmith@yahoo.com',1,'teaspoon');

INSERT INTO Ingredient VALUES ('eggs','southwestern brunch omelette','elizasmith@yahoo.com',12,'eggs');
INSERT INTO Ingredient VALUES ('cilantro','southwestern brunch omelette','elizasmith@yahoo.com',.2,'pounds');
INSERT INTO Ingredient VALUES ('tomato','southwestern brunch omelette','elizasmith@yahoo.com',3,'tomatoes');
INSERT INTO Ingredient VALUES ('serrano peppers','southwestern brunch omelette','elizasmith@yahoo.com',3,'peppers');
INSERT INTO Ingredient VALUES ('hatch chile','southwestern brunch omelette','elizasmith@yahoo.com',.3,'pounds');
INSERT INTO Ingredient VALUES ('sriracha','southwestern brunch omelette','elizasmith@yahoo.com',.1,'liters');

INSERT INTO Ingredient VALUES ('turkey','hot mommas hot taziki casserole','hotsoccermomma@hotmail.com',.5,'pounds');
INSERT INTO Ingredient VALUES ('rotini','hot mommas hot taziki casserole','hotsoccermomma@hotmail.com',.5,'pounds');
INSERT INTO Ingredient VALUES ('tomatoes','hot mommas hot taziki casserole','hotsoccermomma@hotmail.com',3,'tomatoes');
INSERT INTO Ingredient VALUES ('carrots','hot mommas hot taziki casserole','hotsoccermomma@hotmail.com',4,'carrots');
INSERT INTO Ingredient VALUES ('peas','hot mommas hot taziki casserole','hotsoccermomma@hotmail.com',.2,'pounds');
INSERT INTO Ingredient VALUES ('pepper jack cheese','hot mommas hot taziki casserole','hotsoccermomma@hotmail.com',.5,'pounds');
INSERT INTO Ingredient VALUES ('cream','hot mommas hot taziki casserole','hotsoccermomma@hotmail.com',.2,'liters');

INSERT INTO Ingredient VALUES ('oranges','orange slices for camp','hotsoccermomma@hotmail.com',12,'oranges');
INSERT INTO Ingredient VALUES ('brown sugar','orange slices for camp','hotsoccermomma@hotmail.com',.2,'pounds');

INSERT INTO Ingredient VALUES ('chicken','kitchen sink chicken dinner','hotsoccermomma@hotmail.com',1,'chicken');
INSERT INTO Ingredient VALUES ('broccoli','kitchen sink chicken dinner','hotsoccermomma@hotmail.com',12,'pieces');
INSERT INTO Ingredient VALUES ('carrots','kitchen sink chicken dinner','hotsoccermomma@hotmail.com',4,'carrots');
INSERT INTO Ingredient VALUES ('white onions','kitchen sink chicken dinner','hotsoccermomma@hotmail.com',1,'onion');
INSERT INTO Ingredient VALUES ('butter','kitchen sink chicken dinner','hotsoccermomma@hotmail.com',1,'stick');
INSERT INTO Ingredient VALUES ('parsely','kitchen sink chicken dinner','hotsoccermomma@hotmail.com',1,'bundle');
INSERT INTO Ingredient VALUES ('salt','kitchen sink chicken dinner','hotsoccermomma@hotmail.com',1,'teaspoon');
INSERT INTO Ingredient VALUES ('pepper','kitchen sink chicken dinner','hotsoccermomma@hotmail.com',1,'teaspoon');



# INSTRUCTIONS
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('myfoods','jack@gmail.com','dice peaches and apples finely.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('myfoods','jack@gmail.com','mix diced fruit with molasses and sugar in bowl.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('myfoods','jack@gmail.com','lay out dough in pie dish.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('myfoods','jack@gmail.com','dump stuffing in dough.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('myfoods','jack@gmail.com','cook for 30 minutes in oven.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('myfoods','jack@gmail.com','finish with a broil as desired.');

INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('grandma pams potato dumplings','jack@gmail.com','Chop potatoes, carrots, and shallots.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('grandma pams potato dumplings','jack@gmail.com','Braise beef in cast iron pan until rare but seared.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('grandma pams potato dumplings','jack@gmail.com','Chop beef into small to medium-size pieces.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('grandma pams potato dumplings','jack@gmail.com','Lay out pastry dough, place beef and vegetables into pastry.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('grandma pams potato dumplings','jack@gmail.com','Bake for 30 min. Finish with broil.');

INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('hot cheez-it with cheez-wip','jack@gmail.com','Dump out bag of cheez-its.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('hot cheez-it with cheez-wip','jack@gmail.com','Empty spray-can of cheez-wip on top.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('hot cheez-it with cheez-wip','jack@gmail.com','Stir and enjoy.');


INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('steak with butter','john@gmail.com','Melt butter in pan with salt.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('steak with butter','john@gmail.com','Sear steak in pan as desired (rare recommended).');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('steak with butter','john@gmail.com','Lightly pepper and enjoy.');

INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('meat with more meat','john@gmail.com','Fire up grill to 600 degrees.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('meat with more meat','john@gmail.com','Dump meat on grill.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('meat with more meat','john@gmail.com','Sear and chill.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('meat with more meat','john@gmail.com','Close up grill to lightly smoke meats.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('meat with more meat','john@gmail.com','Dump meat on platter and enjoy.');

INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('salami salad','john@gmail.com','Slice fruits and cheeses into small pieces.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('salami salad','john@gmail.com','Wrap the fruits and cheeses in the various meats.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('salami salad','john@gmail.com','Place on platter and enjoy.');


INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('joey bag o donuts','joe@gmail.com','Dice and mix the serranos, tomatoes, and cilantro.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('joey bag o donuts','joe@gmail.com','Heat beans in pot.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('joey bag o donuts','joe@gmail.com','Use rice-cooker to cook rice.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('joey bag o donuts','joe@gmail.com','Pan sear carne asada as desired (medium-rare recommended).');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('joey bag o donuts','joe@gmail.com','Slice carne asada into small strips.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('joey bag o donuts','joe@gmail.com','Grate cheese onto tortilla, fry until melted.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('joey bag o donuts','joe@gmail.com','Place some of everything on tortilla, roll up, sear on all sides.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('joey bag o donuts','joe@gmail.com','Enjoy.');


INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('joey joes doey doughs','joe@gmail.com','Place flour, yeast, and sugar in bowl with water.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('joey joes doey doughs','joe@gmail.com','Cover bowl with saran wrap.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('joey joes doey doughs','joe@gmail.com','Once the dough has started to form, but before it has fully risen, dump in the grains.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('joey joes doey doughs','joe@gmail.com','Stir together and let it continue to rise.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('joey joes doey doughs','joe@gmail.com','After an hour or so once it has risen, place in oven and cook for 35 minutes.');

INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('joeys jonesin special','joe@gmail.com','Roll out dough into thin sheets.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('joeys jonesin special','joe@gmail.com','Grate cheese. Thinly slice meat. Thickly slice butter.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('joeys jonesin special','joe@gmail.com','Lightly sear meat. Dont overcook it.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('joeys jonesin special','joe@gmail.com','Put cheese, meat, and butter on dough. Roll it up. Place in oven.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('joeys jonesin special','joe@gmail.com','Bake until nicely browned. Broil as needed for finishing touch.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('joeys jonesin special','joe@gmail.com','Pull out of oven, dump taziki sauce on top. Enjoy.');


INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('carne asada with balsamic drizzle','elizasmith@yahoo.com','Crumble gorganzola onto plate.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('carne asada with balsamic drizzle','elizasmith@yahoo.com','Wash, then dry arugula.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('carne asada with balsamic drizzle','elizasmith@yahoo.com','Pan sear carne asada as desired (rare recommended).');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('carne asada with balsamic drizzle','elizasmith@yahoo.com','Salt and pepper carne asada. Dump gorganzola crumble on top.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('carne asada with balsamic drizzle','elizasmith@yahoo.com','Apply balsamic drizzle over the top.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('carne asada with balsamic drizzle','elizasmith@yahoo.com','Place arugula on top or on side.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('carne asada with balsamic drizzle','elizasmith@yahoo.com','Enjoy.');

INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('calabacitas with oaxacan cheese','elizasmith@yahoo.com','Chop the cheese into cubes.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('calabacitas with oaxacan cheese','elizasmith@yahoo.com','Boil squash in water for 20 minutes.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('calabacitas with oaxacan cheese','elizasmith@yahoo.com','Chop the squash into similarly-sized cubes.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('calabacitas with oaxacan cheese','elizasmith@yahoo.com','Mix squash with cheese and salt in bowl.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('calabacitas with oaxacan cheese','elizasmith@yahoo.com','Enjoy.');

INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('southwestern brunch omelette','elizasmith@yahoo.com','Dice cilantro, tomato, and serranos. Mix together.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('southwestern brunch omelette','elizasmith@yahoo.com','Pan sear serranos');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('southwestern brunch omelette','elizasmith@yahoo.com','Scramble dozen eggs with the serranos.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('southwestern brunch omelette','elizasmith@yahoo.com','Apply cilantro and tomatoes on top, then the sriracha drizzle.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('southwestern brunch omelette','elizasmith@yahoo.com','Enjoy.');


INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('hot mommas hot taziki casserole','hotsoccermomma@hotmail.com','Roast turkey at 375 degrees in oven for 60 minutes.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('hot mommas hot taziki casserole','hotsoccermomma@hotmail.com','Boil rotini in water until al-dente.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('hot mommas hot taziki casserole','hotsoccermomma@hotmail.com','Dice tomatoes and carrots. Mix with peas and cream.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('hot mommas hot taziki casserole','hotsoccermomma@hotmail.com','Shred cheese.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('hot mommas hot taziki casserole','hotsoccermomma@hotmail.com','Sliced the roasted turkey into fine slices.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('hot mommas hot taziki casserole','hotsoccermomma@hotmail.com','Dump rotini into casserole. Lay turkey slices on top.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('hot mommas hot taziki casserole','hotsoccermomma@hotmail.com','Then dump the mixed vegetables and finally the cheese on top.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('hot mommas hot taziki casserole','hotsoccermomma@hotmail.com','Bake for 25 minutes, then broil until cheese layer browned.');

INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('orange slices for camp','hotsoccermomma@hotmail.com','Slice oranges into 6ths.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('orange slices for camp','hotsoccermomma@hotmail.com','Place in bowl. Dump sugar on top.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('orange slices for camp','hotsoccermomma@hotmail.com','Stir. Cover with saran wrap and leave in fridge until ready to eat.');

INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('kitchen sink chicken dinner','hotsoccermomma@hotmail.com','Baste chicken with butter. Salt and pepper.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('kitchen sink chicken dinner','hotsoccermomma@hotmail.com','Chop carrots, onions and parsely. Placed chopped vegetables inside chicken cavity.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('kitchen sink chicken dinner','hotsoccermomma@hotmail.com','Place broccoli in croque around the chicken.');
INSERT INTO Instruction(RecipeName, RecipeCreator, Text) VALUES ('kitchen sink chicken dinner','hotsoccermomma@hotmail.com','Bake for 45 minutes. Broil to brown as desired.');
