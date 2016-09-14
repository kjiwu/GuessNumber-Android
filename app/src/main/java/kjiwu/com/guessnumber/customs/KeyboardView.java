package kjiwu.com.guessnumber.customs;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TableLayout;

import java.util.ArrayList;

import kjiwu.com.guessnumber.R;

/**
 * Created by wulei on 2016/9/13.
 */
public class KeyboardView extends TableLayout implements View.OnClickListener {

    private final static int DEFAULT_KEY_PRESSED_COUNT = 4;

    private TableLayout mTableLayout;

    private ArrayList<Button> mKeys;

    @Override
    public void onClick(View v) {
        if(mCurrentPressedCount == mMaxPressKeyCount) {
            return;
        }

        if(null != mKeyboardPressedListener) {
            Button button = (Button) v;
            String number = button.getText().toString();
            button.setEnabled(false);

            mKeyboardPressedListener.onPressed(number);
            mCurrentPressedCount++;
        }
    }

    private int mMaxPressKeyCount = DEFAULT_KEY_PRESSED_COUNT;

    public void setMaxPressKeyCount(int maxCount) {
        mMaxPressKeyCount = maxCount;
    }

    private int mCurrentPressedCount = 0;

    public interface OnKeyboardPressedListener {
        public void onPressed(String number);
    }

    private OnKeyboardPressedListener mKeyboardPressedListener;

    public void setKeyboardPressedListener(OnKeyboardPressedListener listener) {
        mKeyboardPressedListener = listener;
    }

    public KeyboardView(Context context) {
        this(context, null);
    }

    public KeyboardView(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.view_keyboard, this);
        mTableLayout = (TableLayout) findViewById(R.id.keyboard_root);

        mKeys = new ArrayList<>();
        getButtonChild(mTableLayout);
    }


    private void getButtonChild(ViewGroup root) {
        int count = root.getChildCount();
        for (int i = 0; i < count; i++) {
            View view = root.getChildAt(i);
            if (view instanceof ViewGroup) {
                getButtonChild((ViewGroup) view);
            } else if(view instanceof Button) {
                mKeys.add((Button)view);
                ((Button)view).setOnClickListener(this);
            }
        }
    }

    public void clearKeyState(String number) {
        for(Button button : mKeys) {
            if(button.getText().toString().equals(number)) {
                button.setEnabled(true);
                mCurrentPressedCount--;
                break;
            }
        }
    }

}
