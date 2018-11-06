An Android Sample in Kotlin implemented in Clean MVP using Dagger 2 for dependency injection.
This project use Firebase OAuth with Google Sign-In API.
There is 3 Dagger scopes: Singleton(Default), UserScope, ActivityScope.
This scheme shows the lifecycle and the use of each scope.


![alt text](https://github.com/Fakher-Hakim/Kotlin-MVP-ROOM/blob/master/scopes_diagram.png)


The architecture adopted for this project is MVP with Repository Pattern for data layer.
The presenter ask data from the repository, in its turn, the repository fetch the data from appropriate source (Either local or remote source). The presenter can force the repository to update it's data but it never get access to the network calls. That way, even if the networking module changed to adopte a new Framework for example, we need just to update the data layer!
this diagram explain the interaction between layers and modules.

![alt text](https://github.com/Fakher-Hakim/Kotlin-MVP-ROOM/blob/master/arch_diagram.png)
