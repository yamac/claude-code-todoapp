DROP TABLE IF EXISTS todos;

CREATE TABLE todos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255) NOT NULL,
    description TEXT,
    completed BOOLEAN DEFAULT FALSE,
    sort_order INTEGER DEFAULT 0,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL
);

-- Insert some sample data
INSERT INTO todos (title, description, completed, sort_order, created_at, updated_at) VALUES
('Sample Todo 1', 'This is a sample todo item', false, 1, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP),
('Sample Todo 2', 'Another sample todo item', true, 2, CURRENT_TIMESTAMP, CURRENT_TIMESTAMP);