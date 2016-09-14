package kjiwu.com.guessnumber.activities;

import android.annotation.TargetApi;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.CallSuper;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

import kjiwu.com.guessnumber.R;

/**
 * Created by kjiwu on 2016/9/13.
 */
public class KJIBaseActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initWindowParams();
        setContentView(getLayoutId());
    }

    protected int getLayoutId() {
        return -1;
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @CallSuper
    protected void initWindowParams() {
        getWindow().setStatusBarColor(getResources().getColor(R.color.colorPrimary));
    }
}
