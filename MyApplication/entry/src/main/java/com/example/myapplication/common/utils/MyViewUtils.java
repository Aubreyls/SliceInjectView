/*
 * Copyright (c) 2021 Huawei Device Co., Ltd.
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.myapplication.common.utils;

import ohos.aafwk.ability.AbilitySlice;
import ohos.agp.components.Component;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * @author: Kerwin.zhou
 * @since: 2021-04-08
 */
public class MyViewUtils {
    public static void bind(AbilitySlice abilitySlice) {
        bindView(abilitySlice);//绑定视图控件

        bindMethod(abilitySlice);//绑定方法
    }


    private static void bindView(AbilitySlice abilitySlice) {
        Class<? extends AbilitySlice> aClass = abilitySlice.getClass();
        Field[] declaredFields = aClass.getDeclaredFields();
        for (Field f : declaredFields) {
            BindView annotation = f.getAnnotation(BindView.class);
            if (annotation != null)//判断一下是否存在MyInjectView注解
            {
                int resID = annotation.value();
                final Component view = abilitySlice.findComponentById(resID);
                f.setAccessible(true);
                try {
                    f.set(abilitySlice, view);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    private static void bindMethod(final AbilitySlice abilitySlice) {
        final Class<? extends AbilitySlice
            > aClass = abilitySlice.getClass();
        Method[] declaredMethods = aClass.getDeclaredMethods();
        for (final Method m : declaredMethods) {
            BindViewClick annotation = m.getAnnotation(BindViewClick.class);
            if (annotation != null)//判断一下是否存在MyInjectView注解
            {
                int resID = annotation.value();
                final Component view = abilitySlice.findComponentById(resID);
                view.setClickedListener(component -> {
                    m.setAccessible(true);
                    try {
                        m.invoke(abilitySlice, view);//当view点击时执行绑定的方法
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    } catch (InvocationTargetException e) {
                        e.printStackTrace();
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                });
            }
        }
    }
}
