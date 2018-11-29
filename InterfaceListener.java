package com.test.ben.hyperproject;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;



public class InterfaceListener implements RecyclerView.OnItemTouchListener {

    private OnItemClickListener Listener;

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    GestureDetector GestureDetector;

    public InterfaceListener(Context context, OnItemClickListener listener) {
        Listener = listener;
        GestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return true;
            }
        });
    }

    @Override
    public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
        View childView = view.findChildViewUnder(e.getX(), e.getY());
        if (childView != null && Listener != null && GestureDetector.onTouchEvent(e)) {
            Listener.onItemClick(childView, view.getChildAdapterPosition(childView));
        }
        return false;
    }

    @Override
    public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
    }

    @Override
    public void onRequestDisallowInterceptTouchEvent(boolean b) {

    }


}

