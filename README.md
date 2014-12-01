RakNet4J
========

Java bindings for RakNet (https://github.com/OculusVR/RakNet)

Package notes
------------------------------------------
Coverage of the original RakNet library is pretty shallow at this point.

Getting it
------------------------------------------
TODO: Maven repository.

1. git clone https://github.com/hebenon/raknet4j.git
2. cd raknet4j
3. mvn clean install -Pdarwin
4. Add maven dependency to your project (dependency will get resolved from your .m2 cache):

```xml
<dependency>
    <groupId>com.spireofbabel.raknet4j</groupId>
    <artifactId>raknet4j-darwin-x86_64</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

### Android:
Because of how Android handles native libs, adding it to your project is a little different. Add the build product jar file (in /packages/android/target) to your projects /libs directory as normal. Then add the native library files for each of your target architectures in a subdirectory of your /libs folder, named after the target architecture (e.g. "armeabi" or "x86"). You can find the RakNet4J native libs in the /native/android/target/libs folder.

Using it
------------------------------------------
TODO:
