package com.example.capston_project;

import com.samsung.android.sdk.healthdata.HealthConstants;
import com.samsung.android.sdk.healthdata.HealthData;
import com.samsung.android.sdk.healthdata.HealthDataObserver;
import com.samsung.android.sdk.healthdata.HealthDataResolver;
import com.samsung.android.sdk.healthdata.HealthDataStore;
import com.samsung.android.sdk.healthdata.HealthResultHolder;

import java.util.Calendar;
import java.util.TimeZone;

public class BloodglucoseReporter {
    private final HealthDataStore mStore;
    private BloodglucoseObserver mBloodglucoseObserver;
    private static final long ONE_DAY_IN_MILLIS = 24 * 60 * 60 * 1000L;

    public BloodglucoseReporter(HealthDataStore store) {
        mStore = store;
    }

    public void start(BloodglucoseObserver listener) {
        mBloodglucoseObserver = listener;
        // Register an observer to listen changes of step count and get today step count
        HealthDataObserver.addObserver(mStore, HealthConstants.BloodGlucose.HEALTH_DATA_TYPE, mObserver);
        readTodayBloodglucoseCount();
    }

    // Read the today's step count on demand
    private void readTodayBloodglucoseCount() {
        HealthDataResolver resolver = new HealthDataResolver(mStore, null);

        // Set time range from start time of today to the current time
        long startTime = getStartTimeOfToday();
        long endTime = startTime + ONE_DAY_IN_MILLIS;

        HealthDataResolver.ReadRequest request = new HealthDataResolver.ReadRequest.Builder()
                .setDataType(HealthConstants.BloodGlucose.HEALTH_DATA_TYPE)
                .setProperties(new String[] {HealthConstants.BloodGlucose.GLUCOSE})
                .setLocalTimeRange(HealthConstants.BloodGlucose.START_TIME, HealthConstants.BloodGlucose.TIME_OFFSET,
                        startTime, endTime)
                .build();
        resolver.read(request).setResultListener(mListener);
    }

    private long getStartTimeOfToday() {
        Calendar today = Calendar.getInstance(TimeZone.getTimeZone("UTC"));

        today.set(Calendar.HOUR_OF_DAY, 0);
        today.set(Calendar.MINUTE, 0);
        today.set(Calendar.SECOND, 0);
        today.set(Calendar.MILLISECOND, 0);

        return today.getTimeInMillis();
    }

    private final HealthResultHolder.ResultListener<HealthDataResolver.ReadResult> mListener = result -> {
        int count = 0;

        try {
            for (HealthData data : result) {
                count = Math.round(data.getFloat(HealthConstants.BloodGlucose.GLUCOSE)*18);
            }
        } finally {
            result.close();
        }

        if (mBloodglucoseObserver != null) {
            mBloodglucoseObserver.onChanged(count);
        }
    };

    private final HealthDataObserver mObserver = new HealthDataObserver(null) {

        @Override
        public void onChange(String dataTypeName) {
            readTodayBloodglucoseCount();
        }
    };

    public interface BloodglucoseObserver {
        void onChanged(int count);
    }
}

