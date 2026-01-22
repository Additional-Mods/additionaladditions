package one.dqu.additionaladditions.config.io;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Used to define comments for fields in config records.
 * <p>
 * The record component name should match the field name in the codec/JSON file.
 * Component names in camelCase will automatically be matched to snake_case fields in the JSON.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.RECORD_COMPONENT)
public @interface Comment {
    String value();
}

