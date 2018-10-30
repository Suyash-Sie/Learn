package com.iris.webapp.converter;

import com.iris.webapp.dto.SkillDto;
import com.iris.webapp.entity.Skill;

public class SkillConverter {
	public static Skill dtoToEntity(SkillDto SkillDto) {
		Skill Skill = new Skill(SkillDto.getSkillName(), null);
		Skill.setSkillId(SkillDto.getSkillId());
		return Skill;
	}

	public static SkillDto entityToDto(Skill skill) {
		return new SkillDto(skill.getSkillId(), skill.getSkillName());
	}
}