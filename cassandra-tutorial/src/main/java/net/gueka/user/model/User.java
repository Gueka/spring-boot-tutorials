package net.gueka.user.model;

import java.util.List;
import java.util.UUID;

import com.datastax.driver.core.DataType.Name;

import org.springframework.data.cassandra.core.mapping.CassandraType;
import org.springframework.data.cassandra.core.mapping.PrimaryKey;
import org.springframework.data.cassandra.core.mapping.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table("users")
public class User {

    @PrimaryKey
    UUID id;
    String name;
    String surname;

    List<String> tags;

    @CassandraType(type = Name.UDT, userTypeName = "LOCATION") 
    Location location;

}