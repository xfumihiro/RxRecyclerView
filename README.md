Experimental binding for RxRecyclerView
=======================================

See [this issue](https://github.com/JakeWharton/RxBinding/pull/115)



Download
--------

from jcenter or maven
```groovy
compile 'com.github.xfumihiro.rxbinding:rxbinding-recyclerview-v7:0.1.0'
```




Usage
-----

Inorder to use the library along with RxBinding, binding method `RxRecyclerViewItemClicks` should be statically imported

```java

    subscription = RxRecyclerViewItemClicks(recyclerView)
          .subscribeOn(AndroidSchedulers.mainThread())
          .subscribe(new DefaultSubscriber<Integer>() {
            @Override public void onNext(Integer integer) {
              // do something with the binding
            }
          });

    subscription2 = RxRecyclerView.scrollEvents(recyclerView)
      .subscribeOn(AndroidSchedulers.mainThread())
      .subscribe(new DefaultSubscriber<RecyclerViewScrollEvent>() {
        @Override public void onNext(RecyclerViewScrollEvent event) {
          // do something with the event
        }
      });
```



License
-------

    Copyright (C) 2015 Jake Wharton
    Copyright (C) 2015 Fumihiro Xue

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.





