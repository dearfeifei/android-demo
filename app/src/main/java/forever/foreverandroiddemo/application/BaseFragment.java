package forever.foreverandroiddemo.application;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import java.io.File;
import java.lang.reflect.Field;

import forever.foreverandroiddemo.utils.ViewInject;

/**
 * Created by forever on 16/7/12.
 */
public  class BaseFragment extends Fragment {

    public File filesDir;
    public NetworkInfo networkInfoInfo;
    public ConnectivityManager manager;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        autoInjectAllField();
        filesDir = getActivity().getFilesDir();
        manager = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfoInfo = manager.getActiveNetworkInfo();


        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * 解析注解
     */
    public void autoInjectAllField() {
        try {
            Class<?> clazz = this.getClass();
            Field[] fields = clazz.getDeclaredFields();// 获得Activity中声明的字段
            for (Field field : fields) {
                // 查看这个字段是否有我们自定义的注解类标志的
                if (field.isAnnotationPresent(ViewInject.class)) {
                    ViewInject inject = field.getAnnotation(ViewInject.class);
                    int id = inject.value();
                    if (id > 0) {
                        field.setAccessible(true);
                        field.set(this, getActivity().findViewById(id));// 给我们要找的字段设置值
                    }
                }
            }
        } catch (IllegalAccessException e) {
            e.printStackTrace();

        } catch (IllegalArgumentException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // TODO Auto-generated method stub


    }


    @Override
    public void onPause() {
        super.onPause();
        // TODO Auto-generated method stub

    }
}

