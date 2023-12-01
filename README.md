# Nimble Android Application (com.nimble)

- minSdk ```21```
- targetSdk ```34```
- versionCode ```1```
- productFlavors
  - staging
  - production (working env)
  - mock

<br><br>

## Description

- Login Authentication Screen:<br>
Create a user-friendly login authentication screen where users can enter their credentials (email and password) to access the application.
- OAuth Authentication with Token Storage:<br>
Integrate OAuth authentication to secure user data and generate access tokens. Implement a secure storage mechanism for storing these access tokens on the user's device.
- Automatic Refresh Token Usage:<br>
Implement a mechanism to automatically refresh access tokens using the OAuth API. This ensures that users remain logged in without the need for manual re-authentication.
- Home Screen:<br>
  - Retrieve the list of surveys from the API when the application is opened, ensuring users have access to the latest survey information.
  - Allow users to horizontally scroll through the surveys, providing an engaging and interactive experience.
  - Implement a loading animation to indicate to users that the application is fetching the list of surveys. This provides feedback and enhances the user experience during data retrieval.
  - Dynamically generate a navigation indicator list (bullets) based on the API response. These indicators should represent the number of available surveys, assisting users in understanding the overall survey count and their current position within the survey list.
  - Local cashing function.

<br><br>

## Release
 
| Release version  | Version Code | Url |
| ------------- | ------------- | https://github.com/CharithaRatnayake/Nimble |
| ```1.0.0```  | ```1```  | https://github.com/CharithaRatnayake/Nimble |

<br><br>

## Build Instructions
You need to clone the project and open it with the Android Studio IDE(), then build the project and select the build variants as 'production'

![image](https://github.com/CharithaRatnayake/Nimble/assets/23307834/74a6e498-4547-4900-8082-3422b296ce5f)


<br><br>

## App Architecture
This app used MVVM architecture pattern and below is the main architecture format,

![nimble drawio](https://github.com/CharithaRatnayake/Nimble/assets/23307834/50fb54a6-25e4-405e-a5fd-4969da4ef207)

<br><br>

## Security
- API Credentials are saved as Base64Strings we can enable ProGuard for code shrinking, obfuscation, and optimization.

<br><br>

## Dependencies
- Common
```
'androidx.core:core-ktx:1.12.0'
'androidx.lifecycle:lifecycle-runtime-ktx:2.6.2'
'androidx.appcompat:appcompat:1.6.1'
'androidx.recyclerview:recyclerview:1.3.2'
'androidx.annotation:annotation:1.7.0'
'com.google.android.material:material:1.10.0'
'androidx.constraintlayout:constraintlayout:2.1.4'
'androidx.lifecycle:lifecycle-livedata-ktx:2.6.2'
'androidx.lifecycle:lifecycle-viewmodel-ktx:2.6.2'
'androidx.legacy:legacy-support-v4:+'
'androidx.navigation:navigation-fragment-ktx:2.7.4'
'androidx.navigation:navigation-ui-ktx:2.7.4'
```

- Unit testing
```
'junit:junit:4.13.2'
'org.mockito:mockito-core:5.1.1'
'org.mockito:mockito-android:3.2.4'
'org.mockito:mockito-kotlin:3.2.0'
'org.mockito.kotlin:mockito-kotlin:3.2.0'
'androidx.arch.core:core-testing:2.2.0'
'org.jetbrains.kotlinx:kotlinx-coroutines-test:1.7.1'
'androidx.test.ext:junit:1.1.5'
'androidx.test.espresso:espresso-core:3.5.1'
```

- Retrofit for network calls
```
"com.squareup.okhttp3:okhttp:4.9.0"
"com.squareup.okhttp3:logging-interceptor:4.9.0"
"com.squareup.retrofit2:retrofit:2.9.0"
"com.squareup.retrofit2:converter-gson:2.9.0"
"com.squareup.retrofit2:adapter-rxjava:2.9.0"
```

- hilt
```
'com.google.dagger:hilt-compiler:2.44'
'com.google.dagger:dagger-android-processor:2.42'
'com.google.dagger:hilt-android-compiler:2.48.1'
'com.google.dagger:hilt-android:2.48.1'
'androidx.hilt:hilt-compiler:1.1.0'
```

- coroutines
```
'org.jetbrains.kotlinx:kotlinx-coroutines-core:1.5.2'
'org.jetbrains.kotlinx:kotlinx-coroutines-android:1.5.2'
'com.google.android.play:core:1.10.3'
'androidx.work:work-runtime-ktx:2.7.0'
```

- Local storage
```
"androidx.room:room-runtime:2.6.0"
"androidx.room:room-ktx:2.6.0"
"androidx.room:room-compiler:2.6.0"
"androidx.datastore:datastore-preferences:1.0.0"
```

- Custom UI and other utils
```
'com.github.bumptech.glide:glide:4.14.2'
'com.github.bumptech.glide:okhttp3-integration:4.14.2'
'com.github.bumptech.glide:compiler:4.14.2'
"androidx.viewpager2:viewpager2:1.0.0"
'io.supercharge:shimmerlayout:2.1.0'
'com.airbnb.android:lottie:6.2.0'
'androidx.swiperefreshlayout:swiperefreshlayout:1.1.0'
"com.mikepenz:materialdrawer:9.0.1"
'com.github.akndmr:AirySnackbar:1.0.0'
```
