package io.github.bty834;

import org.junit.Test;

import io.github.bty834.core.ChainManager;
import io.github.bty834.core.Handler;
import io.github.bty834.core.HandlerChain;
import io.github.bty834.core.supports.chain.DefaultHandlerChain;
import io.github.bty834.core.supports.matcher.AnyMatcher;


public class SpringChainPluginApplicationTests {

    @Test
    public void contextLoads() {

        String namespace = "default";

        DefaultHandlerChain<Object, Object> firstChain = HandlerChain.builder().namespace("推单任务")
                .namespace(namespace)
                .addHandler(new OneHandler())
                .addHandler(new TwoHandler())
                .matcher(new AnyMatcher<>())
                .order(1)
                .build();

        DefaultHandlerChain<Object, Object> secondChain = HandlerChain.builder().namespace("推单任务")
                .namespace(namespace)
                .addHandler(new OneHandler())
                .addHandler(new ThreeHandler())
                .matcher(new AnyMatcher<>())
                .order(2)
                .build();

        Object inputContext = new Object();
        Object outputContext = new Object();

        ChainManager.handle("haha", inputContext, outputContext);



    }


    static class OneHandler implements Handler<Object,Object>{
        @Override
        public boolean handle(Object inputCtx, Object outputCtx) {
            System.out.println("OneHandler");
            return true;
        }
    }
    static class TwoHandler implements Handler<Object,Object>{
        @Override
        public boolean handle(Object inputCtx, Object outputCtx) {
            System.out.println("TwoHandler");
            return true;
        }
    }

    static class ThreeHandler implements Handler<Object,Object>{
        @Override
        public boolean handle(Object inputCtx, Object outputCtx) {
            System.out.println("ThreeHandler");
            return true;
        }
    }

}
