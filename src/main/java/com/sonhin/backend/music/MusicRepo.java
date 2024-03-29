package com.sonhin.backend.music;

import org.springframework.data.neo4j.repository.Neo4jRepository;
import org.springframework.data.neo4j.repository.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MusicRepo extends Neo4jRepository<Music, Long> {
    List<Music> findAllByMockTrue();

    @Query("create fulltext index musicSearch if not exists for (m:Music) on each [m.name]")
    void addNameIndex();

    @Query("call db.index.fulltext.queryNodes('musicSearch', $text) yield node, score return node, score")
    List<Music> fulltextSearch(String text);

    @Query("match (u:User)-[:LIKES]->(m:Music) where id(u)=$userId return m")
    List<Music> favorites(Long userId);

    @Query("match (m:Music)<-[l:LIKES]-(u) return m, count(l) order by count(l) desc")
    List<Music> mostLiked();

    @Query("match(u:User)-[r]-()-[]-()-[]-(m:Music) where id(u)=$userId return m, count(r) order by count(r) desc")
    List<Music> sortedByAllRelations(Long userId);

    @Query("match ()-[r]-(m:Music)-[]-(g:Genre{name:$genreName}) return m, count(r) order by count(r) desc")
    List<Music> fromGenre(String genreName);

    @Query("match (u1:User)-[:FOLLOWS]->(u2:User)-[l:LIKES]->(m:Music) where id(u1)=$userId return m, count(l) order by count(l) desc")
    List<Music> followedUsersLikeThese(Long userId);

    @Query("match(u1:User)-[:LIKES]->(m1:Music)<-[:LIKES]-(u2:User)-[l:LIKES]->(m2:Music) where id(u1)=$userId return m2, count(l) order by count(l) desc")
    List<Music> usersThatLikedAsYouLikedThese(Long userId);

    @Query("match (m1:Music)<-[:LIKES]-(u:User)-[:LIKES]->(m2:Music) where id(m1) = $musicId return m2")
    List<Music> usersThatLikedAlsoLikedThese(Long musicId);

    @Query("match (m:Music)-[:OWNER]->(a:Artist)<-[:FOLLOWS]-(u:User) where id(u) = $userId return m")
    List<Music> fromArtistsYouFollow(Long userId);
}
