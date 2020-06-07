# LoginDemo
The app follows the MVVM architecture with the repository pattern, alongside Koin for DI.

### Compiling
Clone the project and add your own `google-services.json` to the `app` folder.

### Modifying
#### Folder structure
There are 4 main folders: app, data, ui, utils.
* app: Contains Application and dependencies stuff.
* data: Contains the repository and data classes related to the user data and view states.
* ui: Contains the LoginActivity and the AuthViewModel.
* utils: Contains extensions functions. 

### Libraries and other stuff applied
* Koin for dependency injection
* ViewBinding
* Material Components
* ViewModel and LiveData
* Coroutines
* MVVM architecture + Repository pattern
* Unit test with mockk

### Check the app!
You could download the .apk file from the release tab.

Use this credentials if you want to test the functionality:
    logindemo@yopmail.com
    demo12345