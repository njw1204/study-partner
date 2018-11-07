package me.blog.njw1204.studypartner;

import android.content.Intent;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StudySearchActivity extends AppCompatActivity {
    private StudyPartner application;
    private ArrayList<StudyItem> items, foundItems;
    private Typeface fas;
    @BindView(R.id.edittext_search_real) EditText eSearch;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_study_search);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("스터디 검색");
        ButterKnife.bind(this);

        application = (StudyPartner)getApplication();
        items = application.getStudyListItems();
        foundItems = new ArrayList<>();

        fas = Typeface.createFromAsset(getAssets(), "fa_solid_900.ttf");
        applyFontAwesome();

        final StudyRecyclerAdapter adapter = new StudyRecyclerAdapter(foundItems, Glide.with(this));
        final RecyclerView recyclerView = findViewById(R.id.recyclerview_study_search_list);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addOnItemTouchListener(new StudyItemEventListener(this, new StudyItemEventListener.ClickEvents() {
            @Override
            public void onItemSingleTapUp(View view, int position) {
                Intent intent = new Intent(StudySearchActivity.this, StudyInfoActivity.class);
                intent.putExtra("id", StudySearchActivity.this.getIntent().getStringExtra("id"));
                intent.putExtra("pw", StudySearchActivity.this.getIntent().getStringExtra("pw"));
                intent.putExtra("my_school", StudySearchActivity.this.getIntent().getStringExtra("my_school"));
                intent.putExtra("my_nick", StudySearchActivity.this.getIntent().getStringExtra("my_nick"));
                intent.putExtra("title", foundItems.get(position).getTitle());
                intent.putExtra("kind", foundItems.get(position).getKind());
                intent.putExtra("school", foundItems.get(position).getSchool());
                intent.putExtra("area", foundItems.get(position).getArea());
                intent.putExtra("study_no", foundItems.get(position).getStudy_no());
                intent.putExtra("icon", foundItems.get(position).getIcon());
                intent.putExtra("show_icon", !CUtils.IsPseudoEmpty(foundItems.get(position).getIcon()));
                startActivity(intent);
            }
        }));

        eSearch.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.length() > 0) {
                    findViewById(R.id.layout_search_setting).setVisibility(View.INVISIBLE);
                    findViewById(R.id.layout_search_result).setVisibility(View.VISIBLE);
                    foundItems.clear();
                    for (StudyItem item : items) {
                        if (item.getTitle().replace(" ", "").contains(s.toString().replace(" ", ""))) {
                            RadioButton radioButton1 = findViewById(((ToggleButtonGroupTableLayout)findViewById(R.id.radiogroup1)).getCheckedRadioButtonId());
                            if (radioButton1.getText().equals("전체") || radioButton1.getText().equals(item.getKind())) {
                                RadioButton radioButton2 = findViewById(((ToggleButtonGroupTableLayout)findViewById(R.id.radiogroup2)).getCheckedRadioButtonId());
                                if (radioButton2.getText().equals("전체") || radioButton2.getText().equals(item.getArea()) ||
                                    radioButton2.getText().equals("기타") && CUtils.IsPseudoEmpty(item.getArea())) {
                                    boolean rSchool0 = ((RadioButton)findViewById(R.id.radioButton31)).isChecked();
                                    boolean rSchool1 = ((RadioButton)findViewById(R.id.radioButton32)).isChecked();
                                    boolean rSchool2 = ((RadioButton)findViewById(R.id.radioButton33)).isChecked();
                                    if (CUtils.IsEmpty(getIntent().getStringExtra("my_school")) || rSchool0 ||
                                            rSchool1 && item.getSchool().equals(getIntent().getStringExtra("my_school")) ||
                                            rSchool2 && !item.getSchool().equals(getIntent().getStringExtra("my_school"))) {
                                        foundItems.add(item);
                                    }
                                }
                            }
                        }
                    }
                    adapter.notifyDataSetChanged();
                }
                else {
                    findViewById(R.id.layout_search_setting).setVisibility(View.VISIBLE);
                    findViewById(R.id.layout_search_result).setVisibility(View.INVISIBLE);
                }
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
    protected void onPause() {
        super.onPause();
        overridePendingTransition(0, 0);
    }

    private void applyFontAwesome() {
        int[] editTextIdArrForFas = {
            R.id.edittext_search_real
        };

        for (int i : editTextIdArrForFas) {
            EditText editText = findViewById(i);
            editText.setTypeface(fas);
        }
    }
}
