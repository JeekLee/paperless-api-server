# Set database
use account;

# Create position table
CREATE TABLE member (
                        id                      BIGINT          AUTO_INCREMENT      PRIMARY KEY,
                        nickname                VARCHAR(255)    NOT NULL            UNIQUE,
                        email                   VARCHAR(255)    NOT NULL            UNIQUE,
                        image_path              VARCHAR(255),

                        created_at              DATETIME        NOT NULL            DEFAULT CURRENT_TIMESTAMP,
                        updated_at              DATETIME        NOT NULL            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) DEFAULT CHARACTER SET utf8mb4;

CREATE INDEX idx_member_authority_nickname ON member(nickname);
CREATE INDEX idx_member_authority_email ON member(email);