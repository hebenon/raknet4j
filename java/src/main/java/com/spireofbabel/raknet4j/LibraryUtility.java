package com.spireofbabel.raknet4j;

import org.scijava.nativelib.NativeLibraryUtil;

/**
 * Created by bcarson on 28/11/14.
 */
public class LibraryUtility {
    public static void loadNativeLibrary(String libraryName) {
        // Most platforms use NativeLibraryUtil, but some don't.
        // Like Android. It's just Android.
        String javaVendor = System.getProperty("java.vendor");
        if(javaVendor != null && javaVendor.toLowerCase().contains("android")) {
            System.loadLibrary(libraryName);
        } else {
            NativeLibraryUtil.loadNativeLibrary(LibraryUtility.class, libraryName);
        }
    }
}
