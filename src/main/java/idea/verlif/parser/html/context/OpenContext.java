package idea.verlif.parser.html.context;

import idea.verlif.parser.vars.VarsContext;

/**
 * @author Verlif
 */
public class OpenContext extends VarsContext {

    public OpenContext(String context) {
        super(context);

        setStart("<");
        setEnd(">");
    }

}
