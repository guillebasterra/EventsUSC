# Events@USC
Events@USC is an app designed to enhance the campus experience by keeping students and visitors informed about ongoing events in real time, providing a map-based interface to view event locations, descriptions, and routes. Key features include account creation, event viewing, event creation, and commenting. 

## Requirements
- Android Studio
- Firebase
- Google Maps API key

## Set Up Instructions
1. Clone the repository to your local machine
2. Create a Google Maps API key in the Google Cloud Console. Copy and replace the "MAPS_API_KEY" value with it in "local.properties" under Gradle Scripts. This will automatically update it in the AndroidManifest.xml file.
3. Set up the Firebase by creating a new project in the Firebase Console with the app package name "com.example.eventsusc". Download the "google-services.json" file from Firebase and place it in the app directory of the project.
4. Build a Realtime Database in the Console with a new database set up in test mode and enable email/password sign-in under Authentication. Add the class path "classpath 'com.google.gms:google-services:4.3.10'", the Google Services plugin "apply plugin: 'com.google.gms.google-services'" and the Firebase dependencies "dependencies {
    implementation 'com.google.firebase:firebase-auth:21.0.1'
    implementation 'com.google.firebase:firebase-database:20.0.3'
}" to "build.gradle" under Gradle Scripts and sync the project with the Gradle files in Android Studio.

## Running the App
1. Change the emulator device to Medium Phone API 35.
2. Run the app in Android Studio, it should build and launch automatically.
