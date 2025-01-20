CREATE TABLE member (
    id BIGINT PRIMARY KEY,
    email VARCHAR(255) UNIQUE NOT NULL,
    first_name VARCHAR(255),
    last_name VARCHAR(255),
    phone_number VARCHAR(255),
    created_date TIMESTAMP,
    updated_date TIMESTAMP
);

CREATE TABLE organizer (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255),
    contact_info VARCHAR(255)
);

CREATE TABLE participant (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255),
    phone VARCHAR(255),
    registration_date TIMESTAMP,
    status VARCHAR(255)
);
CREATE TABLE venue (
    id BIGINT PRIMARY KEY,
    name VARCHAR(255),
    address VARCHAR(255),
    capacity VARCHAR(255)
);

CREATE TABLE event (
    id BIGINT PRIMARY KEY ,
    name VARCHAR(255),
    description Text,
    location VARCHAR(255),
    start_time TIMESTAMP,
    end_time Timestamp,
    created_time TIMESTAMP,
    updated_time TIMESTAMP,
    member_id BIGINT,
    organizer_id BIGINT,
    venue_id BIGINT,
    FOREIGN KEY (member_id) REFERENCES member(id),
    FOREIGN KEY (organizer_id) REFERENCES organizer(id),
    FOREIGN KEY (venue_id) REFERENCES venue(id)
);

CREATE TABLE feedback (
    id BIGINT PRIMARY KEY,
    rating INT,
    comments TEXT,
    submitted_date TIMESTAMP ,
    event_id BIGINT,
    participant_id BIGINT,
    FOREIGN KEY (event_id) REFERENCES event(id),
    FOREIGN KEY (participant_id) REFERENCES participant(id)
);

CREATE TABLE ticket (
    id BIGINT PRIMARY KEY,
    price NUMERIC,
    status VARCHAR(255),
    purchase_date TIMESTAMP,
    event_id BIGINT,
    participant_id BIGINT,
    FOREIGN KEY (event_id) REFERENCES event(id),
    FOREIGN KEY (participant_id) REFERENCES participant(id)
);

CREATE TABLE event_member(
    member_id BIGINT,
    event_id BIGINT,
    FOREIGN KEY (member_id) REFERENCES member(id),
    FOREIGN KEY (event_id) REFERENCES  event(id)
);

CREATE TABLE event_participant(
    participant_id BIGINT,
    event_id BIGINT,
    FOREIGN KEY (participant_id) REFERENCES participant(id),
    FOREIGN KEY (event_id) REFERENCES  event(id)
);

CREATE SEQUENCE event_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE feedback_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE member_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE organizer_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE participant_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE ticket_id_seq START WITH 1 INCREMENT BY 1;
CREATE SEQUENCE venue_id_seq START WITH 1 INCREMENT BY 1;



