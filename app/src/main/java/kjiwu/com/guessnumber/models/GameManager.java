package kjiwu.com.guessnumber.models;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Random;

/**
 * Created by kjiwu on 2016/9/19.
 */
public class GameManager {

    public interface GameGuessResultListener {
        void onWin();
        void onLose();
    }

    private final String numbers[] = new String[] {
            "1", "2", "3", "4", "5", "6", "7", "8", "9"
    };

    private final int MAX_GUESS_TIMES = 10;

    private String[] generate_number;

    private int current_guess_times = 0;

    private boolean isWin = false;

    private GameGuessResultListener gameGuessResultListener;

    public void setGameGuessResultListener(GameGuessResultListener listener) {
        gameGuessResultListener = listener;
    }

    private GameManager() {

    }

    private static class SingleGameManager {
        private static GameManager manager = new GameManager();
    }

    public static GameManager getGameManager() {
        return SingleGameManager.manager;
    }

    private boolean containNumber(String number) {
        for(int i = 0; i < generate_number.length; i++) {
            if(generate_number[i].equals(number)) {
                return true;
            }
        }

        return false;
    }

    public void getNewNumber() {
        generate_number = new String[4];
        Random random = new Random(new Date().getTime());
        ArrayList temp_numbers = new ArrayList();
        Collections.addAll(temp_numbers, numbers);

        for(int i = 0; i < generate_number.length; i++) {
            int index = random.nextInt(temp_numbers.size());
            generate_number[i] = temp_numbers.get(index).toString();
            temp_numbers.remove(index);
        }

        current_guess_times = 0;
    }

    public String getNumber() {
        String result = "";
        for (int i = 0; i < generate_number.length; i++) {
            result += generate_number[i];
        }
        return result.trim();
    }

    public String getGuessResult(String number) {
        int A = 0;
        int B = 0;

        for(int i = 0; i < number.length(); i++) {
            String temp_number = String.valueOf(number.toCharArray()[i]);
            if(temp_number.equals(generate_number[i])) {
                A++;
            }
            else if(containNumber(temp_number)) {
                B++;
            }
        }

        String result = String.format("%dA%dB", A, B);

        current_guess_times++;

        if(current_guess_times <= MAX_GUESS_TIMES &&
                A == generate_number.length) {
            if(null != gameGuessResultListener) {
                gameGuessResultListener.onWin();
                return null;
            }
        }

        if(current_guess_times == MAX_GUESS_TIMES) {
            if(null != gameGuessResultListener) {
                gameGuessResultListener.onLose();
                return null;
            }
        }

        return result;
    }
}
