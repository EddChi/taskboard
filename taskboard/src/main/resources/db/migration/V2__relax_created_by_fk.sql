-- V2__relax_created_by_fk.sql
ALTER TABLE workspaces
DROP CONSTRAINT IF EXISTS workspaces_created_by_fkey;

ALTER TABLE workspaces
    ALTER COLUMN created_by DROP NOT NULL;
