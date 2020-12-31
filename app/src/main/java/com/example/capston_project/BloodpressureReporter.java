package com.example.capston_project;

import com.samsung.android.sdk.healthdata.HealthConstants;
import com.samsung.android.sdk.healthdata.HealthData;
import com.samsung.android.sdk.healthdata.HealthDataObserver;
import com.samsung.android.sdk.healthdata.HealthDataResolver;
import com.samsung.android.sdk.healthdata.HealthDataStore;
import com.samsung.android.sdk.healthdata.HealthResultHolder;

import java.util.Calendar;
import java.util.TimeZone;

public class BloodpressureReporter {
    private final HealthDataStore mStore;
    private BloodpressureObserver mBloodpressureObserver;
    private static final long ONE_DAY_IN_MILLIS = 24 * 60 * 60 * 1000L;

    public BloodpressureReporter(HealthDataStore store) {
        mStore = store;
    }

    public void start(BloodpressureObserver listener) {
        mBloodpressureObserver = listener;
        // Register an observer to listen changes of step count and get today step count
        HealthDataObserver.addObserver(mStore, HealthConstants.BloodPressure.HEALTH_DATA_TYPE, mObserver);
        readTodayBloodpressureCount();
    }

    // Read the today's step count on demand
    private void readTodayBloodpressureCount() {
        HealthDataResolver resolver = new HealthDataResolver(mStore, null);

        // Set time range from start time of today to the current time
        long startTime = getStartTimeOfToday();
        long endTime = startTime + ONE_DAY_IN_MILLIS;

        HealthDataResolver.ReadRequest request = new HealthDataResolver.ReadRequest.Builder()
                .setDataType(HealthConstants.BloodPressure.HEALTH_DATA_TYPE)
                .setProperties(new String[] {HealthConstants.BloodPressure.DIASTOLIC ,HealthConstants.BloodPressure.SYSTOLIC})
                .setLocalTimeRange(HealthConstants.BloodPressure.START_TIME, HealthConstants.BloodPressure.TIME_OFFSET,
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
        int[] count = {0,0};
        try {
            for (HealthData data : result) {
                count[0] = data.getInt(HealthConstants.BloodPressure.DIASTOLIC);
                count[1] = data.getInt(HealthConstants.BloodPressure.SYSTOLIC);
            }
        } finally {
            result.close();
        }

        if (mBloodpressureObserver != null) {
            mBloodpressureObserver.onChanged(count);
        }
    };

    private final HealthDataObserver mObserver = new HealthDataObserver(null) {

        @Override
        public void onChange(String dataTypeName) {
            readTodayBloodpressureCount();
        }
    };

    public interface BloodpressureObserver {
        void onChanged(int[] count);
    }
}

