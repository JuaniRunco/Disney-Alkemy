package com.example.disney.disney.repository;

import com.example.disney.disney.entity.CharacterEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Set;

@Repository
public interface CharacterRepository extends JpaRepository<CharacterEntity,Long> {

}
