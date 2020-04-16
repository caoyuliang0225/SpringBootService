
-- INSERT INTO user (id, code, nick_name, password, phone, email) VALUES (1, 'user', 'test-user', '123456', '13312344321', 'user@163.com');
-- INSERT INTO user (id, code, nick_name, password, phone, email) VALUES (2, 'admin', 'test-admin', '654321', '13409877890', 'admin@163.com');
-- INSERT INTO user (id, code, nick_name, password, phone, email) VALUES (3, 'other', 'test-other', '098765', '13809878686', 'other@163.com');

INSERT INTO role (id, name) VALUES (1, 'ROLE_USER');
INSERT INTO role (id, name) VALUES (2, 'ROLE_ADMIN');
INSERT INTO role (id, name) VALUES (3, 'ROLE_VISITOR');

-- INSERT INTO user_role (user_id, role_id) VALUES (1, 1);
-- INSERT INTO user_role (user_id, role_id) VALUES (2, 1);
-- INSERT INTO user_role (user_id, role_id) VALUES (2, 2);
-- INSERT INTO user_role (user_id, role_id) VALUES (3, 3);

INSERT INTO user (id, code, nick_name, password, phone, email) VALUES (1, 'user1', 'test-user', '$2a$10$oXAdiK588QUKADShwCEGKe5cB1DK.KgsQGAzp40mIlR5v2coUiRNm', '13312344321', 'user@163.com');
INSERT INTO user_role (user_id, role_id) VALUES (1, 1);