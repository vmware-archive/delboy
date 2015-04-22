package io.pivotal.labs.delboy;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;

import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.persistence.*;
import java.io.Closeable;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;
import java.util.Map;
import java.util.Properties;

public class ServiceRepository {

    private EntityManagerFactory entityManagerFactory = createEntityManagerFactory();

    public EntityManagerFactory createEntityManagerFactory() {
        Properties datasourceProperties = loadProperties("/META-INF/datasource.properties");
        HikariConfig config = new HikariConfig(datasourceProperties);
        HikariDataSource dataSource = new HikariDataSource(config);

        try {
            new InitialContext().bind("DefaultDataSource", dataSource);
        } catch (NamingException e) {
            throw new PersistenceException(e);
        }
        Properties persistenceProperties = loadProperties("/META-INF/persistence.properties");
        return Persistence.createEntityManagerFactory(persistenceProperties.getProperty("javax.persistence.unit"), (Map) persistenceProperties);
    }

    private Properties loadProperties(String name) {
        Properties persistenceProperties = new Properties();
        try {
            try (InputStream in = openResource(name)) {
                persistenceProperties.load(in);
            }
        } catch (IOException e) {
            throw new PersistenceException("error loading properties file " + name, e);
        }
        return persistenceProperties;
    }

    private InputStream openResource(String name) throws FileNotFoundException {
        InputStream in = getClass().getResourceAsStream(name);
        if (in == null) throw new FileNotFoundException(name);
        return in;
    }

    public void persist(Service service) {
        try (EntitySession session = new EntitySession()) {
            session.em.persist(service);
        }
    }

    public Collection<? extends Service> findAll() {
        try (EntitySession session = new EntitySession()) {
            TypedQuery<Service> query = session.em.createQuery("select s from Service s", Service.class);
            return query.getResultList();
        }
    }

    private class EntitySession implements Closeable {
        public EntityManager em;

        private EntitySession() {
            em = entityManagerFactory.createEntityManager();
            em.getTransaction().begin();
        }

        @Override
        public void close() {
            em.getTransaction().commit();
            em.close();
        }
    }

}
