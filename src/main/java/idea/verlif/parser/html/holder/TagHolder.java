package idea.verlif.parser.html.holder;

import idea.verlif.parser.html.node.TagNode;
import idea.verlif.parser.vars.VarsHandler;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Verlif
 */
public class TagHolder implements VarsHandler {

    /**
     * 标签结束符
     */
    public static final char END_TAG = '/';

    /**
     * 忽略的标签名。当出现这些标签时，标签不计入节点。
     */
    protected final Set<Character> IGNORED_PREFIX = new HashSet<>();

    /**
     * 单标签名。当出现这些标签时，直接计入节点，不做闭合处理。
     */
    protected final Set<String> SINGLE_PREFIX = new HashSet<>();

    /**
     * 最终标签，过滤掉其下的标签。出现此标签时，会忽略其下的所有子标签。
     */
    protected final Set<String> END_PREFIX = new HashSet<>();

    /**
     * 标签属性分隔符
     */
    public static final String SPLIT = " ";

    /**
     * 上下文
     */
    private final String context;

    {
        IGNORED_PREFIX.add('!');
        IGNORED_PREFIX.add('=');

        SINGLE_PREFIX.add("br");
        SINGLE_PREFIX.add("meta");
        SINGLE_PREFIX.add("link");
        SINGLE_PREFIX.add("input");
        SINGLE_PREFIX.add("hr");

        END_PREFIX.add("script");
    }

    private final ArrayList<TagNode> openList;
    private final ArrayList<TagNode> topList;
    private TagNode nowNode;

    public TagHolder(String context) {
        openList = new ArrayList<>();
        topList = new ArrayList<>();

        this.context = context;
    }

    @Override
    public String handle(int position, String fullName, String tagName) {
        String[] split = tagName.split(SPLIT, 2);
        String tag = split[0];
        // 过滤标签
        if (IGNORED_PREFIX.contains(tagName.charAt(0)) || tag.length() == 0) {
            return fullName;
        }
        TagNode node = new TagNode(tag, split.length == 2 ? split[1] : null, context);
        node.setStart(position);
        node.setEnd(position + fullName.length());
        // 判断是否是单标签（当标签最后一位为结束符或标签本身是单标签时）
        if (tagName.charAt(tagName.length() - 1) == END_TAG || SINGLE_PREFIX.contains(tag)) {
            // 同级增加标签
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
            // 判断是否是单标签的闭合标签
            if (SINGLE_PREFIX.contains(tag.substring(1))) {
                return fullName;
            }
            // 判断是否是上一个开标签的闭标签
            if (nowNode.like(node)) {
                // 设定标签的结束位置
                nowNode.setEnd(position + fullName.length());
                openList.remove(openSize - 1);
                // 当顶层标签闭合时，开始下一个顶层标签
                if (openList.size() == 0) {
                    nowNode = null;
                } else {
                    nowNode = openList.get(openList.size() - 1);
                }
            }
        } else {
            // 开标签则新增开标签
            if (nowNode == null) {
                topList.add(node);
            } else {
                // 如果上层标签是最终标签时，不添加新的子标签
                if (END_PREFIX.contains(nowNode.name())) {
                    return fullName;
                }
                nowNode.addNode(node);
            }
            openList.add(node);
            nowNode = node;
        }
        return fullName;
    }

    /**
     * 添加单标签
     *
     * @param tag 单标签。用于避免没有闭合标签导致的解析错误。添加后会忽略此标签闭合标签。
     */
    public void addSingleTag(String tag) {
        SINGLE_PREFIX.add(tag);
    }

    /**
     * 添加忽略的前缀
     *
     * @param c 跟随在标签头后的第一个字符
     */
    public void addIgnoredLink(char c) {
        IGNORED_PREFIX.add(c);
    }

    /**
     * 添加最终标签
     *
     * @param tag 最终标签。添加后，此标签中的内容将不做解析。
     */
    public void addEndTag(String tag) {
        END_PREFIX.add(tag);
    }

    public ArrayList<TagNode> getNodes() {
        return topList;
    }
}
