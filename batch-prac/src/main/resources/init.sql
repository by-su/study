DROP TABLE IF EXISTS file_member;

CREATE TABLE file_member(
    id                  INTEGER PRIMARY KEY AUTO_INCREMENT,
    member_id           INTEGER,
    deleted_at          TIMESTAMP,
    created_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at          TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);
