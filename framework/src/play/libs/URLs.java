package play.libs;

import play.exceptions.UnexpectedException;

import java.net.URLEncoder;

public class URLs {
    public static String encodePart(String part) {
        try {
            return URLEncoder.encode(part, "utf-8");
        } catch (Exception e) {
            throw new UnexpectedException(e);
        }
    }
}
