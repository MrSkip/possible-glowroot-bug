# possible-glowroot-bug

To enable the Glowroot please add this line as VM args: 
>-javaagent:libs/glowroot.jar

From what I understand, Glowroot has built its functionality based on editing the classes at runtime via reflection.

This test project uses Mongoc to perform changes to the DB and this lib has its own decorators for some of the classes from the MongoDB package to be able to get control over the query execution flow.


**So we have MongoDB classes, we have decorators for them and we have Glowroot with a reflection for the same classes.**


If we enable glowroot for this test project then we would get:
```
java.lang.NoSuchMethodError: 'com.mongodb.client.MongoCursor java.lang.Object.iterator()'
```
I think it was caused by Glowroot because of an incompatible change to one of Mongock's decorator class (FindIterableDecoratorImpl.class) that Glowroot did.

[NoSuchMethodError - this error can only occur at runtime if the definition of a class has incompatibly changed](https://docs.oracle.com/en/java/javase/11/docs/api/java.base/java/lang/NoSuchMethodError.html)

I've re-compiled the Glowroot without the place that is responsible for editing that specific mongo class and it started to work with the decorator class

If we have not enabled Glowroot for this test project then it will work just fine.

!Important: do not forget to remove all documents (there will be just one) from the collection 'mongockChangeLog' if the project has been executed with no errors.
