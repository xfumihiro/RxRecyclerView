package com.github.xfumihiro.rxbinding.support.v7.widget;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.jakewharton.rxbinding.internal.MainThreadSubscription;
import rx.Observable;
import rx.Subscriber;

import static com.jakewharton.rxbinding.internal.Preconditions.checkUiThread;

final class RecyclerViewItemClickEventOnSubscribe
    implements Observable.OnSubscribe<RecyclerViewItemClickEvent> {
  private final RecyclerView recyclerView;

  public RecyclerViewItemClickEventOnSubscribe(RecyclerView recyclerView) {
    this.recyclerView = recyclerView;
  }

  @Override public void call(final Subscriber<? super RecyclerViewItemClickEvent> subscriber) {
    checkUiThread();

    final RecyclerView.OnChildAttachStateChangeListener childAttachStateChangeListener =
        new RecyclerView.OnChildAttachStateChangeListener() {
          @Override public void onChildViewAttachedToWindow(View view) {
            recyclerView.getChildViewHolder(view).itemView.setOnClickListener(
                new View.OnClickListener() {
                  @Override public void onClick(View child) {
                    if (!subscriber.isUnsubscribed()) {
                      subscriber.onNext(RecyclerViewItemClickEvent.create(recyclerView, child));
                    }
                  }
                });
          }

          @Override public void onChildViewDetachedFromWindow(View view) {
          }
        };

    recyclerView.addOnChildAttachStateChangeListener(childAttachStateChangeListener);
    recyclerView.swapAdapter(recyclerView.getAdapter(), true);

    subscriber.add(new MainThreadSubscription() {
      @Override protected void onUnsubscribe() {
        recyclerView.removeOnChildAttachStateChangeListener(childAttachStateChangeListener);
      }
    });
  }
}
