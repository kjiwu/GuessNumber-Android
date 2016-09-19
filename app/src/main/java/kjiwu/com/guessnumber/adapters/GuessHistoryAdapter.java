package kjiwu.com.guessnumber.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.List;

import kjiwu.com.guessnumber.R;
import kjiwu.com.guessnumber.models.GuessResult;

/**
 * Created by kjiwu on 2016/9/14.
 */
public class GuessHistoryAdapter extends BaseAdapter {

    public class ViewHolder {
        public TextView guessResultTextView;
        public TextView guessNumberTextView;
        public TextView guessTimeTextView;
    }

    private List<GuessResult> mResults;

    private LayoutInflater mInflater;

    public GuessHistoryAdapter(Context context, List<GuessResult> data) {
        mResults = data;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mResults.size();
    }

    @Override
    public Object getItem(int position) {
        return mResults.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        GuessResult result = mResults.get(position);
        ViewHolder holder = null;

        if(null == view) {
            view = mInflater.inflate(R.layout.guess_history_item, parent, false);
            holder = new ViewHolder();
            holder.guessNumberTextView = (TextView) view.findViewById(R.id.text_guess_number);
            holder.guessResultTextView = (TextView) view.findViewById(R.id.text_guess_result);
            holder.guessTimeTextView = (TextView) view.findViewById(R.id.text_guess_time);
            view.setTag(holder);
        }
        else {
            holder = (ViewHolder) view.getTag();
        }

        if(null != holder) {
            holder.guessResultTextView.setText(result.getGuessResult());
            holder.guessNumberTextView.setText(result.getGuessNumber());
            int time = position + 1;
            holder.guessTimeTextView.setText("" + time);
        }

        return view;
    }
}
