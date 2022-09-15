package com.sonhin.backend.group;

import com.sonhin.backend.artist.ArtistRepo;
import com.sonhin.backend.genre.GenreRepo;
import com.sonhin.backend.music.Music;
import com.sonhin.backend.music.MusicRepo;
import com.sonhin.backend.user.User;
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
public abstract class Group<Type> {
    String name;
    String type;
    @Relationship
    List<Music> items;

    public abstract void loadMusics(User user, MusicRepo musicRepo, ArtistRepo artistRepo, GenreRepo genreRepo);
}
