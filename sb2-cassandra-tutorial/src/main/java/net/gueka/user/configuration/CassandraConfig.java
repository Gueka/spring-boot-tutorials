package net.gueka.user.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.cassandra.config.AbstractCassandraConfiguration;
import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;
import org.springframework.data.cassandra.config.CassandraEntityClassScanner;
import org.springframework.data.cassandra.config.SchemaAction;
import org.springframework.data.cassandra.core.cql.keyspace.CreateKeyspaceSpecification;
import org.springframework.data.cassandra.core.cql.keyspace.KeyspaceOption;
import org.springframework.data.cassandra.core.mapping.CassandraMappingContext;
import org.springframework.data.cassandra.core.mapping.SimpleUserTypeResolver;
import org.springframework.data.cassandra.repository.config.EnableCassandraRepositories;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Configuration
@EnableCassandraRepositories(basePackages = "net.gueka.user.repository")
public class CassandraConfig extends AbstractCassandraConfiguration {

    @Value("${cassandra.keyspace}")
    public String keyspace;

    @Value("${cassandra.contactpoints}")
    private String contactPoints;

    @Value("${cassandra.port}")
    private Integer port;

    @Value("${cassandra.metrics.enabled:false}")
    private boolean metricsEnabled;

    @Override
    protected String getKeyspaceName() {
        return keyspace;
    }

    @Bean 
    public CassandraClusterFactoryBean cluster() { 
        log.info("Creating Cassandra Cluster. contactPoints: {}, port: {}, metrics: {}", contactPoints, port, metricsEnabled); 
        CassandraClusterFactoryBean cluster = new RetryingCassandraClusterFactoryBean(); 
        cluster.setContactPoints(contactPoints);
        cluster.setPort(port); 
        cluster.setMetricsEnabled(metricsEnabled);
        cluster.setKeyspaceCreations(getKeyspaceCreations());
        return cluster; 
    }
     
    @Bean
    public CassandraMappingContext cassandraMapping() throws ClassNotFoundException {
        CassandraMappingContext mappingContext = new CassandraMappingContext();
        mappingContext.setUserTypeResolver(new SimpleUserTypeResolver(cluster().getObject(), keyspace));
        mappingContext.setInitialEntitySet(getInitialEntitySet());
        return mappingContext;
    }

    @Override
    public SchemaAction getSchemaAction() {
        return SchemaAction.CREATE_IF_NOT_EXISTS;
    }

    @Override
    protected Set<Class<?>> getInitialEntitySet() throws ClassNotFoundException {
        return CassandraEntityClassScanner.scan(getEntityBasePackages());
    }

    @Override
    protected List<CreateKeyspaceSpecification> getKeyspaceCreations() {
        final CreateKeyspaceSpecification specification = CreateKeyspaceSpecification.createKeyspace(keyspace)
                .ifNotExists().with(KeyspaceOption.DURABLE_WRITES, true).withSimpleReplication();
        List<CreateKeyspaceSpecification> list = new ArrayList<>();
        list.add(specification);
        return list;
    }

    @Override
    protected String getContactPoints() {
        return contactPoints;
    }

    @Override
    public String[] getEntityBasePackages() {
        return new String[] { "net.gueka.user.model", "net.gueka.user.repository" };
    }
}