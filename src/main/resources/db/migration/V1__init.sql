
-- V1__init.sql - initial schema for minipay

CREATE TABLE roles (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(50) UNIQUE NOT NULL
);

CREATE TABLE users (
    id BIGSERIAL PRIMARY KEY,
    username VARCHAR(100) UNIQUE NOT NULL,
    password_hash VARCHAR(255) NOT NULL,
    email VARCHAR(150) UNIQUE,
    status VARCHAR(20) NOT NULL,
    created_at TIMESTAMP WITH TIME ZONE DEFAULT now()
);

CREATE TABLE user_roles (
    user_id BIGINT NOT NULL,
    role_id BIGINT NOT NULL,
    PRIMARY KEY(user_id, role_id),
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (role_id) REFERENCES roles(id)
);

CREATE TABLE merchants (
    id BIGSERIAL PRIMARY KEY,
    merchant_id VARCHAR(100) UNIQUE NOT NULL,
    name VARCHAR(255) NOT NULL,
    email VARCHAR(150),
    status VARCHAR(20) DEFAULT 'ACTIVE',
    settlement_account VARCHAR(100),
    settlement_bank VARCHAR(150),
    callback_url TEXT,
    webhook_secret VARCHAR(255),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT now(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT now()
);

CREATE TABLE charge_settings (
    id BIGSERIAL PRIMARY KEY,
    merchant_id BIGINT NOT NULL,
    percentage_fee NUMERIC(10,4),
    fixed_fee NUMERIC(19,2),
    use_fixed_msc BOOLEAN DEFAULT false,
    msc_cap NUMERIC(19,2),
    vat_rate NUMERIC(5,4),
    platform_provider_rate NUMERIC(10,4),
    platform_provider_cap NUMERIC(19,2),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT now(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT now(),
    FOREIGN KEY (merchant_id) REFERENCES merchants(id)
);

CREATE TABLE payments (
    id BIGSERIAL PRIMARY KEY,
    payment_ref UUID UNIQUE NOT NULL,
    merchant_id BIGINT NOT NULL,
    order_id VARCHAR(100),
    amount NUMERIC(19,2) NOT NULL,
    currency VARCHAR(10) DEFAULT 'NGN',
    channel VARCHAR(50),
    status VARCHAR(20) DEFAULT 'PENDING',
    msc NUMERIC(19,2),
    vat_amount NUMERIC(19,4),
    processor_fee NUMERIC(19,2),
    processor_vat NUMERIC(19,4),
    payable_vat NUMERIC(19,4),
    amount_payable NUMERIC(19,4),
    customer_id VARCHAR(100),
    created_at TIMESTAMP WITH TIME ZONE DEFAULT now(),
    updated_at TIMESTAMP WITH TIME ZONE DEFAULT now(),
    FOREIGN KEY (merchant_id) REFERENCES merchants(id)
);

CREATE TABLE settlement_batches (
    id BIGSERIAL PRIMARY KEY,
    settlement_ref UUID UNIQUE NOT NULL,
    merchant_id BIGINT NOT NULL,
    period_start TIMESTAMP WITH TIME ZONE,
    period_end TIMESTAMP WITH TIME ZONE,
    count INTEGER,
    transaction_amount NUMERIC(19,2),
    msc NUMERIC(19,2),
    vat_amount NUMERIC(19,4),
    processor_fee NUMERIC(19,2),
    processor_vat NUMERIC(19,4),
    income NUMERIC(19,2),
    payable_vat NUMERIC(19,4),
    amount_payable NUMERIC(19,4),
    status VARCHAR(20) DEFAULT 'PENDING',
    created_at TIMESTAMP WITH TIME ZONE DEFAULT now(),
    FOREIGN KEY (merchant_id) REFERENCES merchants(id)
);

CREATE TABLE settlement_items (
    id BIGSERIAL PRIMARY KEY,
    batch_id BIGINT NOT NULL,
    payment_id BIGINT NOT NULL,
    amount NUMERIC(19,2),
    msc NUMERIC(19,2),
    vat_amount NUMERIC(19,4),
    processor_fee NUMERIC(19,2),
    processor_vat NUMERIC(19,4),
    amount_payable NUMERIC(19,4),
    FOREIGN KEY (batch_id) REFERENCES settlement_batches(id),
    FOREIGN KEY (payment_id) REFERENCES payments(id)
);

-- Indexes for heavy queries
CREATE INDEX idx_payments_merchant_created ON payments(merchant_id, created_at);
CREATE INDEX idx_settlements_merchant_created ON settlement_batches(merchant_id, created_at);
