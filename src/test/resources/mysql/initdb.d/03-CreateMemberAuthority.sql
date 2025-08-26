# Set database
use account;

CREATE TABLE member_authority (
                                  member_id BIGINT NOT NULL,
                                  authority VARCHAR(50) NOT NULL,
                                  PRIMARY KEY (member_id, authority),
                                  FOREIGN KEY (member_id) REFERENCES member(id) ON DELETE CASCADE,
                                  CONSTRAINT chk_authority_valid CHECK (authority IN ('USER', 'EXPERT', 'MANAGER'))
);

CREATE INDEX idx_member_authority_authority ON member_authority(authority);