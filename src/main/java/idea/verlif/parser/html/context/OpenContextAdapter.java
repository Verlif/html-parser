package idea.verlif.parser.html.context;

import idea.verlif.parser.html.VarsContextAdapter;
import idea.verlif.parser.html.holder.TagHolder;
import idea.verlif.parser.vars.VarsContext;

/**
 * @author Verlif
 * @version 1.0
 * @date 2022/3/16 10:09
 */
public class OpenContextAdapter implements VarsContextAdapter {

    @Override
    public VarsContext buildContext(String context) {
        return new OpenContext(context);
    }

    @Override
    public TagHolder buildHolder(String context) {
        return new TagHolder(context);
    }
}
