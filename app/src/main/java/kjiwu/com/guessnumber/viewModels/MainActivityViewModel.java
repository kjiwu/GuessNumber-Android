package kjiwu.com.guessnumber.viewModels;

import android.content.Context;
import android.util.Log;
import android.widget.BaseAdapter;

import java.util.ArrayList;
import java.util.List;

import kjiwu.com.guessnumber.adapters.GuessHistoryAdapter;
import kjiwu.com.guessnumber.models.GameManager;
import kjiwu.com.guessnumber.models.GuessResult;

/**
 * Created by kjiwu on 2016/9/19.
 */
public class MainActivityViewModel {
    private final static String MainActivityViewModel_TAG = MainActivityViewModel.class.getName();


    private GameManager gameManager = GameManager.getGameManager();

    private List<GuessResult> guessResults;

    private Context mContext;

    public MainActivityViewModel(Context context) {
        mContext = context;
    }

    private BaseAdapter mAdapter;

    public BaseAdapter getGuessResultAdapter() {
        return mAdapter;
    }

    public void generateGuessNumber() {
        gameManager.getNewNumber();
        Log.d(MainActivityViewModel_TAG, gameManager.getNumber());
        guessResults = new ArrayList<>();
        mAdapter = new GuessHistoryAdapter(mContext, guessResults);
    }

    public void testGuessNumber(String number) {
        String result = gameManager.getGuessResult(number);

        if(null != result) {
            Log.d(MainActivityViewModel_TAG, result);
            if (null == guessResults) {
                guessResults = new ArrayList<>();
            }

            GuessResult guessResult = new GuessResult();
            guessResult.setGuessNumber(number);
            guessResult.setGuessResult(result);
            guessResults.add(0, guessResult);
            mAdapter.notifyDataSetChanged();
        }
    }

    GameManager.GameGuessResultListener mGameGuessResultListener;
    public void setGameGuessResultListener(GameManager.GameGuessResultListener listener) {
        mGameGuessResultListener = listener;
        gameManager.setGameGuessResultListener(listener);
    }

    public void clearHistory() {
        guessResults.clear();
        mAdapter.notifyDataSetChanged();
    }
}
