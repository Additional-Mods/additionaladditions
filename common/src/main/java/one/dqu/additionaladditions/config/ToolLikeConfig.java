package one.dqu.additionaladditions.config;

public interface ToolLikeConfig {
    float attackSpeed();
    float attackDamage();
    int durability();
    default float blockBreakSpeed() {
        return 1.5F; //todo CHECK IF VALUE IS RIGHT; default speed value for swords, other tools should override this
    }
}
