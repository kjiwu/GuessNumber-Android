package kjiwu.com.guessnumber.activities;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

import kjiwu.com.guessnumber.R;
import kjiwu.com.guessnumber.customs.KeyboardView;
import kjiwu.com.guessnumber.customs.NumberListView;
import kjiwu.com.guessnumber.models.GameManager;
import kjiwu.com.guessnumber.viewModels.MainActivityViewModel;

public class MainActivity extends KJIBaseActivity implements GameManager.GameGuessResultListener {

    private final static String MainActivity_Tag = MainActivity.class.getName();

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
                    String input_number = mNumberListView.getInputNumber();
                    mainActivityViewModel.testGuessNumber(input_number);
                    mKeyboardView.clear();
                    mNumberListView.reset();
                    break;
            }
        }
    };

    private DrawerLayout mDrawerLayout;
    private Toolbar mToolbar;
    private ListView mGuessHistoryListView;


    private MainActivityViewModel mainActivityViewModel;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initViews();

        mainActivityViewModel = new MainActivityViewModel(this);
        mainActivityViewModel.generateGuessNumber();
        mGuessHistoryListView.setAdapter(mainActivityViewModel.getGuessResultAdapter());
        mainActivityViewModel.setGameGuessResultListener(this);
    }

    @Override
    protected int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    protected void initWindowParams() {
        super.initWindowParams();
    }

    @Override
    public void onWin() {
        Log.d(MainActivity_Tag, "onWin");

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Win");
        builder.setMessage("You win!");
        builder.setCancelable(true);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                mainActivityViewModel.clearHistory();
                mainActivityViewModel.generateGuessNumber();
            }
        });
        builder.create().show();
    }

    @Override
    public void onLose() {
        Log.d(MainActivity_Tag, "onLose");
        mainActivityViewModel.clearHistory();
        mainActivityViewModel.generateGuessNumber();
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
