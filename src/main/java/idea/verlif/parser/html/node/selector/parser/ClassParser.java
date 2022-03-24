package idea.verlif.parser.html.node.selector.parser;

import idea.verlif.parser.html.node.NodeLink;
import idea.verlif.parser.html.node.selector.NoSuchParamException;
import idea.verlif.parser.html.node.selector.SelectorParser;

/**
 * 通过class获取第一个匹配的元素
 *
 * @author Verlif
 * @version 1.0
 * @date 2022/3/24 9:34
 */
public class ClassParser implements SelectorParser {

    private static final String SPLIT = "\\.";

    @Override
    public NodeLink match(String param, NodeLink nodeLink) {
        String[] ss = param.split(SPLIT, 2);
        if (ss.length == 2) {
            String tag = ss[0];
            String classname = ss[1].replaceAll(SPLIT, " ");
            for (NodeLink child : nodeLink.children()) {
                if (child.name().equals(tag) && classname.equals(child.props().get("class"))) {
                    return child;
                }
            }
            throw new NoSuchParamException(param);
        } else {
            return null;
        }
    }
}
