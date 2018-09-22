package org.githubissue.ext;

/**
 * Created on 9/21/2018.
 */
public interface PojoMapper<T, R> {
    T mapTo(R pR);
}