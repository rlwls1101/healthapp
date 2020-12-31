package com.example.capston_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import static com.example.capston_project.Loginloading.tip;
import static com.example.capston_project.Loginloading.user;

public class TipResult extends Activity {
    private LinearLayout dynamicLayout;
    TextView[] textView = new TextView[58];
    int[] id = {R.id.textView_1, R.id.textView_2, R.id.textView_3, R.id.textView_4, R.id.textView_5, R.id.textView_6, R.id.textView_7, R.id.textView_8, R.id.textView_9, R.id.textView_10
            , R.id.textView_11, R.id.textView_12, R.id.textView_13, R.id.textView_14, R.id.textView_15, R.id.textView_16, R.id.textView_17, R.id.textView_18, R.id.textView_19, R.id.textView_20
            , R.id.textView_21, R.id.textView_22, R.id.textView_23, R.id.textView_24, R.id.textView_25, R.id.textView_26, R.id.textView_27, R.id.textView_28, R.id.textView_29, R.id.textView_30
            , R.id.textView_31, R.id.textView_32, R.id.textView_33, R.id.textView_34, R.id.textView_35, R.id.textView_36, R.id.textView_37, R.id.textView_38, R.id.textView_39, R.id.textView_40
            , R.id.textView_41, R.id.textView_42, R.id.textView_43, R.id.textView_44, R.id.textView_45, R.id.textView_46, R.id.textView_47, R.id.textView_48, R.id.textView_49, R.id.textView_50
            , R.id.textView_51, R.id.textView_52, R.id.textView_53, R.id.textView_54, R.id.textView_55, R.id.textView_56, R.id.textView_57, R.id.textView_58,0};
    int buf = 0;
    int num = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tip);
        setResult();
        getObject();
        MakeButton();
    }

    private void getObject() {
        for (int i = 0; i < 58; i++) {
            textView[i] = (TextView) findViewById(id[i]);
        }
    }

    private void MakeButton() {

        for (int i = 0; i < 58; i++) {
            buf = i + 1;
            textView[i].setOnClickListener(new View.OnClickListener() {
                int num = buf;

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(v.getContext(), TipTable.class);
                    intent.putExtra("num", num);
                    startActivity(intent);
                    overridePendingTransition(0, 0);
                }
            });
        }
    }

    private void setResult() {
        dynamicLayout = (LinearLayout) findViewById(R.id.dynamicArea3);
        dynamicLayout.removeAllViews();//다이나믹 레이아웃 초기화
        checktip(user.tipfavorite);
        checktip(user.tipfavorite2);
        checktip(user.tipfavorite3);
    }

    public void MakeResult(String result) {
        TextView view = new TextView(TipResult.this);
        view.setId(1000 + num);
        view.setText(num + ". " + result);
        dynamicLayout.addView(view, new ViewGroup.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        num += 1;

    }
    public static int[] fromString(String string) {
        String[] strings = string.replace("[", "").replace("]", "").replace("\"", "").split(", ");
        int result[] = new int[strings.length];
        for (int i = 0; i < result.length; i++) {
            result[i] = Integer.parseInt(strings[i]);
        }
        return result;
    }

    public void checktip(int number){
        if(number==201){
            MakeResult(tip.getT201());
        }
        if(number==202){
            MakeResult(tip.getT202());
        }
        if(number==203){
            MakeResult(tip.getT203());
        }
        if(number==204){
            MakeResult(tip.getT204());
        }
        if(number==205){
            MakeResult(tip.getT205());
        }
        if(number==206){
            MakeResult(tip.getT206());
        }
        if(number==207){
            MakeResult(tip.getT207());
        }
        if(number==208){
            MakeResult(tip.getT208());
        }
        if(number==209){
            MakeResult(tip.getT209());
        }
        if(number==210){
            MakeResult(tip.getT210());
        }
        if(number==211){
            MakeResult(tip.getT211());
        }
        if(number==212){
            MakeResult(tip.getT212());
        }
        if(number==213){
            MakeResult(tip.getT213());
        }
        if(number==214){
            MakeResult(tip.getT214());
        }
        if(number==215){
            MakeResult(tip.getT215());
        }
    }
}