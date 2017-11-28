package play.data.binding.types;

import play.data.binding.AnnotationHelper;
import play.data.binding.TypeBinder;
import play.i18n.Lang;
import play.libs.I18N;

import java.lang.annotation.Annotation;
import java.lang.reflect.Type;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import static org.apache.commons.lang.StringUtils.isBlank;

/**
 * Binder that support Calendar class.
 */
public class CalendarBinder implements TypeBinder<Calendar> {

    @Override
    public Calendar bind(String name, Annotation[] annotations, String value, Class actualClass, Type genericType) throws ParseException {
        if (isBlank(value)) {
            return null;
        }
        Calendar cal = Calendar.getInstance(Lang.getLocale());

        Date date = AnnotationHelper.getDateAs(annotations, value);
        if (date != null) {
            cal.setTime(date);
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat(I18N.getDateFormat());
            sdf.setLenient(false);
            cal.setTime(sdf.parse(value));
        }
        return cal;
    }
}
