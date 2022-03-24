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
public class LastParser implements WithParamParser.WithParser {

    private static final String MATCH = "last";

    @Override
    public NodeLink match(String tag, String param, NodeLink nodeLink) {
        if (param.equals(MATCH)) {
            List<? extends NodeLink> list = nodeLink.children();
            NodeLink result = null;
            for (NodeLink link : list) {
                if (link.name().equals(tag)) {
                    result = link;
                }
            }
            if (result != null) {
                return result;
            } else {
                throw new NoSuchParamException(param);
            }
        } else {
            return null;
        }
    }
}
