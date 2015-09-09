package com.github.xfumihiro.rxbinding.support.v7.widget;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import rx.Observable;
import rx.functions.Action1;

/**
 * Static factory methods for creating {@linkplain Observable observables} and {@linkplain Action1
 * actions} for {@link RecyclerView}.
 */
public final class RxRecyclerView {

  /**
   * Create an observable of the position of item clicks for {@code recyclerView}.
   * <p>
   * <em>Warning:</em> The created observable keeps a strong reference to {@code recyclerView}.
   * Unsubscribe to free this reference.
   */
  @CheckResult @NonNull public static Observable<Integer> RxRecyclerViewItemClicks(
      @NonNull RecyclerView recyclerView) {
    return Observable.create(new RecyclerViewItemClickOnSubscribe(recyclerView));
  }

  private RxRecyclerView() {
    throw new AssertionError("No instances.");
  }
}
