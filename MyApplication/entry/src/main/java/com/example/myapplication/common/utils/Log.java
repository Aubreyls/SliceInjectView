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

/**
 * @function:日志工具。
 */
public class Log {
    private static String className;//类名
    private static String methodName;//方法名
    private static int lineNumber;//行数

    /**
     * 设置日志消息
     *
     * @param log
     * @return
     */
    private static String createLog(String log) {
        StringBuffer buffer = new StringBuffer();
        buffer.append("  ");
        buffer.append(methodName);
        buffer.append("(").append(className).append(":").append(lineNumber).append(")::");
        buffer.append(log);
        return buffer.toString();
    }

    /**
     * 获取文件名、方法名、所在行数
     *
     * @param sElements
     */
    private static void getMethodNames(StackTraceElement[] sElements) {
        className = sElements[1].getFileName();
        methodName = sElements[1].getMethodName();
        lineNumber = sElements[1].getLineNumber();
    }

    /**
     * 输出ERROR级别的日志。ERROR级别日志表示存在错误。
     *
     * @param message
     * @paramTAG
     */
    public static void e(String TAG, String message) {
        getMethodNames(new Throwable().getStackTrace());
        System.out.println("Kerwin::"+TAG + ":" + createLog(message));
    }

    /**
     * 输出INFO级别的日志。INFO级别日志表示普通的信息。
     *
     * @param message
     * @paramTAG
     */
    public static void i(String TAG, String message) {
        getMethodNames(new Throwable().getStackTrace());
        System.out.println("Kerwin::"+TAG + ":" + createLog(message));
    }

    /**
     * 输出DEBUG级别的日志。DEBUG级别日志表示仅用于应用调试，默认不输出，输出前需要在设备的“开发人员选项”中打开“USB调试”开关。
     *
     * @param message
     * @paramTAG
     */
    public static void d(String TAG, String message) {
        getMethodNames(new Throwable().getStackTrace());
        System.out.println("Kerwin::"+TAG + ":" + createLog(message));
    }

    /**
     * 输出FATAL级别的日志。FATAL级别日志表示出现致命错误、不可恢复错误。
     *
     * @param message
     * @paramTAG
     */
    public static void f(String TAG, String message) {
        getMethodNames(new Throwable().getStackTrace());
        System.out.println("Kerwin::"+TAG + ":" + createLog(message));
    }

    /**
     * 输出WARN级别的日志。WARN级别日志表示存在警告。
     *
     * @param message
     * @paramTAG
     */
    public static void w(String TAG, String message) {
        getMethodNames(new Throwable().getStackTrace());
        System.out.println("Kerwin::"+TAG + ":" + createLog(message));
    }
}

