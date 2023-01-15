package idea.verlif.parser.html.node.selector.parser;

import idea.verlif.parser.html.node.NodeLink;
import idea.verlif.parser.html.node.selector.NoSuchParamException;
import idea.verlif.parser.html.node.selector.SelectorParser;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Verlif
 * @version 1.0
 * @date 2022/3/24 9:25
 */
public class TagParser implements SelectorParser {

    private static final Map<String, String> EMPTY_PARAM = new HashMap<>();

    @Override
    public NodeLink match(String param, NodeLink nodeLink) {
        List<NodeLink> nodeLinks = null;
        int i = param.indexOf('#');
        if (i > -1) {
            Map<String, String> map = new HashMap<>();
            map.put("id", param.substring(i + 1));
            nodeLinks = nodeLink.find(param.substring(0, i), map);
        } else {
            i = param.indexOf('.');
            if (i > -1) {
                Map<String, String> map = new HashMap<>();
                map.put("class", param.substring(i + 1));
                nodeLinks = nodeLink.find(param.substring(0, i), map);
            } else {
                nodeLinks = nodeLink.find(param, EMPTY_PARAM);
            }
        }
        if (nodeLinks == null || nodeLinks.size() == 0) {
            throw new NoSuchParamException(param);
        } else {
            return nodeLinks.get(0);
        }
    }

}
