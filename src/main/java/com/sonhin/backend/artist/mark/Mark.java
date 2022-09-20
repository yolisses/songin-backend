package com.sonhin.backend.artist.mark;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sonhin.backend.user.User;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;

@Node
@NoArgsConstructor
@AllArgsConstructor
public class Mark {
    @Id
    String id;

    @JsonIgnore
    @Relationship
    public List<User> identifies;
}