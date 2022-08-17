package com.musiks.backend.search;

import com.musiks.backend.music.Music;
import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;

import java.util.List;

public interface SearchRepo extends Neo4jRepository<Music, Long> {
    @Query("call db.index.fulltext.queryNodes('search', $text) yield node, score return node, score")
    public List<Object> fulltextSearch(String text);

    @Query("call db.index.fulltext.queryNodes('search', $text) yield node, score where node:$type return node, score")
    public List<Object> fulltextSearchWithType(String text, String type);

    @Query("create fulltext index search if not exists for (o:Music|User) on each [o.name, o.nickname]")
    public void addNameIndex();
}
