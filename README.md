# Events@USC
Events@USC is an app designed to enhance the campus experience by keeping students and visitors informed about ongoing events in real time, providing a map-based interface to view event locations, descriptions, and routes. Key features include account creation, event viewing, event creation, and commenting. 

## What You'll Need
- Android Studio
- Firebase
- Google Maps API key

## Set Up Instructions
1. Clone the repository to your local machine
2. Create a Google Maps API key in the Google Cloud Console. Copy paste it and replace the MAPS_API_KEY value in the local.properties under Gradle Scripts. This will automatically update it in the AndroidManifest.xml file.
3. Set up the Firebase by creating a new project in the Firebase Console with the app package name "com.example.eventsusc". Build a Realtime Database in the Console with a new database set up in test mode, enable email/password sign-in under Authentication, and sync Firebase with the project.

## Running the App
1. Change the emulator device to Medium Phone API 35.
2. Run the app in Android Studio, it should build and launch automatically.
