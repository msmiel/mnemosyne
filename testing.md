> “We can make good tests run fast, but we can’t make fast tests be good.”

    - Ryan Tomayko, GitHub

## Framework Principles

### User Strategies

These aren't mutually exclusive. They each have particular use cases and should be used in tandem.

#### Stub Requests

- Fast, Easy, Flexible.
- No server/DB
- Not truly E2E
- Requires Fixtures.

#### Static user

- Real E2E
- Requires Server
- Needs DB to be seeded
- Potentially shares test state

#### Dynamic user

- No State mutation
- Flexible/Powerful
- Requires DB Setup/Teardown
- Can be slow/complex

## Technical Writing

- [Documenting APIs : I'd Rather Be Writing](http://idratherbewriting.com/learnapidoc/)

## Testing/Continuous Delivery

### Philosophy

- [What Makes a Good End-to-End Test : Google Testing on the Toilet](https://testing.googleblog.com/2016/09/testing-on-toilet-what-makes-good-end.html)
- [No More Flakey Tests on the Go Team : Thoughtworks](https://www.thoughtworks.com/insights/blog/no-more-flaky-tests-go-team)
- [The Practical Test Pyramid : Thoughtworks](https://martinfowler.com/articles/practical-test-pyramid.html)
- Separate framework from the tests themselves as much as possible.[^1]
- Make your framework as portable as possible
- [Move Fast & Don't Break Things : Slides](https://docs.google.com/presentation/d/15gNk21rjer3xo-b1ZqyQVGebOp_aPvHU3YH7YnOMxtE/edit#slide=id.g437663ce1_53_391)
- [Build the 'Right' Regression Testing Suite Using Behavior Driven Testing : Thoughtworks](https://www.thoughtworks.com/insights/blog/build-right-regression-suite-using-behavior-driven-testing-bdt)
- [Which Test Cases Should I Automate : DevelopSense](http://www.developsense.com/blog/2018/06/which-test-cases-should-i-automate/)

### Patterns

- [Page Objects Refactored : Towards the Screenplay Pattern](https://dzone.com/articles/page-objects-refactored-solid-steps-to-the-screenp)

### Web

- [Helpers & Tips for NPM run scripts](https://michael-kuehnel.de/tooling/2018/03/22/helpers-and-tips-for-npm-run-scripts.html)
- [Eslint: Why and How to Use](https://medium.com/the-node-js-collection/why-and-how-to-use-eslint-in-your-project-742d0bc61ed7)
- [Your Last ESLint Config](https://medium.com/@netczuk/your-last-eslint-config-9e35bace2f99)

### Tips & Tricks

- [List of Cool Chrome Devtools and Tricks](https://flaviocopes.com/chrome-devtools-tips/)

## Templates

### PR template

## Description

Please include a summary of the change and which issue is fixed. Please also include relevant motivation and context. List any dependencies that are required for this change.

Fixes:

## Type of change

Check the box that applies.

- [ ] Bug fix (non-breaking change which fixes an issue)
- [ ] New feature (non-breaking change which adds functionality)
- [ ] Breaking change (fix or feature that would cause existing functionality to not work as expected)
- [ ] This change requires a documentation update

## Steps to Test or Repro

For a bug, include steps to reproduce the issue.
For a new feature, include the desired outcome. Link to the design doc as necessary.

## Related PRs

If this is a change that has been split into multiple PRs, please link to the other PRs.

## Todos

- [ ] Something out of scope but would be cool to add?
- [ ] Maybe clean up tech debt?

---

[^1]:

  Selected from a Blazemeter article [here.](https://www.blazemeter.com/blog/top-15-ui-test-automation-best-practices-you-should-follow)
