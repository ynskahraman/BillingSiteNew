CREATE TABLE payments
(
    id                     BIGINT AUTO_INCREMENT NOT NULL,
    invoiceid              INT NOT NULL,
    type                   VARCHAR(255) NULL,
    price                  FLOAT NULL,
    status                 VARCHAR(255) NULL,
    date_start             date NULL,
    date_end               date NULL,
    paid_confirmation_code VARCHAR(255) NULL,
    user_id                BIGINT NULL,
    CONSTRAINT pk_payments PRIMARY KEY (id)
);

ALTER TABLE payments
    ADD CONSTRAINT FK_PAYMENTS_ON_USER FOREIGN KEY (user_id) REFERENCES user (id);