package idea.verlif.parser.html.holder;

import idea.verlif.parser.html.TagNode;
import idea.verlif.parser.vars.VarsHandler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Verlif
 */
public class TagHolder implements VarsHandler {

    public static final char END_TAG = '/';
    public static final char IGNORED = '!';
    public static final Set<String> SINGLE_PREFIX = new HashSet<>();
    public static final String SPLIT = " ";

    static {
        SINGLE_PREFIX.add("meta");
    }

    private final ArrayList<TagNode> openList;
    private final ArrayList<TagNode> topList;
    private TagNode nowNode;

    public TagHolder() {
        openList = new ArrayList<>();
        topList = new ArrayList<>();
    }

    @Override
    public String handle(String fullName, String tagName) {
        // 过滤单标签
        String[] split = tagName.split(SPLIT, 2);
        String tag = split[0];
        if (tagName.charAt(0) == IGNORED) {
            return fullName;
        }
        TagNode node = new TagNode(tag, split.length == 2 ? split[1] : null);
        // 判断是否是单标签
        if (tagName.charAt(tagName.length() - 1) == END_TAG || SINGLE_PREFIX.contains(tag)) {
            if (nowNode == null) {
                topList.add(node);
            } else {
                nowNode.addNode(node);
            }
            return fullName;
        }
        int openSize = openList.size();
        // 判定是否是成对的闭标签
        if (tag.charAt(0) == END_TAG) {
            // 判断是否是上一个开标签的闭标签
            if (nowNode.like(node)) {
                openList.remove(openSize - 1);
                // 当顶层标签闭合时，开始下一个顶层标签
                if (openList.size() == 0) {
                    nowNode = null;
                } else {
                    nowNode = openList.get(openList.size() - 1);
                }
            }
            return fullName;
        }
        // 开标签则新增开标签
        if (nowNode == null) {
            topList.add(node);
        } else {
            nowNode.addNode(node);
        }
        openList.add(node);
        nowNode = node;
        return fullName;
    }

    public ArrayList<TagNode> getNodes() {
        return topList;
    }
}
