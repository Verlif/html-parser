package idea.verlif.parser.html;

import idea.verlif.parser.html.context.OpenContextAdapter;
import idea.verlif.parser.html.holder.TagHolder;
import idea.verlif.parser.html.node.TagNodeHolder;
import idea.verlif.parser.vars.VarsContext;

import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;

/**
 * @author Verlif
 */
public class HtmlParser {

    private VarsContextAdapter adapter;

    public void setAdapter(VarsContextAdapter adapter) {
        this.adapter = adapter;
    }

    public TagNodeHolder parser(File file) throws IOException {
        return parser(new InputStreamReader(Files.newInputStream(file.toPath()), StandardCharsets.UTF_8));
    }

    public TagNodeHolder parser(Reader reader) throws IOException {
        StringBuilder sb = new StringBuilder();

        char[] chars = new char[1024];
        int length;
        while ((length = reader.read(chars)) != -1) {
            sb.append(chars, 0, length);
        }

        return parser(sb.toString());
    }

    public TagNodeHolder parser(String context) {
        if (adapter == null) {
            adapter = new OpenContextAdapter();
        }
        VarsContext varsContext = adapter.buildContext();
        TagHolder openHolder = adapter.buildHolder(context);

        varsContext.build(context, openHolder);
        return new TagNodeHolder(context, openHolder.getNodes());
    }

}
