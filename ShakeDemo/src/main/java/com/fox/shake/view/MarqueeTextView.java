package com.fox.shake.view;

import android.content.Context;
import android.graphics.Canvas;
import android.util.AttributeSet;
import android.widget.TextView;

import java.lang.reflect.Field;

public class MarqueeTextView extends TextView {
	private boolean flag = true;
	private float time = 1f;

	public MarqueeTextView(final Context context, final AttributeSet attrs, final int defStyle) {
		super(context, attrs, defStyle);
	}

	public MarqueeTextView(final Context context, final AttributeSet attrs) {
		super(context, attrs);
	}

	public MarqueeTextView(final Context context) {
		super(context);
	}

	@Override
	protected void onDraw(final Canvas canvas) {
		super.onDraw(canvas);

		if (flag) {
			if (Float.compare(time, 1f) != 0) {
				if (getmMarquee() != null) {
					timeSpeed(time);
					flag = false;
				}
			}
		}
	}

	private Object getmMarquee() {
		try {
			Field field = TextView.class.getDeclaredField("mMarquee");
			field.setAccessible(true);
			return field.get(this);
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (NoSuchFieldException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		return null;
	}

	private void timeSpeed(final float time) {
		float mScrollUnit = 1f;
		Class<?> clazz = null;
		for (Class<?> c : TextView.class.getDeclaredClasses()) {
			if ("Marquee".equals(c.getSimpleName())) {
				clazz = c;
				break;
			}
		}

		Object mMarquee = getmMarquee();

		if (clazz != null && mMarquee != null) {
			try {
				Field field = clazz.getDeclaredField("mScrollUnit");
				field.setAccessible(true);
				mScrollUnit = field.getFloat(mMarquee);
				mScrollUnit *= time;

				field.setFloat(mMarquee, mScrollUnit);
			} catch (SecurityException e) {
				e.printStackTrace();
			} catch (IllegalArgumentException e) {
				e.printStackTrace();
			} catch (NoSuchFieldException e) {
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				e.printStackTrace();
			}
		}
	}

	public void setTime(final float time) {
		this.time = time;
	}
}