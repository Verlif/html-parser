package idea.verlif.parser.html;

import idea.verlif.parser.html.context.OpenContextAdapter;
import idea.verlif.parser.html.holder.TagHolder;
import idea.verlif.parser.html.node.TagNodeHolder;
import idea.verlif.parser.vars.VarsContext;

import java.io.*;
import java.nio.charset.StandardCharsets;

/**
 * @author Verlif
 */
public class HtmlParser {

    private final String context;
    private VarsContextAdapter adapter;

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

    public void setAdapter(VarsContextAdapter adapter) {
        this.adapter = adapter;
    }

    public TagNodeHolder parser() {
        if (adapter == null) {
            adapter = new OpenContextAdapter();
        }
        VarsContext varsContext = adapter.buildContext(context);
        TagHolder openHolder = adapter.buildHolder(context);

        varsContext.build(openHolder);
        return new TagNodeHolder(context, openHolder.getNodes());
    }

}
