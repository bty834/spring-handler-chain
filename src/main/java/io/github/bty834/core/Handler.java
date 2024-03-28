package io.github.bty834.core;

/**
 *
 * @author: baotingyu
 * @date: 2024/3/28
 **/
public interface Handler<IN,OUT> {


    /**
     *
     * @param inputCtx
     * @param outputCtx
     * @return continue chain or not
     */
    boolean handle(IN inputCtx,OUT outputCtx);

}
