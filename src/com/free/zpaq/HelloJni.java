/*
 * Copyright (C) 2009 The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.free.zpaq;

import android.app.Activity;
import android.widget.TextView;
import android.os.Bundle;
import android.util.*;
//import com.free.util.*;
import java.io.*;


public class HelloJni extends Activity
{
    /** Called when the activity is first created. */
    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        /* Create a TextView and set its content.
         * the text is retrieved by calling a native
         * function.
         */
        TextView  tv = new TextView(this);
        try
		{
//			InputStream is = getAssets().open("7za");
//			FileUtil.is2OS(is, new FileOutputStream("/data/data/com.free.p7zip/7za"));
//			System.out.println(CommandUtils.exec("chmod", "777", "/data/data/com.free.p7zip/7za"));
//			System.out.println(CommandUtils.exec("ls", "-l", "/data/data/com.free.p7zip"));
//			System.out.println(CommandUtils.exec("/data/data/com.free.p7zip/7za"));
			Object[] run7za = new Zpaq().runZpaq(true, "a", "/storage/emulated/0/a.zpaq", "/storage/emulated/0/ftjj500.7z");
			//System.out.println(run7za[0]);
			//System.out.println(run7za[1]);
			//Log.d("HelloJni", FileUtil.getFiles(new File("/data/data/com.free.p7zip"), false) + ".");
			run7za = new Zpaq().runZpaq(true, "l", "/storage/emulated/0/a.zpaq", "-test");
			tv.setText(run7za[1].toString());
			System.out.println( new Zpaq().runZpaq(true, "")[1].toString());
			//com.free.util.CommandUtils.exec("/data/data/com.free.p7zip/").toString();
			setContentView(tv);
		}
		catch (Throwable e)
		{
			e.printStackTrace();
		}
    }

    /* A native method that is implemented by the
     * 'hello-jni' native library, which is packaged
     * with this application.
     */
    public native String  stringFromJNI();

    /* This is another native method declaration that is *not*
     * implemented by 'hello-jni'. This is simply to show that
     * you can declare as many native methods in your Java code
     * as you want, their implementation is searched in the
     * currently loaded native libraries only the first time
     * you call them.
     *
     * Trying to call this function will result in a
     * java.lang.UnsatisfiedLinkError exception !
     */
    public native String  unimplementedStringFromJNI();

    /* this is used to load the 'hello-jni' library on application
     * startup. The library has already been unpacked into
     * /data/data/com.example.hellojni/lib/libhello-jni.so at
     * installation time by the package manager.
     */
    static {
        System.loadLibrary("zpaq");
    }
}
