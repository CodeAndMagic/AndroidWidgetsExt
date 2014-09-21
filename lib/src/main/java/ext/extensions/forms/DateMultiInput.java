package ext.extensions.forms;

import android.util.Log;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import ext.R;

import static android.text.TextUtils.isEmpty;

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
		List<ValidationFailure> failures = new ArrayList<>();

		int day = -1, month = -1, year = -1;
		try {
			day = Integer.parseInt(data.get(dayKey));
			if (day < 1) {
				failures.add(new ValidationFailure(dayKey, R.string.form_error_min, 1));
			}
		} catch (NumberFormatException e) {
			failures.add(new ValidationFailure(dayKey, R.string.form_error_int));
		}
		try {
			month = Integer.parseInt(data.get(monthKey));
			if (month < 1) {
				failures.add(new ValidationFailure(monthKey, R.string.form_error_min, 1));
			}
			if (month > 12) {
				failures.add(new ValidationFailure(monthKey, R.string.form_error_min, 12));
			}
		} catch (NumberFormatException e) {
			failures.add(new ValidationFailure(monthKey, R.string.form_error_int));
		}
		try {
			year = Integer.parseInt(data.get(yearKey));
		} catch (NumberFormatException e) {
			failures.add(new ValidationFailure(yearKey, R.string.form_error_int));
		}

		if (failures.size() > 0) {
			throw new ValidationException(failures);
		}

		DateTime cal = new DateTime(year, month, 1, 0, 0, DateTimeZone.forTimeZone(TimeZone.getDefault()));
		final int maxDay = cal.dayOfMonth().getMaximumValue();

		if (day > maxDay) {
			throw new ValidationException(dayKey, R.string.form_error_max, maxDay);
		}

		final DateTime result = cal.withDayOfMonth(day);

		if (min != null && result.isBefore(min))
			throw new ValidationException(key, R.string.form_error_min, min.toString("dd/MM/yyyy"));

		if (max != null && result.isAfter(max))
			throw new ValidationException(key, R.string.form_error_max, max.toString("dd/MM/yyyy"));

		return result;
	}

	@Override
	public Map<String, String> unbind(DateTime value) {
		return super.unbind(value);
	}

	@Override
	protected void onDataChanged(String key, String oldValue, String newValue, Map<String, String> partialData) throws ValidationException {
		Log.d("form", "DateMultiInput: " + key + " changed to " + newValue);
		if(key.equals(dayKey) || key.equals(monthKey) || key.equals(yearKey)){
			String day = partialData.get(dayKey);
			String month = partialData.get(monthKey);
			String year = partialData.get(yearKey);

			if(!isEmpty(day) && !isEmpty(month) && !isEmpty(year)){
				// FIXME the bind should be done on the Input resulting from verifying() or map()
				bind(partialData);
			}
		}
	}
}
