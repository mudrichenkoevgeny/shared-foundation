# Consumer Proguard / R8 keep rules for shared-foundation kotlinx.serialization
-keepclassmembers class * {
    @kotlinx.serialization.SerialName <fields>;
    @kotlinx.serialization.Serializable <fields>;
}
-keepclassmembers class * {
    public static final **$serializer INSTANCE;
}
-keepclasseswithmembers class * {
    public static final **$serializer INSTANCE;
}
