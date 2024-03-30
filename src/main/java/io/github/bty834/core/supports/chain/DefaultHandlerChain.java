package io.github.bty834.core.supports.chain;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import io.github.bty834.core.ChainManager;
import io.github.bty834.core.ChainMatcher;
import io.github.bty834.core.HandlerChain;
import io.github.bty834.core.Handler;
import io.github.bty834.core.supports.matcher.AnyMatcher;

/**
 *
 * @author: baotingyu
 * @date: 2024/3/28
 **/
public class DefaultHandlerChain<IN,OUT> implements HandlerChain<IN,OUT> {

    private final List<Handler<IN,OUT>> handlers;

    private final int order;

    private final ChainMatcher<IN> matcher;

    private final String namespace ;


    private DefaultHandlerChain(List<Handler<IN, OUT>> handlers, int order, ChainMatcher<IN> matcher, String namespace) {
        this.handlers = handlers;
        this.order = order;
        this.matcher = matcher;
        this.namespace = namespace;
    }


    @Override
    public void handle(IN inputCtx, OUT outputCtx) {
        if(CollectionUtils.isEmpty(handlers)){
            return;
        }
        for (Handler<IN,OUT> handler : handlers) {
            if(!handler.handle(inputCtx,outputCtx)){
                break;
            }
        }
    }

    @Override
    public boolean matches(IN inputCtx) {
        Assert.notNull(matcher,"matcher cannot be null!");
        return matcher.matches(inputCtx);
    }

    @Override
    public int getOrder() {
        return order;
    }

    @Override
    public String namespace() {
        return this.namespace;
    }

    public static class DefaultHandlerChainBuilder<IN, OUT> {
        List<Handler<IN, OUT>> handlers = new ArrayList<>();

        private int order = 0;

        private ChainMatcher<IN> matcher = new AnyMatcher<>();

        private String namespace = "default";

        public synchronized DefaultHandlerChainBuilder<IN, OUT> addHandler(Handler<IN,OUT> handler) {
            if(Objects.isNull(handler)){
                throw new IllegalArgumentException("handler cannot be null!");
            }
            this.handlers.add(handler);
            return this;
        }

        public DefaultHandlerChainBuilder<IN, OUT> order(int order) {
            this.order = order;
            return this;
        }

        public DefaultHandlerChainBuilder<IN, OUT> matcher(ChainMatcher<IN> matcher) {
            if(Objects.isNull(matcher)){
                throw new IllegalArgumentException("matcher cannot be null!");
            }
            this.matcher = matcher;
            return this;
        }

        public DefaultHandlerChainBuilder<IN, OUT> namespace(String ns){
            if(!StringUtils.hasText(ns)){
                throw new IllegalArgumentException("namespace cannot be null!");
            }
            this.namespace = ns.trim();
            return this;
        }

        public DefaultHandlerChain<IN,OUT> build(){
            DefaultHandlerChain<IN, OUT> chain =
                    new DefaultHandlerChain<>(handlers, order, matcher, namespace);
            ChainManager.register(chain);
            return chain;
        }

    }
}
