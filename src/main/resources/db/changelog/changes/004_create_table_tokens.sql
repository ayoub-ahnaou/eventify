CREATE TABLE IF NOT EXISTS tokens (
        id BIGSERIAL PRIMARY KEY,
        value VARCHAR(255) UNIQUE NOT NULL,
        expires_at TIMESTAMP,
        user_id BIGINT NOT NULL,
        revoked BOOLEAN DEFAULT FALSE,

        CONSTRAINT fk_tokens_user
            FOREIGN KEY (user_id)
                REFERENCES users(id)
                ON DELETE CASCADE
);