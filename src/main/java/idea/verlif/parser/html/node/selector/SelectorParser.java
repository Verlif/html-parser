package idea.verlif.parser.html.node.selector;

import idea.verlif.parser.html.node.NodeLink;

/**
 * @author Verlif
 * @version 1.0
 * @date 2022/3/24 9:20
 */
public interface SelectorParser {

    /**
     * selector语义匹配
     *
     * @param param    selector元素
     * @param nodeLink 当前的节点
     * @return 是否匹配；不为null则匹配成功，不进行后续匹配
     */
    NodeLink match(String param, NodeLink nodeLink);
}
