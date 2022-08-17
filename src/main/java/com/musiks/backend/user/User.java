package com.musiks.backend.user;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.musiks.backend.music.Music;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.GeneratedValue;
import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import javax.validation.constraints.Email;
import java.util.Set;

@Node
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue
    public long id;
    public String name;
    @Email
    public String email;
    public String image;
    @Relationship
    public Set<Music> likes;
    @Relationship
    public Set<User> follows;
    @Relationship
    public Set<Music> shares;
    @JsonIgnore
    @Relationship
    public Set<Music> listened;
    public boolean mock;
}
