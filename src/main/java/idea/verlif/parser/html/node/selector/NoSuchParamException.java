package idea.verlif.parser.html.node.selector;

/**
 * @author Verlif
 * @version 1.0
 * @date 2022/3/24 9:30
 */
public class NoSuchParamException extends RuntimeException {

    public NoSuchParamException(String param) {
        super("No such param like " + param);
    }
}
