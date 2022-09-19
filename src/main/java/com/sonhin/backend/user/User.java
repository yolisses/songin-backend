package com.sonhin.backend.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sonhin.backend.music.Music;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.neo4j.ogm.annotation.Index;
import org.neo4j.ogm.annotation.Required;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import javax.validation.constraints.Email;
import java.util.List;
import java.util.Set;


@Node
@NoArgsConstructor
@AllArgsConstructor
public class User {
    public boolean mock;

    @Id
    @GeneratedValue
    public long id;

    @Required
    public String name;
    public String image;

    @Required
    @Index(unique = true)
    public String nick;

    @Email
    @JsonIgnore
    @Index(unique = true)
    public String email;

    @JsonIgnore
    @Relationship
    public List<Music> likes;

    @JsonIgnore
    @Relationship
    public Set<User> follows;

    @JsonIgnore
    @Relationship
    public Set<Music> shares;

    @JsonIgnore
    @Relationship
    public List<Music> listened;
}
