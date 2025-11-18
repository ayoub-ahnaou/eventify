CREATE TABLE IF NOT EXISTS registrations (
    id BIGSERIAL PRIMARY KEY,
    register_at TIMESTAMP,
    status VARCHAR(255),

    user_id BIGINT,
    event_id BIGINT,

    CONSTRAINT fk_registration_user
    FOREIGN KEY (user_id) REFERENCES users(id),

    CONSTRAINT fk_registration_event
    FOREIGN KEY (event_id) REFERENCES events(id)
)