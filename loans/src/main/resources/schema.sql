CREATE TABLE IF NOT EXISTS loans (
    loan_id BIGINT NOT NULL AUTO_INCREMENT,
    mobile_number VARCHAR(15) NOT NULL,
    loan_number VARCHAR(100) NOT NULL,
    loan_type VARCHAR(100) NOT NULL,
    total_loan DECIMAL(15,2) NOT NULL,
    amount_paid DECIMAL(15,2) NOT NULL,
    outstanding_amount DECIMAL(15,2) NOT NULL,
    created_at DATE NOT NULL,
    created_by VARCHAR(20) NOT NULL,
    updated_at DATE DEFAULT NULL,
    updated_by VARCHAR(20) DEFAULT NULL,
    PRIMARY KEY (loan_id)
);