package io.github.qishr.cascara.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/// For things where left-recursion has been eliminated
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface LeftRecursionEliminated {

}

