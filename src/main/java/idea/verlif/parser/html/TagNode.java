package idea.verlif.parser.html;

import idea.verlif.parser.html.holder.TagHolder;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Verlif
 */
public class TagNode {

    private final String name;
    private final Map<String, String> propMap;

    private final ArrayList<TagNode> children;

    public TagNode(String name, String props) {
        this.name = name.replace("/", "");
        this.children = new ArrayList<>();
        this.propMap = new HashMap<>();

        if (props != null) {
            for (String s : props.split(TagHolder.SPLIT)) {
                String pro = s.trim();
                if (pro.length() > 0) {
                    String[] prop = pro.split("=");
                    if (prop.length == 2) {
                        propMap.put(prop[0], prop[1].substring(1, prop[1].length() - 1));
                    }
                }
            }
        }
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

    public boolean like(TagNode node) {
        return this.name.equals(node.name);
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
