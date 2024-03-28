package io.github.bty834.core;

import static io.github.bty834.core.HandlerChain.EMPTY_CHAIN;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.OrderComparator;

/**
 *
 * @author: baotingyu
 * @date: 2024/3/29
 **/
public class ChainManager {

    private static Map<String, List<HandlerChain>> namespace2Chains = new HashMap<>();


    public static void register(HandlerChain chain){
        List<HandlerChain> handlerChains = namespace2Chains.computeIfAbsent(chain.namespace(), k -> new ArrayList<>());
        handlerChains.add(chain);
        handlerChains.sort(OrderComparator.INSTANCE);
    }

    public static List<HandlerChain> getChainsByNamespace(String ns){
        return namespace2Chains.getOrDefault(ns,new ArrayList<>());
    }

    public static  <IN,OUT> void handle(String ns,IN inCtx,OUT outCtx){
        List<HandlerChain> chainsByNamespace = getChainsByNamespace(ns);
        HandlerChain handlerChain = chainsByNamespace.stream()
                .filter(c -> c.matches(inCtx))
                .findFirst()
                .orElse(EMPTY_CHAIN);
        handlerChain.handle(inCtx,outCtx);
    }

}
