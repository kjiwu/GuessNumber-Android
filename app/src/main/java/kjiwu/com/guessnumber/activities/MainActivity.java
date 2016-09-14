package kjiwu.com.guessnumber.activities;

import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import kjiwu.com.guessnumber.R;
import kjiwu.com.guessnumber.customs.KeyboardView;
import kjiwu.com.guessnumber.customs.NumberListView;

public class MainActivity extends KJIBaseActivity {

    private KeyboardView.OnKeyboardPressedListener mAddClickListener =
            new KeyboardView.OnKeyboardPressedListener() {
                @Override
                public void onPressed(String number) {
                    mNumberListView.inputNumber(number);
                }
            };

    private NumberListView mNumberListView;
    private KeyboardView mKeyboardView;

    private Button mBackButton;
    private Button mEnterButton;

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            int id = v.getId();
            switch (id) {
                case R.id.button_back:
                    String number = mNumberListView.removeNumber();
                    mKeyboardView.clearKeyState(number);
                    break;
                case R.id.button_enter:
                    break;
            }
        }
    };

    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private ListView mGuessHistoryListView;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViews();
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWindowParams() {
        super.initWindowParams();
    }





    private void initViews() {
        mNumberListView = (NumberListView) findViewById(R.id.numberListView);
        mKeyboardView = (KeyboardView) findViewById(R.id.keyboard);
        mKeyboardView.setKeyboardPressedListener(mAddClickListener);

        mBackButton = (Button) findViewById(R.id.button_back);
        mBackButton.setOnClickListener(mOnClickListener);

        mEnterButton = (Button) findViewById(R.id.button_enter);
        mEnterButton.setOnClickListener(mOnClickListener);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerLayout);
        mToolbar = (Toolbar) findViewById(R.id.tooBar);
        setSupportActionBar(mToolbar);
        mToolbar.setBackgroundColor(getResources().getColor(R.color.colorPrimary));
        mToolbar.setTitleTextColor(getResources().getColor(R.color.defaultTitleColor));
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this,
                mDrawerLayout,
                mToolbar, 0, 0) {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }
        };
        toggle.syncState();
        mDrawerLayout.addDrawerListener(toggle);

        mGuessHistoryListView = (ListView) findViewById(R.id.listview_guessHistory);
    }
}
