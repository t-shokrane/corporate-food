-- Corporate Food API - single baseline: full schema (DDL) + initial seed data (DML)
-- Schema follows BaseDomain: id, is_deleted, version, created_on, updated_on
SET NAMES utf8mb4;
SET FOREIGN_KEY_CHECKS = 0;

-- ---------------------------------------------------------------------------
-- DDL
-- ---------------------------------------------------------------------------

DROP TABLE IF EXISTS food_order;
DROP TABLE IF EXISTS weekly_menu;
DROP TABLE IF EXISTS working_week;
DROP TABLE IF EXISTS week_day;
DROP TABLE IF EXISTS food;
DROP TABLE IF EXISTS employee;
DROP TABLE IF EXISTS company;

CREATE TABLE company (
    id                BIGINT       NOT NULL AUTO_INCREMENT,
    is_deleted        BOOLEAN      NOT NULL DEFAULT FALSE,
    version           BIGINT       NOT NULL DEFAULT 0,
    created_on        DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_on        DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    name              VARCHAR(150) NOT NULL,
    company_type      VARCHAR(50)  NULL,
    parent_company_id BIGINT       NULL,
    PRIMARY KEY (id),
    INDEX idx_company_parent (parent_company_id),
    CONSTRAINT fk_company_parent FOREIGN KEY (parent_company_id) REFERENCES company (id)
        ON DELETE SET NULL ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_persian_ci;

CREATE TABLE employee (
    id              BIGINT       NOT NULL AUTO_INCREMENT,
    is_deleted      BOOLEAN      NOT NULL DEFAULT FALSE,
    version         BIGINT       NOT NULL DEFAULT 0,
    created_on      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_on      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    username        VARCHAR(100) NOT NULL,
    password        VARCHAR(255) NOT NULL,
    first_name      VARCHAR(100) NULL,
    last_name       VARCHAR(100) NULL,
    personnel_code  VARCHAR(50)  NULL,
    role            ENUM('ADMIN','MANAGER','EMPLOYEE') NOT NULL,
    enabled         TINYINT(1)   NOT NULL DEFAULT 1,
    company_id      BIGINT       NOT NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX uq_employee_username (username),
    UNIQUE INDEX uq_employee_personnel_code (personnel_code),
    INDEX idx_employee_company (company_id),
    CONSTRAINT fk_employee_company FOREIGN KEY (company_id) REFERENCES company (id)
        ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_persian_ci;

CREATE TABLE food (
    id              BIGINT       NOT NULL AUTO_INCREMENT,
    is_deleted      BOOLEAN      NOT NULL DEFAULT FALSE,
    version         BIGINT       NOT NULL DEFAULT 0,
    created_on      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_on      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    name            VARCHAR(150) NOT NULL,
    description     VARCHAR(300) NULL,
    price           BIGINT       NOT NULL,
    enabled         TINYINT(1)   NOT NULL DEFAULT 1,
    PRIMARY KEY (id),
    INDEX idx_food_enabled (enabled)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_persian_ci;

CREATE TABLE week_day (
    id              BIGINT       NOT NULL AUTO_INCREMENT,
    is_deleted      BOOLEAN      NOT NULL DEFAULT FALSE,
    version         BIGINT       NOT NULL DEFAULT 0,
    created_on      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_on      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    week_day_number INT          NOT NULL,
    persian_name    VARCHAR(50)  NULL,
    english_name    VARCHAR(50)  NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX uq_week_day_number (week_day_number)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_persian_ci;

CREATE TABLE working_week (
    id              BIGINT       NOT NULL AUTO_INCREMENT,
    is_deleted      BOOLEAN      NOT NULL DEFAULT FALSE,
    version         BIGINT       NOT NULL DEFAULT 0,
    created_on      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_on      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    week_number     INT          NOT NULL,
    persian_year    INT          NOT NULL,
    week_name       VARCHAR(150) NULL,
    start_date      DATE         NOT NULL,
    end_date        DATE         NOT NULL,
    enabled         TINYINT(1)   NOT NULL DEFAULT 1,
    PRIMARY KEY (id),
    UNIQUE INDEX uq_week_year_number (week_number, persian_year),
    INDEX idx_week_range (start_date, end_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_persian_ci;

CREATE TABLE weekly_menu (
    id              BIGINT       NOT NULL AUTO_INCREMENT,
    is_deleted      BOOLEAN      NOT NULL DEFAULT FALSE,
    version         BIGINT       NOT NULL DEFAULT 0,
    created_on      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_on      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    week_id         BIGINT       NOT NULL,
    weekday_id      BIGINT       NOT NULL,
    food_id         BIGINT       NOT NULL,
    capacity        INT          NOT NULL,
    enabled         TINYINT(1)   NOT NULL DEFAULT 1,
    PRIMARY KEY (id),
    UNIQUE INDEX uq_menu_week_day_food (week_id, weekday_id, food_id),
    INDEX idx_menu_week_day (week_id, weekday_id),
    CONSTRAINT fk_menu_week FOREIGN KEY (week_id) REFERENCES working_week (id)
        ON DELETE CASCADE ON UPDATE RESTRICT,
    CONSTRAINT fk_menu_day FOREIGN KEY (weekday_id) REFERENCES week_day (id)
        ON DELETE CASCADE ON UPDATE RESTRICT,
    CONSTRAINT fk_menu_food FOREIGN KEY (food_id) REFERENCES food (id)
        ON DELETE CASCADE ON UPDATE RESTRICT,
    CONSTRAINT chk_capacity CHECK (capacity > 0)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_persian_ci;

CREATE TABLE food_order (
    id              BIGINT       NOT NULL AUTO_INCREMENT,
    is_deleted      BOOLEAN      NOT NULL DEFAULT FALSE,
    version         BIGINT       NOT NULL DEFAULT 0,
    created_on      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_on      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    employee_id     BIGINT       NOT NULL,
    week_id         BIGINT       NOT NULL,
    weekday_id      BIGINT       NOT NULL,
    food_id         BIGINT       NOT NULL,
    order_status    ENUM('REGISTERED','CANCELLED') NOT NULL DEFAULT 'REGISTERED',
    ordered_at      DATETIME     NOT NULL DEFAULT CURRENT_TIMESTAMP,
    cancelled_at    DATETIME     NULL,
    PRIMARY KEY (id),
    UNIQUE INDEX uq_employee_day (employee_id, week_id, weekday_id),
    INDEX idx_order_week_day (week_id, weekday_id),
    INDEX idx_order_employee (employee_id),
    CONSTRAINT fk_order_employee FOREIGN KEY (employee_id) REFERENCES employee (id)
        ON DELETE CASCADE ON UPDATE RESTRICT,
    CONSTRAINT fk_order_week FOREIGN KEY (week_id) REFERENCES working_week (id)
        ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT fk_order_day FOREIGN KEY (weekday_id) REFERENCES week_day (id)
        ON DELETE RESTRICT ON UPDATE RESTRICT,
    CONSTRAINT fk_order_food FOREIGN KEY (food_id) REFERENCES food (id)
        ON DELETE RESTRICT ON UPDATE RESTRICT
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_persian_ci;

-- ---------------------------------------------------------------------------
-- Seed data
-- ---------------------------------------------------------------------------

INSERT INTO company (id, name, company_type, parent_company_id) VALUES
    (1, 'گروه الین', 'HOLDING', NULL),
    (2, 'شرکت لمون', 'SUBSIDIARY', 1),
    (3, 'شرکت الین', 'SUBSIDIARY', 1),
    (4, 'شرکت شمس', 'SUBSIDIARY', 1);

INSERT INTO week_day (id, week_day_number, persian_name, english_name) VALUES
    (1, 1, 'شنبه', 'SATURDAY'),
    (2, 2, 'یکشنبه', 'SUNDAY'),
    (3, 3, 'دوشنبه', 'MONDAY'),
    (4, 4, 'سه‌شنبه', 'TUESDAY'),
    (5, 5, 'چهارشنبه', 'WEDNESDAY'),
    (6, 6, 'پنج‌شنبه', 'THURSDAY'),
    (7, 7, 'جمعه', 'FRIDAY');

-- BCrypt hash for password: password
INSERT INTO employee (id, username, password, first_name, last_name, personnel_code, role, enabled, company_id) VALUES
    (1, 'admin1', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'علی', 'محمدی', '1001', 'ADMIN', 1, 1),
    (2, 'manager1', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'سارا', 'احمدی', '1002', 'MANAGER', 1, 2),
    (3, 'emp1', '$2a$10$N9qo8uLOickgx2ZMRZoMyeIjZAgcfl7p92ldGxad68LJZdL17lhWy', 'رضا', 'کریمی', '1003', 'EMPLOYEE', 1, 2);

INSERT INTO food (id, name, description, price, enabled) VALUES
    (1, 'قرمه سبزی', 'غذای سنتی ایرانی با گوشت و سبزیجات', 1200000, 1),
    (2, 'قیمه', 'خورشت قیمه با سیب زمینی', 1100000, 1),
    (3, 'کوکو سبزی', 'کوکو سبزی تازه', 800000, 1),
    (4, 'شنسل مرغ', 'شنسل سوخاری مرغ', 1500000, 1),
    (5, 'جوجه کباب', 'جوجه کباب زعفرانی', 1800000, 1),
    (6, 'زرشک پلو با مرغ', 'زرشک پلو مجلسی', 2000000, 1),
    (7, 'عدس پلو', 'عدس پلو سنتی', 900000, 1);

INSERT INTO working_week (id, week_number, persian_year, week_name, start_date, end_date, enabled) VALUES
    (1, 4, 1405, 'هفته چهارم خرداد ۱۴۰۵', '2026-06-20', '2026-06-26', 1);

INSERT INTO weekly_menu (week_id, weekday_id, food_id, capacity, enabled) VALUES
    (1, 1, 1, 50, 1),
    (1, 1, 3, 40, 1),
    (1, 2, 2, 30, 1),
    (1, 2, 5, 20, 1);

INSERT INTO food_order (id, employee_id, week_id, weekday_id, food_id, order_status, ordered_at) VALUES
    (1, 1, 1, 1, 1, 'REGISTERED', CURRENT_TIMESTAMP);

-- ---------------------------------------------------------------------------
-- Triggers
-- ---------------------------------------------------------------------------

DROP TRIGGER IF EXISTS trg_before_insert_order;

CREATE TRIGGER trg_before_insert_order
    BEFORE INSERT ON food_order
    FOR EACH ROW
BEGIN
    DECLARE cnt INT;
    DECLARE cap INT;

    SELECT capacity INTO cap
    FROM weekly_menu
    WHERE week_id = NEW.week_id
      AND weekday_id = NEW.weekday_id
      AND food_id = NEW.food_id
      AND enabled = TRUE
      AND is_deleted = FALSE
    LIMIT 1;

    IF cap IS NULL THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Food not available in menu for this day';
    END IF;

    SELECT COUNT(*) INTO cnt
    FROM food_order
    WHERE week_id = NEW.week_id
      AND weekday_id = NEW.weekday_id
      AND food_id = NEW.food_id
      AND order_status = 'REGISTERED'
      AND is_deleted = FALSE;

    IF cnt >= cap THEN
        SIGNAL SQLSTATE '45000'
            SET MESSAGE_TEXT = 'Food capacity exceeded';
    END IF;
END;

SET FOREIGN_KEY_CHECKS = 1;
