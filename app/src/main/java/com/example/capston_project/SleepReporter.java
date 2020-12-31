package com.example.capston_project;

import com.samsung.android.sdk.healthdata.HealthConstants;
import com.samsung.android.sdk.healthdata.HealthData;
import com.samsung.android.sdk.healthdata.HealthDataObserver;
import com.samsung.android.sdk.healthdata.HealthDataResolver;
import com.samsung.android.sdk.healthdata.HealthDataStore;
import com.samsung.android.sdk.healthdata.HealthResultHolder;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;

public class SleepReporter {
    private final HealthDataStore mStore;
    private SleepObserver mSleepObserver;
    private static final long ONE_DAY_IN_MILLIS = 24 * 60 * 60 * 1000L;

    public SleepReporter(HealthDataStore store) {
        mStore = store;
    }

    public void start(SleepObserver listener) {
        mSleepObserver = listener;
        // Register an observer to listen changes of step count and get today step count
        HealthDataObserver.addObserver(mStore, HealthConstants.Sleep.HEALTH_DATA_TYPE, mObserver);
        readTodaySleepCount();
    }

    // Read the today's step count on demand
    private void readTodaySleepCount() {
        HealthDataResolver resolver = new HealthDataResolver(mStore, null);

        // Set time range from start time of today to the current time
        long startTime = getStartTimeOfToday();
        long endTime = startTime + ONE_DAY_IN_MILLIS*8;

        HealthDataResolver.ReadRequest request = new HealthDataResolver.ReadRequest.Builder()
                .setDataType(HealthConstants.Sleep.HEALTH_DATA_TYPE)
                .setProperties(new String[] {HealthConstants.Sleep.START_TIME, HealthConstants.Sleep.END_TIME})
                .setLocalTimeRange(HealthConstants.Sleep.START_TIME, HealthConstants.Sleep.TIME_OFFSET,
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

        return today.getTimeInMillis()-637200000;
    }

    private final HealthResultHolder.ResultListener<HealthDataResolver.ReadResult> mListener = result -> {
        List<long[]> array = new ArrayList<long[]>();
        try {
            for (HealthData data : result) {
                long[] count = {0,0};
                count[0] = data.getLong(HealthConstants.Sleep.START_TIME);
                count[1] = data.getLong(HealthConstants.Sleep.END_TIME);
                array.add(count);
            }
        } finally {
            result.close();
        }

        if (mSleepObserver != null) {
            mSleepObserver.onChanged(array);
        }
    };

    private final HealthDataObserver mObserver = new HealthDataObserver(null) {

        @Override
        public void onChange(String dataTypeName) {
            readTodaySleepCount();
        }
    };

    public interface SleepObserver {
        void onChanged(List<long[]> array);
    }
}

