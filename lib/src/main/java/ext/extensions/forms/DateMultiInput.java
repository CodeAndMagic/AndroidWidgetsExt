package ext.extensions.forms;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;

import ext.R;

/**
 * Created by evelina on 19/09/14.
 */
public class DateMultiInput extends Input<DateTime> {
	public final String dayKey;
	public final String monthKey;
	public final String yearKey;
	public final DateTime min;
	public final DateTime max;

	public DateMultiInput(String key) {
		this(key, key + "_day", key + "_month", key + "_year");
	}

	public DateMultiInput(String key, String dayKey, String monthKey, String yearKey) {
		this(key, dayKey, monthKey, yearKey, null, null);
	}

	public DateMultiInput(String key, String dayKey, String monthKey, String yearKey, DateTime min, DateTime max) {
		super(key);
		this.dayKey = dayKey;
		this.monthKey = monthKey;
		this.yearKey = yearKey;
		this.min = min;
		this.max = max;
	}

	@Override
	public DateTime bind(Map<String, String> data) throws ValidationException {
		final int day, month, year;
		try {
			day = Integer.parseInt(data.get(dayKey));
		} catch (NumberFormatException e) {
			throw new ValidationException(dayKey, R.string.form_error_int);
		}
		try {
			month = Integer.parseInt(data.get(monthKey));
		} catch (NumberFormatException e) {
			throw new ValidationException(monthKey, R.string.form_error_int);
		}
		try {
			year = Integer.parseInt(data.get(yearKey));
		} catch (NumberFormatException e) {
			throw new ValidationException(yearKey, R.string.form_error_int);
		}
		if(day < 1){
			throw new ValidationException(dayKey, R.string.form_error_min, 1);
		}
		if(month < 1){
			throw new ValidationException(monthKey, R.string.form_error_min, 1);
		}
		if(month > 12){
			throw new ValidationException(monthKey, R.string.form_error_min, 12);
		}

		DateTime cal = new DateTime(year, month, 1, 0, 0, DateTimeZone.forTimeZone(TimeZone.getDefault()));
		final int maxDay = cal.dayOfMonth().getMaximumValue();

		if(day > maxDay){
			throw new ValidationException(dayKey, R.string.form_error_max, maxDay);
		}

		final DateTime result = cal.withDayOfMonth(day);

		if(result.isBefore(min))
			throw new ValidationException(key, R.string.form_error_min, min.toString("dd/MM/yyyy"));

		if(result.isAfter(min))
			throw new ValidationException(key, R.string.form_error_max, min.toString("dd/MM/yyyy"));

		return result;
	}

	@Override
	public Map<String, String> unbind(DateTime value) {
		return super.unbind(value);
	}
}
