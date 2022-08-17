package com.musiks.backend.group;

import com.musiks.backend.music.Music;
import com.musiks.backend.music.MusicRepo;
import com.musiks.backend.user.User;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Relationship;

import java.util.List;


@Node
@Data
@NoArgsConstructor
@AllArgsConstructor
public abstract class Group {
    String name;
    String type;
    @Relationship
    List<Music> musics;

    public abstract void loadMusics(MusicRepo musicRepo, User user);
}
