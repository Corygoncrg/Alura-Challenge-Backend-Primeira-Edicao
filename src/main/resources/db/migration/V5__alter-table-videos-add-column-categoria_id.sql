ALTER TABLE videos add column categoria_id INT NOT NULL;
update videos set categoria_id = 1;
