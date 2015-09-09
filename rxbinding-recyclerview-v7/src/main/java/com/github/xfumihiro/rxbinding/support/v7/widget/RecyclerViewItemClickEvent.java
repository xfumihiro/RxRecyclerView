package com.github.xfumihiro.rxbinding.support.v7.widget;

import android.support.annotation.CheckResult;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import com.jakewharton.rxbinding.view.ViewEvent;

public class RecyclerViewItemClickEvent extends ViewEvent<RecyclerView> {
  private final View child;

  @CheckResult @NonNull
  public static RecyclerViewItemClickEvent create(@NonNull RecyclerView view, @NonNull View child) {
    return new RecyclerViewItemClickEvent(view, child);
  }

  private RecyclerViewItemClickEvent(@NonNull RecyclerView view, @NonNull View child) {
    super(view);
    this.child = child;
  }

  @Override public boolean equals(Object o) {
    if (o == this) return true;
    if (!(o instanceof RecyclerViewItemClickEvent)) return false;
    RecyclerViewItemClickEvent other = (RecyclerViewItemClickEvent) o;
    return other.view() == view()
        && other.child() == child();
  }

  @Override public int hashCode() {
    int result = 17;
    result = result * 37 + view().hashCode();
    result = result * 37 + child().hashCode();
    return result;
  }

  @Override public String toString() {
    return "RecyclerViewItemClickEvent{view="
        + view()
        + ", child="
        + child()
        + '}';
  }

  /** The child from which this event occurred. */
  @NonNull public final View child() {
    return child;
  }
}
