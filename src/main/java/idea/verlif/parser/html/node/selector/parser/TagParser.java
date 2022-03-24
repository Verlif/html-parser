package idea.verlif.parser.html.node.selector.parser;

import idea.verlif.parser.html.node.NodeLink;
import idea.verlif.parser.html.node.selector.NoSuchParamException;
import idea.verlif.parser.html.node.selector.SelectorParser;

import java.util.List;

/**
 * @author Verlif
 * @version 1.0
 * @date 2022/3/24 9:25
 */
public class TagParser implements SelectorParser {

    @Override
    public NodeLink match(String param, NodeLink nodeLink) {
        List<? extends NodeLink> list = nodeLink.children();
        for (NodeLink link : list) {
            if (param.equals(link.name())) {
                return link;
            }
        }
        throw new NoSuchParamException(param);
    }

}
