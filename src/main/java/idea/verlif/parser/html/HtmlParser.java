package idea.verlif.parser.html;

import idea.verlif.parser.html.context.OpenContext;
import idea.verlif.parser.html.holder.TagHolder;
import idea.verlif.parser.html.node.TagNodeHolder;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author Verlif
 */
public class HtmlParser {

    private final String context;

    public HtmlParser(String html) {
        this.context = html;
    }

    public HtmlParser(File file) throws IOException {
        this(new InputStreamReader(new FileInputStream(file), StandardCharsets.UTF_8));
    }

    public HtmlParser(Reader reader) throws IOException {
        StringBuilder sb = new StringBuilder();

        char[] chars = new char[1024];
        int length;
        while ((length = reader.read(chars)) != -1) {
            sb.append(chars, 0, length);
        }

        this.context = sb.toString();
    }

    public TagNodeHolder parser() {
        OpenContext openContext = new OpenContext(context);

        TagHolder openHolder = new TagHolder();
        openContext.build(openHolder);

        return new TagNodeHolder(context, openHolder.getNodes());
    }

}
