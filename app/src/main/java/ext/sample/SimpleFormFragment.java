package ext.sample;

import android.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.TextView;

import org.joda.time.DateTime;

import java.util.HashMap;
import java.util.Map;

import butterknife.ButterKnife;
import butterknife.OnClick;
import ext.extensions.forms.Condition;
import ext.extensions.forms.DateMultiInput;
import ext.extensions.forms.Form;
import ext.extensions.forms.FormBuilder;
import ext.extensions.forms.Mapping;
import ext.extensions.forms.StringInput;
import ext.extensions.forms.ValidationException;
import ext.extensions.forms.ViewErrorHandler;
import ext.sample.SimpleFormFragment.User.Gender;


/**
 * Created by evelina on 20/09/14.
 */
public class SimpleFormFragment extends Fragment {

	private Form<User> mForm;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.fragment_simple_form, container, false);
		ButterKnife.inject(this, view);
		return view;
	}

	@Override
	public void onViewCreated(View view, Bundle savedInstanceState) {
		super.onViewCreated(view, savedInstanceState);

		FormBuilder builder = new FormBuilder();
		builder.addViewAndInput((EditText) view.findViewById(R.id.username), new StringInput("username", 6, 32));
		builder.addView("birthday_day", (EditText) view.findViewById(R.id.birthday_day));
		builder.addView("birthday_month", (EditText) view.findViewById(R.id.birthday_month));
		builder.addView("birthday_year", (EditText) view.findViewById(R.id.birthday_year));
		builder.addInput(new DateMultiInput("birthday").verifying(new Condition<DateTime>() {
			@Override
			public boolean verify(DateTime value) {
				// at least 18
				return value.isBefore(DateTime.now().minusYears(18));
			}
		}, R.string.birthday_error));
		builder.addViewAndInput((RadioGroup) view.findViewById(R.id.gender),
			new StringInput("gender").map(new Mapping<String, Gender>() {
				@Override
				public Gender bind(String value) throws ValidationException {
					try {
						int id = Integer.parseInt(value);
						if (id == R.id.male) return Gender.MALE;
						if (id == R.id.female) return Gender.FEMALE;
						throw new ValidationException("gender", R.string.gender_error);

					} catch (NumberFormatException e) {
						throw new ValidationException("gender", R.string.gender_error);
					}
				}

				@Override
				public String unbind(Gender value) {
					return value.name();
				}
			}));
		builder.addErrorHandler(new ViewErrorHandler((TextView) view.findViewById(R.id.birthday_error)), "birthday");

		mForm = builder.build(new Mapping<Map<String, Object>, User>() {
			@Override
			public User bind(Map<String, Object> value) throws ValidationException {
				return new User((String) value.get("username"), (DateTime) value.get("birthday"), (Gender) value.get("gender"));
			}

			@Override
			public Map<String, Object> unbind(final User value) {
				return new HashMap<String, Object>() {{
					put("username", value.username);
					put("birthday", value.birthday);
					put("gender", value.gender);
				}};
			}
		});


	}

	@OnClick(R.id.submit)
	void submit() {
		try {
			User user = mForm.bind();
			Log.i("FORMS", "Validation passed: " + user);
		} catch (ValidationException e) {
			Log.i("FORMS", "Validation failed: " + e.toString(getActivity()));
		}
	}

	public static class User {

		public static enum Gender {
			MALE, FEMALE
		}


		public final String username;
		public final DateTime birthday;
		public final Gender gender;

		public User(String username, DateTime birthday, Gender gender) {
			this.username = username;
			this.birthday = birthday;
			this.gender = gender;
		}

		@Override
		public String toString() {
			return "User{" +
				"username='" + username + '\'' +
				", birthday=" + birthday +
				", gender=" + gender +
				'}';
		}
	}
}
