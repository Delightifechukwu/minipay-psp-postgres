-- ===============================
-- Seed Default Roles
-- ===============================

INSERT INTO roles (name) VALUES
                             ('ADMIN'),
                             ('MAKER'),
                             ('CHECKER'),
                             ('MERCHANT_USER')
    ON CONFLICT (name) DO NOTHING;
