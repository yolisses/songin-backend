package com.musiks.backend.music;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicRepo extends Neo4jRepository<Music, Long> {
    @Query("match(u1:User)-[:LIKES]->(m1:Music)<-[:LIKES]-(u2:User)-[l:LIKES]->(m2:Music) where id(u1)=$userId return m2, count(l) order by count(l) desc")
    public List<Music> usersThatLikedTheSameLikedThese(long userId);
}
