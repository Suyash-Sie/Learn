package com.iris.webapp.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iris.webapp.entity.Skill;

@Repository
public interface SkillRepository extends JpaRepository<Skill, Integer> {
}