INSERT INTO team(id, name)
VALUES (1, 'Dynamo');
INSERT INTO team(id, name)
VALUES (2, 'Juventus');

INSERT INTO player(id, first_name, last_name, birthday, position, team_id)
VALUES (1, 'Denys', 'Boiko', '1988-01-29', 'GOALKEEPER', 1);
INSERT INTO player(id, first_name, last_name, birthday, position, team_id)
VALUES (2, 'Mykyta', 'Burda', '1995-03-24', 'DEFENDER', 1);
INSERT INTO player(id, first_name, last_name, birthday, position, team_id)
VALUES (3, 'Viktor', 'Tsyhankov', '1997-11-15', 'MIDFILDER', 1);
INSERT INTO player(id, first_name, last_name, birthday, position, team_id)
VALUES (4, 'Artem', 'Besedin', '1997-11-15', 'FORWARD', 1);

INSERT INTO player(id, first_name, last_name, birthday, position, team_id)
VALUES (5, 'Wojciech', 'Szczęsny', '1988-01-29', 'GOALKEEPER', 2);
INSERT INTO player(id, first_name, last_name, birthday, position, team_id)
VALUES (6, 'Giorgio', 'Chiellini', '1995-03-24', 'DEFENDER', 2);
INSERT INTO player(id, first_name, last_name, birthday, position, team_id)
VALUES (7, 'Sami', 'Khedira', '1997-11-15', 'MIDFILDER', 2);
INSERT INTO player(id, first_name, last_name, birthday, position, team_id)
VALUES (8, 'Cristiano', 'Ronaldo', '1997-11-15', 'FORWARD', 2);

UPDATE team
SET captain_id=3
WHERE team.id = 1;