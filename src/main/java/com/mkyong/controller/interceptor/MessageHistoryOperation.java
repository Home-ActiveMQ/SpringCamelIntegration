package com.mkyong.controller.interceptor;

import org.springframework.http.MediaType;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.annotation.ElementType;
import java.lang.annotation.Inherited;
import java.lang.annotation.Documented;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Inherited
@Documented
public @interface MessageHistoryOperation {

    /** Connector client (String) (example: VTS, MDES, Bank, Auth) (default: from CallingContext) */
    String connectorClient() default "";

    /**
     * Response media type
     */
    String responseMediaType() default MediaType.APPLICATION_JSON_VALUE;

    /**
     * Operator ID
     */
    String operatorID() default "";


    /**
     * Operation Type
     */
    String operationType() default "";
}
