package idea.verlif.parser.html.node;

import idea.verlif.parser.html.holder.TagHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Verlif
 */
public class TagNode implements NodeLink {

    /**
     * 节点名称
     */
    protected final String name;

    /**
     * 内联参数表
     */
    protected final Map<String, String> propMap;

    /**
     * 上下文
     */
    protected final String context;

    /**
     * 子节点集合
     */
    final ArrayList<TagNode> children;

    /**
     * 节点开始时所处的位置
     */
    int start;

    /**
     * 节点结束时所处的位置
     */
    int end;

    public TagNode(String name, String props, String context) {
        this.name = name.replace("/", "");
        this.children = new ArrayList<>();
        this.propMap = new HashMap<>();
        this.context = context;

        if (props != null) {
            // 以空格为分隔符
            String[] ss = props.split(TagHolder.SPLIT);
            String lastKsy = null;
            for (String s : ss) {
                String pro = s.trim();
                if (pro.length() > 0) {
                    String[] prop = s.split("=");
                    int length = prop.length == 2 ? prop[1].length() : prop[0].length();
                    if (prop.length == 2) {
                        lastKsy = prop[0];
                        propMap.put(lastKsy, prop[1].substring(1, length));
                    } else if (lastKsy != null && length > 0) {
                        if (prop[0].charAt(length - 1) == '\"') {
                            propMap.put(lastKsy, propMap.get(lastKsy) + TagHolder.SPLIT + prop[0].substring(0, length - 1));
                        } else {
                            propMap.put(lastKsy, propMap.get(lastKsy) + TagHolder.SPLIT + prop[0]);
                        }
                    }
                }
            }
            for (String key : propMap.keySet()) {
                String value = propMap.get(key);
                if (value.charAt(value.length() - 1) == '\"') {
                    propMap.put(key, value.substring(0, value.length() - 1));
                }
            }
        }
    }

    public void setStart(int start) {
        this.start = start;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public void addNode(TagNode node) {
        children.add(node);
    }

    public TagNode getNode(int index) {
        if (index >= children.size()) {
            return null;
        }
        return children.get(index);
    }

    /**
     * 获取其下第 i 个节点
     *
     * @param i 从0开始的节点序号
     * @return 当 i 超出节点数量时返回null
     */
    @Override
    public TagNode index(int i) {
        return i < children.size() ? children.get(i) : null;
    }

    @Override
    public List<NodeLink> find(String name, Map<String, String> params) {
        List<NodeLink> results = new ArrayList<>();
        for (TagNode node : children) {
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

    @Override
    public String total() {
        return context.substring(start, end);
    }

    @Override
    public List<? extends NodeLink> children() {
        return children;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Map<String, String> props() {
        return propMap;
    }

    /**
     * 匹配参数
     *
     * @param params 节点参数表
     * @return 是否匹配
     */
    public boolean match(Map<String, String> params) {
        if (params == null) {
            return true;
        }
        for (String key : params.keySet()) {
            if (!params.get(key).equals(this.propMap.get(key))) {
                return false;
            }
        }
        return true;
    }

    /**
     * 节点名与匹配参数
     *
     * @param name   节点名称
     * @param params 节点参数表
     * @return 是否匹配
     */
    public boolean match(String name, Map<String, String> params) {
        if (name == null || name.length() == 0 || this.name.equals(name)) {
            return match(params);
        } else {
            return false;
        }
    }

    public boolean like(TagNode node) {
        return this.name.equals(node.name);
    }

    public boolean like(String name) {
        return name == null || name.length() == 0 || this.name.equals(name);
    }

    @Override
    public String toString() {
        return "{" +
                "\"name\":\"" + name + '\"' +
                ", \"children\":" + children +
                ", \"props\":\"" + propMap.keySet() +
                "\"}";
    }
}
