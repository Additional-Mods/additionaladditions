package one.dqu.additionaladditions.config;

public interface ToolLikeConfig {
    float attackSpeed();
    float attackDamage();
    int durability();
    default float blockBreakSpeed() {
        return 1.5F;
    }
}
