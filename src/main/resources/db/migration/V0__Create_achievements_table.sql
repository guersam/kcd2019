CREATE TABLE achievements (
  id BIGSERIAL NOT NULL PRIMARY KEY,
  team_id TEXT NOT NULL,
  user_id TEXT NOT NULL,
  text TEXT NOT NULL,
  created_at TIMESTAMP WITH TIME ZONE NOT NULL DEFAULT current_timestamp
);

CREATE INDEX achievements_team_id_idx ON achievements (team_id);
