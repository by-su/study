
DROP TABLE IF EXISTS members;
DROP TABLE IF EXISTS file_member;

CREATE TABLE members (
                         id INTEGER PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         email VARCHAR(255) NOT NULL,
                         age INTEGER NOT NULL,
                         active BOOLEAN NOT NULL
);

CREATE TABLE file_member(
                            id INTEGER PRIMARY KEY,
                            name VARCHAR(255) NOT NULL,
                            email VARCHAR(255) NOT NULL,
                            age INTEGER NOT NULL,
                            active BOOLEAN NOT NULL
)