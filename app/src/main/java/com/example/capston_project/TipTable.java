package com.example.capston_project;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import static com.example.capston_project.Loginloading.info;
import static com.example.capston_project.Loginloading.tip;

public class TipTable extends Activity {
    String[] tipResult = {tip.getT101(), tip.getT102(), tip.getT103(), tip.getT104(), tip.getT105(), tip.getT106(), tip.getT107(), tip.getT108(), tip.getT109(),
            tip.getT110(), tip.getT111(), tip.getT201(), tip.getT202(), tip.getT202(), tip.getT203(), tip.getT204(), tip.getT205(), tip.getT206(), tip.getT207(),
            tip.getT208(), tip.getT209(), tip.getT210(), tip.getT211(), tip.getT212(), tip.getT213(), tip.getT214(), tip.getT215(),tip.getT216(),tip.getT301() ,tip.getT302()
    ,tip.getT303(),tip.getT401(),tip.getT402(),tip.getT403(),tip.getT404(),tip.getT405(),tip.getT406(),tip.getT407(),tip.getT408(),tip.getT409(),tip.getT410(),tip.getT411()
    ,tip.getT412(),tip.getT413(),tip.getT414(),tip.getT415(),tip.getT416F(),tip.getT501(),tip.getT502(),tip.getT503(),tip.getT504(),tip.getT505(),tip.getT506(),tip.getT507()
    ,tip.getT508(),tip.getT509(),tip.getT601(),tip.getT602()};
    String[] infoResult = { info.getT101(), info.getT102(), info.getT103(), info.getT104(), info.getT105(), info.getT106(), info.getT107(), info.getT108(), info.getT109(),
            info.getT110(), info.getT111(), info.getT201(), info.getT202(), info.getT202(), info.getT203(), info.getT204(), info.getT205(), info.getT206(), info.getT207(),
            info.getT208(), info.getT209(), info.getT210(), info.getT211(), info.getT212(), info.getT213(), info.getT214(), info.getT215(),info.getT216(),info.getT301() ,info.getT302()
            ,info.getT303(),info.getT401(),info.getT402(),info.getT403(),info.getT404(),info.getT405(),info.getT406(),info.getT407(),info.getT408(),info.getT409(),info.getT410(),info.getT411()
            ,info.getT412(),info.getT413(),info.getT414(),info.getT415(),info.getT416F(),info.getT501(),info.getT502(),info.getT503(),info.getT504(),info.getT505(),info.getT506(),info.getT507()
            ,info.getT508(),info.getT509(),info.getT601(),info.getT602()};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.tiptable);
        Intent intent = getIntent();
        int num = intent.getExtras().getInt("num");
        SetTip(num);
        SetInfo(num);
    }

    private void SetTip(int no) {
        MakeResult(tipResult[no-1]);
    }

    public void MakeResult(String result) {
        TextView view = (TextView) findViewById(R.id.textView2);
        view.setText(result);
    }

    private void SetInfo(int no) {
        MakeInfomation(infoResult[no-1]);
    }
    public void MakeInfomation(String info) {
        TextView view = (TextView) findViewById(R.id.textView9);
        TextView view1 = (TextView) findViewById(R.id.textView10);

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (view.getText().equals("\n더보기")) {
                    view.setText("\n접기");
                    view1.setText(info);
                } else {
                    view.setText("\n더보기");
                    view1.setText("");
                }
            }
        });

    }

}
