# Forgotten Films REST API Documentation

#### Contents
* [Overview](https://github.com/Bogomil-Stoyanov/ForgottenFilmsApp#overview)
* [Features](https://github.com/Bogomil-Stoyanov/ForgottenFilmsApp#features)
* [Libraries](https://github.com/Bogomil-Stoyanov/ForgottenFilmsApp#libraries)
* [Architecture](https://github.com/Bogomil-Stoyanov/ForgottenFilmsApp#architecture)
* [Tests](https://github.com/Bogomil-Stoyanov/ForgottenFilmsApp#tests)
* [Security](https://github.com/Bogomil-Stoyanov/ForgottenFilmsApp#security)
* [Screenshots](https://github.com/Bogomil-Stoyanov/ForgottenFilmsApp#screenshots)
* [Deployment](https://github.com/Bogomil-Stoyanov/ForgottenFilmsApp#deployment)

## Overview
Forgotten Films App is an Android application for enjoying films from the past. It provides everything that you will need to immerse the user in the world of classic films.
**Get it here:** [Forgotten Films](http://bbsapps.eu/forgottenfilms/forgottenfilms.html)

#### Tech stack:
- Language: Kotlin
- UI: Jetpack Compose

## Features
- The app is connected to a REST API (https://github.com/Bogomil-Stoyanov/ForgottenFilmsApi)
- Register a user with email, password, nickname and favourite film genres
- Login with email and password
- Forgotten password and change password
- Get recommended films to watch depending on your favourite genres
- Search, watch, like, dislike and share films
- Watch time statistics - get statistcs on your watchtime and top genres. This data is collected to imporve the recommendations algorithm. 

## Libraries

Forgotten Films uses the most modern libraries for backend development with Ktor

- [AndroidX] - Extension libraries for Android (https://github.com/androidx)
- [Retrofit] - A type-safe HTTP client for Android (https://github.com/square/retrofit)
- [ExoPlayer] - An extensible media player for Android (https://github.com/google/ExoPlayer)
- [Jetpack Compose] - Android’s modern toolkit for building native UI (https://developer.android.com/jetpack/compose)
- [Coil] - An image loading library (https://coil-kt.github.io/coil/)

## Architecture

The application is built following Clean Architecture MVVM.

![Architecture](https://github.com/Bogomil-Stoyanov/ForgottenFilmsApp/blob/master/pictures/appArch.png)

## Tests
All ViewModels have tests that cover the business logic. 

[ViewModel tests](https://github.com/Bogomil-Stoyanov/ForgottenFilmsApp/blob/master/pictures/vmTests.png)

There are automated UI tests that verify the most user intansive screens

![UI tests](https://github.com/Bogomil-Stoyanov/ForgottenFilmsApp/blob/master/pictures/uiTests.png)

*Note: All tests HAVE to be ran on a REAL device as there are problems with the current version with Jetpack Compose and the testing library. These bugs should be fixed in the next release of the libraries.

## Security
Security is a top priority. The application communicates to the REST API via HTTPS. When a user selects to be remembered in the device, their login credentials are saved in encrypted shared preferences.

## Screenshots
![Screenshot](https://github.com/Bogomil-Stoyanov/ForgottenFilmsApp/blob/master/pictures/l1.png)
![Screenshot](https://github.com/Bogomil-Stoyanov/ForgottenFilmsApp/blob/master/pictures/d1.png)
![Screenshot](https://github.com/Bogomil-Stoyanov/ForgottenFilmsApp/blob/master/pictures/l2.png)
![Screenshot](https://github.com/Bogomil-Stoyanov/ForgottenFilmsApp/blob/master/pictures/d2.png)
![Screenshot](https://github.com/Bogomil-Stoyanov/ForgottenFilmsApp/blob/master/pictures/l3.png)
![Screenshot](https://github.com/Bogomil-Stoyanov/ForgottenFilmsApp/blob/master/pictures/d3.png)
![Screenshot](https://github.com/Bogomil-Stoyanov/ForgottenFilmsApp/blob/master/pictures/l4.png)
![Screenshot](https://github.com/Bogomil-Stoyanov/ForgottenFilmsApp/blob/master/pictures/d4.png)
![Screenshot](https://github.com/Bogomil-Stoyanov/ForgottenFilmsApp/blob/master/pictures/l5.png)
![Screenshot](https://github.com/Bogomil-Stoyanov/ForgottenFilmsApp/blob/master/pictures/d5.png)

## Deployment
1. Clone the repository 
2. Generate debug or a signed apk / bundle (depends on where the app will be distributed, it has to be a bundle, if uploaded to Google Play)
