package net.gueka.user.configuration;

import com.datastax.driver.core.exceptions.NoHostAvailableException;
import com.datastax.driver.core.exceptions.TransportException;

import org.springframework.data.cassandra.config.CassandraClusterFactoryBean;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class RetryingCassandraClusterFactoryBean extends CassandraClusterFactoryBean {

    @Override
    public void afterPropertiesSet() throws Exception {
        connect();
    }
    
    private void connect() throws Exception {
        try {
            super.afterPropertiesSet();
        } catch (TransportException | IllegalArgumentException | NoHostAvailableException e) {
            log.warn(e.getMessage());
            log.warn("Retrying connection in 10 seconds");
            sleep();
            connect();
        }
    }

    private void sleep() {
        try {
            Thread.sleep(10000);
        } catch (InterruptedException ignored) {
        }
    }

}