/* db 연결 설정을 먼저 진행한 후에 가능합니다. init_table 에서 생성한 테이블에 집어넣을 초기값들을 여기에 적어주세요오
   test가 아닌 운용단계에서 주석이 풀려있으면 데이터가 오염될 수 있습니다.
작성예시 : INSERT INTO {table}(parm1, parm2, parm3...) Values*/
/*목록판 PK(auto_increment)는 빼고 입력
users : email, name, nickname, password, phone, user_role, isDeleted, createdAt, updatedAt
products : category, name, content, price, quantity, user_id, isDeleted, createdAt, updatedAt
orders : paymentMethod, quantity, totalPrice, address, orderStatus, user_id, product_id, createdAt, updatedAt
reports : content, reportType, reporter_id, product_id, createdAt, updatedAt
review : content, score int, user_id, order_id, product_id, createdAt, updatedAt
*/
/*
INSERT INTO {table}(parm1, parm2, parm3...) Values
*/
