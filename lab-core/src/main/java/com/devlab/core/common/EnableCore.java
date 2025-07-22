package com.devlab.core.common;

import com.devlab.core.LabCoreApplication;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Import(LabCoreApplication.class)
public @interface EnableCore {

}
