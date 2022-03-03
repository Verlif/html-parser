package idea.verlif.parser.html.node;

import idea.verlif.parser.html.context.OpenContext;
import idea.verlif.parser.vars.VarsHandler;

/**
 * @author Verlif
 * @version 1.0
 * @date 2022/3/3 14:58
 */
public interface NodeLink {

    /**
     * 获取其下名称为 name 的第 0 个节点
     *
     * @param name 节点名称
     * @return 当没有名为 name 的节点时，返回null
     */
    NodeLink name(String name);

    /**
     * 获取其下名称为 name 的第 i 个节点
     *
     * @param i    从0开始的节点序号
     * @param name 节点名称
     * @return 当 i 超出名为 name 节点数量时返回null
     */
    NodeLink name(String name, int i);

    /**
     * 获取其下第 i 个子节点的内容
     *
     * @param i 节点序号
     * @return 第 i 个子节点包含的内容（包括节点名称），若超出了节点数量则返回null
     */
    NodeLink index(int i);

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
}
