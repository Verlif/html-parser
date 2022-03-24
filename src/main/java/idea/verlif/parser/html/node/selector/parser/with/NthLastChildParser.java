package idea.verlif.parser.html.node.selector.parser.with;

import idea.verlif.parser.html.node.NodeLink;
import idea.verlif.parser.html.node.selector.NoSuchParamException;
import idea.verlif.parser.html.node.selector.parser.WithParamParser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Verlif
 * @version 1.0
 * @date 2022/3/24 9:59
 */
public class NthLastChildParser implements WithParamParser.WithParser {

    private static final String MATCH = "nth-last-child(";

    @Override
    public NodeLink match(String tag, String param, NodeLink nodeLink) {
        if (param.startsWith(MATCH)) {
            try {
                int i = Integer.parseInt(param.substring(MATCH.length(), param.length() - 1));
                List<? extends NodeLink> children = nodeLink.children();
                List<NodeLink> list = new ArrayList<>();
                for (NodeLink link : children) {
                    if (link.name().equals(tag)) {
                        list.add(0, link);
                    }
                }
                if (list.size() <= i) {
                    throw new NoSuchParamException(param);
                }
                return list.get(i - 1);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                throw new NoSuchParamException(param);
            }
        } else {
            return null;
        }
    }
}
