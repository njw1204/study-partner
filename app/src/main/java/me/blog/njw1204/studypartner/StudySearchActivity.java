package me.blog.njw1204.studypartner;

import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.EditText;

public class StudySearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("스터디 검색");
        applyFontAwesome();
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

    private void applyFontAwesome() {
        Typeface fas = Typeface.createFromAsset(getAssets(), "fa_solid_900.ttf");
        int[] editTextIdArrForFas = {
            R.id.edittext_search_real
        };

        for (int i : editTextIdArrForFas) {
            EditText editText = findViewById(i);
            editText.setTypeface(fas);
        }
    }
}
