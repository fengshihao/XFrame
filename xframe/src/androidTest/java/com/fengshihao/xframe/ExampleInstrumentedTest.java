package com.fengshihao.xframe;

import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;

import android.content.Context;
import android.support.test.InstrumentationRegistry;
import android.support.test.runner.AndroidJUnit4;
import android.util.Log;

import com.fengshihao.xframe.logic.rxtool.Task;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;

import static org.junit.Assert.assertEquals;

/**
 * Instrumented test, which will execute on an Android device.
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
@RunWith(AndroidJUnit4.class)
public class ExampleInstrumentedTest {
  private static final String TAG = "ExampleInstrumentedTest";

  @Test
  public void useAppContext() {
    // Context of the app under test.
    Context appContext = InstrumentationRegistry.getTargetContext();

    assertEquals("com.fengshihao.xframe.test", appContext.getPackageName());
  }

  @Test
  public void testRxTool() {
    Observable<Task<String>> observable = Observable.create(emitter -> {

    });

    Observable<String> string1 = Observable.range(0, 100)
        .doOnNext(i -> {
              Log.d(TAG, "string1: i=" + i);
              Thread.sleep(20);
            }
        )
        .map(i -> "string 1 i=" + i)
        .subscribeOn(Schedulers.io());

    Observable<String> string2 = Observable.range(0, 10)
        .doOnNext(i -> {
          Log.d(TAG, "string2: i=" + i);
          Thread.sleep(20);
        })
        .map(i -> "string 2 i=" + i)
        .subscribeOn(Schedulers.computation());

    Observable<String> string3 = Observable.range(0, 10)
        .doOnNext(i -> {
          Log.d(TAG, "string3: i=" + i);
          Thread.sleep(20);
        })
        .map(i -> "string 3 i=" + i);


    Observable<String> test = Observable.just("empty test");

    test = Observable
        .merge(
            string1.throttleLast(40, TimeUnit.MILLISECONDS),
            string2.throttleLast(50, TimeUnit.MILLISECONDS));

    test = string1.sample(40, TimeUnit.MILLISECONDS, true);

    test = Observable.<String>just("hello").switchIfEmpty(string1);

    test.observeOn(AndroidSchedulers.mainThread())
        .blockingSubscribe(result -> {
          Log.d(TAG, "subscribe result = [" + result + "]");
        }, err -> {
          Log.e(TAG, "subscribe: err", err);
        });
  }

  @Test
  public void testRxRetrofit() {
    Retrofit retrofit = new Retrofit.Builder()
        .baseUrl("https://example.com/")
        .addConverterFactory(GsonConverterFactory.create())
        .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
        .build();
    MyService service = retrofit.create(MyService.class);
    service.getUser().subscribe();
  }

  interface MyService {
    @GET("/user")
    Observable<String> getUser();
  }
}
