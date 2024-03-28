package io.github.bty834.core;

/**
 *
 * @author: baotingyu
 * @date: 2024/3/28
 **/
public interface ChainMatcher<IN> {

    boolean matches(IN inputCtx);
}
