# CatAstrophic
|<img width="250" alt="Screen Shot 2021-07-07 at 9 16 22 PM" src="https://user-images.githubusercontent.com/5355440/124802314-e36b8780-df68-11eb-9c3e-c82afe3c2de8.png">
|<img width="250" alt="Screen Shot 2021-07-07 at 9 16 49 PM" src="https://user-images.githubusercontent.com/5355440/124802326-e6ff0e80-df68-11eb-9eec-a58ca5eedc46.png">
|<img width="250" alt="Screen Shot 2021-07-07 at 9 16 49 PM" src="https://user-images.githubusercontent.com/5355440/124802322-e5cde180-df68-11eb-9557-8210dbaa40c3.png">
|<img width="250" alt="Screen Shot 2021-07-07 at 9 17 09 PM" src="https://user-images.githubusercontent.com/5355440/124802348-eb2b2c00-df68-11eb-83eb-8a17a3ea3414.png">
|<img width="250" alt="Screen Shot 2021-07-07 at 9 18 15 PM" src="https://user-images.githubusercontent.com/5355440/124802353-ec5c5900-df68-11eb-81f0-9840ae82de01.png">

## About
It simply loads **Cats** data from API and stores it in Room databse. Posts will be always loaded from local database. Remote data (from API) and Local data is always synchronized. 
- Supports offline first architecture. 
- Displays the images in a square 3-column layout.
- Allow the user to browse and scroll through the endless cat images.
- Supports pinch and zoom to change grid layout from 3 <-> 2 <-> 1


## Built With 🛠
- [Kotlin](https://kotlinlang.org/) - First class and official programming language for Android development.
- [Coroutines](https://kotlinlang.org/docs/reference/coroutines-overview.html) - For asynchronous programming.
- [Android Architecture Components](https://developer.android.com/topic/libraries/architecture) - Collection of libraries that help you design robust, testable, and maintainable apps.
  - [LiveData](https://developer.android.com/topic/libraries/architecture/livedata) - Data objects that notify views when the underlying database changes.
  - [ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel) - Stores UI-related data that isn't destroyed on UI changes. 
  - [ViewBinding](https://developer.android.com/topic/libraries/view-binding) - Generates a binding class for each XML layout file present in that module and allows you to more easily write code that interacts with views.
  - [Room](https://developer.android.com/topic/libraries/architecture/room) - SQLite object mapping library.
- [Dependency Injection](https://developer.android.com/training/dependency-injection) -
  - [Dagger](https://dagger.dev/) - Dagger is a fully static, compile-time dependency injection framework for Java, Kotlin, and Android.
  - For Hilt implementation -
    - see branch [***`add-hilt-di`***](https://github.com/roymithun/catastrophic/tree/add-hilt-di)
  - [Hilt-Dagger](https://dagger.dev/hilt/) - Standard library to incorporate Dagger dependency injection into an Android application.
  - [Hilt-ViewModel](https://developer.android.com/training/dependency-injection/hilt-jetpack) - DI for injecting `ViewModel`.
- [Retrofit](https://square.github.io/retrofit/) - A type-safe HTTP client for Android and Java.
- [Moshi](https://github.com/square/moshi) - A modern JSON library for Kotlin and Java.
- [Moshi Converter](https://github.com/square/retrofit/tree/master/retrofit-converters/moshi) - A Converter which uses Moshi for serialization to and from JSON.
- [Coil-kt](https://coil-kt.github.io/coil/) - An image loading library for Android backed by Kotlin Coroutines.
