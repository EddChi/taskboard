CREATE EXTENSION IF NOT EXISTS citext;

CREATE TABLE users (
   id UUID PRIMARY KEY,
   email CITEXT UNIQUE NOT NULL,
   password_hash TEXT NOT NULL,
   full_name TEXT NOT NULL,
   created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE workspaces (
    id UUID PRIMARY KEY,
    name TEXT NOT NULL,
    created_by UUID NOT NULL REFERENCES users(id),
    created_at TIMESTAMPTZ NOT NULL DEFAULT now()
);

CREATE TABLE memberships (
     user_id UUID NOT NULL REFERENCES users(id),
     workspace_id UUID NOT NULL REFERENCES workspaces(id),
     role TEXT NOT NULL CHECK (role IN ('OWNER','ADMIN','MEMBER')),
     PRIMARY KEY (user_id, workspace_id)
);
