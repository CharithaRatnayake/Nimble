# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

-dontwarn com.google.auto.**
-dontwarn com.squareup.kotlinpoet.javapoet.J2kInteropKt
-dontwarn javax.lang.model.**
-dontwarn javax.tools.**
-dontwarn kotlin.**
-dontwarn sun.reflect.annotation.**
-dontwarn com.google.auto.service.AutoService
-dontwarn javax.lang.model.**
-dontwarn javax.tools.Diagnostic$Kind

# For okhttp
-dontwarn okhttp3.**
-dontwarn javax.annotation.**
-dontwarn org.conscrypt.**
# A resource is loaded with a relative path so the package of this class must be preserved.
-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase

# For rxjava
-keep class rx.internal.util.** { *; }
-keep class retrofit2.** { *; }
-keepattributes Signature
-keepattributes Exceptions

-keepclasseswithmembers class * {
    @retrofit2.http.* <methods>;
}

-keep class com.google.gson.examples.android.model.**{*;}

# Glide
-keep public class * implements com.bumptech.glide.module.GlideModule
-keep public enum com.bumptech.glide.load.resource.bitmap.ImageHeaderParser$** {
    **[] $VALUES;
    public *;
}
# support-v7-appcompat
-keep public class android.support.v7.widget.** { *; }
-keep public class android.support.v7.internal.widget.** { *; }
-keep public class android.support.v7.internal.view.menu.** { *; }
-keep public class * extends android.support.v4.view.ActionProvider {
    public <init>(android.content.Context);
}
# support-design
-dontwarn android.support.design.**
-keep class android.support.design.** { *; }
-keep interface android.support.design.** { *; }
-keep public class android.support.design.R$* { *; }

# For Enum
-keepclassmembers class * extends java.lang.Enum {
    <fields>;
    public static **[] values();
    public static ** valueOf(java.lang.String);
}
-keepattributes InnerClasses
-keep class * {
    public enum **;
}

-keep class com.google.gson.examples.android.model.** { <fields>; }

-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer

-keepattributes Signature

-keepattributes Annotation

-if interface * { @retrofit2.http.* <methods>; }
-keep,allowobfuscation interface <1>

-keepnames class okhttp3.internal.publicsuffix.PublicSuffixDatabase