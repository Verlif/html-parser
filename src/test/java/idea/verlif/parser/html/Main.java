package idea.verlif.parser.html;

import idea.verlif.parser.html.node.TagNodeHolder;
import stopwatch.Stopwatch;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * @author Verlif
 */
public class Main {

    private static final String HTML = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "    <head>\n" +
            "        <meta charset='utf-8'>\n" +
            "        <meta http-equiv='X-UA-Compatible' content='IE=edge'>\n" +
            "        <title>Page Title</title>\n" +
            "        <meta name='viewport' content='width=device-width, initial-scale=1'>\n" +
            "        <link rel='stylesheet' type='text/css' media='screen' href='main.css'>\n" +
            "        <script src='main.js'></script>\n" +
            "    </head>\n" +
            "    <body>\n" +
            "    <div class=\"first\">\n" +
            "        <div id=\"second\">这里是second的节点</div>\n" +
            "        <div class=\"second2\">这里是second2的节点</div>\n" +
            "    </div>\n" +
            "    <div class=\"third\">\n" +
            "        <label id=\"label\">你好</label>\n" +
            "    </div>\n" +
            "    </body>\n" +
            "</html>";

    public static void main(String[] args) throws IOException {
        Stopwatch stopwatch = Stopwatch.start("this");
        HtmlParser parser = new HtmlParser();
        TagNodeHolder holder = parser.parser(new File("F:\\temp\\Verlif的地下室 _ basement.html"));
        System.out.println(holder.select("body > div.wrapper > section > ul:nth-child(2) > li:nth-child(3)").content());
        stopwatch.stop();
        System.out.println(stopwatch.getIntervalLine(TimeUnit.MILLISECONDS));
    }
}
