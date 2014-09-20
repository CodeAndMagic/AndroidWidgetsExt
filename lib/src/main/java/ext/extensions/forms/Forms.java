package ext.extensions.forms;

import android.view.View;
import android.widget.CompoundButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import ext.extensions.util.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by evelina on 20/09/14.
 */
public class Forms {

	public static <T, V extends View> Form<T> single(final Input<T> input, ViewPair<V> view) {
		return new SingleForm<>(input, view);
	}

	public static <T> Form<T> single(final Input<T> input, TextView view) {
		return new SingleForm<>(input, new ViewPair<>(view, TEXT_VIEW_EXTRACTOR));
	}

	public static <T> Form<T> single(final Input<T> input, CompoundButton view) {
		return new SingleForm<>(input, new ViewPair<>(view, COMPOUND_BUTTON_EXTRACTOR));
	}

	public static <T> Form<T> single(final Input<T> input, RadioGroup view) {
		return new SingleForm<>(input, new ViewPair<>(view, RADIO_GROUP_EXTRACTOR));
	}

	public static Form<Map<String, Object>> multiple(final Input<?>[] inputs, Map<String, ViewPair<?>> views) {
		return new MapForm(inputs, views);
	}

	public static <T> Form<T> mapping(Mapping<Map<String, Object>, T> mapping, Input<?>[] inputs, Map<String, ViewPair<?>> views) {
		return new ObjectForm(mapping, inputs, views);
	}

    public static <A, B> Form<Tuple2<A, B>> tuple2(final Input<A> _1, final Input<B> _2) {
        return new Form<Tuple2<A, B>>(_1, _2) {
            public Tuple2<A, B> bind(Map<String, String> data) throws ValidationException {
                return new Tuple2<>(_1.bind(data), _2.bind(data));
            }
        };
    }

    public static <A, B, C> Form<Tuple3<A, B, C>> tuple3(final Input<A> _1, final Input<B> _2, final Input<C> _3) {
        return new Form<Tuple3<A, B, C>>(_1, _2, _3) {
            public Tuple3<A, B, C> bind(Map<String, String> data) throws ValidationException {
                return new Tuple3<>(_1.bind(data), _2.bind(data), _3.bind(data));
            }
        };
    }

    public static <A, B, C, D> Form<Tuple4<A, B, C, D>> tuple4(final Input<A> _1, final Input<B> _2, final Input<C> _3, final Input<D> _4) {
        return new Form<Tuple4<A, B, C, D>>(_1, _2, _3, _4) {
            public Tuple4<A, B, C, D> bind(Map<String, String> data) throws ValidationException {
                return new Tuple4<>(_1.bind(data), _2.bind(data), _3.bind(data), _4.bind(data));
            }
        };
    }

    public static <A, B, C, D, E> Form<Tuple5<A, B, C, D, E>> tuple5(final Input<A> _1, final Input<B> _2, final Input<C> _3, final Input<D> _4, final Input<E> _5) {
        return new Form<Tuple5<A, B, C, D, E>>(_1, _2, _3, _4, _5) {
            public Tuple5<A, B, C, D, E> bind(Map<String, String> data) throws ValidationException {
                return new Tuple5<>(_1.bind(data), _2.bind(data), _3.bind(data), _4.bind(data), _5.bind(data));
            }
        };
    }

    public static <A, B, C, D, E, F> Form<Tuple6<A, B, C, D, E, F>> tuple6(final Input<A> _1, final Input<B> _2, final Input<C> _3, final Input<D> _4, final Input<E> _5, final Input<F> _6) {
        return new Form<Tuple6<A, B, C, D, E, F>>(_1, _2, _3, _4, _5, _6) {
            public Tuple6<A, B, C, D, E, F> bind(Map<String, String> data) throws ValidationException {
                return new Tuple6<>(_1.bind(data), _2.bind(data), _3.bind(data), _4.bind(data), _5.bind(data), _6.bind(data));
            }
        };
    }

    public static <A, B, C, D, E, F, G> Form<Tuple7<A, B, C, D, E, F, G>> tuple7(final Input<A> _1, final Input<B> _2, final Input<C> _3, final Input<D> _4, final Input<E> _5, final Input<F> _6, final Input<F> _7) {
        return new Form<Tuple7<A, B, C, D, E, F, G>>(_1, _2, _3, _4, _5, _6, _7) {
            public Tuple7<A, B, C, D, E, F, G> bind(Map<String, String> data) throws ValidationException {
                return new Tuple7<>(_1.bind(data), _2.bind(data), _3.bind(data), _4.bind(data), _5.bind(data), _6.bind(data), _7.bind(data));
            }
        };
    }

    public static <A, B, C, D, E, F, G, H> Form<Tuple8<A, B, C, D, E, F, G, H>> tuple8(final Input<A> _1, final Input<B> _2, final Input<C> _3, final Input<D> _4, final Input<E> _5, final Input<F> _6, final Input<F> _7, final Input<F> _8) {
        return new Form<Tuple8<A, B, C, D, E, F, G, H>>(_1, _2, _3, _4, _5, _6, _7, _8) {
            public Tuple8<A, B, C, D, E, F, G, H> bind(Map<String, String> data) throws ValidationException {
                return new Tuple8<>(_1.bind(data), _2.bind(data), _3.bind(data), _4.bind(data), _5.bind(data), _6.bind(data), _7.bind(data), _8.bind(data));
            }
        };
    }

