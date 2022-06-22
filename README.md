# OAuth2-demo
A small demo application for practicing OAuth2 authentication
The application demonstrates forgot password sign up and sign in of user using custom authentication.
The applciation also has session handling .i.e. the refresh of access tokens in the event of expiry.
The application is built using clean architecture , MVVM and jetpack libraries.
Libraries used include:
- Jetpack compose : declarative UI building
- Retrofit: library for making lightweight http calls over the network
- Datastore: local lightweight storage for applications.
- Material design library for UI widgets
- Kotlin Coroutines which include Flows and Channels.
- Ramcosta's Navigation Destination for in app screen navigation. It is paired with Jetpack Navigation Component and KSP for annotation processing.
- Hilt for dependency Injection
