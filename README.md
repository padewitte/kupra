Kupra - MongoDB REST Server
==============================

Download
------------------------------
[Download the lastest release ](https://github.com/padewitte/kupra/releases)

Quick start
-----------------------------
Kupra is a simple REST Server for MongoDB. It could help you building quickly a simple webapp without taking care of a the server side. You just have to set the KUPRA_MONGOD_URI on your system then deploy Kupra war in your tomcat or Jetty.  

With Kupra you can perform basic CRUD operation but also bulk insert or update, aggregation, count and sort.

##### Open your browser at [http://127.0.0.1:8080/kupra/documentation](http://127.0.0.1:8080/kupra/documentation) and enjoy !

##### Search for all documents where name equals Sylvain CHAVANEL with AngularJS
````
$http({
	method :  'GET',
	url : 'http://127.0.0.1:8668/kupra/rest/myDB/myCollection?query={"name" : "Sylvain CHAVANEL"}'}
}).success(function(data, status, headers, config) {
	  $scope.myList = data;
});
````

##### Save a document in a collection with JQuery
````
$.post( 'http://127.0.0.1:8668/kupra/rest/myDB/myCollection', {"_id" : 1, "name" : "Sylvain CHAVANEL"},
	function( data ) {
  		console.log( data.ok );
	}, "json");
````


Documentation Index
-----------------------------------
- [Getting started](https://github.com/padewitte/kupra/wiki/Getting-Started)
- [GET  - List, search, aggregate, count, colStats](https://github.com/padewitte/kupra/wiki/%5BAPI%5D-GET)
- [GET by id - Consult one document ](https://github.com/padewitte/kupra/wiki/%5BAPI%5D-GET_BY_ID)
- [POST - Add document](https://github.com/padewitte/kupra/wiki/%5BAPI%5D-POST)
- [PUT - Partial or multi-document update](https://github.com/padewitte/kupra/wiki/%5BAPI%5D-PUT)
- [PUT by id - Document replacement](https://github.com/padewitte/kupra/wiki/%5BAPI%5D-PUT_BY_ID)
- [DELETE - Delete with a filter](https://github.com/padewitte/kupra/wiki/%5BAPI%5D-DELETE)
- [DELETE by id - Delete one document](https://github.com/padewitte/kupra/wiki/%5BAPI%5D-DELETE_BY_ID)

Developer corner
----------------------------------
To build kupra, clone this repo then `gradle war`

Please [open an issue](https://github.com/padewitte/kupra/issue) if you find a bug or if you want to suggest an improvement. Contribution are also really welcome.

Licence
----------------------------------
Kupra is released under the MIT Licence. It is open source and free of charge.
