package idea.verlif.parser.html.node.selector;

import idea.verlif.parser.html.node.NodeLink;
import idea.verlif.parser.html.node.selector.parser.ClassParser;
import idea.verlif.parser.html.node.selector.parser.IdParser;
import idea.verlif.parser.html.node.selector.parser.TagParser;
import idea.verlif.parser.html.node.selector.parser.WithParamParser;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Verlif
 * @version 1.0
 * @date 2022/3/24 10:15
 */
public class Selector {

    private static final String SPLIT = ">";

    private final List<SelectorParser> parserList;
    private final WithParamParser withParamParser;

    public Selector() {
        parserList = new ArrayList<>();
        withParamParser = new WithParamParser();
        parserList.add(withParamParser);

        parserList.add(new IdParser());
        parserList.add(new ClassParser());
        parserList.add(new TagParser());
    }

    public void addSelectorParser(SelectorParser parser) {
        parserList.add(parser);
    }

    public void addWithParser(WithParamParser.WithParser parser) {
        withParamParser.addWithParser(parser);
    }

    public NodeLink select(String selector, NodeLink root) {
        String[] ss = selector.split(SPLIT);
        NodeLink nodeLink = root;
        for (String s : ss) {
            for (SelectorParser parser : parserList) {
                NodeLink next = parser.match(s.trim(), nodeLink);
                if (next != null) {
                    nodeLink = next;
                    break;
                }
            }
        }
        return nodeLink;
    }
}
