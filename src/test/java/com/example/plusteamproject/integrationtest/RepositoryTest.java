package com.example.plusteamproject.integrationtest;

import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
//필요하다면 써주세요, 필요없다면 지워주세요
@ActiveProfiles("test")
@DataJpaTest    // MySQL 일부 function 지원 X

@Sql(scripts = "classpath:/db/init_table.sql")  // TestContainer 사용 시 테이블 생성
@Sql(scripts = "classpath:/db/data_init.sql")
public abstract class RepositoryTest {
}
