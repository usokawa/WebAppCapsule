# Web App Capsule

## Overview

Web App Capsule is a project template designed to easily convert your local web content (e.g., HTML, CSS, JavaScript, media files, etc.) into a native Android application. By placing your web content into a specific folder, you can build a self-contained Android app that displays it within a WebView, making it accessible offline.

## Usage

1.  **Open the Project:**
    * On the Android Studio welcome screen, select **Open** and choose the downloaded project folder.
    * Alternatively, you can select **Clone Repository** to clone the project from a Git repository.

2.  **Add Your Web Content:**
    * Place all your web content, such as `index.html`, CSS files, JavaScript files, and media files, into the `WebAppCapsule/app/www/` folder. This is the designated folder for all web content you want to display in the app.

3.  **Build and Run the Application:**
    * Simply click the **Run 'app'** button in the Android Studio toolbar. This will build the application with your local web content and install it on an Android emulator or a connected physical device.

## Features

* **Local Web Content Integration:** The project is configured to treat the `WebAppCapsule/app/www/` folder as a source for assets. This is done in the `app/build.gradle.kts` file by adding `assets.srcDir("www")`. This setup allows the application to bundle all your HTML, CSS, JavaScript, and media files directly into the app package, making it completely self-contained and available offline.

* **WebView as the User Interface:** The application's entire user interface is a `WebView` component that fills the screen. This is defined in `activity_main.xml`. In `MainActivity.kt`, this `WebView` loads and renders all your local web content, effectively serving as the container for your web application.

* **Immersive Full-Screen Display:** To provide a native-like, immersive experience, the application hides the Android system's status bar and navigation bar. This is achieved in `MainActivity.kt` using `WindowInsetsControllerCompat` to control the system UI. The behavior is set to `BEHAVIOR_SHOW_TRANSIENT_BARS_BY_SWIPE`, which keeps the focus on your web content while allowing the user to temporarily access system UI with a swipe gesture.

* **Enabling Modern Web Functionality:** The application supports modern, interactive web experiences by enabling necessary `WebView` settings. In `MainActivity.kt`, `javaScriptEnabled` is set to `true` to allow your JavaScript code to run, and `domStorageEnabled` is also set to `true` to support web storage mechanisms like `localStorage` and `sessionStorage`.

* **Strict Security Sandboxing:** For enhanced security, the `WebView` is sandboxed to prevent it from accessing the device's file system or content providers. In `MainActivity.kt`, this is explicitly enforced by setting `allowFileAccess = false` and `allowContentAccess = false`. This ensures that even if a script in your web content is compromised, it cannot access sensitive data outside of the packaged assets.

* **Secure and Modern Asset Loading:** To load your local web content, the app uses `WebViewAssetLoader` as recommended by Android for security and performance. In `MainActivity.kt`, an instance of `WebViewAssetLoader` is created and configured to handle requests for a specific virtual domain (`appassets.androidplatform.net`). When the `WebView` tries to load the initial URL `https://appassets.androidplatform.net/www/index.html`, the custom `WebViewClient` intercepts this request and uses the asset loader to securely serve the `index.html` file from your local `www` asset folder.

## Rename Project

For instructions on how to rename the entire project, please refer to the wiki: [Rename Android Project](https://github.com/usokawa/WebAppCapsule/wiki/Rename-Android-Project)
