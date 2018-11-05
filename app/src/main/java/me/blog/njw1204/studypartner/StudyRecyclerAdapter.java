package me.blog.njw1204.studypartner;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestManager;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;
import java.util.Locale;

public class StudyRecyclerAdapter extends RecyclerView.Adapter<StudyRecyclerAdapter.StudyItemViewHolder> {
    private ArrayList<StudyItem> items;
    private RequestManager requestManager;

    public StudyRecyclerAdapter(ArrayList<StudyItem> items, RequestManager requestManager) {
        this.items = items;
        this.requestManager = requestManager;
    }

    @NonNull
    @Override
    public StudyItemViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.study_item, viewGroup, false);
        return new StudyItemViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull StudyItemViewHolder studyItemViewHolder, int i) {
        StudyItem item = items.get(i);
        studyItemViewHolder.title.setText(
            item.isStaff()
                ? String.format("[관리] %s", item.getTitle()) : item.getTitle()
        );
        if (CUtils.IsPseudoEmpty(item.getKind()))
            studyItemViewHolder.kind.setText("기타");
        else
            studyItemViewHolder.kind.setText(CUtils.NNNull(item.getKind()));
        if (CUtils.IsPseudoEmpty(item.getSchool()))
            studyItemViewHolder.school.setText("");
        else
            studyItemViewHolder.school.setText(String.format("/%s", item.getSchool()));
        studyItemViewHolder.cnt.setText(String.format(Locale.KOREA, "%d명", item.getCnt()));
        RequestOptions options = new RequestOptions()
                                     .centerCrop()
                                     .placeholder(R.drawable.book)
                                     .error(R.drawable.book);
        requestManager.load(item.getIcon()).apply(options).into(studyItemViewHolder.icon);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public class StudyItemViewHolder extends RecyclerView.ViewHolder {
        private ImageView icon;
        private TextView title, kind, school, cnt;

        public StudyItemViewHolder(@NonNull View itemView) {
            super(itemView);
            icon = itemView.findViewById(R.id.imageview_study_icon_item);
            title = itemView.findViewById(R.id.textview_study_title_item);
            kind = itemView.findViewById(R.id.textview_kind_item);
            school = itemView.findViewById(R.id.textview_school_item);
            cnt = itemView.findViewById(R.id.textview_people_cnt_item);
        }
    }
}
