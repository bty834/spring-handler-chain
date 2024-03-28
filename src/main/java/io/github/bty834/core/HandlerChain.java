package io.github.bty834.core;

import org.springframework.core.Ordered;

import io.github.bty834.core.supports.chain.DefaultHandlerChain;
import io.github.bty834.core.supports.chain.DefaultHandlerChain.DefaultHandlerChainBuilder;

/**
 *
 * @author: baotingyu
 * @date: 2024/3/27
 **/
public interface HandlerChain<IN,OUT> extends Ordered {

    DefaultHandlerChain EMPTY_CHAIN = builder().build();
    static <I,O> DefaultHandlerChainBuilder<I,O> builder(){
        return new DefaultHandlerChainBuilder<>();
    }

    String namespace();

    void handle(IN inputCtx,OUT outputCtx);

    boolean matches(IN inputCtx);
}
