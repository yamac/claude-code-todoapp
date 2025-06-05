DROP TABLE IF EXISTS todos;

CREATE TABLE todos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    completed BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

-- Insert some sample data
INSERT INTO todos (title, description, completed, created_at, updated_at) VALUES
('Sample Todo 1', 'This is a sample todo item', false, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Sample Todo 2', 'Another sample todo item', true, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);