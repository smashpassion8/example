# Five Tools to Improve Your Java Code
## 1. Add Test Coverage With JaCoCo

Many projects aim for 100% test coverage. This metric can be deceiving, a low coverage score is generally a bad sign, but a high score doesn’t say anything about the quality of tests. There is a point of diminishing returns where you start writing tests just to hit an arbitrary coverage number. You should instead use test coverage to find problems and give you confidence in the quality of your code.

There are a few coverage options in Java-land, but my favorite has been JaCoCo (originally named EclEmma, which is still in the URL).

JaCoCo runs as a Java Agent, which makes it easy to add to any test framework (or any java execution). For Maven-based projects you simply add the JaCoCo Maven Plugin to your pom.

## 2. Do Static Source Code Analysis with PMD
PMD is a static code analyzer that can detect potential issues such as dead code, empty blocks, complicated statements, suboptimal code, and duplicate code. PMD doesn’t stand for anything; unofficially, some refer to it as "Programming Mistake Detector."

PMD’s Maven plugin one of the officially supported plugins in the Apache Maven project and comes with a robust set of default rules; however, if you are not happy with these rules, you can change them or define your own. 

## 3. Conduct Bytecode Analysis with SpotBugs and Find Security Bugs
SpotBugs checks bytecode, whereas PMD scans source files, this means anything the compiler throws away (unused imports or example) would not be reported by SpotBugs. The SpotBugs project is an updated version of FindBugs, many items in the documentation still reference "FindBugs." For those of you still using FindBugs, updating to SpotBugs is trivial.

SpotBugs also has a few plugins, my favorite being "Find Security Bugs", and as you might guess, it helps you detect security issues like weak hash functions, file/path traversals, untrusted inputs, and many more.

## 4. Ensure Backward Compatibility and Semantic Versioning with japicmp
Ensuring backward compatibility is difficult, there are many nuances that even a well-trained eye will miss. To add more complexity, Java has a notion of "source" compatibility and "binary" compatibility. Source compatibility means your code will compile without changes, whereas binary means it will run without modification. However, there are some exceptions to this rule too:

```
Adding a default method, or changing a method from abstract to default, does not break compatibility with pre-existing binaries, but may cause an IncompatibleClassChangeError if a pre-existing binary attempts to invoke the method.
                                    - The Java Language Specification
                                    Chapter 13 - Binary Compatibility
```
Usually, this is safe to ignore, but I point this out to help explain the complexity of this topic. If you want to ensure backward compatibility, you need a tool to help, and I strongly recommend japicmp.

## 5. Don't Skip Code Reviews
Using the above tools can help make your code reviews more effective. The goal should be to automate as much as possible out of your code review so that the human element can shine through. Have you ever asked, "is there a test for this" in a code review? Automate that, send your coverage data to a tool like Codecov, which can add the coverage delta to your pull requests. If your project has strict code style guidelines, you can use Checkstyle.

Code reviews are great; they provide an opportunity for both the author and the reviewer to learn from each other and ask questions, suggest alternatives, or discuss other architectural topics. The reviewer shouldn’t be wasting time checking for things that a program can detect.

```
Keep the number of changes in your reviews small and to the point. Nobody wants to review hundreds of potentially unrelated code changes at once.
```

## 6. Bonus: Scan your Dependencies for Vunerabilities

our code is just a small percentage of your overall application. Dependencies (direct and indirect) make up the rest. Keeping on top of vulnerabilities in those dependencies is not something you can do manually. Luckily for us, there are several tools available to help us out.

- OWASP Dependency Check - I’ve been using the Maven plugin with success for years. The only downside is there is a high rate of false-positive matches that requires updating an "exclusion" file in your repository.
- Snyk.io - Offers dependency scanning, and includes additional security issues that are not official in the NIST National Vulnerability Database.
- GitHub Dependabot - GitHub has been rolling out Dependabot, and there is a good chance it’s already scanning your public repositories. I’ve had mixed success in the past, specifically when it comes to Maven multi-module projects. I’m sure this will improve in the future.
- Many more! Have a favorite dependency scanner; let us know in the comments!

```
One key thing to remember the code in your repository doesn’t always match the code that is running in production. Make sure you track the dependencies in your production code too!
```

Using the tools in this post will help you write better (and more secure) Java code. Many of the issues detected also provide excellent examples of how to fix the problems, which is a great way to learn. You can also integrate most of them in your IDE so you can see the issues as soon as you type them.

This is just the tip of the iceberg. There are many other great projects; for example, SonarLint's IntelliJ plugin is excellent and will detect many of the issues I showed above.

## 7. maven-project-info-reports-plugin