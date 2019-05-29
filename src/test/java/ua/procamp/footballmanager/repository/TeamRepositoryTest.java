package ua.procamp.footballmanager.repository;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;
import ua.procamp.footballmanager.config.JpaTestConfig;
import ua.procamp.footballmanager.entity.Team;

import java.util.List;

@SpringJUnitConfig(JpaTestConfig.class)
public class TeamRepositoryTest {
    @Autowired
    private TeamRepository repository;

    @Test
    @Sql(scripts = "/sql-scripts/dataset.sql")
    void test() {
        List<Team> all = repository.findAll();
        System.out.println(all);
    }
}
