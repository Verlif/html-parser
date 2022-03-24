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
 * @date 2022/3/24 9:29
 */
public class IdParser implements SelectorParser {

    private static final char FIRST = '#';

    @Override
    public NodeLink match(String param, NodeLink nodeLink) {
        if (param.charAt(0) == FIRST) {
            String id = param.substring(1);
            Map<String, String> map = new HashMap<>(1);
            map.put("id", id);
            List<? extends NodeLink> list = nodeLink.find(null, map);
            if (list.size() > 0) {
                return list.get(0);
            } else {
                throw new NoSuchParamException(param);
            }
        } else {
            return null;
        }
    }
}
