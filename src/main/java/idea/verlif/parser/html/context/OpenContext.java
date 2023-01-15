package idea.verlif.parser.html.context;

import idea.verlif.parser.vars.VarsContext;

/**
 * @author Verlif
 */
public class OpenContext extends VarsContext {

    private static final OpenContext INSTANCE = new OpenContext();

    private OpenContext() {
        setStart("<");
        setEnd(">");

        ignoredPrefix.add('!');
    }

    public static OpenContext getInstance() {
        return INSTANCE;
    }
}
