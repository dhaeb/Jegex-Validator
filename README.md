# Jegex-Validator

The Jegex-Validator (Java Regular-Expression Validator) is a simple library to check your java regular expression strings at *compile time*.
It has no runtime dependencies at all! You don't need to distribute it with your code.

If you are using regular expression a lot in your code, you may have noticed that it is cumbersome to maintain those. You must write tests to ensure their wellformedness and also that they match your expected inputs.  
This is of course much harder because in java a regular expression is constructed using  an instance of string. 

Using this library, you can use the java compiler to ensure that regex strings are compilable to java patterns:

```java

@ValidateRegex(matches={"29.05.2014"})
private static final String PATTERN = "(\\d\\d)\\.(\\d\\d)\\.(\\d{4})"; // can be used later as input for java.util.regex.Pattern.compile(...)

```

## Getting Started
There are no prerequisites and runtime thrid party dependencies. You just need to have an JDK and maven installed.

### Include the java library to your compile classpath

This library is build with maven. This project will be uploaded to maven central soon. Until then, please build the project with maven on your own. You just need to checkout the git project, install it using maven, and reference it in your own classpath. At best, you use also maven and include a new dependency into your pom:
```xml
<dependency>
    <groupId>de.kdi</groupId>
    <artifactId>JavaRegexValidator</artifactId>
    <version>0.0.1-SNAPSHOT</version>
</dependency>
```

### Use it!

After adding this project to your compile classpath, the used annotation processor will be automatically detected by your javac compiler.
Be aware of the settings for annotation processors. For the oracle / sun java compiler, checkout this [http://docs.oracle.com/javase/7/docs/technotes/tools/windows/javac.html](link).

Now your are ready to use it. Well lets start easy. The library was designed to annotate *final and initialized string fields* to ensure their usability as regular expressions at compile time.
Therefore, the annotation which you need to use has the full qualfied class name: <code>de.kdi.validate.ValidateRegex</code>. 

As an example, the usage can look like that:

```java
//...package declaration
import java.util.regex.Pattern;
import de.kdi.validate.ValidateRegex;

public class SimpleWorkingExample {

        private static final String MATCHABLE = "The quick brown fox jumps over the lazy dog.";
            
        @ValidateRegex
        private static final String REGEX1 = "fox"; // works as expected
              
        @ValidateRegex
        private final String regex2 = "snake"; // works as expected
                                   
        public static void main(String[] args) {
            Pattern compiledPattern1 = Pattern.compile(REGEX1);
            Pattern compiledPattern2 = Pattern.compile(new SimpleWorkingExample().regex2);
            assert compiledPattern1.matcher(MATCHABLE).find();
            assert !compiledPattern2.matcher(MATCHABLE).find();
        }
                                                                                    
}

This example, like all other examples, is included as a test under /src/test/java-fixtures. 
As you can see we define 2 string constants and a final field in this class.
Of course the first string has the purpose to save the matchable into a constant. 
More interesting are the constant <code>REGEX1</code> and field <code>regex2</code>. 
Those are annotated like issued above. If you annotate a string constant like that it will be ensured to compile time that your regex is wellformed.
This means you can check the regex now during compilation!

Of course this is quite nice, but wellformedness is only one side of the medal. 
Validation for a given set of strings is the other one. This frameworks does support this too. 
Take a look on the following example code:


```java
// ...package declaration
import java.util.regex.Pattern;

import de.kdi.validate.ValidateRegex;

public class ExtendedWorkingExample {

        private static final String MATCHABLE = "The quick brown fox jumps over the lazy dog.";
            
        @ValidateRegex(matches={"What does the fox say?", MATCHABLE})
        private static final String REGEX1 = ".*fox.*"; // compiles when all strings of the matches array matches() the pattern
                       
        @ValidateRegex(matches="The snake")
        private final String regex2 = ".*snake$"; 
                                   
        public static void main(String[] args) {
            Pattern compiledPattern1 = Pattern.compile(REGEX1);
            Pattern compiledPattern2 = Pattern.compile(new ExtendedWorkingExample().regex2);
            assert compiledPattern1.matcher(MATCHABLE).matches();
            assert !compiledPattern2.matcher(MATCHABLE).matches();
        }
}

```

Last but not least, you can set the patternQuote flag of @ValidateRegex to true. 
This will apply the java.util.regex.Pattern.quote(string) function on the pattern string before using the pattern for the examples matches. 
The call of this function is often convenient when the pattern has many characters to escape. 

Happy regexing! Feel free to suggest any enhancements! 

## Developer info

Limitation: In the moment, this project builds just using JDK 7 (due to the test output)

## Colophon
This project in published in terms of the Apache license version 2.0:

Copyright 2014 Dan Häberlein

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.

I added and modified the class [http://aphillips.googlecode.com/svn/commons-test-support/trunk/src/main/java/com/qrmedia/commons/test/annotation/processing/AbstractAnnotationProcessorTest.java](AbstractAnnotationProcessorTest) by Andew Phillips.
He described in [http://blog.xebia.com/2009/07/21/testing-annotation-processors/](this article) how to create a easy testing environment for annotation processores, which I adopted and used in this project.
