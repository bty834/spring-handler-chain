package io.github.bty834.core.supports.matcher;

import io.github.bty834.core.ChainMatcher;

/**
 *
 * @author: baotingyu
 * @date: 2024/3/28
 **/
public class AnyMatcher<IN> implements ChainMatcher<IN> {

    @Override
    public boolean matches(IN inputCtx) {
        return true;
    }
}
