CREATE TABLE IF NOT EXISTS events (
    id BIGSERIAL PRIMARY KEY,
    title VARCHAR(255),
    location VARCHAR(255),
    date_time TIMESTAMP,
    capacity INTEGER,
    organizer_id BIGINT,

    CONSTRAINT fk_events_organizer
    FOREIGN KEY (organizer_id) REFERENCES users(id)
);