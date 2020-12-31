package com.example.capston_project;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RatingBar;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TimeZone;
import static com.example.capston_project.Loginloading.user;

public class ClockView extends Fragment {
    TextView mStartTv;
    TextView mEndTv;
    TextView tip;
    RatingBar ratingBar;
    private SleepReporter mSleepReporter;
    int age, rate;
    String result;
    long start = getTimeOfToday() - 550800000;
    long[] sleeptime = {0, 0, 0, 0, 0, 0, 0, 0};
    long[] startsleep = {0, 0, 0, 0, 0, 0, 0};
    long[] endsleep = {0, 0, 0, 0, 0, 0, 0};
    long[] totalsleep = {0, 0, 0, 0, 0, 0, 0, 0};

    public static ClockView newInstance() {
        return new ClockView();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    private SleepReporter.SleepObserver mSleepObserver = array -> {

        long[] clock;
        Date date;
        SimpleDateFormat datef;
        String dates;

        array = SortArray(array);
        setWeekSleep(array);
        if (array.size() != 0) {
            clock = array.get(array.size() - 1);
            date = new Date(clock[0]);
            datef = new SimpleDateFormat("MM-dd hh:mm", Locale.KOREA);
            dates = datef.format(date);
            updateSleepStartView(String.valueOf(dates));
            date = new Date(clock[1]);
            datef = new SimpleDateFormat("MM-dd hh:mm", Locale.KOREA);
            dates = datef.format(date);
            updateSleepStopView(String.valueOf(dates));
        }
    };

    private List<long[]> SortArray(List<long[]> array) {
        long[] buf1;
        long[] buf2;
        long[][] buf3;
        int i = 0, size;

        size = array.size();
        buf1 = new long[size];
        buf2 = new long[size];
        buf3 = new long[size][2];
        for (long[] a : array) {
            buf1[i] = a[0];
            buf2[i] = a[1];
            i += 1;
        }
        Arrays.sort(buf1);
        Arrays.sort(buf2);
        array.clear();
        for (i = 0; i < size; i++) {
            buf3[i][0] = buf1[i];
            buf3[i][1] = buf2[i];
            array.add(buf3[i]);
        }
        return array;
    }

    private void setWeekSleep(List<long[]> array) {
        int i = 0;

        for (long[] a : array) {
            long s = a[0];
            long e = a[1];

            if (i != 0) {
                if ((s - endsleep[i - 1]) <= 30000) {
                    endsleep[i - 1] = e;
                    sleeptime[i] += (e - s) / 60000;
                }
            }
            if (e >= start) {
                while ((e - start) > 86400000) {
                    start += 86400000;
                    i += 1;
                }
                i += 1;
                startsleep[i - 1] = s;
                endsleep[i - 1] = e;
                sleeptime[i] = (e - s) / 60000;
                totalsleep[i] = (e - s) / 60000;
                start += 86400000;
            } else {
                totalsleep[i] += (e - s) / 60000;
            }

            if (start > getTimeOfToday())
                break;
            if (i > 7)
                break;
        }
        Date date;
        SimpleDateFormat datef;
        String dates;
        for (int j = 1; j < 8; j++) {
            System.out.println("********************************");
            System.out.println(totalsleep[j]);
            System.out.println(sleeptime[j]);
            date = new Date(startsleep[j - 1]);
            datef = new SimpleDateFormat("MM-dd hh:mm", Locale.KOREA);
            dates = datef.format(date);
            System.out.println(dates);
            date = new Date(endsleep[j - 1]);
            datef = new SimpleDateFormat("MM-dd hh:mm", Locale.KOREA);
            dates = datef.format(date);
            System.out.println(dates);
        }
    }

    public void setResult() {
        FirebaseDatabase database = FirebaseDatabase.getInstance();
        DatabaseReference mDatabaseRef = database.getReference();
        Map<String, Object> childUpdates = new HashMap<>();
        Map<String, Object> surveyValue;

        Date date;
        SimpleDateFormat datef;
        String dates;
        int sleep;


        if (sleeptime[7] == 0) {
            result = "시차가이드 기능을 사용하기 위해서는 오늘의 수면 데이터가 필요합니다.\nSamsung Health - 메인 화면에서 아래로 드래그해 \"보라색 달\" 표시가 있는 버튼 클릭 - 수면 시간 입력을 해주시기 바랍니다.";
            return;
        }
        long time = sleeptime[7] / 60;
        if (age >= 65) {
            if (time >= 10) {
                result = "측정된 수면 시간은 " + time + "시간으로 과도한 수면 시간입니다.\n";
                result += "수면 시간이 너무 과도합니다. " + (sleeptime[7] - 480) + "분 더 늦게 잠에 들거나, 일찍 일어나는 것을 추천합니다.\n";

            } else if (time >= 9) {
                result = "측정된 수면 시간은 " + time + "시간으로 적당한 수면 시간입니다.\n";
                if (rate == 1)
                    result += "수면 권장 시간을 잘 지키고 있지만, 수면 시 문제가 있는 것 같으므로 전문의의 상담을 받아보세요.\n";
                else if (rate == 2)
                    result += "수면 권장 시간을 잘 지키고 있지만, 수면 시 문제가 있는 것 같으므로 전문의의 상담을 받아보세요.\n";
                else if (rate == 3)
                    result += "수면 권장 시간을 잘 지키고 있지만, 피로가 계속된다면 20분 더 일찍 잠에 들어보세요.\n";
                else if (rate == 4)
                    result += "수면 권장 시간을 잘 지키고 있지만, 피로가 계속된다면 10분 더 일찍 잠에 들어보세요.\n";
                else if (rate == 5) {
                    result += "수면 권장 시간을 잘 지키고 있습니다!";
                    date = new Date(startsleep[6]);
                    datef = new SimpleDateFormat("HH", Locale.KOREA);
                    dates = datef.format(date);
                    sleep = Integer.parseInt(dates) * 60;
                    datef = new SimpleDateFormat("mm", Locale.KOREA);
                    dates = datef.format(date);
                    sleep += Integer.parseInt(dates);
                    user.startsleep = Integer.toString(sleep);
                    date = new Date(endsleep[6]);
                    datef = new SimpleDateFormat("HH", Locale.KOREA);
                    dates = datef.format(date);
                    sleep = Integer.parseInt(dates) * 60;
                    datef = new SimpleDateFormat("mm", Locale.KOREA);
                    dates = datef.format(date);
                    sleep += Integer.parseInt(dates);
                    user.endsleep = Integer.toString(sleep);
                    surveyValue = user.towMap();
                    childUpdates.put("User" + "/" + user.id, surveyValue);
                    mDatabaseRef.updateChildren(childUpdates);
                }

            } else if (time >= 7) {
                result = "측정된 수면 시간은 " + time + "시간으로 권장 수면 시간입니다.\n";
                if (rate == 1)
                    result += "수면 권장 시간을 잘 지키고 있지만, 수면 시 문제가 있는 것 같으므로 전문의의 상담을 받아보세요.\n";
                else if (rate == 2)
                    result += "수면 권장 시간을 잘 지키고 있지만, 수면 시 문제가 있는 것 같으므로 전문의의 상담을 받아보세요.\n";
                else if (rate == 3)
                    result += "수면 권장 시간을 잘 지키고 있지만, 피로가 계속된다면 20분 더 일찍 잠에 들어보세요.\n";
                else if (rate == 4)
                    result += "수면 권장 시간을 잘 지키고 있지만, 피로가 계속된다면 10분 더 일찍 잠에 들어보세요.\n";
                else if (rate == 5) {
                    result += "수면 권장 시간을 잘 지키고 있습니다!";
                    date = new Date(startsleep[6]);
                    datef = new SimpleDateFormat("HH", Locale.KOREA);
                    dates = datef.format(date);
                    sleep = Integer.parseInt(dates) * 60;
                    datef = new SimpleDateFormat("mm", Locale.KOREA);
                    dates = datef.format(date);
                    sleep += Integer.parseInt(dates);
                    user.startsleep = Integer.toString(sleep);
                    date = new Date(endsleep[6]);
                    datef = new SimpleDateFormat("HH", Locale.KOREA);
                    dates = datef.format(date);
                    sleep = Integer.parseInt(dates) * 60;
                    datef = new SimpleDateFormat("mm", Locale.KOREA);
                    dates = datef.format(date);
                    sleep += Integer.parseInt(dates);
                    user.endsleep = Integer.toString(sleep);
                    surveyValue = user.towMap();
                    childUpdates.put("User" + "/" + user.id, surveyValue);
                    mDatabaseRef.updateChildren(childUpdates);
                }

            } else if (time >= 5) {
                result = "측정된 수면 시간은 " + time + "시간으로 적당한 수면 시간입니다.\n";
                if (rate == 1)
                    result += "수면 권장 시간을 잘 지키고 있지만, 수면 시 문제가 있는 것 같으므로 전문의의 상담을 받아보세요.\n";
                else if (rate == 2)
                    result += "수면 권장 시간을 잘 지키고 있지만, 수면 시 문제가 있는 것 같으므로 전문의의 상담을 받아보세요.\n";
                else if (rate == 3)
                    result += "수면 권장 시간을 잘 지키고 있지만, 피로가 계속된다면 20분 더 일찍 잠에 들어보세요.\n";
                else if (rate == 4)
                    result += "수면 권장 시간을 잘 지키고 있지만, 피로가 계속된다면 10분 더 일찍 잠에 들어보세요.\n";
                else if (rate == 5) {
                    result += "수면 권장 시간을 잘 지키고 있습니다!";
                    date = new Date(startsleep[6]);
                    datef = new SimpleDateFormat("HH", Locale.KOREA);
                    dates = datef.format(date);
                    sleep = Integer.parseInt(dates) * 60;
                    datef = new SimpleDateFormat("mm", Locale.KOREA);
                    dates = datef.format(date);
                    sleep += Integer.parseInt(dates);
                    user.startsleep = Integer.toString(sleep);
                    date = new Date(endsleep[6]);
                    datef = new SimpleDateFormat("HH", Locale.KOREA);
                    dates = datef.format(date);
                    sleep = Integer.parseInt(dates) * 60;
                    datef = new SimpleDateFormat("mm", Locale.KOREA);
                    dates = datef.format(date);
                    sleep += Integer.parseInt(dates);
                    user.endsleep = Integer.toString(sleep);
                    surveyValue = user.towMap();
                    childUpdates.put("User" + "/" + user.id, surveyValue);
                    mDatabaseRef.updateChildren(childUpdates);
                }

            } else if (time < 5) {
                result = "측정된 수면 시간은 " + time + "시간으로 부족합니다.\n";
                result += "현재의 수면 시간이 부족하므로, " + (420 - sleeptime[7]) + "분 더 일찍 잠을 자는 것이 권장됩니다.\n";
            }
        } else if (age >= 26) {
            if (time >= 11) {
                result = "측정된 수면 시간은 " + time + "시간으로 과도한 수면 시간입니다.\n";
                result += "수면 시간이 너무 과도합니다. " + (sleeptime[7] - 480) + "분 더 늦게 잠에 들거나, 일찍 일어나는 것을 추천합니다.\n";


            } else if (time >= 10) {
                result = "측정된 수면 시간은 " + time + "시간으로 적당한 수면 시간입니다.\n";
                if (rate == 1)
                    result += "수면 권장 시간을 잘 지키고 있지만, 수면 시 문제가 있는 것 같으므로 전문의의 상담을 받아보세요.\n";
                else if (rate == 2)
                    result += "수면 권장 시간을 잘 지키고 있지만, 수면 시 문제가 있는 것 같으므로 전문의의 상담을 받아보세요.\n";
                else if (rate == 3)
                    result += "수면 권장 시간을 잘 지키고 있지만, 피로가 계속된다면 20분 더 일찍 잠에 들어보세요.\n";
                else if (rate == 4)
                    result += "수면 권장 시간을 잘 지키고 있지만, 피로가 계속된다면 10분 더 일찍 잠에 들어보세요.\n";
                else if (rate == 5){
                    result += "수면 권장 시간을 잘 지키고 있습니다!\n";
                    date = new Date(startsleep[6]);
                    datef = new SimpleDateFormat("HH", Locale.KOREA);
                    dates = datef.format(date);
                    sleep = Integer.parseInt(dates) * 60;
                    datef = new SimpleDateFormat("mm", Locale.KOREA);
                    dates = datef.format(date);
                    sleep += Integer.parseInt(dates);
                    user.startsleep = Integer.toString(sleep);
                    date = new Date(endsleep[6]);
                    datef = new SimpleDateFormat("HH", Locale.KOREA);
                    dates = datef.format(date);
                    sleep = Integer.parseInt(dates) * 60;
                    datef = new SimpleDateFormat("mm", Locale.KOREA);
                    dates = datef.format(date);
                    sleep += Integer.parseInt(dates);
                    user.endsleep = Integer.toString(sleep);
                    surveyValue = user.towMap();
                    childUpdates.put("User" + "/" + user.id, surveyValue);
                    mDatabaseRef.updateChildren(childUpdates);
                }

            } else if (time >= 7) {
                result = "측정된 수면 시간은 " + time + "시간으로 권장 수면 시간입니다.\n";
                if (rate == 1)
                    result += "수면 권장 시간을 잘 지키고 있지만, 수면 시 문제가 있는 것 같으므로 전문의의 상담을 받아보세요.\n";
                else if (rate == 2)
                    result += "수면 권장 시간을 잘 지키고 있지만, 수면 시 문제가 있는 것 같으므로 전문의의 상담을 받아보세요.\n";
                else if (rate == 3)
                    result += "수면 권장 시간을 잘 지키고 있지만, 피로가 계속된다면 20분 더 일찍 잠에 들어보세요.\n";
                else if (rate == 4)
                    result += "수면 권장 시간을 잘 지키고 있지만, 피로가 계속된다면 10분 더 일찍 잠에 들어보세요.\n";
                else if (rate == 5){
                    result += "수면 권장 시간을 잘 지키고 있습니다!\n";
                    date = new Date(startsleep[6]);
                    datef = new SimpleDateFormat("HH", Locale.KOREA);
                    dates = datef.format(date);
                    sleep = Integer.parseInt(dates) * 60;
                    datef = new SimpleDateFormat("mm", Locale.KOREA);
                    dates = datef.format(date);
                    sleep += Integer.parseInt(dates);
                    user.startsleep = Integer.toString(sleep);
                    date = new Date(endsleep[6]);
                    datef = new SimpleDateFormat("HH", Locale.KOREA);
                    dates = datef.format(date);
                    sleep = Integer.parseInt(dates) * 60;
                    datef = new SimpleDateFormat("mm", Locale.KOREA);
                    dates = datef.format(date);
                    sleep += Integer.parseInt(dates);
                    user.endsleep = Integer.toString(sleep);
                    surveyValue = user.towMap();
                    childUpdates.put("User" + "/" + user.id, surveyValue);
                    mDatabaseRef.updateChildren(childUpdates);
                }
            } else if (time >= 6) {
                result = "측정된 수면 시간은 " + time + "시간으로 적당한 수면 시간입니다.\n";
                if (rate == 1)
                    result += "수면 권장 시간을 잘 지키고 있지만, 수면 시 문제가 있는 것 같으므로 전문의의 상담을 받아보세요.\n";
                else if (rate == 2)
                    result += "수면 권장 시간을 잘 지키고 있지만, 수면 시 문제가 있는 것 같으므로 전문의의 상담을 받아보세요.\n";
                else if (rate == 3)
                    result += "수면 권장 시간을 잘 지키고 있지만, 피로가 계속된다면 20분 더 일찍 잠에 들어보세요.\n";
                else if (rate == 4)
                    result += "수면 권장 시간을 잘 지키고 있지만, 피로가 계속된다면 10분 더 일찍 잠에 들어보세요.\n";
                else if (rate == 5){
                    result += "수면 권장 시간을 잘 지키고 있습니다!\n";
                    date = new Date(startsleep[6]);
                    datef = new SimpleDateFormat("HH", Locale.KOREA);
                    dates = datef.format(date);
                    sleep = Integer.parseInt(dates) * 60;
                    datef = new SimpleDateFormat("mm", Locale.KOREA);
                    dates = datef.format(date);
                    sleep += Integer.parseInt(dates);
                    user.startsleep = Integer.toString(sleep);
                    date = new Date(endsleep[6]);
                    datef = new SimpleDateFormat("HH", Locale.KOREA);
                    dates = datef.format(date);
                    sleep = Integer.parseInt(dates) * 60;
                    datef = new SimpleDateFormat("mm", Locale.KOREA);
                    dates = datef.format(date);
                    sleep += Integer.parseInt(dates);
                    user.endsleep = Integer.toString(sleep);
                    surveyValue = user.towMap();
                    childUpdates.put("User" + "/" + user.id, surveyValue);
                    mDatabaseRef.updateChildren(childUpdates);
                }
            } else if (time < 6) {
                result = "측정된 수면 시간은 " + time + "시간으로 부족합니다.\n";
                result += "현재의 수면 시간이 부족하므로, " + (420 - sleeptime[7]) + "분 더 일찍 잠을 자는 것이 권장됩니다.\n";
            }
        } else if (age >= 18) {
            if (time >= 12) {
                result = "측정된 수면 시간은 " + time + "시간으로 과도한 수면 시간입니다.\n";
                result += "수면 시간이 너무 과도합니다. " + (sleeptime[7] - 480) + "분 더 늦게 잠에 들거나, 일찍 일어나는 것을 추천합니다.\n";


            } else if (time >= 10) {
                result = "측정된 수면 시간은 " + time + "시간으로 적당한 수면 시간입니다.\n";
                if (rate == 1)
                    result += "수면 권장 시간을 잘 지키고 있지만, 수면 시 문제가 있는 것 같으므로 전문의의 상담을 받아보세요.\n";
                else if (rate == 2)
                    result += "수면 권장 시간을 잘 지키고 있지만, 수면 시 문제가 있는 것 같으므로 전문의의 상담을 받아보세요.\n";
                else if (rate == 3)
                    result += "수면 권장 시간을 잘 지키고 있지만, 피로가 계속된다면 20분 더 일찍 잠에 들어보세요.\n";
                else if (rate == 4)
                    result += "수면 권장 시간을 잘 지키고 있지만, 피로가 계속된다면 10분 더 일찍 잠에 들어보세요.\n";
                else if (rate == 5){
                    result += "수면 권장 시간을 잘 지키고 있습니다!\n";
                    date = new Date(startsleep[6]);
                    datef = new SimpleDateFormat("HH", Locale.KOREA);
                    dates = datef.format(date);
                    sleep = Integer.parseInt(dates) * 60;
                    datef = new SimpleDateFormat("mm", Locale.KOREA);
                    dates = datef.format(date);
                    sleep += Integer.parseInt(dates);
                    user.startsleep = Integer.toString(sleep);
                    date = new Date(endsleep[6]);
                    datef = new SimpleDateFormat("HH", Locale.KOREA);
                    dates = datef.format(date);
                    sleep = Integer.parseInt(dates) * 60;
                    datef = new SimpleDateFormat("mm", Locale.KOREA);
                    dates = datef.format(date);
                    sleep += Integer.parseInt(dates);
                    user.endsleep = Integer.toString(sleep);
                    surveyValue = user.towMap();
                    childUpdates.put("User" + "/" + user.id, surveyValue);
                    mDatabaseRef.updateChildren(childUpdates);
                }
            } else if (time >= 7) {
                result = "측정된 수면 시간은 " + time + "시간으로 권장 수면 시간입니다.\n";
                if (rate == 1)
                    result += "수면 권장 시간을 잘 지키고 있지만, 수면 시 문제가 있는 것 같으므로 전문의의 상담을 받아보세요.\n";
                else if (rate == 2)
                    result += "수면 권장 시간을 잘 지키고 있지만, 수면 시 문제가 있는 것 같으므로 전문의의 상담을 받아보세요.\n";
                else if (rate == 3)
                    result += "수면 권장 시간을 잘 지키고 있지만, 피로가 계속된다면 20분 더 일찍 잠에 들어보세요.\n";
                else if (rate == 4)
                    result += "수면 권장 시간을 잘 지키고 있지만, 피로가 계속된다면 10분 더 일찍 잠에 들어보세요.\n";
                else if (rate == 5){
                    result += "수면 권장 시간을 잘 지키고 있습니다!\n";
                    date = new Date(startsleep[6]);
                    datef = new SimpleDateFormat("HH", Locale.KOREA);
                    dates = datef.format(date);
                    sleep = Integer.parseInt(dates) * 60;
                    datef = new SimpleDateFormat("mm", Locale.KOREA);
                    dates = datef.format(date);
                    sleep += Integer.parseInt(dates);
                    user.startsleep = Integer.toString(sleep);
                    date = new Date(endsleep[6]);
                    datef = new SimpleDateFormat("HH", Locale.KOREA);
                    dates = datef.format(date);
                    sleep = Integer.parseInt(dates) * 60;
                    datef = new SimpleDateFormat("mm", Locale.KOREA);
                    dates = datef.format(date);
                    sleep += Integer.parseInt(dates);
                    user.endsleep = Integer.toString(sleep);
                    surveyValue = user.towMap();
                    childUpdates.put("User" + "/" + user.id, surveyValue);
                    mDatabaseRef.updateChildren(childUpdates);
                }
            } else if (time >= 6) {
                result = "측정된 수면 시간은 " + time + "시간으로 적당한 수면 시간입니다.\n";
                if (rate == 1)
                    result += "수면 권장 시간을 잘 지키고 있지만, 수면 시 문제가 있는 것 같으므로 전문의의 상담을 받아보세요.\n";
                else if (rate == 2)
                    result += "수면 권장 시간을 잘 지키고 있지만, 수면 시 문제가 있는 것 같으므로 전문의의 상담을 받아보세요.\n";
                else if (rate == 3)
                    result += "수면 권장 시간을 잘 지키고 있지만, 피로가 계속된다면 20분 더 일찍 잠에 들어보세요.\n";
                else if (rate == 4)
                    result += "수면 권장 시간을 잘 지키고 있지만, 피로가 계속된다면 10분 더 일찍 잠에 들어보세요.\n";
                else if (rate == 5){
                    result += "수면 권장 시간을 잘 지키고 있습니다!\n";
                    date = new Date(startsleep[6]);
                    datef = new SimpleDateFormat("HH", Locale.KOREA);
                    dates = datef.format(date);
                    sleep = Integer.parseInt(dates) * 60;
                    datef = new SimpleDateFormat("mm", Locale.KOREA);
                    dates = datef.format(date);
                    sleep += Integer.parseInt(dates);
                    user.startsleep = Integer.toString(sleep);
                    date = new Date(endsleep[6]);
                    datef = new SimpleDateFormat("HH", Locale.KOREA);
                    dates = datef.format(date);
                    sleep = Integer.parseInt(dates) * 60;
                    datef = new SimpleDateFormat("mm", Locale.KOREA);
                    dates = datef.format(date);
                    sleep += Integer.parseInt(dates);
                    user.endsleep = Integer.toString(sleep);
                    surveyValue = user.towMap();
                    childUpdates.put("User" + "/" + user.id, surveyValue);
                    mDatabaseRef.updateChildren(childUpdates);
                }
            } else if (time < 6) {
                result = "측정된 수면 시간은 " + time + "시간으로 부족합니다.\n";
                result += "현재의 수면 시간이 부족하므로, " + (420 - sleeptime[7]) + "분 더 일찍 잠을 자는 것이 권장됩니다.\n";
            }
        } else if (age >= 14) {
            if (time >= 12) {
                result = "측정된 수면 시간은 " + time + "시간으로 과도한 수면 시간입니다.\n";
                result += "수면 시간이 너무 과도합니다. " + (sleeptime[7] - 480) + "분 더 늦게 잠에 들거나, 일찍 일어나는 것을 추천합니다.\n";


            } else if (time >= 11) {
                result = "측정된 수면 시간은 " + time + "시간으로 적당한 수면 시간입니다.\n";
                if (rate == 1)
                    result += "수면 권장 시간을 잘 지키고 있지만, 수면 시 문제가 있는 것 같으므로 전문의의 상담을 받아보세요.\n";
                else if (rate == 2)
                    result += "수면 권장 시간을 잘 지키고 있지만, 수면 시 문제가 있는 것 같으므로 전문의의 상담을 받아보세요.\n";
                else if (rate == 3)
                    result += "수면 권장 시간을 잘 지키고 있지만, 피로가 계속된다면 20분 더 일찍 잠에 들어보세요.\n";
                else if (rate == 4)
                    result += "수면 권장 시간을 잘 지키고 있지만, 피로가 계속된다면 10분 더 일찍 잠에 들어보세요.\n";
                else if (rate == 5){
                    result += "수면 권장 시간을 잘 지키고 있습니다!\n";
                    date = new Date(startsleep[6]);
                    datef = new SimpleDateFormat("HH", Locale.KOREA);
                    dates = datef.format(date);
                    sleep = Integer.parseInt(dates) * 60;
                    datef = new SimpleDateFormat("mm", Locale.KOREA);
                    dates = datef.format(date);
                    sleep += Integer.parseInt(dates);
                    user.startsleep = Integer.toString(sleep);
                    date = new Date(endsleep[6]);
                    datef = new SimpleDateFormat("HH", Locale.KOREA);
                    dates = datef.format(date);
                    sleep = Integer.parseInt(dates) * 60;
                    datef = new SimpleDateFormat("mm", Locale.KOREA);
                    dates = datef.format(date);
                    sleep += Integer.parseInt(dates);
                    user.endsleep = Integer.toString(sleep);
                    surveyValue = user.towMap();
                    childUpdates.put("User" + "/" + user.id, surveyValue);
                    mDatabaseRef.updateChildren(childUpdates);
                }
            } else if (time >= 8) {
                result = "측정된 수면 시간은 " + time + "시간으로 권장 수면 시간입니다.\n";
                if (rate == 1)
                    result += "수면 권장 시간을 잘 지키고 있지만, 수면 시 문제가 있는 것 같으므로 전문의의 상담을 받아보세요.\n";
                else if (rate == 2)
                    result += "수면 권장 시간을 잘 지키고 있지만, 수면 시 문제가 있는 것 같으므로 전문의의 상담을 받아보세요.\n";
                else if (rate == 3)
                    result += "수면 권장 시간을 잘 지키고 있지만, 피로가 계속된다면 20분 더 일찍 잠에 들어보세요.\n";
                else if (rate == 4)
                    result += "수면 권장 시간을 잘 지키고 있지만, 피로가 계속된다면 10분 더 일찍 잠에 들어보세요.\n";
                else if (rate == 5){
                    result += "수면 권장 시간을 잘 지키고 있습니다!\n";
                    date = new Date(startsleep[6]);
                    datef = new SimpleDateFormat("HH", Locale.KOREA);
                    dates = datef.format(date);
                    sleep = Integer.parseInt(dates) * 60;
                    datef = new SimpleDateFormat("mm", Locale.KOREA);
                    dates = datef.format(date);
                    sleep += Integer.parseInt(dates);
                    user.startsleep = Integer.toString(sleep);
                    date = new Date(endsleep[6]);
                    datef = new SimpleDateFormat("HH", Locale.KOREA);
                    dates = datef.format(date);
                    sleep = Integer.parseInt(dates) * 60;
                    datef = new SimpleDateFormat("mm", Locale.KOREA);
                    dates = datef.format(date);
                    sleep += Integer.parseInt(dates);
                    user.endsleep = Integer.toString(sleep);
                    surveyValue = user.towMap();
                    childUpdates.put("User" + "/" + user.id, surveyValue);
                    mDatabaseRef.updateChildren(childUpdates);
                }
            } else if (time >= 7) {
                result = "측정된 수면 시간은 " + time + "시간으로 적당한 수면 시간입니다.\n";
                if (rate == 1)
                    result += "수면 권장 시간을 잘 지키고 있지만, 수면 시 문제가 있는 것 같으므로 전문의의 상담을 받아보세요.\n";
                else if (rate == 2)
                    result += "수면 권장 시간을 잘 지키고 있지만, 수면 시 문제가 있는 것 같으므로 전문의의 상담을 받아보세요.\n";
                else if (rate == 3)
                    result += "수면 권장 시간을 잘 지키고 있지만, 피로가 계속된다면 20분 더 일찍 잠에 들어보세요.\n";
                else if (rate == 4)
                    result += "수면 권장 시간을 잘 지키고 있지만, 피로가 계속된다면 10분 더 일찍 잠에 들어보세요.\n";
                else if (rate == 5){
                    result += "수면 권장 시간을 잘 지키고 있습니다!\n";
                    date = new Date(startsleep[6]);
                    datef = new SimpleDateFormat("HH", Locale.KOREA);
                    dates = datef.format(date);
                    sleep = Integer.parseInt(dates) * 60;
                    datef = new SimpleDateFormat("mm", Locale.KOREA);
                    dates = datef.format(date);
                    sleep += Integer.parseInt(dates);
                    user.startsleep = Integer.toString(sleep);
                    date = new Date(endsleep[6]);
                    datef = new SimpleDateFormat("HH", Locale.KOREA);
                    dates = datef.format(date);
                    sleep = Integer.parseInt(dates) * 60;
                    datef = new SimpleDateFormat("mm", Locale.KOREA);
                    dates = datef.format(date);
                    sleep += Integer.parseInt(dates);
                    user.endsleep = Integer.toString(sleep);
                    surveyValue = user.towMap();
                    childUpdates.put("User" + "/" + user.id, surveyValue);
                    mDatabaseRef.updateChildren(childUpdates);
                }
            } else if (time < 7) {
                result = "측정된 수면 시간은 " + time + "시간으로 부족합니다.\n";
                result += "현재의 수면 시간이 부족하므로, " + (420 - sleeptime[7]) + "분 더 일찍 잠을 자는 것이 권장됩니다.\n";
            }
        } else if (age >= 6) {
            if (time >= 13) {
                result = "측정된 수면 시간은 " + time + "시간으로 과도한 수면 시간입니다.\n";
                result += "수면 시간이 너무 과도합니다. " + (sleeptime[7] - 480) + "분 더 늦게 잠에 들거나, 일찍 일어나는 것을 추천합니다.\n";


            } else if (time >= 12) {
                result = "측정된 수면 시간은 " + time + "시간으로 적당한 수면 시간입니다.\n";
                if (rate == 1)
                    result += "수면 권장 시간을 잘 지키고 있지만, 수면 시 문제가 있는 것 같으므로 전문의의 상담을 받아보세요.\n";
                else if (rate == 2)
                    result += "수면 권장 시간을 잘 지키고 있지만, 수면 시 문제가 있는 것 같으므로 전문의의 상담을 받아보세요.\n";
                else if (rate == 3)
                    result += "수면 권장 시간을 잘 지키고 있지만, 피로가 계속된다면 20분 더 일찍 잠에 들어보세요.\n";
                else if (rate == 4)
                    result += "수면 권장 시간을 잘 지키고 있지만, 피로가 계속된다면 10분 더 일찍 잠에 들어보세요.\n";
                else if (rate == 5){
                    result += "수면 권장 시간을 잘 지키고 있습니다!\n";
                    date = new Date(startsleep[6]);
                    datef = new SimpleDateFormat("HH", Locale.KOREA);
                    dates = datef.format(date);
                    sleep = Integer.parseInt(dates) * 60;
                    datef = new SimpleDateFormat("mm", Locale.KOREA);
                    dates = datef.format(date);
                    sleep += Integer.parseInt(dates);
                    user.startsleep = Integer.toString(sleep);
                    date = new Date(endsleep[6]);
                    datef = new SimpleDateFormat("HH", Locale.KOREA);
                    dates = datef.format(date);
                    sleep = Integer.parseInt(dates) * 60;
                    datef = new SimpleDateFormat("mm", Locale.KOREA);
                    dates = datef.format(date);
                    sleep += Integer.parseInt(dates);
                    user.endsleep = Integer.toString(sleep);
                    surveyValue = user.towMap();
                    childUpdates.put("User" + "/" + user.id, surveyValue);
                    mDatabaseRef.updateChildren(childUpdates);
                }
            } else if (time >= 9) {
                result = "측정된 수면 시간은 " + time + "시간으로 권장 수면 시간입니다.\n";
                if (rate == 1)
                    result += "수면 권장 시간을 잘 지키고 있지만, 수면 시 문제가 있는 것 같으므로 전문의의 상담을 받아보세요.\n";
                else if (rate == 2)
                    result += "수면 권장 시간을 잘 지키고 있지만, 수면 시 문제가 있는 것 같으므로 전문의의 상담을 받아보세요.\n";
                else if (rate == 3)
                    result += "수면 권장 시간을 잘 지키고 있지만, 피로가 계속된다면 20분 더 일찍 잠에 들어보세요.\n";
                else if (rate == 4)
                    result += "수면 권장 시간을 잘 지키고 있지만, 피로가 계속된다면 10분 더 일찍 잠에 들어보세요.\n";
                else if (rate == 5){
                    result += "수면 권장 시간을 잘 지키고 있습니다!\n";
                    date = new Date(startsleep[6]);
                    datef = new SimpleDateFormat("HH", Locale.KOREA);
                    dates = datef.format(date);
                    sleep = Integer.parseInt(dates) * 60;
                    datef = new SimpleDateFormat("mm", Locale.KOREA);
                    dates = datef.format(date);
                    sleep += Integer.parseInt(dates);
                    user.startsleep = Integer.toString(sleep);
                    date = new Date(endsleep[6]);
                    datef = new SimpleDateFormat("HH", Locale.KOREA);
                    dates = datef.format(date);
                    sleep = Integer.parseInt(dates) * 60;
                    datef = new SimpleDateFormat("mm", Locale.KOREA);
                    dates = datef.format(date);
                    sleep += Integer.parseInt(dates);
                    user.endsleep = Integer.toString(sleep);
                    surveyValue = user.towMap();
                    childUpdates.put("User" + "/" + user.id, surveyValue);
                    mDatabaseRef.updateChildren(childUpdates);
                }
            } else if (time >= 7) {
                result = "측정된 수면 시간은 " + time + "시간으로 적당한 수면 시간입니다.\n";
                if (rate == 1)
                    result += "수면 권장 시간을 잘 지키고 있지만, 수면 시 문제가 있는 것 같으므로 전문의의 상담을 받아보세요.\n";
                else if (rate == 2)
                    result += "수면 권장 시간을 잘 지키고 있지만, 수면 시 문제가 있는 것 같으므로 전문의의 상담을 받아보세요.\n";
                else if (rate == 3)
                    result += "수면 권장 시간을 잘 지키고 있지만, 피로가 계속된다면 20분 더 일찍 잠에 들어보세요.\n";
                else if (rate == 4)
                    result += "수면 권장 시간을 잘 지키고 있지만, 피로가 계속된다면 10분 더 일찍 잠에 들어보세요.\n";
                else if (rate == 5){
                    result += "수면 권장 시간을 잘 지키고 있습니다!\n";
                    date = new Date(startsleep[6]);
                    datef = new SimpleDateFormat("HH", Locale.KOREA);
                    dates = datef.format(date);
                    sleep = Integer.parseInt(dates) * 60;
                    datef = new SimpleDateFormat("mm", Locale.KOREA);
                    dates = datef.format(date);
                    sleep += Integer.parseInt(dates);
                    user.startsleep = Integer.toString(sleep);
                    date = new Date(endsleep[6]);
                    datef = new SimpleDateFormat("HH", Locale.KOREA);
                    dates = datef.format(date);
                    sleep = Integer.parseInt(dates) * 60;
                    datef = new SimpleDateFormat("mm", Locale.KOREA);
                    dates = datef.format(date);
                    sleep += Integer.parseInt(dates);
                    user.endsleep = Integer.toString(sleep);
                    surveyValue = user.towMap();
                    childUpdates.put("User" + "/" + user.id, surveyValue);
                    mDatabaseRef.updateChildren(childUpdates);
                }
            } else if (time < 7) {
                result = "측정된 수면 시간은 " + time + "시간으로 부족합니다.\n";
                result += "현재의 수면 시간이 부족하므로, " + (420 - sleeptime[7]) + "분 더 일찍 잠을 자는 것이 권장됩니다.\n";
            }
        } else {
            result = "생년월일을 제대로 입력해주세요.";
            return;
        }
        float avarage = getAvarage(startsleep,endsleep);
        if ((avarage*100) < 70)
            result += "최근 일주일간 수면 시간에 비해 평균 수면 시간이 " + String.format("%.2f",avarage*100) + "% 불규칙적입니다. 규칙적인 수면이 필요합니다.\n";
        long sum = getStart(startsleep);
        date = new Date(startsleep[6]);
        datef = new SimpleDateFormat("HH", Locale.KOREA);
        dates = datef.format(date);
        int dd = Integer.parseInt(dates);
        if (sum != 0)
            if (sum <= 0) {
                if (dd - sum >= 24)
                    result += "최근 일주일간의 입면 시각은 " + (dd - sum - 24) + "시로 최근 입면 시각인 " + dd + "시보다 " + -sum + "시간 느립니다. 정해진 시간에 잠에 들 수 있도록 하는 것이 좋습니다.\n";
                else
                    result += "최근 일주일간의 입면 시각은 " + (dd - sum) + "시로 최근 입면 시각인 " + dd + "시보다 " + -sum + "시간 느립니다. 정해진 시간에 잠에 들 수 있도록 하는 것이 좋습니다.\n";
            } else {
                if (dd - sum < 0)
                    result += "최근 일주일간의 입면 시각은 " + (dd - sum + 24) + "시로 최근 입면 시각인 " + dd + "시보다 " + Math.abs(sum) + "시간 빠릅니다. 정해진 시간에 잠에 들 수 있도록 하는 것이 좋습니다.\n";
                else
                    result += "최근 일주일간의 입면 시각은 " + (dd - sum) + "시로 최근 입면 시각인 " + dd + "시보다 " + Math.abs(sum) + "시간 빠릅니다. 정해진 시간에 잠에 들 수 있도록 하는 것이 좋습니다.\n";
            }
        if ((endsleep[6] - startsleep[6]) - sleeptime[7] < 30)
            result += "수면 중에 깬적이 있으신가요? 과도한 피로나 만성 스트레스가 의심됩니다. 증상이 계속되면 전문의와 상담을 받는 것을 추천드립니다.\n";
        if (sleeptime[7] == 0)
            result += "잠을 자지 않으면 몸에 부담을 주게됩니다. 수면 부족으로 집중력이 떨어져 판단이 흐려질 수 있고, 다양한 질환이 발생할 확률이 매우 높아집니다.\n";
    }

    private long getStart(long[] ssb) {
        long sum = 0;
        long count = 0;
        long[] ss= new long[ssb.length];
        for (int i=0; i<ssb.length;i++) {
            ss[i]=ssb[i];
        }
        for (int i = 0; i < 7; i++)
            if (ss[i] != 0) {
                ss[i] -= 86400000 * i;
                count += 1;
            }
        for (int i = 0; i < 6; i++)
            if (ss[i] != 0) {
                    sum += ss[6] - ss[i];
            }
        return (sum/count)/3600000;
    }

    private float getAvarage(long[] ssb, long[] esb) {
        float p=0;
        int count = 0;
        long[] ss= new long[ssb.length];
        long[] es = new long[esb.length];
        for (int i=0; i<ssb.length;i++) {
            ss[i]=ssb[i];
        }
        for (int i=0; i<esb.length;i++) {
            es[i]=esb[i];
        }
        for (int i = 0; i < 7; i++)
            if (ss[i] != 0) {
                ss[i] -= 86400000 * i;
                es[i] -= 86400000 * i;
                count += 1;
            }
        for (int i = 0; i < 6; i++)
            if (ss[i] != 0) {
                if(es[i]<ss[6] || es[6]<ss[i])
                    p+=0;
                if(ss[6]<ss[i]) {
                    if (es[6] < es[i])
                        p += (float) (es[6] - ss[i]) / (float) (es[i] - ss[6]);
                    else
                        p += (float) (es[i] - ss[i]) / (float) (es[6] - ss[6]);
                }else{
                    if (es[6] < es[i])
                        p += (float) (es[6] - ss[6]) / (float) (es[i] - ss[i]);
                    else
                        p += (float) (es[i] - ss[6]) / (float) (es[6] - ss[i]);
                }
                count += 1;
            }

        p = p/count;
        return p;
    }

    private long getTimeOfToday() {
        Calendar today = Calendar.getInstance(TimeZone.getTimeZone("UTC"));
        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);

        return today.getTimeInMillis();
    }

    private void updateSleepStartView(final String count) {
        getActivity().runOnUiThread(() -> mStartTv.setText(count));
    }

    private void updateSleepStopView(final String count) {
        getActivity().runOnUiThread(() -> mEndTv.setText(count));
    }

    public int getAge(int year) {
        Calendar current = Calendar.getInstance();
        int currentYear = current.get(Calendar.YEAR);
        int age = currentYear - year;
        return age;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle
            savedInstanceState) {
        View v = inflater.inflate(R.layout.clock_view, container, false);
        mSleepReporter = new SleepReporter(MainActivity.mStore);
        mStartTv = v.findViewById(R.id.textView6);
        mEndTv = v.findViewById(R.id.textView7);
        tip = v.findViewById(R.id.textView8);
        ratingBar = v.findViewById(R.id.rbar1);
        mSleepReporter.start(mSleepObserver);
        age = getAge(1996);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar1, float rating, boolean fromUser) {
                rate = (int) Math.floor(rating);
                ratingBar.setIsIndicator(true);
                setResult();
                tip.setText(result);
            }
        });
        return v;
    }


}
