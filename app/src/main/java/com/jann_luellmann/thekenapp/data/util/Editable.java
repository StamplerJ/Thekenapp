package com.jann_luellmann.thekenapp.data.util;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Editable {
    int stringId();
}
