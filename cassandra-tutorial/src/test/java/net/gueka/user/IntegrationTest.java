package net.gueka.user;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.Session;

import org.apache.cassandra.exceptions.ConfigurationException;
import org.apache.thrift.transport.TTransportException;
import org.cassandraunit.CQLDataLoader;
import org.cassandraunit.dataset.cql.ClassPathCQLDataSet;
import org.cassandraunit.utils.EmbeddedCassandraServerHelper;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.cassandra.core.CassandraAdminOperations;
import org.springframework.data.cassandra.core.cql.CqlIdentifier;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import net.gueka.user.model.Location;
import net.gueka.user.model.User;
import net.gueka.user.repository.UserRepository;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = WebEnvironment.RANDOM_PORT)
public class IntegrationTest {

	@LocalServerPort
	Integer port;
	
	@Autowired
	CassandraAdminOperations adminTemplate;
	
	@Autowired
	UserRepository repository;
	
	TestRestTemplate restTemplate = new TestRestTemplate();
	
	private static final Integer DB_PORT = 9142;
	private static final String CONTACT_POINTS = "localhost";
	private static final String KEYSPACE_CREATION_QUERY = "CREATE KEYSPACE tutorial WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 1 };";
	private static final String KEYSPACE_ACTIVATE_QUERY = "USE tutorial;";
	private static final String LOCATION_TYPE_CREATION_QUERY = "CREATE TYPE LOCATION (city TEXT, zip_code TEXT, address TEXT);";
	private static final String DATA_TABLE_NAME = "users";

	@BeforeAll
    public static void startCassandraEmbedded() throws InterruptedException, TTransportException, ConfigurationException, IOException {
		EmbeddedCassandraServerHelper.startEmbeddedCassandra();
		final Cluster cluster = Cluster.builder()
			.addContactPoints(CONTACT_POINTS)
			.withPort(DB_PORT)
			.withoutMetrics()
			.build();
		final Session session = cluster.connect();
		session.execute(KEYSPACE_CREATION_QUERY);
		session.execute(KEYSPACE_ACTIVATE_QUERY);
		session.execute(LOCATION_TYPE_CREATION_QUERY);
	}
	
    
    @Test
    public void addNewUserTest() throws Exception {
		List<User> users = repository.findAll();
		assertTrue(users.size() == 0, "No users");
       
        HttpEntity<User> entity = new HttpEntity<User>(getUser("batman","gotham"));

		ResponseEntity<User> response = restTemplate.exchange(
				"http://localhost:" + port + "/user",
				HttpMethod.POST, entity, new ParameterizedTypeReference<User>() {
				});

		assertTrue(response.getBody() != null, "Has to return the created user");
		assertEquals(entity.getBody().getName(), response.getBody().getName(), "Has to return test username");
		
		users = repository.findAll();
		assertTrue(users.size() == 1, "1 user added");
	}
	
	@Test
    public void getUserByIdTest() throws Exception {
		User user = getUser("batman","gotham");
		User saved = repository.save(user);
		assertNotNull(saved, "User should be saved");
		assertTrue(saved.getName() == user.getName());
    
		ResponseEntity<User> response = restTemplate.exchange(
				"http://localhost:" + port + "/user?id=" + user.getId(),
				HttpMethod.GET, null, new ParameterizedTypeReference<User>() {
				});

		assertTrue(response.getBody() != null, "Has to return the created user");
		assertEquals(user.getName(), response.getBody().getName(), "Has to return batman username");
	}
	
	@Test
    public void updateUserTest() throws Exception {
		User user = getUser("batman","gotham");
		User saved = repository.save(user);
		assertNotNull(saved, "User should be saved");
		assertTrue(saved.getName() == user.getName());
	
		String name = "Bruce";
		user.setName(name);
		String surname = "Wayne";
		user.setSurname(surname);
		HttpEntity<User> entity = new HttpEntity<User>(user);

		ResponseEntity<User> response = restTemplate.exchange(
				"http://localhost:" + port + "/user",
				HttpMethod.PUT, entity, new ParameterizedTypeReference<User>() {
				});

		assertTrue(response.getBody() != null, "Has to return the created user");
		assertEquals(name, response.getBody().getName(), "Has to return " + name + " name");
		assertEquals(surname, response.getBody().getSurname(), "Has to return " + surname + " surname");
	}
	
	@Test
    public void deleteUserByIdTest() throws Exception {
		User user = getUser("batman","gotham");
		User saved = repository.save(user);
		assertNotNull(saved, "User should be saved");
		assertTrue(saved.getName() == user.getName());

		ResponseEntity<User> response = restTemplate.exchange(
				"http://localhost:" + port + "/user?id=" + user.getId(),
				HttpMethod.DELETE, null, new ParameterizedTypeReference<User>() {
				});

		assertTrue(response.getStatusCode() == HttpStatus.OK, "Should return status ok");
	}

    private User getUser(String name, String city) {
		return User.builder()
			.id(UUID.randomUUID())
			.name(name)
			.location(Location.builder()
				.city(city)
				.build())
			.build();
    }

    @BeforeEach
    public void createTable() throws InterruptedException, TTransportException, ConfigurationException, IOException {
        adminTemplate.createTable(true, CqlIdentifier.of(DATA_TABLE_NAME, true), User.class, new HashMap<String, Object>());
    }

	@AfterEach
	public void dropTable() {
		adminTemplate.dropTable(CqlIdentifier.of(DATA_TABLE_NAME, true));
	}

	@AfterAll
	public static void stopCassandraEmbedded() {
		EmbeddedCassandraServerHelper.cleanEmbeddedCassandra();
	}
	
}