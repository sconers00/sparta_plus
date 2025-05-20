package com.example.plusteamproject.integrationtest;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
//필요하면 써주세요, 필요없다면 지워주세요
@ActiveProfiles("test")
@SpringBootTest
@Sql(scripts = "classpath:/db/init_table.sql")  // TestContainer 사용 시 테이블 생성
@Sql(scripts = "classpath:/db/data_init.sql")
public abstract class IntergrationTest {
}
