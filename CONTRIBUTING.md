# Contributing

Contributions to this project are appreciated. If you would like to contribute, please create a fork of this repository
and propose your changes in a pull request.

### Contributions should aim to do any of these things:

- Fix a bug<sup>1</sup>
- Improve the quality of life for users<sup>2</sup>
- Increase compatibility between other mods

<sup><i>1 - If you are fixing a bug that does not currently have a report on the GitHub repository, create it
first.</i></sup>
<br><sup><i>2 - QoL improvements can include things like adding configuration options for a feature or eliminating
quirks of existing features. It does not include adding new content to the game.</i></sup>

#### Contributions Involving New Game Content

Contributions for new game content *are* accepted, however they must be discussed thoroughly. The best place to start a
discussion is in the official Discord server, which can be found in
[README.md](https://github.com/AnonymousHacker1279/ImmersiveWeapons/blob/master/CONTRIBUTING.md).

### Contributing to End of Life Versions

Immersive Weapons versions are marked EoL whenever a new version of Minecraft releases, and I have updated to the new
version. I do this because I cannot feasibly support multiple versions of the game with such a large project. If you are
willing to backport features from modern versions to old toolchains, I will certainly form an LTS team for that. Please
mention me in the official Discord server if you are interested.

**I cannot help you learn to work with old MC versions if you don't already know how they work. Chances are I don't know
either.**

## Simple and Easy Contributions

If you want to contribute but don't know where to start, there are some smaller bits that can always use some
improvement.

### JavaDocs

JavaDocs explain what methods do, the arguments they accept, and how to properly use them. While I have added
documentation to some methods, they are often *very* basic and lack useful detail. Feel free to rewrite these.
Additionally, adding comments in places where the function of code is vague would be appreciated.

### Feature Configurations

The more a user can configure, the better. I've added the ability to configure things like structure spacing, ore
generation settings, and entity spawns, along with a few miscellaneous client features. If you see something that would
definitely benefit from being configurable, make it configurable! It's pretty easy to do, look at how I do it if you
need help.

## What NOT to Contribute

There are a few things that don't need to be contributed.

### Bumping Dependency Versions

Don't make a PR that bumps the Forge build version up from like `39.0.43` to `39.0.44`. There's just no point. Same with
dependencies. I don't need a PR every time one of the dependencies updates! The only exception is if it fixes a reported
bug. If that's the case, *definitely* make a PR.

### Minor Code Cleanups

Don't rearrange my code to make three lines look fancier. I don't want 37 different PRs about how I could make a tiny
section prettier. Now, if you're cleaning up multiple classes with significant changes because there is a genuine need
for a cleanup, go ahead. Just don't get into refactor-haven.

## How to Prepare your Pull Request

As stated at the top of this document, you will need to fork the repository and make your changes there. You can create
a PR whenever you are ready to merge your changes.

You should target the development branch for the Minecraft version you are working with. For example, a PR under
Minecraft 1.18.1 should target the ``1.18.x-dev`` branch. Additionally, you will be asked to sign a Contributor License
Agreement (CLA). This ensures you are aware of the MIT license governance over your contributions.

**Please note that creating bug reports/suggestions does not involve the CLA. This is only required for PRs.**

Your PR title should be a short description of what's being changed. It doesn't need to be an essay in length, but I
don't want to see a PR titled "pr" either. Your description needs to describe ***in detail*** each change, what it does,
and, if an existing feature is changing, why its being changed.

Ideally, your PR should aim to modify *one* component. You are free to make as many PRs as you want, but by keeping each
one to one component, it is easier to see what changes are going where. And if something were to go wrong, it could be
reverted without also removing other changes.

## Code Style Guide

Pretty simple. For the most part, keep the style like it is in other classes. You can tell your IDE to automatically use
these if you don't feel like doing it yourself.

### Newline Curly Braces? Nope.

Don't put curly braces for classes/methods/etc. on new lines. They should be kept on the same line.

### Tabs? Heck yeah.

Tabs are superior to spaces. For indentations, use tabs. The tab width should be **4**.

### Line Width? Make it readable.

Most IDEs will give you a fancy line that tells you when you should wrap a line. Like, if your line only passes it by a
few characters, it's fine. If you're trying to fit an entire class in one line then you probably need to wrap it. The
code should be easily readable.

### Object Names? As long as it makes sense.

Names should make sense. Not super long, not too short. If I can read the name and get an idea of what it does without
major context, it's good.

For example, making a variable that contains a cooldown timer might be called something like `cooldown`. It ***SHOULD
NOT*** be something like `c`.

**If I see an object that's named something like `c` I will hunt you down**

Just like with short and non-descriptive names, I don't want to see variables that are twenty words long. Like c'mon
man. Just why.

## Resources for Setting Up a Development Environment

As with any project you'll need some resources to prepare the development environment.

### Integrated Development Environment (IDE)

The IDE you use really shouldn't matter that much. The two IDEs with support from Forge are Eclipse and IntelliJ IDEA.
While Eclipse works for the most part I find that IDEA is much more professional and offers many more helpful tools.
While I highly recommend IDEA, if you are more comfortable with Eclipse, use that instead.

### Git

You'll have to use some sort of version control software for pushing changes to your fork. The Git client you use
shouldn't matter. Personally, I use GitHub Desktop because I don't care to memorize every CLI command, and it provides a
nice screen for viewing diffs.

Your IDE might have a Git client built in that you can use to manage commits, if you want to use that.

### Java Experience

If you want to work with a Java project then you'll need to know Java. Here are some resources for that:

Official documentation: https://docs.oracle.com/javase/tutorial/

Absolute basics with an interactive editor: https://www.codecademy.com/learn/learn-java

Ongoing online course with assignments: https://java-programming.mooc.fi/

Specific language features you need:

- The Basics: Classes, Primitives, Methods, Variables
- Functional Java: Lambda Expressions, Method References, Streams (Java 8+)
- Forge Specifics: Registration, Events, Annotations

While most of these are Java 8 language features, if you are working in a version of Minecraft that uses a newer Java
version (i.e., 1.18 uses Java 17), you can (and probably should) make use of those features. A lot of times your IDE
will give you suggestions for migrating to newer Java features.

### Gradle Experience

Gradle is used to set up tasks for your IDE and to prepare your workspace. You **do not** need in-depth experience with
it. All you need to know is how to import a Gradle project into your IDE and run Gradle tasks. You should also take a
look at the `build.gradle` file in the project root. It specifies dependency versions, the project build version, etc.