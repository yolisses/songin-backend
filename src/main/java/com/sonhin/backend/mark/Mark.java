package com.sonhin.backend.mark;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sonhin.backend.user.User;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.Set;

@Node
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PUBLIC)
public class Mark {
    @Id
    String visitorId;

    @JsonIgnore
    @Relationship
    Set<User> identifies;
}