/* (C)2023 - Ahian Fernández Puelles*/
package cl.ahianf.rankbot.config;

import javax.sql.DataSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;

@Configuration
public class DatabaseConfig {

    @Primary
    @Bean(name = "postgresDataSource")
    @ConfigurationProperties(prefix = "spring.datasource")
    public DataSource postgresDatasource() {
        return DataSourceBuilder.create().build();
    }

    @Bean(name = "tm1")
    @Autowired
    @Primary
    DataSourceTransactionManager postgresTransactionManager(
            @Qualifier("postgresDataSource") DataSource datasource) {
        return new DataSourceTransactionManager(datasource);
    }
}
