package idea.verlif.parser.html.node.selector.parser.with;

import idea.verlif.parser.html.node.NodeLink;
import idea.verlif.parser.html.node.selector.NoSuchParamException;
import idea.verlif.parser.html.node.selector.parser.WithParamParser;

import java.util.List;

/**
 * @author Verlif
 * @version 1.0
 * @date 2022/3/24 9:37
 */
public class FirstParser implements WithParamParser.WithParser {

    private static final String MATCH = "first";

    @Override
    public NodeLink match(String tag, String param, NodeLink nodeLink) {
        if (param.equals(MATCH)) {
            List<? extends NodeLink> list = nodeLink.children();
            for (NodeLink link : list) {
                if (link.name().equals(tag)) {
                    return link;
                }
            }
            throw new NoSuchParamException(param);
        } else {
            return null;
        }
    }
}
