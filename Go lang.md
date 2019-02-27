# Go

## Learning Resources

- [Go Bootcamp](http://www.golangbootcamp.com/book/)
- [Arden Labs](https://www.ardanlabs.com)
- [Resources for New Go Programmers: Dave Cheney](https://dave.cheney.net/resources-for-new-go-programmers)
- [Go Project Layout](https://github.com/golang-standards/project-layout)
- [Standard Package Layout](https://medium.com/@benbjohnson/standard-package-layout-7cdbc8391fc1)
- [Go By Example](https://gobyexample.com)
- [I Would Like to Go Now](https://medium.com/@bpatrick.walker/i-would-like-to-go-now-free-resources-for-learning-go-2018-9834f3d064e1)
- [ArdenLabs: Ultimate Go](https://github.com/ardanlabs/gotraining/tree/master/topics/go)
- [Exercism: Go Kata](https://exercism.io/tracks/go)
- [Bit Hacking with Go](https://medium.com/learning-the-go-programming-language/bit-hacking-with-go-e0acee258827)

## Communities

- [GoBridge](https://golangbridge.org)
- [Women Who Go](https://womenwhogo.org)

## Testing

- [Go Advanced Testing Tips & Tricks](https://medium.com/@povilasve/go-advanced-tips-tricks-a872503ac859)
- [Learn Go With TDD](https://github.com/quii/learn-go-with-tests)

## Talks

- [Rob Pike: Concurrency is not Parallelism](https://www.youtube.com/watch?v=cN_DpYBzKso)
- [Ashley McNamara's GopherCon 2018 Russia](https://www.youtube.com/watch?v=MzTcsI6tn-0)
- [Steve Francia: 7 Common Mistakes in Go](https://www.youtube.com/watch?v=29LLRKIL_TI)

Packages (libraries & applications)

Two key areas that are critical to the maintenance and testability of your app:

- Package naming
- Package organization

Packages contain code that have 1 purpose.
Package Libraries - Describe its purpose - Easy to see what package does by looking at the name - Generally short - When necessary, use a descriptive parent package and several children implementing the interface (like encoding)

Package Application

- Similar to libraries, but the command ties all the packages together.
- Should be easy to understand what the app is doing by looking at the packages.
- Organize code into packages, but packages should focus around:
  - Domain types: model business functionality and objects (i.e. search)
    - Should be in the root dir & NO external dependencies
      - IT'S A ROAD MAP
    - should also define the interfaces between the domain types. Interfaces define the things we want to do with domain types.
  - Services: operate on or with the domain types (i.e. )

Naming Conventions

Source Code Conventions
