CREATE TABLE users (
  id UUID PRIMARY KEY,
  name TEXT,
  surname TEXT,
  creation_timestamp TIMESTAMP,
  tags list<TEXT>,
  location LOCATION
);

create type location (
    city TEXT,
    zip_code TEXT,
    address TEXT
);