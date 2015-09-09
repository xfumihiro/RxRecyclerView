package com.github.xfumihiro.rxbinding.support.v7.widget;

import android.app.Instrumentation;
import android.support.test.InstrumentationRegistry;
import android.support.test.espresso.Espresso;
import android.support.test.espresso.ViewInteraction;
import android.support.test.espresso.matcher.ViewMatchers;
import android.support.test.rule.ActivityTestRule;
import android.support.test.runner.AndroidJUnit4;
import android.support.v7.widget.RecyclerView;
import com.github.xfumihiro.rxbinding.RecordingObserver;
import com.github.xfumihiro.rxbinding.ViewDirtyIdlingResource;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.runner.RunWith;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;

import static com.google.common.truth.Truth.assertThat;

@RunWith(AndroidJUnit4.class)
public final class RxRecyclerViewTest {
  @Rule public final ActivityTestRule<RxRecyclerViewTestActivity> activityRule =
      new ActivityTestRule<>(RxRecyclerViewTestActivity.class);

  private final Instrumentation instrumentation = InstrumentationRegistry.getInstrumentation();

  private RecyclerView view;
  private ViewInteraction interaction;
  private ViewDirtyIdlingResource viewDirtyIdler;

  @Before public void setUp() {
    RxRecyclerViewTestActivity activity = activityRule.getActivity();
    view = activity.recyclerView;
    interaction = Espresso.onView(ViewMatchers.withId(android.R.id.primary));
    viewDirtyIdler = new ViewDirtyIdlingResource(activity);
    Espresso.registerIdlingResources(viewDirtyIdler);
  }

  @After public void tearDown() {
    Espresso.unregisterIdlingResources(viewDirtyIdler);
  }

  @Test public void itemClicks() {
    RecordingObserver<Integer> o = new RecordingObserver<>();
    Subscription subscription = RxRecyclerView.RxRecyclerViewItemClicks(view)
        .subscribeOn(AndroidSchedulers.mainThread())
        .subscribe(o);
    o.assertNoMoreEvents();

    instrumentation.runOnMainSync(new Runnable() {
      @Override public void run() {
        view.findViewHolderForLayoutPosition(1).itemView.performClick();
      }
    });
    assertThat(o.takeNext()).isEqualTo(1);

    instrumentation.runOnMainSync(new Runnable() {
      @Override public void run() {
        view.smoothScrollToPosition(50);
        view.findViewHolderForLayoutPosition(5).itemView.performClick();
      }
    });
    assertThat(o.takeNext()).isEqualTo(5);

    subscription.unsubscribe();

    instrumentation.runOnMainSync(new Runnable() {
      @Override public void run() {
        view.smoothScrollToPosition(50);
        view.findViewHolderForLayoutPosition(5).itemView.performClick();
      }
    });
    o.assertNoMoreEvents();
  }

  @Test public void itemClickEvents() {
    RecordingObserver<RecyclerViewItemClickEvent> o = new RecordingObserver<>();
    Subscription subscription = RxRecyclerView.RxRecyclerViewItemClickEvents(view)
        .subscribeOn(AndroidSchedulers.mainThread())
        .subscribe(o);
    o.assertNoMoreEvents();

    instrumentation.runOnMainSync(new Runnable() {
      @Override public void run() {
        view.findViewHolderForLayoutPosition(1).itemView.performClick();
      }
    });
    assertThat(o.takeNext()).isEqualTo(RecyclerViewItemClickEvent.create(view, view.findViewHolderForLayoutPosition(1).itemView));

    instrumentation.runOnMainSync(new Runnable() {
      @Override public void run() {
        view.smoothScrollToPosition(50);
        view.findViewHolderForLayoutPosition(5).itemView.performClick();
      }
    });
    assertThat(o.takeNext()).isEqualTo(RecyclerViewItemClickEvent.create(view, view.findViewHolderForLayoutPosition(5).itemView));

    subscription.unsubscribe();

    instrumentation.runOnMainSync(new Runnable() {
      @Override public void run() {
        view.smoothScrollToPosition(50);
        view.findViewHolderForLayoutPosition(5).itemView.performClick();
      }
    });
    o.assertNoMoreEvents();
  }
}
