package com.software.tempe.titatwo;

import android.content.Intent;
import android.os.Handler;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{

    private Button button[][] = new Button[3][3];

    private LinearLayout mainLayout;

    private boolean p1_turn = true;

    private int color1;
    private int color2;

    private int boxCount;

    private int p1_point;
    private int p2_point;

    private static final String P1 = "Play: Player 1";
    private static final String P2 = "Play: Player 2";

    private TextView p1_score;
    private TextView p2_score;

    private Button play_turn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mainLayout = findViewById(R.id.mainLayout);

        p1_score = findViewById(R.id.p1_score);
        p2_score = findViewById(R.id.p2_score);

        play_turn = findViewById(R.id.play_turn);

        play_turn.setText(P1);
        play_turn.setBackgroundColor(getResources().getColor(R.color.p1_color));


        // button pad instance
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String buttonID = "button_" + i + j;
                int resID = getResources().getIdentifier(buttonID, "id", getPackageName());
                button[i][j] = findViewById(resID);
                button[i][j].setBackgroundColor(getResources().getColor(R.color.default_button));
                button[i][j].setOnClickListener(this);
            }
        }

    }

    @Override
    public void onClick(View v) {
        if(!((Button)v).getText().toString().equals(""))    {
            // check if the box is ticked or not
            return;
        }

        if(p1_turn) {
            ((Button) v).setText("X");
            v.setBackgroundColor(getResources().getColor(R.color.p1_color));
            play_turn.setText(P2);
            play_turn.setBackgroundColor(getResources().getColor(R.color.p2_color));

        } else {
            ((Button) v).setText("O");
            v.setBackgroundColor(getResources().getColor(R.color.p2_color));
            play_turn.setText(P1);
            play_turn.setBackgroundColor(getResources().getColor(R.color.p1_color));
        }

        // every box ticked, its incremented value by one
        boxCount = boxCount + 1;

        if(winnerDecision())    {
            if(p1_turn) {
                color1 = getResources().getColor(R.color.p2_color);
                play_turn.setText(P2);
                play_turn.setBackgroundColor(color1);
                p1_wins();
            } else {
                color2 = getResources().getColor(R.color.p1_color);
                play_turn.setText(P1);
                play_turn.setBackgroundColor(color2);
                p2_wins();
            }
        } else
            if(boxCount == 9){
                drawDecision();
        } else {
            // switch turn
            p1_turn = !p1_turn;
            }
    }

    private void drawDecision() {
        // Toast.makeText(this, "Draw!", Toast.LENGTH_SHORT).show();
        p1_turn = !p1_turn;
        Snackbar.make(mainLayout, "Draw!", Snackbar.LENGTH_SHORT).show();
        restartGame();
    }

    private void p1_wins() {
        p1_turn = false;
        p1_point = p1_point + 1;
        // Toast.makeText(this, "Player 1 wins!", Toast.LENGTH_SHORT).show();
        Snackbar.make(mainLayout, "Player 1 wins!", Snackbar.LENGTH_SHORT).show();
        updatePoint();
        restartGame();
    }

    private void p2_wins() {
        p1_turn = true;
        p2_point = p2_point + 1;
        // Toast.makeText(this, "Player 2 wins!", Toast.LENGTH_SHORT).show();
        Snackbar.make(mainLayout, "Player 2 wins!", Snackbar.LENGTH_SHORT).show();
        updatePoint();
        restartGame();
    }

    private void updatePoint() {
        p1_score.setText("Player 1: " + p1_point);
        p2_score.setText("Player 2: " + p2_point);
    }

    private void restartGame() {

        for(int i = 0; i < 3; i++)  {
            for(int j = 0; j < 3; j++)  {
                // button[i][j].setTextColor(getResources().getColor(R.color.colorAccent));
                button[i][j].setEnabled(false);
            }
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                for(int i = 0; i < 3; i++)  {
                    for(int j = 0; j < 3; j++)  {
                        button[i][j].setText("");
                        button[i][j].setBackgroundColor(getResources().getColor(R.color.default_button));
                        button[i][j].setTextColor(getResources().getColor(R.color.text_transparent));
                    }
                }

                for(int i = 0; i < 3; i++)  {
                    for(int j = 0; j < 3; j++)  {
                        button[i][j].setEnabled(true);
                    }
                }

                boxCount = 0;
                // p1_turn = true;
            }
        }, 2175);

    }

    private boolean winnerDecision()    {

        // save the value into array
        String[][] box = new String[3][3];

        for(int i = 0; i < 3; i++)    {
            for(int j = 0; j < 3; j++)  {
                box[i][j] = button[i][j].getText().toString();
            }
        }

        for(int i = 0; i < 3; i++)  {
            if(box[i][0].equals(box[i][1])
                    && box[i][0].equals(box[i][2])
                    && !box[i][0].equals(""))   {
                button[i][0].setTextColor(getResources().getColor(R.color.colorAccent));
                button[i][1].setTextColor(getResources().getColor(R.color.colorAccent));
                button[i][2].setTextColor(getResources().getColor(R.color.colorAccent));
                return true;
            }
        }

        for(int i = 0; i < 3; i++)  {
            if(box[0][i].equals(box[1][i])
                    && box[0][i].equals(box[2][i])
                    && !box[0][i].equals(""))   {
                button[0][i].setTextColor(getResources().getColor(R.color.colorAccent));
                button[1][i].setTextColor(getResources().getColor(R.color.colorAccent));
                button[2][i].setTextColor(getResources().getColor(R.color.colorAccent));
                return true;
            }
        }

        if(box[0][0].equals(box[1][1])
                && box[0][0].equals(box[2][2])
                && !box[0][0].equals(""))   {
            button[0][0].setTextColor(getResources().getColor(R.color.colorAccent));
            button[1][1].setTextColor(getResources().getColor(R.color.colorAccent));
            button[2][2].setTextColor(getResources().getColor(R.color.colorAccent));
            return true;
        }

        if(box[0][2].equals(box[1][1])
                && box[0][2].equals(box[2][0])
                && !box[0][2].equals(""))   {
            button[0][2].setTextColor(getResources().getColor(R.color.colorAccent));
            button[1][1].setTextColor(getResources().getColor(R.color.colorAccent));
            button[2][0].setTextColor(getResources().getColor(R.color.colorAccent));
            return true;
        }

        return false;

    }

    private void resetPad() {
        p1_point = 0;
        p2_point = 0;
        updatePoint();

        for(int i = 0; i < 3; i++)  {
            for(int j = 0; j < 3; j++)  {
                // button[i][j].setTextColor(getResources().getColor(R.color.colorAccent));
                button[i][j].setEnabled(false);
            }
        }

        for(int i = 0; i < 3; i++)  {
            for(int j = 0; j < 3; j++)  {
                button[i][j].setText("");
                button[i][j].setBackgroundColor(getResources().getColor(R.color.default_button));
                button[i][j].setTextColor(getResources().getColor(R.color.text_transparent));
            }
        }

        for(int i = 0; i < 3; i++)  {
            for(int j = 0; j < 3; j++)  {
                button[i][j].setEnabled(true);
            }
        }

        boxCount = 0;
        p1_turn = true;
        play_turn.setBackgroundColor(getResources().getColor(R.color.p1_color));
        play_turn.setText(P1);

        Toast.makeText(this, "Restart!", Toast.LENGTH_SHORT).show();

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putInt("boxCount", boxCount);
        outState.putInt("p1_point", p1_point);
        outState.putInt("p2_point", p2_point);
        outState.putBoolean("p1_turn", p1_turn);
        outState.putInt("color1", color1);
        outState.putInt("color2", color2);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        boxCount = savedInstanceState.getInt("boxCount");
        p1_point = savedInstanceState.getInt("p1_point");
        p2_point = savedInstanceState.getInt("p2_point");
        p1_turn = savedInstanceState.getBoolean("p1_turn");
        color1 = savedInstanceState.getInt("color1");
        color2 = savedInstanceState.getInt("color2");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.action_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId())   {
            case R.id.resetBoard:
                resetPad();
                return true;
            case R.id.aboutApp:
                startAboutActivity();
                return true;

                default:
                    return false;
        }
    }

    private void startAboutActivity() {
        Intent aboutActivityIntent = new Intent(this, AboutActivity.class);
        startActivity(aboutActivityIntent);
    }
}
