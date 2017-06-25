package de.appsfactory.preferences.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import de.appsfactory.preferences.PreferenceAdapter;

/**
 * Created by Collider on 24.06.2017.
 */

@Retention(RetentionPolicy.CLASS)
@Target(ElementType.METHOD)
public @interface PreferenceOptions {
    Class<? extends PreferenceAdapter> adapter() default PreferenceAdapter.class;

    int defaultIntValue() default 0;

    float defaultFloatValue() default 0f;

    long defaultLongValue() default 0L;

    boolean defaultBooleanValue() default false;

    String defaultStringValue() default "";

    String[] defaultStringSetValue() default {};
}
