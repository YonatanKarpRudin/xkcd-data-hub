create table outbox_event
(
    id             uuid primary key       not null,
    aggregate_type character varying(255) not null,
    aggregate_id   character varying(255) not null,
    payload        jsonb
);
