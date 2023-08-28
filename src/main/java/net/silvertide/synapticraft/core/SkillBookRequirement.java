package net.silvertide.synapticraft.core;

public class SkillBookRequirement {
    UseRequirementEnum requirementType;
    private SkillBookRequirement(Properties properties){

    }

    public static class Properties {
        UseRequirementEnum requirementType;
        int minimumValue = 0;
        int maximumValue;

        SkillBookRequirement.Properties skillLevel(){
            this.requirementType = UseRequirementEnum.SKILL_LEVEL;
            return this;
        }
    }
}
