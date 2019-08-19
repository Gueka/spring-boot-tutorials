package net.gueka.user.repository;

import java.util.UUID;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import net.gueka.user.model.User;

@Repository
public interface UserRepository extends CassandraRepository<User, UUID> {

}