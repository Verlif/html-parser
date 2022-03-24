package idea.verlif.parser.html.node;

import idea.verlif.parser.html.node.selector.Selector;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 标签节点包
 *
 * @author Verlif
 * @version 1.0
 * @date 2022/3/2 10:49
 */
public class TagNodeHolder implements NodeLink {

    /**
     * link语法节点标识分隔符
     */
    private static final String LINK_SPLIT = ">";

    private final String context;
    private final ArrayList<TagNode> nodes;

    private final Selector selector;

    public TagNodeHolder(String context, ArrayList<TagNode> nodes) {
        this.context = context;
        this.nodes = nodes;

        this.selector = new Selector();
    }

    /**
     * 获取第 i 个节点的内容
     *
     * @param i 节点序号
     * @return 第 i 个节点包含的内容（包括节点名称），若超出了节点数量则返回null
     */
    @Override
    public NodeLink index(int i) {
        return i < nodes.size() ? nodes.get(i) : null;
    }

    @Override
    public String total() {
        return context;
    }

    @Override
    public List<? extends NodeLink> children() {
        return nodes;
    }

    @Override
    public String name() {
        return null;
    }

    @Override
    public Map<String, String> props() {
        return new HashMap<>(0);
    }

    /**
     * 查找名称为 name 的标签
     *
     * @param name 标签名称
     * @return 标签列表
     */
    public List<NodeLink> find(String name) {
        List<NodeLink> results = new ArrayList<>();
        for (TagNode node : nodes) {
            find(name, node, results);
        }
        return results;
    }

    private void find(String name, TagNode node, List<NodeLink> list) {
        if (node.like(name)) {
            list.add(node);
        }
        for (TagNode child : node.children) {
            find(name, child, list);
        }
    }

    public NodeLink id(String id) {
        Map<String, String> param = new HashMap<>(1);
        param.put("id", id);
        List<NodeLink> list = find("", param);
        return (list.size() == 0) ? null : list.get(0);
    }

    /**
     * 使用link语法来获取节点链，通过标识链来匹配唯一的节点。<br/>
     * 标识使用规则如下：
     * <ul>
     *     <li>字母开头 - 标签名。第一个标签标识使用的是 {@link #name(String)} 的参数语法，后续的使用 {@link NodeLink#name(String)} 的参数语法。
     *     <li># 开头 - 匹配参数id，无论标签名。例如 {@code #footer} 匹配 {@code &lt;abc id="footer">}</li>
     *     <li>
     *         [] 包裹的数字 - 当前节点下的第几个子节点（从0开始，参数小于0则从0开始）。<br/>
     *         例如 {@code [2]} 匹配当前节点下的第3个子节点。<br/>
     *         例如 {@code div[2]} 匹配当前节点下的第3个名为div的子节点。<br/>
     *         例如 {@code div;class=colorful[2]} 匹配当前节点下的名为div且参数class为colorful的第3个子节点。
     *     </li>
     * </ul>
     * 所有的标识间都通过 {@link #LINK_SPLIT 标识区隔} 来隔断。
     * <p>
     * 例如 {@code #footer>div>[2]>p} 会按照以下顺序获取标签：
     * <ol>
     *     <li>[id为footer的标签]</li>
     *     <li>[第一个 名为div 的标签]</li>
     *     <li>[第3个标签]</li>
     *     <li>[第一个 名为p 的标签]</li>
     * </ol>
     *
     * @param link link语法组成的参数
     * @return 定位的节点，可能为null
     */
    public NodeLink link(String link) throws IllegalArgumentException {
        String[] names = link.split(LINK_SPLIT);
        if (names.length == 0) {
            throw new IllegalArgumentException(link + " is not allowed!");
        }
        NodeLink node = this;
        for (String name : names) {
            int start = name.indexOf('[');
            if (start < 0) {
                // 非序号节点描述
                char f = name.charAt(0);
                if (f == '#') {
                    // id查找
                    node = id(name.substring(1));
                } else {
                    // 名称查找
                    node = node.name(name);
                }
            } else {
                // 节点序号描述
                int end = name.length() - 1;
                int index;
                try {
                    index = Integer.parseInt(name.substring(start + 1, end));
                } catch (Exception ignored) {
                    throw new IllegalArgumentException(name + " is not supported! Please put number in \"[]\" like \"[2].\"");
                }
                if (index < 0) {
                    index = 0;
                }
                if (start == 0) {
                    node = node.index(index);
                } else {
                    node = node.name(name.substring(0, start), index);
                }
            }
            if (node == null) {
                return null;
            }
        }
        return node;
    }

    @Override
    public List<NodeLink> find(String name, Map<String, String> params) {
        List<NodeLink> results = new ArrayList<>();
        for (TagNode node : nodes) {
            find(name, params, node, results);
        }
        return results;
    }

    private void find(String name, Map<String, String> params, TagNode node, List<NodeLink> list) {
        if (node.match(name, params)) {
            list.add(node);
        }
        for (TagNode child : node.children) {
            find(name, params, child, list);
        }
    }

    /**
     * 通过selector语法来获取节点
     *
     * @param selector selector字符串
     * @return 获取的节点
     */
    public NodeLink select(String selector) {
        return this.selector.select(selector, this);
    }

    /**
     * 获取所有的节点
     */
    public ArrayList<TagNode> allNodes() {
        return nodes;
    }

}
