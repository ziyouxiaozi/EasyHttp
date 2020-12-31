package com.hjq.http.lifecycle;

import androidx.annotation.NonNull;
import androidx.lifecycle.Lifecycle;
import androidx.lifecycle.LifecycleEventObserver;
import androidx.lifecycle.LifecycleOwner;

import com.hjq.http.EasyHttp;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/EasyHttp
 *    time   : 2020/04/05
 *    desc   : 请求生命周期控制
 */
public final class HttpLifecycleManager implements LifecycleEventObserver {

    /**
     * 绑定组件的生命周期
     */
    public static void bind(LifecycleOwner lifecycleOwner) {
        lifecycleOwner.getLifecycle().addObserver(new HttpLifecycleManager());
    }

    /**
     * 判断宿主是否处于活动状态
     */
    public static boolean isLifecycleActive(LifecycleOwner lifecycleOwner) {
        return lifecycleOwner != null && lifecycleOwner.getLifecycle().getCurrentState() != Lifecycle.State.DESTROYED;
    }

    @Override
    public void onStateChanged(@NonNull LifecycleOwner source, @NonNull Lifecycle.Event event) {
        if (event == Lifecycle.Event.ON_DESTROY) {
            // 移除监听
            source.getLifecycle().removeObserver(this);
            // 取消请求
            EasyHttp.cancel(source);
        }
    }
}