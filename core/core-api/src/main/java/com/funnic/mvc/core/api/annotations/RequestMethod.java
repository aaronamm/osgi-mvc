package com.funnic.mvc.core.api.annotations;

import com.funnic.mvc.core.api.RequestType;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author Per
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface RequestMethod {

	/**
	 * The relative name of the method. If not set then the name becomes the same as the name of the method
	 *
	 * @return The name to the method
	 */
	String name() default "";

	/**
	 * Requests that this method can handle
	 *
	 * @return
	 */
	RequestType[] types() default {RequestType.GET, RequestType.POST};
}
