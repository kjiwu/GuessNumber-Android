package kjiwu.com.guessnumber.customs;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.View;
import kjiwu.com.guessnumber.R;

/**
 * Created by kjiwu on 2016/9/13.
 */
public class NumberTextView extends View {

    private final static int DEFAULT_CURSOR_STROKE = 3;
    private final static int DEFAULT_NUMBER_SIZE = 24;
    private final static int DEFAULT_BLINK_DURATION = 1000;

    private final static int CURSOR_TRANSPARENT_COLOR = Color.TRANSPARENT;

    public NumberTextView(Context context) {
        this(context, null);
    }

    public NumberTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initParams(attrs);
    }


    private String mNumber;
    private int mNumberSize = DEFAULT_NUMBER_SIZE;
    private int mNumberColor;

    private int mCursorStroke;
    private int mCursorColor;

    private boolean mIsNeedBlink;
    private int mBlinkDuration = DEFAULT_BLINK_DURATION;

    private Bitmap mDefaultBackground;

    private Rect mTextRect;
    private int mShowCursorTimes = 0;

    public String getNumber() {
        return mNumber;
    }

    public void setNumber(String number) {
        if(!number.equals(mNumber)) {
            mNumber = number;
            getTextRect();
            invalidate();
        }
    }

    public int getCursorStroke() {
        return mCursorStroke;
    }

    public void setCursorStroke(int cursorStroke) {
        mCursorStroke = cursorStroke;
    }

    public int getCursorColor() {
        return mCursorColor;
    }

    public void setCursorColor(int cursorColor) {
        mCursorColor = cursorColor;
    }

    public boolean isNeedBlink() {
        return mIsNeedBlink;
    }

    public void setNeedBlink(boolean needBlink) {
        if(needBlink != mIsNeedBlink) {
            mIsNeedBlink = needBlink;
            mShowCursorTimes = 0;
            invalidate();
        }
    }

    public Bitmap getDefaultBackground() {
        return mDefaultBackground;
    }

    public void setDefaultBackground(Bitmap defaultBackground) {
        mDefaultBackground = defaultBackground;
    }

    public int getNumberSize() {
        return mNumberSize;
    }

    public void setNumberSize(int numberSize) {
        mNumberSize = numberSize;
        getTextRect();
    }

    private void initParams(AttributeSet attrs) {
        TypedArray ta = getContext().obtainStyledAttributes(attrs, R.styleable.NumberTextView);
        for (int i = 0; i < ta.getIndexCount(); i++) {
            int index = ta.getIndex(i);
            switch (index) {
                case R.styleable.NumberTextView_cursorColor:
                    mCursorColor = ta.getColor(index, getResources().getColor(android.R.color.black));
                    break;
                case R.styleable.NumberTextView_cursorStroke:
                    mCursorStroke = ta.getDimensionPixelSize(index, DEFAULT_CURSOR_STROKE);
                    break;
                case R.styleable.NumberTextView_defaultBackground:
                    BitmapDrawable drawable = (BitmapDrawable) ta.getDrawable(index);
                    mDefaultBackground = drawable.getBitmap();
                    break;
                case R.styleable.NumberTextView_isNeedBlink:
                    mIsNeedBlink = ta.getBoolean(index, false);
                    break;
                case R.styleable.NumberTextView_number:
                     mNumber = ta.getString(index);
                    break;
                case R.styleable.NumberTextView_numberSize:
                    mNumberSize = ta.getDimensionPixelSize(index, DEFAULT_NUMBER_SIZE);
                    break;
                case R.styleable.NumberTextView_numberColor:
                    mNumberColor = ta.getColor(index, getResources().getColor(android.R.color.black));
                    break;
                case R.styleable.NumberTextView_blinkDuration:
                    mBlinkDuration = ta.getIndex(index);
                    break;
            }
        }
        ta.recycle();

        getTextRect();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setAntiAlias(true);

        if(null != mDefaultBackground) {
            canvas.drawBitmap(mDefaultBackground, 0, 0, paint);
        }

        if(!TextUtils.isEmpty(mNumber)) {
            paint.setColor(mNumberColor);
            paint.setTextSize(getRealSize(mNumberSize));
            paint.setFakeBoldText(true);
            int text_x = getMeasuredWidth() / 2 - mTextRect.width() / 2 - mTextRect.left;
            int text_y = (getMeasuredHeight() - mCursorStroke) / 2 + mTextRect.height() / 2 - mTextRect.bottom;
            canvas.drawText(mNumber, text_x, text_y, paint);
        }

        if(mShowCursorTimes % 2 == 0) {
            paint.setColor(mCursorColor);
        }
        else {
            paint.setColor(CURSOR_TRANSPARENT_COLOR);
        }
        Rect rect = new Rect(0, getMeasuredHeight() - mCursorStroke, getMeasuredWidth(), getMeasuredHeight());
        canvas.drawRect(rect, paint);

        if(mIsNeedBlink) {
            postInvalidateDelayed(mBlinkDuration);
            mShowCursorTimes = (mShowCursorTimes + 1) % 2;
        }
    }

    private float getRealSize(int size) {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP, size, getResources().getDisplayMetrics());
    }

    private void getTextRect() {
        mTextRect = new Rect();
        if (!TextUtils.isEmpty(mNumber)) {
            Paint mPaint = new Paint();
            mPaint.setTextSize(getRealSize(mNumberSize));
            mPaint.getTextBounds(mNumber, 0, mNumber.length(), mTextRect);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(getWidth(widthMeasureSpec), getHeight(heightMeasureSpec));
    }

    private int getWidth(int widthMeasureSpec) {
        int mode = MeasureSpec.getMode(widthMeasureSpec);
        int sizeSpec = MeasureSpec.getSize(widthMeasureSpec);

        if(mode == MeasureSpec.EXACTLY) {
            return sizeSpec;
        }
        else if(mode == MeasureSpec.AT_MOST) {
            int size = mTextRect.width() + getPaddingLeft() + getPaddingRight();
            return Math.min(size, sizeSpec);
        }

        return sizeSpec;
    }

    private int getHeight(int heightMeasureSpec) {
        int mode = MeasureSpec.getMode(heightMeasureSpec);
        int sizeSpec = MeasureSpec.getSize(heightMeasureSpec);

        if(mode == MeasureSpec.EXACTLY) {
            return sizeSpec;
        }
        else if(mode == MeasureSpec.AT_MOST) {
            int size = mTextRect.height() + getPaddingTop() + getPaddingBottom() + mCursorStroke;
            return Math.min(size, sizeSpec);
        }

        return sizeSpec;
    }


    public boolean haveNumber() {
        return !TextUtils.isEmpty(mNumber);
    }
}
