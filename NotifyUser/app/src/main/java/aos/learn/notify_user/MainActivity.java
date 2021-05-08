package aos.learn.notify_user;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;

import com.google.android.material.snackbar.Snackbar;

import java.text.SimpleDateFormat;
import java.util.Date;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "cy.act.main";
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // bind click listener to all view
        ViewGroup root = findViewById(R.id.ll_buttons);
        for (int i = 0; i < root.getChildCount(); i++) {
            root.getChildAt(i).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_toast:
                showToastMessage("Example message");
                break;
            case R.id.btn_snackbar:
                showSnackbarMessage("Snackbar message");
                break;
            case R.id.btn_dialog:
                showAlertDialog("Example", "Example AlertDialog message");
                break;
            case R.id.btn_notification:
                showNotification("Example", "Example notification message!");
                break;
            default:
                Log.i(TAG, "in progress");
        }
    }

    private void showToastMessage(String message){
        Toast toast = Toast.makeText(this, message, Toast.LENGTH_SHORT);
        toast.show();
    }

    private void showSnackbarMessage(String message){
        View containerView = findViewById(R.id.container);
        Snackbar snackbar = Snackbar.make(containerView, message, Snackbar.LENGTH_SHORT);
        snackbar.setAction("Undo", v->showToastMessage("undo clicked"));
        snackbar.show();
    }

    private void showAlertDialog(String title, String message){
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(title)
                .setMessage(message)
                .setPositiveButton("Confirm", (dialog, which) -> showToastMessage("confirm clicked"))
                .setNegativeButton("Cancel",  (dialog, which) -> showToastMessage("cancel clicked"))
                .setCancelable(true)
                .show();
    }

    private void showNotification(String title, String message){
        // intent to open activity when notification clicked
        Intent toInfo = new Intent(this, InfoActivity.class);
        toInfo.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        toInfo.putExtra("created_at", sdf.format(new Date()));
        toInfo.putExtra("created_by", this.getClass().getSimpleName());
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, toInfo, PendingIntent.FLAG_UPDATE_CURRENT);

        // build notification
        Notification notification = new NotificationCompat.Builder(this, ExampleApplication.CHANNEL_ID)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentTitle(title)
                .setContentText(message)
                .setContentIntent(pendingIntent)
                .setDefaults(Notification.DEFAULT_SOUND | Notification.DEFAULT_VIBRATE)
                .setPriority(Notification.PRIORITY_MAX)
                .build();
        // show notification
        NotificationManager notificationManager = getSystemService(NotificationManager.class);
        notificationManager.notify(1, notification);
    }
}