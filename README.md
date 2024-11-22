# Ion
A simple and lightweight java repository / gradle plugin that modifies java code. This library as of version 0.1 has no transforms included, and functions more as a proof of concept. 

Ion is, at its core, a gradle plugin which adds a new task called "toJava" which is executed immediately before the compileJava task. The plugin creates a copy
of every .java file in the source directory (src/main/java) into the output directory (build/java) and then modifies compileJava to compile the copied .java files rather than the originals.

The goal of this plugin in the future is to add customizable code operations called "transformers" which perform a function on the .java file source code. Every java file in the source directory will have its contents transformed by all enabled transformers, and then copied into the generated clone in the build directory, and subsequently compiled. This opens up the possibility for more flexible and newer java features, or even a separate programming language that transforms itself into java.

Of course, this is currently a proof of concept.
 
