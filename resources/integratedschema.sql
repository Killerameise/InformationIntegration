CREATE TABLE "club" (
	"id" SERIAL PRIMARY KEY,  
	"name" varchar(255),  
	"liga" integer
);


CREATE TABLE "liga" (
	"id" SERIAL PRIMARY KEY,  
	"verband" varchar(255),  
	"erstaustragung" date,  
	"meister" integer,  
	"rekordspieler" varchar(255),
	"spiele_rekordspieler" integer
);

CREATE TABLE "match" (
	"id" SERIAL PRIMARY KEY,  
	"spieltag" integer,  
	"date" date,  
	"time" time,  
	"home" integer,
	"foreign" integer,
	"tore_heim" integer,  
	"tore_gast" integer
) ;

CREATE TABLE "sportsman" (
    "id" SERIAL PRIMARY KEY,
    "firstname" character varying(255),
    "lastname" character varying(255),
    "birthdate" date,
    "captain" boolean,
    "position" character varying(10),
    "Anzahl Einsaetze" integer,
    "club_id" integer,
    "playernumber" integer,
    "country" varchar(255),  
	"games" integer,  
	"goals" integer,  
	"assist" integer,
	"hand" char,
	"contract_until" text,
	"market_value" text,
	"belt" character varying(255),
	"coach" character varying(255),
	"familynamelocal" character varying(255),
	"middlenamelocal" character varying(255),
	"givenname" character varying(255),
	"givennamelocal" character varying(255),
	"favoritetechnique" character varying(255),
	"gender" character varying(6),
	"middlename" character varying(255),
	"shortname" character varying(255),
	"side" character(1),
	"birthCountry" VARCHAR(50),
    "birthState" VARCHAR(30),
    "birthCity" VARCHAR(50),
    "deathYear" INTEGER,
    "deathMonth" INTEGER,
    "deathDay" INTEGER,
    "deathCountry" VARCHAR(50),
    "deathState" VARCHAR(20),
    "deathCity" VARCHAR(50),
    "nameGiven" VARCHAR(255),
    "weight" INTEGER,
    "height" DOUBLE PRECISION,
    "bats" VARCHAR(1),
    "throws" VARCHAR(1),
    "debut" DATE,
    "finalGame" DATE,
    "retroID" VARCHAR(9),
    "bbrefID" VARCHAR(9),
    "comment" character varying(20),
  	"startdate" character varying(20),
  	"enddate" character varying(20),
  	"occupation" varchar(255)
);

CREATE TABLE hall_of_fame (
    "sportsman_id" integer,
    "year" INTEGER,
    "voted_by" VARCHAR(64),
    "ballots" INTEGER,
    "needed" INTEGER,
    "votes" INTEGER,
    "inducted" VARCHAR(1),
    "category" VARCHAR(20),
    "needed_note" VARCHAR(25),
    PRIMARY KEY ( "sportsman_id", "year", "voted_by" )
);

CREATE TABLE school (
    "id" VARCHAR(15) PRIMARY KEY,
    "name_full" VARCHAR(255),
    "city" VARCHAR(55),
    "state" VARCHAR(55),
    "country" VARCHAR(55)
);

CREATE TABLE college_playing (
    "sportsman_id" integer,
    "school_id" VARCHAR(15),
    "year" INTEGER
);

CREATE TABLE team_sportsman (
	"team_id" INTEGER,
	"sportsman_id" INTEGER
);

CREATE TABLE "team" (
    "id" SERIAL PRIMARY KEY,
    "name" character varying(50),
    "group" character varying(20),
    "code" TEXT,
	"short_name" TEXT,
	"squad_market_value" TEXT,
	"crest_url" TEXT,
	"min" real,
	"possfor" integer,
	"possopp" integer,
	"pointsfor" integer,
	"pointopp" integer,
	"offrtg" real,
	"defrtg" real,
	"overallrtg" real,
	"orebfor" integer,
	"orebopp" integer,
	"drebfor" integer,
	"drebopp" integer,
	"orebrate" real,
	"drebrate" real
);

