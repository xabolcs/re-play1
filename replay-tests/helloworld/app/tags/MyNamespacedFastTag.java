package tags;

import groovy.lang.Closure;
import play.templates.ExecutableTemplate;
import play.templates.FastTags;

import java.io.PrintWriter;
import java.util.Map;

@FastTags.Namespace("my.tags")
public class MyNamespacedFastTag extends FastTags {
    public static void _hello(Map<?, ?> args, Closure body, PrintWriter out,
                              ExecutableTemplate template, int fromLine) {
        out.print("Hello, " + args.get("arg").toString());
    }
}
