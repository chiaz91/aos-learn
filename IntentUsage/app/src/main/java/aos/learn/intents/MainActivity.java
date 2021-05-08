package aos.learn.intents;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ClipData;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.viewpager.widget.ViewPager;

import java.util.ArrayList;
import java.util.Locale;

import aos.learn.intents.adapter.PreviewPagerAdapter;

public class MainActivity extends InfoActivity implements View.OnClickListener {
    private static final String TAG = "cy.act.main";
    private static final int REQ_TAKE_PHOTO = 1001;
    private static final int REQ_PICK_PHOTO = 1002;
    private static final int REQ_PICK_FILE = 1003;
    private static final int REQ_SPEECH_INPUT = 1004;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ViewGroup root = findViewById(R.id.ll_container);
        for (int i=0; i<root.getChildCount() ; i++) {
            root.getChildAt(i).setOnClickListener(this);
        }
    }

    @Override
    public void onClick(View v) {
        try{
            switch (v.getId()){
                case R.id.btn_take_photo:
                    Intent takePhoto = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    startActivityForResult(takePhoto, REQ_TAKE_PHOTO);
                    break;

                case R.id.btn_pick_photo:
//                  Intent pickPhoto = new Intent(Intent.ACTION_GET_CONTENT);
                    Intent pickPhoto = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    pickPhoto.setType("image/*");
                    pickPhoto.putExtra(Intent.EXTRA_ALLOW_MULTIPLE, true);
                    startActivityForResult(Intent.createChooser(pickPhoto, "Select Picture"), REQ_PICK_PHOTO);
                    break;

                case R.id.btn_pick_file: // READ FILE NEED PERMISSION
                    Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
                    intent.setType("file/*");
                    startActivityForResult(intent, REQ_PICK_FILE);
                    break;

                case R.id.btn_email:
                    Intent email = new Intent(Intent.ACTION_SENDTO);
                    email.setData(Uri.parse("mailto:"));
                    email.putExtra(Intent.EXTRA_EMAIL, new String[]{"cy@gmail.com", "cy@outlook.com"});
                    email.putExtra(Intent.EXTRA_SUBJECT, "App feedback");
                    email.putExtra(Intent.EXTRA_TEXT, "Email Body");
                    startActivity(email);
                    break;

                case R.id.btn_url:
                    Intent toUrl = new Intent(Intent.ACTION_VIEW);
                    toUrl.setData(Uri.parse("http://www.example.com"));
                    startActivity(toUrl);
                    break;

                case R.id.btn_voice_recognition:
                    Intent voiceRecognition = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                    voiceRecognition.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                    voiceRecognition.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
                    voiceRecognition.putExtra(RecognizerIntent.EXTRA_PROMPT, "Voice Recognition");
                    voiceRecognition.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS,3 );
//                    voiceRecognition.putExtra(RecognizerIntent.EXTRA_CONFIDENCE_SCORES, true);
                    startActivityForResult(voiceRecognition, REQ_SPEECH_INPUT);
                    break;

                //  OTHER USAGE LIKE MONITORING SYSTEM EVENT
            }
        }catch (ActivityNotFoundException e){
            e.printStackTrace();
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if (resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case REQ_TAKE_PHOTO:
                    Bundle extras = data.getExtras();
                    Bitmap imageBitmap = (Bitmap) extras.get("data");
                    showPreviewDialog(imageBitmap);
                    break;

                case REQ_PICK_PHOTO:
                    if (data.getClipData()!=null){
                        ClipData clipData = data.getClipData();
                        Uri[] uris = new Uri[clipData.getItemCount()];
                        for (int i=0; i<clipData.getItemCount() ; i++) {
                            uris[i] = clipData.getItemAt(i).getUri();
                        }
                        showPreviewDialog(uris);
                    } else if(data.getData()!=null){
                        showPreviewDialog(data.getData());
                    }
                    break;

                case REQ_SPEECH_INPUT:
                    ArrayList<String> messages = data.getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                    showRecognisedText(messages);
                    break;
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void showPreviewDialog(Bitmap img){
        Log.i(TAG,"showPreviewDialog");
        ImageView imgView = new ImageView(this);
        imgView.setImageBitmap(img);

        new AlertDialog.Builder(this)
                .setTitle("Preview")
                .setView(imgView)
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showPreviewDialog(Uri... imgUris){
        ViewPager pager = (ViewPager) LayoutInflater.from(this).inflate(R.layout.layout_previews, null, false);
        PreviewPagerAdapter adapter = new PreviewPagerAdapter(imgUris);
        pager.setAdapter(adapter);

        new AlertDialog.Builder(this)
                .setTitle("Preview")
                .setView(pager)
                .setNegativeButton("Cancel", null)
                .show();
    }

    private void showRecognisedText(ArrayList<String> messages) {
        final ArrayAdapter adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, messages);

        new AlertDialog.Builder(this)
                .setTitle("Recognition results")
                .setAdapter( adapter, (dialog, which)->{
                    Toast.makeText(MainActivity.this, "clicked "+messages.get(which), Toast.LENGTH_SHORT).show();
                })
                .show();
    }
}