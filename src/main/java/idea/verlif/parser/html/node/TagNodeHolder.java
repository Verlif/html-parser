package idea.verlif.parser.html.node;

import idea.verlif.parser.html.context.OpenContext;
import idea.verlif.parser.vars.VarsHandler;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 标签节点包
 *
 * @author Verlif
 * @version 1.0
 * @date 2022/3/2 10:49
 */
public class TagNodeHolder {

    private final String context;
    private final ArrayList<TagNode> nodes;

    public TagNodeHolder(String context, ArrayList<TagNode> nodes) {
        this.context = context;
        this.nodes = nodes;
    }

    /**
     * 获取第 i 个节点的内容
     *
     * @param i 节点序号
     * @return 第 i 个节点包含的内容（包括节点名称），若超出了节点数量则返回null
     */
    public NodeLink index(int i) {
        return i < nodes.size() ? new NodeLink(nodes.get(i)) : null;
    }

    /**
     * 获取名为 name 的第 i 个节点的内容
     *
     * @param name 节点名称
     * @param i    节点序号
     * @return 第 i 个节点包含的内容（包括节点名称），若超出了节点数量则返回null
     */
    public NodeLink index(String name, int i) {
        int t = -1;
        for (TagNode node : nodes) {
            if (node.like(name)) {
                t++;
            }
            if (t == i) {
                return new NodeLink(node);
            }
        }
        return null;
    }

    public NodeLink name(String name) {
        return index(name, 0);
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
            list.add(new NodeLink(node));
        }
        for (TagNode child : node.children) {
            find(name, child, list);
        }
    }

    /**
     * 查找名称为 name 且标签参数匹配的标签
     *
     * @param name   标签名称
     * @param params 标签参数匹配
     * @return 标签列表
     */
    public List<NodeLink> find(String name, Map<String, String> params) {
        List<NodeLink> results = new ArrayList<>();
        for (TagNode node : nodes) {
            find(name, params, node, results);
        }
        return results;
    }

    private void find(String name, Map<String, String> params, TagNode node, List<NodeLink> list) {
        if (node.match(name, params)) {
            list.add(new NodeLink(node));
        }
        for (TagNode child : node.children) {
            find(name, params, child, list);
        }
    }

    /**
     * 获取所有的节点
     */
    public ArrayList<TagNode> allNodes() {
        return nodes;
    }

    public class NodeLink {

        private TagNode node;

        public NodeLink(TagNode node) {
            this.node = node;
        }

        /**
         * 获取无标签的内容
         *
         * @return 无标签内容文本
         */
        public String content() {
            return content(((i, s, s1) -> ""));
        }

        /**
         * 通过自定义变量处理器来获取内容
         *
         * @param handler 变量处理器
         * @return 由变量处理器处理后的内容文本
         */
        public String content(VarsHandler handler) {
            OpenContext varsContext = new OpenContext(total());
            return varsContext.build(handler);
        }

        /**
         * 获取无修改的原内容文本
         *
         * @return 原内容文本
         */
        public String total() {
            return context.substring(node.start, node.end);
        }

        /**
         * 获取其下第 i 个子节点的内容
         *
         * @param i 节点序号
         * @return 第 i 个子节点包含的内容（包括节点名称），若超出了节点数量则返回null
         */
        public NodeLink index(int i) {
            TagNode re = node.index(i);
            if (re == null) {
                return null;
            } else {
                this.node = re;
                return this;
            }
        }

        /**
         * 获取其下名称为 name 的第 i 个节点
         *
         * @param i 从0开始的节点序号
         * @return 当 i 超出名为 name 节点数量时返回null
         */
        public NodeLink index(String name, int i) {
            TagNode re = node.index(name, i);
            if (re == null) {
                return null;
            } else {
                this.node = re;
                return this;
            }
        }

        public NodeLink name(String name) {
            return index(name, 0);
        }
    }
}
