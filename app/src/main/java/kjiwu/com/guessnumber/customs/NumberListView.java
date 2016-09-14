package kjiwu.com.guessnumber.customs;

import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import java.util.ArrayList;
import kjiwu.com.guessnumber.R;

/**
 * Created by kjiwu on 2016/9/13.
 */
public class NumberListView extends LinearLayout {

    private ArrayList<NumberTextView> mNumberTextViews;

    public NumberListView(Context context) {
        super(context, null);
    }

    public NumberListView(Context context, AttributeSet attrs) {
        super(context, attrs);
        LayoutInflater.from(context).inflate(R.layout.view_numberlist, this);

        mNumberTextViews = new ArrayList<>(4);
        mNumberTextViews.add((NumberTextView) findViewById(R.id.numberTextView1));
        mNumberTextViews.add((NumberTextView) findViewById(R.id.numberTextView2));
        mNumberTextViews.add((NumberTextView) findViewById(R.id.numberTextView3));
        mNumberTextViews.add((NumberTextView) findViewById(R.id.numberTextView4));
    }

    public void inputNumber(String number) {
        if (TextUtils.isEmpty(number)) return;

        for (int i = 0; i < mNumberTextViews.size(); i++) {
            NumberTextView view = mNumberTextViews.get(i);
            if (!view.haveNumber()) {
                view.setNumber(number);
                view.setNeedBlink(false);
                if (i != mNumberTextViews.size() - 1) {
                    mNumberTextViews.get(i + 1).setNeedBlink(true);
                }
                break;
            }
        }
    }

    public String removeNumber() {
        if (null == mNumberTextViews || mNumberTextViews.size() == 0) {
            return null;
        }

        String removeNumber = null;
        for (int i = mNumberTextViews.size() - 1; i >= 0; i--) {
            NumberTextView view = mNumberTextViews.get(i);
            removeNumber = view.getNumber();
            if(view.haveNumber()) {
                view.setNumber("");
                view.setNeedBlink(true);
                if(i != mNumberTextViews.size() - 1) {
                    mNumberTextViews.get(i + 1).setNeedBlink(false);
                }

                break;
            }
        }

        return removeNumber;
    }

    public void reset() {
        if (null == mNumberTextViews || mNumberTextViews.size() == 0) {
            return;
        }

        for (NumberTextView view : mNumberTextViews) {
            view.setNeedBlink(false);
            view.setNumber("");
        }

        mNumberTextViews.get(0).setNeedBlink(true);
    }
}
