DROP TABLE IF EXISTS team;
DROP TABLE IF EXISTS player;

CREATE TABLE team
(
  id         SERIAL,
  name       VARCHAR(255) NOT NULL,
  captain_id INT,
  CONSTRAINT team_name_UQ UNIQUE (name),
  CONSTRAINT team_PK PRIMARY KEY (id)
);

CREATE TABLE player
(
  id         SERIAL,
  first_name VARCHAR(255) NOT NULL,
  last_name  VARCHAR(255) NOT NULL,
  position   VARCHAR(255) NOT NULL,
  birthday   TIMESTAMP    NOT NULL,
  team_id    INT,
  CONSTRAINT player_PK PRIMARY KEY (id),
  CONSTRAINT player_team_FK FOREIGN KEY (team_id) REFERENCES team
);

ALTER TABLE team
  ADD CONSTRAINT team_player_FK FOREIGN KEY (captain_id) REFERENCES player;