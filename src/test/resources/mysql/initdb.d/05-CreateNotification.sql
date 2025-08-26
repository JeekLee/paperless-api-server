# Set database
use account;

# Create position table
CREATE TABLE notification (
                        id                      BIGINT          AUTO_INCREMENT      PRIMARY KEY,
                        member_id               BIGINT          NOT NULL            ,
                        message                 VARCHAR(255)    NOT NULL            ,
                        redirect_path           VARCHAR(255),
                        is_read                 BOOLEAN         NOT NULL            DEFAULT FALSE,
                        created_at              DATETIME        NOT NULL            DEFAULT CURRENT_TIMESTAMP,
                        updated_at              DATETIME        NOT NULL            DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

                        FOREIGN KEY (member_id) REFERENCES member(id) ON DELETE CASCADE
) DEFAULT CHARACTER SET utf8mb4;