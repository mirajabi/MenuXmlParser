package com.miaadrajabi.menuxmlparser.java.presentation.menu;

import androidx.lifecycle.DefaultLifecycleObserver;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

public class FlowObserver<T> implements DefaultLifecycleObserver {
    private final Lifecycle lifecycle;
    private final androidx.lifecycle.LiveData<T> liveData;
    private final Observer<T> observer;

    public FlowObserver(Lifecycle lifecycle, androidx.lifecycle.LiveData<T> liveData, Observer<T> observer) {
        this.lifecycle = lifecycle;
        this.liveData = liveData;
        this.observer = observer;
    }

    @Override
    public void onStart(LifecycleOwner owner) {
        liveData.observe(owner, observer);
    }

    @Override
    public void onStop(LifecycleOwner owner) {
        liveData.removeObserver(observer);
    }
}
