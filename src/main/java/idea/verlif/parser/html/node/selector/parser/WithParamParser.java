package idea.verlif.parser.html.node.selector.parser;

import idea.verlif.parser.html.node.NodeLink;
import idea.verlif.parser.html.node.selector.NoSuchParamException;
import idea.verlif.parser.html.node.selector.SelectorParser;
import idea.verlif.parser.html.node.selector.parser.with.FirstParser;
import idea.verlif.parser.html.node.selector.parser.with.LastParser;
import idea.verlif.parser.html.node.selector.parser.with.NthChildParser;
import idea.verlif.parser.html.node.selector.parser.with.NthLastChildParser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Verlif
 * @version 1.0
 * @date 2022/3/24 9:41
 */
public class WithParamParser implements SelectorParser {

    protected static final char SPLIT = ':';

    private final List<WithParser> parserList;

    public WithParamParser() {
        parserList = new ArrayList<>();

        parserList.add(new FirstParser());
        parserList.add(new LastParser());
        parserList.add(new NthChildParser());
        parserList.add(new NthLastChildParser());
    }

    @Override
    public NodeLink match(String param, NodeLink nodeLink) {
        int i = param.indexOf(SPLIT);
        if (i > -1) {
            String tag = param.substring(0, i);
            String p = param.substring(i + 1);
            for (WithParser withParser : parserList) {
                NodeLink result = withParser.match(tag, p, nodeLink);
                if (result != null) {
                    return result;
                }
            }
            throw new NoSuchParamException(param);
        } else {
            return null;
        }
    }

    public interface WithParser {

        /**
         * 匹配selector元素
         * @param tag 标签
         * @param param 标签附属参数
         * @param nodeLink 节点
         * @return 匹配的节点；未匹配到则返回null
         */
        NodeLink match(String tag, String param, NodeLink nodeLink);
    }
}
