package com.tatar.shoppinglist.utils.ui.recyclerview;

import android.graphics.Canvas;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.View;

import com.tatar.shoppinglist.ui.activeshoppinglistitems.ItemAdapter;

import timber.log.Timber;

public class RecyclerItemTouchHelper extends ItemTouchHelper.SimpleCallback {

    private RecyclerItemTouchHelperListener listener;

    public RecyclerItemTouchHelper(int dragDirs, int swipeDirs, RecyclerItemTouchHelperListener listener) {
        super(dragDirs, swipeDirs);
        this.listener = listener;
    }

    @Override
    public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
        return true;
    }

    @Override
    public void onSelectedChanged(RecyclerView.ViewHolder viewHolder, int actionState) {
        if (viewHolder != null) {
            final View foregroundView = ((ItemAdapter.ViewHolder) viewHolder).foregroundView;
            final View backgroundView = ((ItemAdapter.ViewHolder) viewHolder).backgroundView;
            backgroundView.setVisibility(View.VISIBLE);
            getDefaultUIUtil().onSelected(foregroundView);
            Timber.d("SWIPE: onSelectedChanged" );
        }
    }

    @Override
    public void onChildDrawOver(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        final View foregroundView = ((ItemAdapter.ViewHolder) viewHolder).foregroundView;
        getDefaultUIUtil().onDrawOver(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
        Timber.d("SWIPE: onChildDrawOver" );
    }

    @Override
    public void clearView(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
        final View foregroundView = ((ItemAdapter.ViewHolder) viewHolder).foregroundView;
        final View backgroundView = ((ItemAdapter.ViewHolder) viewHolder).backgroundView;
        backgroundView.setVisibility(View.INVISIBLE);
        getDefaultUIUtil().clearView(foregroundView);
        Timber.d("SWIPE: clearView" );
    }

    @Override
    public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        final View foregroundView = ((ItemAdapter.ViewHolder) viewHolder).foregroundView;
        getDefaultUIUtil().onDraw(c, recyclerView, foregroundView, dX, dY, actionState, isCurrentlyActive);
        Timber.d("SWIPE: onChildDraw" );
    }

    @Override
    public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
        listener.onSwiped(viewHolder, direction, viewHolder.getAdapterPosition());
    }

    @Override
    public int convertToAbsoluteDirection(int flags, int layoutDirection) {
        return super.convertToAbsoluteDirection(flags, layoutDirection);
    }

    public interface RecyclerItemTouchHelperListener {
        void onSwiped(RecyclerView.ViewHolder viewHolder, int direction, int position);
    }
}
