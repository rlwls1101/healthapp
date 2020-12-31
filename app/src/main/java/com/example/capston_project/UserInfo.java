package com.example.capston_project;

import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

public class UserInfo {
    String id;
    int sex;
    int age;
    float height;
    float weight;
    float BMI;
    String rate;//심박수
    String minpressure;//혈압
    String maxpressure;//혈압
    String glucose;//혈당
    int q1;
    int q2;
    int q3;
    int q4;
    int q5;
    int q6;
    int q7;
    int q8;
    String q9;
    int q10;
    int q11;
    int q12;
    int q13;
    int q14;
    int q15;
    int q16;
    String q17;
    String q18;
    String q19;
    String w1;
    int w2;
    int w3;
    int w4;
    double f1;
    double f2;
    double f3;
    int tipfavorite;
    int tipfavorite2;
    int tipfavorite3;
    String startsleep;
    String endsleep;
    String arrivalport;
    String departureport;
    String arrivaltime;
    String departuretime;
    String arrivalcountry;
    String departurecountry;
    public void setId(String id)
    {
        this.id=id;
    }

    @Exclude
    public Map<String, Object> towMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("id", id);
        result.put("rate", rate);
        result.put("minpressure", minpressure );
        result.put("maxpressure", maxpressure );
        result.put("glucose", glucose);
        result.put("q1", q1);
        result.put("q2", q2);
        result.put("q3", q3);
        result.put("q4", q4);
        result.put("q5", q5);
        result.put("q6", q6);
        result.put("q7", q7);
        result.put("q8", q8);
        result.put("q9", q9);
        result.put("q10", q10);
        result.put("q11", q11);
        result.put("q12", q12);
        result.put("q13", q13);
        result.put("q14", q14);
        result.put("q15", q15);
        result.put("q16", q16);
        result.put("q17", q17);
        result.put("q18", q18);
        result.put("q19", q19);
        result.put("w1", w1);
        result.put("w2",w2);
        result.put("w3",w3);
        result.put("w4",w4);
        result.put("f1", f1);
        result.put("f2",f2);
        result.put("f3",f3);
        result.put("age",age);
        result.put("sex",sex);
        result.put("height",height);
        result.put("weight",weight);
        result.put("tipfavorite",tipfavorite);
        result.put("tipfavorite2",tipfavorite2);
        result.put("tipfavorite3",tipfavorite3);
        result.put("startsleep",startsleep);
        result.put("endsleep",endsleep);
        result.put("arrivalport", arrivalport);
        result.put("departureport",departureport);
        result.put("arrivaltime",arrivaltime);
        result.put("departuretime",departuretime);
        result.put("arrivalcountry",arrivalcountry);
        result.put("departurecountry", departurecountry);
        return result;
    }

    public String getQ9() {
        return q9;
    }

    public float getBMI() {
        return BMI;
    }

    public float getHeight() {
        return height;
    }

    public float getWeight() {
        return weight;
    }

    public int getAge() {
        return age;
    }

    public int getQ1() {
        return q1;
    }

    public int getQ2() {
        return q2;
    }

    public int getQ3() {
        return q3;
    }

    public int getQ4() {
        return q4;
    }

    public int getQ5() {
        return q5;
    }

    public int getQ6() {
        return q6;
    }

    public int getQ7() {
        return q7;
    }

    public int getQ8() {
        return q8;
    }

    public int getQ10() {
        return q10;
    }

    public int getQ11() {
        return q11;
    }

    public int getQ12() {
        return q12;
    }

    public int getQ13() {
        return q13;
    }

    public int getQ14() {
        return q14;
    }

    public int getQ15() {
        return q15;
    }

    public String getQ18() {
        return q18;
    }

    public int getQ16() {
        return q16;
    }

    public int getSex() {
        return sex;
    }

    public String getGlucose() {
        return glucose;
    }

    public String getId() {
        return id;
    }

    public String getMaxpressure() {
        return maxpressure;
    }

    public String getMinpressure() {
        return minpressure;
    }

    public String getQ17() {
        return q17;
    }

    public String getRate() {
        return rate;
    }

    public double getF1() {
        return f1;
    }

    public double getF2() {
        return f2;
    }

    public double getW2() {
        return w2;
    }

    public double getF3() {
        return f3;
    }

    public int getW3() {
        return w3;
    }

    public int getW4() {
        return w4;
    }

    public String getW1() {
        return w1;
    }

    public String getQ19() {
        return q19;
    }

    public int getTipfavorite() {
        return tipfavorite;
    }

    public int getTipfavorite2() {
        return tipfavorite2;
    }

    public int getTipfavorite3() {
        return tipfavorite3;
    }

    public String getstartsleep(){ return startsleep;}

    public String getendsleep(){return endsleep;}

    public String getarrivalport(){return arrivalport; }

    public String getdepartureport(){return departureport;}

    public String getarrivaltime(){return arrivaltime;}

    public String getdeparturetime(){return departuretime;}

    public String getarrivalcountry(){return arrivalcountry;}

    public String getdeparturecountry(){return departurecountry;}

}
