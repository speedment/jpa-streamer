Contributing to Speedment
=========================

Contributor License Agreement
-----------------------------

In order to protect your rights to your contributions and in order to protect the users of Speedment, you need
to sign a Contributor License Agreement with Speedment, Inc. before we can accept your contributions.

The contract can be found [here](https://github.com/speedment/jpa-streamer/blob/master/CONTRIBUTOR_LICENSE_AGREEMENT) and a signed version of this is e-mailed to info@speedment.com.

Once we have received the signed CLA, your name will be added to the project's ```<contributors>...</contributors>``` tags :-)

The simplest way of signing the CLA is to:

* Copy the file `CONTRIBUTOR_LICENSE_AGREEMENT` to the directory `/cla`. 
* Fill in the requested data in the file.
* Rename the file to your GitHub handle name. 
* Include the file in your first pull request.

Creating a Pull Request
-----------------------------
Make sure you are creating a **pull request against the 'develop' branch**. This makes it much easier for us to merge the pull request in the proper way (since all new stuff is funneled through the 'develop' branch). It also enables your name as a contributor to be retained for the changes files, which is nice.


Code Style
----------

Please take a moment and check the existing code style before writing your own 
contributions. This will make it much easier to review and accept your pull
requests. Main code style features includes:

* Break up stream commands on separate lines:
```java
    list.stream()
        .filter(...)
        .map(...)
        .collect(...)
```

* Keep methods short
* Keep classes short (yeah, we failed on this on some occasions)
* Use Java 8's features like lambdas, ```Function```, ```Supplier```, etc
* Break up method parameters on separate lines if you have more than three parameters
* Never return ```null``` values, use ```Optional``` or empty ```Collections``` instead
* Favor composition over inheritance
* Write JavaDoc to your methods and classes
* Add Unit Tests to your code
* Make your variables ```final``` when possible
