package idea.verlif.parser.html.node;

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
public class TagNodeHolder implements NodeLink {

    private static final String LINK_SPLIT = "\\.";

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
    @Override
    public TagNodeLink index(int i) {
        return i < nodes.size() ? new TagNodeLink(nodes.get(i)) : null;
    }

    @Override
    public String total() {
        return context;
    }

    /**
     * 获取名为 name 的第 i 个节点的内容
     *
     * @param name 节点名称
     * @param i    节点序号
     * @return 第 i 个节点包含的内容（包括节点名称），若超出了节点数量则返回null
     */
    @Override
    public TagNodeLink name(String name, int i) {
        int t = -1;
        for (TagNode node : nodes) {
            if (node.like(name)) {
                t++;
            }
            if (t == i) {
                return new TagNodeLink(node);
            }
        }
        return null;
    }

    @Override
    public TagNodeLink name(String name) {
        return name(name, 0);
    }

    /**
     * 查找名称为 name 的标签
     *
     * @param name 标签名称
     * @return 标签列表
     */
    public List<TagNodeLink> find(String name) {
        List<TagNodeLink> results = new ArrayList<>();
        for (TagNode node : nodes) {
            find(name, node, results);
        }
        return results;
    }

    /**
     * 使用link语法来获取节点链。<br/>
     * 例如：html.body.div[2].div.label
     *
     * @param link link语法组成的参数
     * @return 定位的节点，可能为null
     */
    public NodeLink link(String link) throws IllegalArgumentException {
        String[] names = link.split(LINK_SPLIT);
        if (names.length == 0) {
            throw new IllegalArgumentException(link + "is not allowed!");
        }
        NodeLink node = this;
        for (String name : names) {
            int start = name.indexOf('[');
            if (start < 0) {
                node = node.name(name);
            } else {
                int end = name.length() - 1;
                int index;
                try {
                    index = Integer.parseInt(name.substring(start + 1, end));
                } catch (Exception ignored) {
                    throw new IllegalArgumentException(name + " is not supported!");
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

    private void find(String name, TagNode node, List<TagNodeLink> list) {
        if (node.like(name)) {
            list.add(new TagNodeLink(node));
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
    public List<TagNodeLink> find(String name, Map<String, String> params) {
        List<TagNodeLink> results = new ArrayList<>();
        for (TagNode node : nodes) {
            find(name, params, node, results);
        }
        return results;
    }

    private void find(String name, Map<String, String> params, TagNode node, List<TagNodeLink> list) {
        if (node.match(name, params)) {
            list.add(new TagNodeLink(node));
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

    public class TagNodeLink implements NodeLink {

        private TagNode node;

        public TagNodeLink(TagNode node) {
            this.node = node;
        }

        public TagNode getNode() {
            return node;
        }

        /**
         * 获取无修改的原内容文本
         *
         * @return 原内容文本
         */
        @Override
        public String total() {
            return context.substring(node.start, node.end);
        }

        /**
         * 获取其下第 i 个子节点的内容
         *
         * @param i 节点序号
         * @return 第 i 个子节点包含的内容（包括节点名称），若超出了节点数量则返回null
         */
        @Override
        public TagNodeLink index(int i) {
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
        @Override
        public TagNodeLink name(String name, int i) {
            TagNode re = node.name(name, i);
            if (re == null) {
                return null;
            } else {
                this.node = re;
                return this;
            }
        }

        @Override
        public TagNodeLink name(String name) {
            return name(name, 0);
        }
    }
}
