package net.gueka.user.model;

import org.springframework.data.cassandra.core.mapping.Column;
import org.springframework.data.cassandra.core.mapping.UserDefinedType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@UserDefinedType("LOCATION")
@NoArgsConstructor
@AllArgsConstructor
public class Location {
    String city;
    @Column("zip_code")
    String zipCode;
    String address;
}