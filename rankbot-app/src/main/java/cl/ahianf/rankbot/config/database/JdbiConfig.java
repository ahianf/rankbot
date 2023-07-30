/* (C)2023 - Ahian Fernández Puelles*/
package cl.ahianf.rankbot.config.database;

import javax.sql.DataSource;
import org.jdbi.v3.core.Jdbi;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.datasource.TransactionAwareDataSourceProxy;

@Configuration
public class JdbiConfig {

    @Bean("postgresJdbi")
    public Jdbi postgresJdbi(@Qualifier("postgresDataSource") DataSource ds) {
        TransactionAwareDataSourceProxy proxy = new TransactionAwareDataSourceProxy(ds);

        return Jdbi.create(proxy);
    }
}
