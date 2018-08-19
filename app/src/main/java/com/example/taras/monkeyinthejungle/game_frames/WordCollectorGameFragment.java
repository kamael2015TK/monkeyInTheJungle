
package com.example.taras.monkeyinthejungle.game_frames;

        import android.app.Activity;
        import android.os.Bundle;
        import android.support.annotation.NonNull;
        import android.support.v4.app.Fragment;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.widget.LinearLayout;
        import android.widget.TableRow.LayoutParams;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.Button;
        import android.widget.TableLayout;
        import android.widget.TableRow;
        import android.widget.TextView;

        import com.example.taras.monkeyinthejungle.R;
        import com.example.taras.monkeyinthejungle.games.WordCollectorGame;

public class WordCollectorGameFragment extends Fragment {
    private static final String ARG_PARAM1 = "gameId";
    private int gameId;
    private View activeView;
    private char shuffledWord[];
    private String answer;
    private Button allButtons[];

    public WordCollectorGameFragment() {
    }


    public static WordCollectorGameFragment newInstance(int gameId) {
        WordCollectorGameFragment fragment = new WordCollectorGameFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, gameId);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            gameId = savedInstanceState.getInt(ARG_PARAM1);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        System.out.println(gameId);
        activeView = inflater.inflate(R.layout.fragment_word_collector_game, container, false);
        WordCollectorGame game = new WordCollectorGame();
        setValues(game, activeView);
        setResetButtonListener(activeView);
        return activeView;
    }

    private void setValues(WordCollectorGame game,View v ) {
        shuffledWord = game.getShuffledWord();
        answer = game.getWord();
        Activity activity = getActivity();
        LinearLayout mainLayout = v.findViewById(R.id.lnl_collect_word_layout);
        LinearLayout line = setNewLine(activity);
        allButtons = new Button[shuffledWord.length];
        for(int i = 0; i< shuffledWord.length; i++) {
            if( i % 4 == 0) {
                mainLayout.addView(line);
                line = setNewLine(activity);
            }
            Button btn = new Button(activity);
            btn.setLayoutParams( new LayoutParams(0, LayoutParams.MATCH_PARENT, 1f));
            btn.setId(i);
            btn.setText(shuffledWord[i] + "");
            allButtons[i] = btn;
            btn.setOnClickListener( new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TextView result = activeView.findViewById(R.id.txt_result_world_collector);
                    String txt = shuffledWord[v.getId()] + "";
                    String collectedWord = result.getText() + txt;
                    result.setText(collectedWord);
                    activeView.findViewById(v.getId()).setVisibility(View.INVISIBLE);
                    if(collectedWord.equals(answer)) {
                        result.setText("correct");
                    }
                }
            });
            line.addView(btn);
        }
        mainLayout.addView(line);
    }

    private LinearLayout setNewLine(Activity activity) {
        LinearLayout line = new LinearLayout(activity);
        line.setLayoutParams(new LinearLayout.LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.MATCH_PARENT));
        line.setOrientation(LinearLayout.HORIZONTAL);
        return line;
    }

    private void setResetButtonListener(View v) {
        Button reset = v.findViewById(R.id.btn_collect_word_reset);
        reset.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                TextView result = activeView.findViewById(R.id.txt_result_world_collector);
                result.setText("");
                for (Button b: allButtons) {
                    b.setVisibility(View.VISIBLE);
                }
            }
        });
    }

}