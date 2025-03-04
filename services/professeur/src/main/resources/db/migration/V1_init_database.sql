
CREATE TABLE IF NOT EXISTS professeur (
                                          id          INTEGER PRIMARY KEY,
                                          nom         VARCHAR(255) NOT NULL,
    prenom      VARCHAR(255) NOT NULL,
    email       VARCHAR(255) UNIQUE NOT NULL,
    tel         VARCHAR(20) UNIQUE NOT NULL,
    specialite  VARCHAR(255) NOT NULL
    );

CREATE SEQUENCE IF NOT EXISTS professeur_seq INCREMENT BY 50;