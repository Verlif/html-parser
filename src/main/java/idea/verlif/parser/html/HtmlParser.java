package idea.verlif.parser.html;

import idea.verlif.parser.html.context.OpenContext;
import idea.verlif.parser.html.holder.TagHolder;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * @author Verlif
 */
public class HtmlParser {

    private final ArrayList<TagNode> nodes;

    private final StringBuilder sb;

    public HtmlParser(String html) {
        sb = new StringBuilder();
        nodes = new ArrayList<>();

        sb.append(html);
    }

    public void parser() {
        String context = sb.toString();
        OpenContext openContext = new OpenContext(context);

        TagHolder openHolder = new TagHolder();
        openContext.build(openHolder);

        System.out.println(Arrays.toString(openHolder.getNodes().toArray()));
    }

}
