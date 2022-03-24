package idea.verlif.parser.html.node.selector.parser.with;

import idea.verlif.parser.html.node.NodeLink;
import idea.verlif.parser.html.node.selector.NoSuchParamException;
import idea.verlif.parser.html.node.selector.parser.WithParamParser;

import java.util.List;

/**
 * @author Verlif
 * @version 1.0
 * @date 2022/3/24 9:59
 */
public class NthChildParser implements WithParamParser.WithParser {

    private static final String MATCH = "nth-child(";

    @Override
    public NodeLink match(String tag, String param, NodeLink nodeLink) {
        if (param.startsWith(MATCH)) {
            try {
                int i = Integer.parseInt(param.substring(MATCH.length(), param.length() - 1));
                int t = 0;
                List<? extends NodeLink> list = nodeLink.children();
                for (NodeLink link : list) {
                    if (link.name().equals(tag)) {
                        t++;
                    }
                    if (t == i) {
                        return link;
                    }
                }
                throw new NoSuchParamException(param);
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
                throw new NoSuchParamException(param);
            }
        } else {
            return null;
        }
    }
}
