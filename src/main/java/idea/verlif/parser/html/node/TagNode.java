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
    private final String name;

    /**
     * 内联参数表
     */
    private final Map<String, String> propMap;

    /**
     * 上下文
     */
    private final String context;

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

    public String getName() {
        return name;
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
    public String total() {
        return context;
    }

    @Override
    public List<? extends NodeLink> children() {
        return children;
    }

    @Override
    public NodeLink name(String name) {
        return name(name, 0);
    }

    /**
     * 获取其下名称为 name 的第 i 个节点
     *
     * @param i 从0开始的节点序号
     * @return 当 i 超出名为 name 节点数量时返回null
     */
    @Override
    public TagNode name(String name, int i) {
        int t = -1;
        for (TagNode node : children) {
            if (node.like(name)) {
                t++;
            }
            if (t == i) {
                return node;
            }
        }
        return null;
    }

    /**
     * 匹配参数
     *
     * @param params 节点参数表
     * @return 是否匹配
     */
    public boolean match(Map<String, String> params) {
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
        if (this.name.equals(name)) {
            for (String key : params.keySet()) {
                if (!params.get(key).equals(this.propMap.get(key))) {
                    return false;
                }
            }
            return true;
        } else {
            return false;
        }
    }

    public boolean like(TagNode node) {
        return this.name.equals(node.name);
    }

    public boolean like(String name) {
        return this.name.equals(name);
    }

    /**
     * 获取标签节点的参数表
     */
    public Map<String, String> getPropMap() {
        return propMap;
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
