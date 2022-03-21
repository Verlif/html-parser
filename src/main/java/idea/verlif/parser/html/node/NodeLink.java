package idea.verlif.parser.html.node;

import idea.verlif.parser.html.context.OpenContext;
import idea.verlif.parser.vars.VarsHandler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Verlif
 * @version 1.0
 * @date 2022/3/3 14:58
 */
public interface NodeLink {

    /**
     * name语法参数分隔符
     */
    String PARAMS_SPLIT = ";";

    /**
     * 参数键值分隔符
     */
    String VALUE_SPLIT = "=";

    /**
     * 通过 {@link #name(String, int)} 来获取节点，此时 i = 0
     *
     * @param name 节点名称
     * @return 匹配的第一个节点对象
     */
    default NodeLink name(String name) {
        return name(name, 0);
    }

    /**
     * 获取当前节点下的名为 {@code name} 的第 i+1 的节点。<br/>
     * 允许使用 {@code ;} 来添加参数匹配。<br/>
     * 例如 {@code div;class=colorful} 匹配 {@code &lt;div class="colorful">}。<br/>
     * 例如 {@code ;class=colorful} 会忽略标签名进行匹配 {@code &lt;abc class="colorful">}。<br/>
     *
     * @param name 节点名称
     * @param i    从0开始的节点序号
     * @return 匹配的节点对象
     */
    default NodeLink name(String name, int i) {
        String[] names = name.split(PARAMS_SPLIT, 2);
        if (names.length == 1) {
            int t = -1;
            for (NodeLink child : children()) {
                if (names[0].equals(child.name())) {
                    t++;
                }
                if (t == i) {
                    return child;
                }
            }
            return null;
        } else {
            String[] params = names[1].split(PARAMS_SPLIT);
            Map<String, String> paramMap = new HashMap<>(params.length);
            for (String param : params) {
                String[] kv = param.split(VALUE_SPLIT, 2);
                if (kv.length == 2) {
                    paramMap.put(kv[0], kv[1]);
                }
            }
            List<NodeLink> list = find(names[0], paramMap);
            return list.size() - 1 < i ? null : list.get(i);
        }
    }

    /**
     * 获取其下第 i 个子节点的内容
     *
     * @param i 节点序号
     * @return 第 i 个子节点包含的内容（包括节点名称），若超出了节点数量则返回null
     */
    NodeLink index(int i);

    /**
     * 查找名称为 name 且标签参数匹配的标签
     *
     * @param name   标签名称
     * @param params 标签参数匹配
     * @return 标签列表
     */
    List<NodeLink> find(String name, Map<String, String> params);

    /**
     * 获取无标签的内容
     *
     * @return 无标签内容文本
     */
    default String content() {
        return content(((i, s, s1) -> ""));
    }

    /**
     * 通过自定义变量处理器来获取内容
     *
     * @param handler 变量处理器
     * @return 由变量处理器处理后的内容文本
     */
    default String content(VarsHandler handler) {
        OpenContext varsContext = new OpenContext(total());
        return varsContext.build(handler);
    }

    /**
     * 获取无修改的原内容文本
     *
     * @return 原内容文本
     */
    String total();

    /**
     * 获取其下的所有子节点
     *
     * @return 子节点列表
     */
    List<? extends NodeLink> children();

    /**
     * 节点名称
     *
     * @return 节点名称
     */
    String name();

    /**
     * 节点的所有参数
     *
     * @return 节点参数表
     */
    Map<String, String> props();
}
