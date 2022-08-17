package com.musiks.backend.artist;


import org.springframework.data.neo4j.repository.Neo4jRepository;

public interface ArtistRepo extends Neo4jRepository<Artist, Long> {

}
