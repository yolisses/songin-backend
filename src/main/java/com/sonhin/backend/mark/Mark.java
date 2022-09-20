package com.sonhin.backend.mark;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sonhin.backend.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Mark {
    @Id
    String id;

    @JsonIgnore
    @Relationship
    List<User> identifies;
}