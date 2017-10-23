#基础配置
-optimizationpasses 5
-dontusemixedcaseclassnames
-dontoptimize
-dontpreverify
-verbose
-optimizations !code/simplification/arithmetic,!field/*,!class/merging/*
-keepattributes *Annotation*
-keep public class * extends android.app.Fragment
-keep public class * extends android.app.Activity
-keep public class * extends android.app.Application
-keep public class * extends android.app.Service
-keep public class * extends android.content.BroadcastReceiver
-keep public class * extends android.content.ContentProvider
-keep public class * extends android.app.backup.BackupAgentHelper
-keep public class * extends android.preference.Preference
-keep public class com.android.vending.licensing.ILicensingService
-keep public class * extends android.support.v4.app.Fragment
-ignorewarnings
-dontwarn android.support.**
-keepclasseswithmembernames class * { native <methods>; }
-keepclasseswithmembers,allowshrinking class * {native <methods>;}
-keepclasseswithmembers class * {public <init>(android.content.Context, android.util.AttributeSet);}
-keepclassmembers class * extends android.app.Activity {public void *(android.view.View);}
-keep class * implements android.os.Parcelable {public static final android.os.Parcelable$Creator *;}
-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    !static !transient <fields>;
    !private <fields>;
    !private <methods>;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}
-keepclassmembers class * { public void *ButtonClicked(android.view.View); }
-keepclassmembers class **.R$* {public static <fields>; }
#Retrofit配置
-dontwarn okio.**
-dontwarn javax.annotation.**
#Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public class * extends com.bumptech.glide.AppGlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
  **[] $VALUES;
  public *;
}