    public static <A, B, C, D, E, F, G, H, I> Form<Tuple9<A, B, C, D, E, F, G, H, I>> tuple9(final Input<A> _1, final Input<B> _2, final Input<C> _3, final Input<D> _4, final Input<E> _5, final Input<F> _6, final Input<F> _7, final Input<F> _8, final Input<F> _9) {
        return new Form<Tuple9<A, B, C, D, E, F, G, H, I>>(_1, _2, _3, _4, _5, _6, _7, _8, _9) {
            public Tuple9<A, B, C, D, E, F, G, H, I> bind(Map<String, String> data) throws ValidationException {
                return new Tuple9<>(_1.bind(data), _2.bind(data), _3.bind(data), _4.bind(data), _5.bind(data), _6.bind(data), _7.bind(data), _8.bind(data), _9.bind(data));
            }
        };
    }

    public static <A, B, C, D, E, F, G, H, I, J> Form<Tuple10<A, B, C, D, E, F, G, H, I, J>> tuple10(final Input<A> _1, final Input<B> _2, final Input<C> _3, final Input<D> _4, final Input<E> _5, final Input<F> _6, final Input<F> _7, final Input<F> _8, final Input<F> _9, final Input<F> _10) {
        return new Form<Tuple10<A, B, C, D, E, F, G, H, I, J>>(_1, _2, _3, _4, _5, _6, _7, _8, _9, _10) {
            public Tuple10<A, B, C, D, E, F, G, H, I, J> bind(Map<String, String> data) throws ValidationException {
                return new Tuple10<>(_1.bind(data), _2.bind(data), _3.bind(data), _4.bind(data), _5.bind(data), _6.bind(data), _7.bind(data), _8.bind(data), _9.bind(data), _10.bind(data));
            }
        };
    }

	public static class FormBuilder {

		private Set<Input<?>> mInputs = new HashSet<>();
		private Map<String, ViewPair<?>> mViewMap = new HashMap<>();

		private Input<?>[] toArray() {
			return mInputs.toArray(new Input<?>[mInputs.size()]);
		}

		public synchronized FormBuilder addInput(Input<?> input) {
			mInputs.add(input);
			return this;
		}

		public synchronized <V extends View> FormBuilder addView(String key, V view, ViewExtractor<V> extractor) {
			mViewMap.put(key, new ViewPair<V>(view, extractor));
			return this;
		}

		public synchronized FormBuilder addView(String key, TextView view) {
			return addView(key, view, TEXT_VIEW_EXTRACTOR);
		}

		public synchronized FormBuilder addView(String key, RadioGroup view) {
			return addView(key, view, RADIO_GROUP_EXTRACTOR);
		}

		public synchronized FormBuilder addView(String key, CompoundButton view) {
			return addView(key, view, COMPOUND_BUTTON_EXTRACTOR);
		}

		public synchronized <V extends View> FormBuilder addViewAndInput(V view, ViewExtractor<V> extractor, Input<?> input) {
			addView(input.key, view, extractor);
			return addInput(input);
		}

		public synchronized FormBuilder addViewAndInput(TextView view, Input<?> input) {
			addInput(input);
			return addView(input.key, view, TEXT_VIEW_EXTRACTOR);
		}

		public synchronized FormBuilder addViewAndInput(RadioGroup view, Input<?> input) {
			addInput(input);
			return addView(input.key, view, RADIO_GROUP_EXTRACTOR);
		}

		public synchronized FormBuilder addViewAndInput(CompoundButton view, Input<?> input) {
			addInput(input);
			return addView(input.key, view, COMPOUND_BUTTON_EXTRACTOR);
		}

		public synchronized Form<Map<String, Object>> build() {
			return multiple(toArray(), mViewMap);
		}

		public synchronized <T> Form<T> build(Mapping<Map<String, Object>, T> mapping) {
			return mapping(mapping, toArray(), mViewMap);
		}
	}

	public static interface ViewExtractor<V extends View> {
		String get(V view);

		void set(V view, String value);
	}

	public static final ViewExtractor<TextView> TEXT_VIEW_EXTRACTOR = new ViewExtractor<TextView>() {
		@Override
		public String get(TextView view) {
			return view.getText().toString();
		}

		@Override
		public void set(TextView view, String value) {
			view.setText(value);
		}
	};

	public static final ViewExtractor<CompoundButton> COMPOUND_BUTTON_EXTRACTOR = new ViewExtractor<CompoundButton>() {
		@Override
		public String get(CompoundButton view) {
			return view.isChecked() ? "true" : "false";
		}

		@Override
		public void set(CompoundButton view, String value) {
			view.setChecked("true".equals(value));
		}
	};

	public static ViewExtractor<RadioGroup> RADIO_GROUP_EXTRACTOR = new ViewExtractor<RadioGroup>() {
		@Override
		public String get(RadioGroup view) {
			return String.valueOf(view.getCheckedRadioButtonId());
		}

		@Override
		public void set(RadioGroup view, String value) {
			view.check(Integer.parseInt(value));
		}
	};

}
