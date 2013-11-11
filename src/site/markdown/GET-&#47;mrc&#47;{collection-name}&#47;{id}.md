>**Get an element by his id**   
>Query the *{collection name}* in mongo, to find the document with the *_id* filed equals to the *{id}* in query parameter.    
>The matching document is return as a JSON string in body workload.


###Path Parameters

<table>
<tr><td><strong>collection name</strong></td><td>The name of the collection to perform the requested action</td><td>mandatory</td></tr>
<tr><td><strong>id</strong></td><td>Id of object to get</td><td>Mandatory</td></tr>
</table>

### Header Parameters
<table  width="95%">
<tr><td><strong>FieldsFilter</strong></td><td>Perform a <a href="http://docs.mongodb.org/manual/reference/method/db.collection.find/">projection as describe in MongoDB documentation</a>. Useful when to discard or only show some fileds of a document.</td><td>Optional</td></tr>
</table>

###Examples

#####To get the document with _id 1 in collection test
 `curl -X GET http://127.0.0.1:8667/mrc/test/1` should produce `{ "_id" : "1" , "name" : "Sylvain CHAVANEL"}`

### Going deeper
A find by id is performed in database.