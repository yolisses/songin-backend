package com.musiks.backend.user;

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
    public Long id;
    public String name;
    @Email
    public String email;
    public String image;
    @Relationship
    public Set<Music> likes;
    @Relationship
    public Set<Music> listened;
}