CREATE TABLE fight (
	"id" SERIAL PRIMARY KEY,
	"white" integer,
	"blue" integer,
	"winner" integer,
	"competition" integer,
	"date" date,
	"duration" time without time zone,
	"fightnumber" integer,
	"ipponblue" integer,
	"ipponwhite" integer,
	"penaltyblue" integer,
	"penaltywhite" integer,
	"round" integer,
	"roundcode" character varying(255),
	"roundname" character varying(255),
	"sccountdownoffset" integer,
	"tagged" integer,
	"wazablue" integer,
	"wazawhite" integer,
	"weight" integer,
	"yukoblue" integer,
	"yukowhite" integer,
	"sportsman_id" integer
);

CREATE TABLE "ranking" (
    "id" serial PRIMARY KEY,
    "date" integer,
    "position" integer,
	"sportsman_id" integer,
	"pts" integer
);

CREATE TABLE league_table (
	"id" serial PRIMARY KEY,
	"leagueCaption" TEXT,
	"matchday" INTEGER,
	"position" INTEGER,
	"team_id" INTEGER,
	"playedGames" INTEGER,
	"points" INTEGER,
	"goals" INTEGER,
	"goalsAgainst" INTEGER,
	"goalsDifference" INTEGER,
	"wins" INTEGER,
	"draws" INTEGER,
	"losses" INTEGER,
	"homeGoals" INTEGER,
	"homeGoalsAgainst" INTEGER,
	"homeWins" INTEGER,
	"homeDraws" INTEGER,
	"homeLosses" INTEGER,
	"awayGoals" INTEGER,
	"awayGoalsAgainst" INTEGER,
	"awayWins" INTEGER,
	"awayDraws" INTEGER,
	"awayLosses" INTEGER
);


CREATE TABLE team_season (
	"season_id" INTEGER,
	"team_id" INTEGER
);

CREATE TABLE season(
	"id" SERIAL PRIMARY KEY,
	"caption" TEXT,
	"league" TEXT,
	"year" INTEGER,
	"number_of_teams" INTEGER,
	"last_updated" TEXT,
	"league_table" INTEGER
);

ALTER TABLE season ADD CONSTRAINT season_league_table_fk FOREIGN KEY (league_table) REFERENCES league_table(id);
ALTER TABLE team_season ADD CONSTRAINT team_season_season_fk FOREIGN KEY(season_id) REFERENCES season(id);
ALTER TABLE team_season ADD CONSTRAINT team_season_team_id_fk FOREIGN KEY(team_id) REFERENCES team(id);
ALTER TABLE league_table ADD CONSTRAINT league_table_team_id_fk FOREIGN KEY(team_id) REFERENCES team(id);
ALTER TABLE "ranking" ADD CONSTRAINT ranking_sportsman_id_fk FOREIGN KEY("sportsman_id") REFERENCES "sportsman" ("id");
ALTER TABLE team_sportsman ADD CONSTRAINT team_sportsman_team_id_fk FOREIGN KEY(team_id) REFERENCES team(id);
ALTER TABLE team_sportsman ADD CONSTRAINT team_sportsman_sportsman_id_fk FOREIGN KEY(sportsman_id) REFERENCES sportsman(id);
ALTER TABLE college_playing ADD CONSTRAINT college_playing_sportsman_id_fk FOREIGN KEY("sportsman_id") REFERENCES sportsman(id);
ALTER TABLE college_playing ADD CONSTRAINT college_playing_school_id_fk FOREIGN KEY("school_id") REFERENCES school(id);
ALTER TABLE hall_of_fame ADD CONSTRAINT hall_of_fame_sportsman_id_fk FOREIGN KEY("sportsman_id") REFERENCES sportsman(id);
ALTER TABLE sportsman ADD CONSTRAINT sportsman_club_id_fk FOREIGN KEY("club_id") REFERENCES club(id);
ALTER TABLE match ADD CONSTRAINT match_home_club_id_fk FOREIGN KEY("home") REFERENCES club(id);
ALTER TABLE match ADD CONSTRAINT match_foreign_club_id_fk FOREIGN KEY("foreign") REFERENCES club(id);
ALTER TABLE liga ADD CONSTRAINT liga_club_id_fk FOREIGN KEY("meister") REFERENCES club(id);
ALTER TABLE club ADD CONSTRAINT club_liga_id_fk FOREIGN KEY("liga") REFERENCES liga(id);
ALTER TABLE fight ADD CONSTRAINT fight_sportsman_id_fk FOREIGN KEY("sportsman_id") REFERENCES sportsman(id);