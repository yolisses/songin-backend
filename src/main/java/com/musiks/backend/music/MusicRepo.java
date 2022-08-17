package com.musiks.backend.music;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicRepo extends Neo4jRepository<Music, Long> {

    @Query("create fulltext index musicSearch if not exists for (m:Music) on each [m.name]")
    public void addNameIndex();

    @Query("call db.index.fulltext.queryNodes('musicSearch', $text) yield node, score return node, score")
    public List<Music> fulltextSearch(String text);

    @Query("match (m:Music)<-[l:LIKES]-(u) return m, count(l) order by count(l) desc")
    public List<Music> mostLiked();

    @Query("match (u1:User)-[:FOLLOWS]->(u2:User)-[l:LIKES]->(m:Music) where id(u1)=$userId return m, count(l) order by count(l) desc")
    public List<Music> followedUsersLikeThese(long userId);

    @Query("match(u1:User)-[:LIKES]->(m1:Music)<-[:LIKES]-(u2:User)-[l:LIKES]->(m2:Music) where id(u1)=$userId return m2, count(l) order by count(l) desc")
    public List<Music> usersThatLikedAsYouLikedThese(long userId);

    @Query("match (m1:Music)<-[:LIKES]-(u:User)-[:LIKES]->(m2:Music) where id(m1) = $musicId return m2")
    public List<Music> usersThatLikedAlsoLikedThese(long musicId);
}
