CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

INSERT INTO movies (id, title, imdb_id)
VALUES (gen_random_uuid(), 'The Fast and the Furious', 'tt0232500'),
       (gen_random_uuid(), '2 Fast 2 Furious', 'tt0322259'),
       (gen_random_uuid(), 'The Fast and the Furious: Tokyo Drift', 'tt0463985'),
       (gen_random_uuid(), 'Fast & Furious', 'tt1013752'),
       (gen_random_uuid(), 'Fast Five', 'tt1596343'),
       (gen_random_uuid(), 'Fast & Furious 6', 'tt1905041'),
       (gen_random_uuid(), 'Furious 7', 'tt2820852'),
       (gen_random_uuid(), 'The Fate of the Furious', 'tt4630562'),
       (gen_random_uuid(), 'F9: The Fast Saga', 'tt5433138');
