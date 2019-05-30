# Kotlin + MVVM + Clean Architecture + Coroutines + Koin

This project was made with the objective of creating a base structure for new apps, using tools and components supported by Google and by most of the Android development community.


## Clean Architecture

Clean architecture promotes separation of concerns, making the code loosely coupled. This results in a more testable and flexible code. This approach divides the project in 3 modules: presentation, data and domain.

* __Presentation__: Layer with the Android Framework, the MVVM pattern and the DI module. Depends on domain to access the use cases and on di, to inject dependencies.
* __Domain__: Layer with the business logic. Contains the use cases, in charge of calling the correct repository or data member.
* __Data__: Layer with the responsibility of selecting the proper data source for the domain layer. It contains the implementations of  the repositories declared in the domain layer. It may, for example, check if the data in a database is up to date, and retrieve it from a service if it’s not.

As there isn’t a single way to implement Clean Architecture, this could affront changes in the future.

### Domain

Business logic can be defined as the core operations done by the application. The domain tries to encapsulate this business logic, to make it agnostic of its context. The components of the domain are: 

* __Entities__: Simple classes that represent the objects in which the business is based.
* __Repositories__: Interfaces used by the use cases. Implemented in the data layer.
* __Use cases__: Also called interactors. They enclose a single action, like getting data from a database or posting to a service. They use the repositories to resolve the action they are supposed to do. They usually override the operator “invoke”, so they can be called as a function. 

### Data

The data layer is the implementation of all the repositories declared by the domain layer. This acts as a support of the business layer, from where it obtains the data needed to be shown in the UI.

Data is also an Android module so, besides databases and network requests, it can provide locations, bluetooth access, gyroscope data, among other information respective to the device. This could be separated in another module to provide independency from the framework.

It’s the repository job to know what should be the source of the data. The repository should decide whether the data in the local database is good enough or if it should pull it from a service. The repository shouldn’t be tied to an implementation of database/services. It should have references to interfaces that access the actual framework. A boolean may be passed as a parameter to the repository to force an update from a specific source.

### Presentation

Presentation layer contains every component involved in showing information to the user. The main part of this layer are the Views and ViewModels that will be explained in the next section. In general, the presentation layer is the one using all the Use Cases/Interactors that we created in the domain layer.

Views in this layer are the fragments and activities designed to show information to the user. In MVVM, these views are separated from the logic, which is encapsulated in the ViewModel. 

## MVVM

The Model View ViewModel pattern helps with the separation of concerns, dividing the user interface with the logic behind. The decision to use this pattern is mainly based on the support Google has been giving to it. Not only they have created a ViewModel class to use as a parent to the viewmodels, there is also a huge use of the pattern in official Android presentations and samples. Moreover, MVVM is vastly used in today’s Android development, and combines very well with Android Architecture Components like LiveData and DataBindings. 

### Model

As we are implementing MVVM alongside with Clean Architecture, we decided not to have a model class per se. The ViewModel interacts directly with the domain, utilizing the use cases. 

### View Model

The orchestrator of the relationship between the data and the user interface of the application. The ViewModel has the logic to convert what the use cases provide into information that the view can understand and present. Furthermore, it has the logic to react to the user’s input, and call the pertinent use cases. 

The most useful part of the Android’s ViewModel class is its lifecycle consciousness. It only communicates to the View with LiveData components, so it’s totally agnostic of contexts and activities: it can keep the information alive even against configuration changes like screen rotations or calls to background.

### View

The view in our implementation of MVVM is actually a Fragment or an Activity. The views enclose everything needed to handle the user interface. They observe the ViewModel, using LiveData components, and react to its changes as they need to. 

### LiveData Architecture Component

The view uses LiveData to observe changes in the ViewModel. This has  several advantages:

* The UI matches the data state, and this keeps data up to date.
* Not having to worry about stopped activities and memory leaks. Live data objects are subscript to a lifecycle and automatically stop observing when that lifecycle is ended.
* Handles configuration changes properly.
* The same data could be shared between activities.

## Dependency Injection with Koin

Dependency injection is closely related to two SOLID concepts: dependency inversion, which states that high level modules should not depend on low level modules, both should depend on abstractions; and single responsibility principle, which states that every class or module is responsible for just a single piece of functionality.
DI supports these goals by decoupling the creation and the usage of an object. It allows you to replace dependencies without changing the class that uses them and also reduces the risk of modifying a class because one of its dependencies changed.
This sample app uses Koin as the dependency injection library.

Koin is one of the most popular dependency injection frameworks for Android. It’s written purely in Kotlin, it’s lightweight and it’s easier to learn than its competition. It works in three simple steps:

1. __Declare a module__: Defines those entities which will be injected at some point in the app.

```
val applicationModule = module {
  single { AppRepository }
}
```

2. __Start Koin__: A single line, startKoin(this, listOf(applicationModule)), allows you to launch the DI process and indicate which modules will be available when needed, in this case, only applicationModule.

```
class BaseApplication : Application() {
  override fun onCreate() {
    super.onCreate()
    startKoin(this, listOf(applicationModule))
  }
}
```

3. __Perform an injection__:
In consonance with Kotlin features, Koin allows to perform lazy injections in a very convenient way.

```
class FeatureActivity : AppCompatActivity() {
  private val appRepository: AppRepository by inject()
  ...
}
```

## Coroutines

Coroutines are a new way of managing background threads that can simplify code by reducing the need for callbacks. They convert async callbacks for long-running tasks, such as database or network access, into sequential code.
We use coroutines to do tasks in a background thread. This goes very well with the idea of use cases, single actions that the ViewModel calls depending of its needs. The guideline should be that every task executed by a use case should be done in a background thread, so, in the main thread, we could show a loading screen or any alternative, and the UI doesn’t get blocked.

__Job__: a job is a cancellable task with a life-cycle that culminates in its completion. By default, a failure of any of the job’s children leads to an immediate failure of its parent and cancellation of the rest of its children. This behavior can be customized using SupervisorJob.

__Dispatchers__: 
  * Dispatchers.Default – is used by all standard builders by default. It uses a common pool of shared background threads. This is an appropriate choice for compute-intensive coroutines that consume CPU resources.
  * Dispatchers.IO – uses a shared pool of on-demand created threads and is designed for offloading of IO-intensive blocking operations (like file I/O and blocking socket I/O).
  
## Other concepts
 
### AndroidX

AndroidX is a redesigned library, replacing the support library, to make package names more clear. Each androidx package has it own version, detached from the Android API version, so extension libraries can be developed independently. 

Androidx also improves understanding of what is added to the app: 

* android.* -> bundled in the platform.
* androidx.* -> extension library.
