package play.modules.gtengineplugin.gt_integration.example;

import groovy.lang.Closure;
import play.templates.ExecutableTemplate;
import play.templates.FastTags;
import play.templates.JavaExtensions;
import play.templates.TagContext;

import java.util.Map;
import java.io.PrintWriter;

public class MyVerbatimFastTag extends FastTags {
    public static void _verbatim(Map<?, ?> args, Closure body, PrintWriter out,
                                 ExecutableTemplate template, int fromLine) {
        out.print("<verbatim>");
        out.print(JavaExtensions.toString(body));
        out.print("</verbatim>");
    }

    public static void _option(Map<?, ?> args, Closure body, PrintWriter out,
                               ExecutableTemplate template, int fromLine) {

        Object value = args.get("arg");
        Object selection = TagContext.parent("select").data.get("selected");
        boolean selected = selection != null && value != null
                && selection.equals(value);

        out.print("<option value=\"" + (value == null ? "" : value) + "\" "
                + (selected ? "selected=\"selected\"" : "")
                + "" + serialize(args, "selected", "value") + ">");
        out.println(JavaExtensions.toString(body));
        out.print("</option>");
    }
}
