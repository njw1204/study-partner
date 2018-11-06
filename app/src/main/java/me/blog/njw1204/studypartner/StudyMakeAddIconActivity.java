package me.blog.njw1204.studypartner;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Matrix;
import android.media.ExifInterface;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.io.File;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class StudyMakeAddIconActivity extends AppCompatActivity {
    private String realPath = null;
    private final int REQ_CODE_SELECT_IMAGE = 1001;
    private StudyPartner application;

    @BindView(R.id.button_load_image) Button bLoad;
    @BindView(R.id.imageView) ImageView iIcon;
    @BindView(R.id.imageView2) ImageView iCover;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_make_add_icon);
        ButterKnife.bind(this);
        application = (StudyPartner)getApplication();

        getSupportActionBar().setTitle("스터디 대표 사진 추가");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        if (!CUtils.IsEmpty(application.getAddIconUrl())) {
            realPath = application.getAddIconUrl();
            RequestOptions options = new RequestOptions()
                                         .centerCrop();
            Glide.with(this)
                .load(new File(realPath))
                .apply(options)
                .into(iIcon);
            Glide.with(this)
                .load(new File(realPath))
                .apply(options)
                .into(iCover);
        }

        bLoad.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                intent.setType("image/*");
                startActivityForResult(intent, REQ_CODE_SELECT_IMAGE);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case android.R.id.home:
                finish();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQ_CODE_SELECT_IMAGE) {
            if (resultCode == Activity.RESULT_OK) {
                putPicture(data.getData());
            }
        }
    }

    private void putPicture(Uri imgUri) {
        realPath = getRealPathFromURI(imgUri);
        application.setAddIconUrl(realPath);

        RequestOptions options = new RequestOptions()
                                     .centerCrop();
        Glide.with(this)
            .load(new File(realPath))
            .apply(options)
            .into(iIcon);
        Glide.with(this)
            .load(new File(realPath))
            .apply(options)
            .into(iCover);
    }

    private String getRealPathFromURI(Uri contentUri) {
        if (contentUri == null) return null;
        int column_index = 0;
        String[] proj = {MediaStore.Images.Media.DATA};
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            column_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
        }
        return cursor.getString(column_index);
    }
}
