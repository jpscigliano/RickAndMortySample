# Rick and Morty  

Rick and Morty Sample is a multi-module android app that follows clean architecture principles and guidelines. 
It's built using Jetpack compose and many other jetpack libraries.  
The app consumes the data from [Rick and Morty Api](https://rickandmortyapi.com) and  consists of two screens;
#### Character List

#### Character Detail

 
## Architecture

![Base architecture](https://user-images.githubusercontent.com/7504105/199520745-0d8d8f10-3a5f-4692-9a9c-dfd698164944.png)

<img width="377" alt="Screenshot 2022-11-02 at 11 48 09" src="https://user-images.githubusercontent.com/7504105/199521633-a283631c-e86f-4ba4-a1da-dc21347b5034.png">

 
**CoreX**

Consists of 3 modules, each holding a set of extensions functions and abstractions meant to be used in the corresponding Feed**XYZ**Module

**FeedModules**
- FeedPresentation. Holds ViewModels and Composable functions grouped by features.
- FeedFramework. Implementation of Remote and Local Datasources, the remote datasource uses Ktor for making the API calls and the local datasource uses Room to store in a database.
- FeedDomain. specific business objects as well it contains the UseCases for retrieving the characters.
- FeedData.   Holds the Repositories, the task of it is to decide from which data source the data would be provided.


## Testing
Each module consists of a suite of unit-test. [Mockk](https://mockk.io) and [Turbine](https://github.com/cashapp/turbine) are used as support libraries.
Mock data is independent for each module.

Unit tests are documented with some comments following the Given-When-Then style.
- The given part describes the state of the world before beginning the behavior specified in the scenario under test. 
- The when part is the behavior that we are specifying. 
- The then section describes the changes expected due to the specified behavior.

### Test Coverage
[Kover gradle plugin](https://github.com/Kotlin/kotlinx-kover) is used for gathering reports. To execute just run;
```bash
./gradlew koverMergedHtmlReport
```
