package me.blog.njw1204.studypartner;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

public class StudyItemEventListener implements RecyclerView.OnItemTouchListener{
    private GestureDetector mGestureDetector;
    private ClickEvents clickEvents;

    public interface ClickEvents {
        void onItemSingleTapUp(View view, int position);
    };

    StudyItemEventListener(Context context, ClickEvents clickEvents) {
        this.mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener(){
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
        this.clickEvents = clickEvents;
    }

    @Override
    public boolean onInterceptTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {
        View childView = rv.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && mGestureDetector.onTouchEvent(e)) {
            clickEvents.onItemSingleTapUp(childView, rv.getChildAdapterPosition(childView));
            return true;
        }
        return false;
    }

    @Override
    public void onTouchEvent(@NonNull RecyclerView rv, @NonNull MotionEvent e) {

    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {

    }
}
