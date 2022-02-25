package cosmo.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

import java.io.File;


/*
   This is a the broadcast receiver responsible for generating the file with the
   code coverage.
*/
public class EndCoverageBroadcast extends BroadcastReceiver {
    public static String TAG = "JacocoInstrumenter";

    private void generateCoverageReport(String coverageFile) {
        Log.d(TAG, "EndCoverageBroadcast received, generating coverage report in " + coverageFile);
        try {
            Class.forName("com.vladium.emma.rt.RT")
                    .getMethod("dumpCoverageData", File.class, boolean.class)
                    .invoke(null, new File(coverageFile), true);
        } catch (Exception e) {
            Log.d(TAG, "EndCoverageBroadcast threw an exception while generating the coverage report", e);
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
        try {
            final String coverageFile = new File(context.getFilesDir(), "coverage.ec").toString();
            generateCoverageReport(coverageFile);
            Toast.makeText(context, "Coverage report generated in " + coverageFile, Toast.LENGTH_LONG).show();
        } catch (Exception e) {
            Log.d(TAG, "EndCoverageBroadcast threw an exception while generating the coverage report", e);
        }
    }
}
