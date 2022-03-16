package idea.verlif.parser.html;

import idea.verlif.parser.vars.VarsContext;

/**
 * @author Verlif
 * @version 1.0
 * @date 2022/3/16 10:07
 */
public interface VarsContextAdapter {

    VarsContext buildContext(String context);
}
