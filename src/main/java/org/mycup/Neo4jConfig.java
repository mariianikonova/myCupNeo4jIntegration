package org.mycup;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.kernel.EmbeddedGraphDatabase;
import org.neo4j.kernel.impl.transaction.SpringTransactionManager;
import org.neo4j.kernel.impl.transaction.UserTransactionImpl;
import org.springframework.context.annotation.AdviceMode;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.neo4j.config.EnableNeo4jRepositories;
import org.springframework.data.neo4j.config.Neo4jConfiguration;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;
import org.springframework.transaction.jta.JtaTransactionManager;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

import static org.mycup.Constants.NEO4J_DATA_LOCATION_PROPERTY;

@Configuration
@EnableNeo4jRepositories("org.mycup.datastore.dao")
@EnableTransactionManagement(mode = AdviceMode.ASPECTJ)
@PropertySource("classpath:mycup.properties")
public class Neo4jConfig extends Neo4jConfiguration {

    @Resource
    private Environment env;

    @Bean
    public GraphDatabaseService graphDatabaseService() {
        Map<String, String> params = new HashMap<>();
        params.put("enable_remote_shell", "true");
        params.put("dump_configuration", "true");
        return new EmbeddedGraphDatabase(env.getProperty(NEO4J_DATA_LOCATION_PROPERTY), params);
    }

    @Bean
    public TransactionManagementConfigurer neo4jTansactionManagementConfigurer() {
        return new TransactionManagementConfigurer() {
            @Override
            public PlatformTransactionManager annotationDrivenTransactionManager() {
                return neo4jTransactionManager();
            }
        };
    }

    @Bean
    public JtaTransactionManager neo4jTransactionManager() {
        JtaTransactionManager transactionManager = new JtaTransactionManager();
        transactionManager.setTransactionManager(new SpringTransactionManager((EmbeddedGraphDatabase)graphDatabaseService()));
        transactionManager.setUserTransaction(new UserTransactionImpl((EmbeddedGraphDatabase)graphDatabaseService()));
        return transactionManager;
    }

}