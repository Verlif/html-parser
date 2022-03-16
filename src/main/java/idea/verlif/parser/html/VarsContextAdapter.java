package idea.verlif.parser.html;

import idea.verlif.parser.html.holder.TagHolder;
import idea.verlif.parser.vars.VarsContext;

/**
 * @author Verlif
 * @version 1.0
 * @date 2022/3/16 10:07
 */
public interface VarsContextAdapter {

    /**
     * 构建变量上下文
     *
     * @param context 上下文
     */
    VarsContext buildContext(String context);

    /**
     * 构建标签管理器
     *
     * @param context 上下文
     */
    TagHolder buildHolder(String context);
}
