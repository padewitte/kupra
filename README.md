MRC - Query MongoDB in REST
==============================

Download
------------------------------
Download last release as a [zip file](http://padewitte.github.io/mrc/release/mrc-0.0.3.zip) or as download it as a [tarball](http://padewitte.github.io/mrc/release/mrc-0.0.3.tar.gz).

Quick start
-----------------------------
Tired of writing boiler plate code to interact with your MongoDB database. MRC is for you. After a copy / run installation you will be able to query MongoDB collections as REST entities. For example a GET to http://mymrchost/mrc/collection will list all items of your collection in JSON.   
Assuming java is installed and MongoDB is running localy, download MRC, unzip it then launch `mrc.bat -doc 127.0.0.1/mrc` or `mrc.ksh -doc 127.0.0.1/mrc` depending of your plateform. Open your browser at [http://127.0.0.1:8668/](http://127.0.0.1:8668/) and interact with your database.

Going further
----------------------------------
A full documentation is available in [project wiki](https://github.com/padewitte/mrc/wiki).    
MRC is released under the MIT Licence. It is open source and free of charge.    
Please [open an issue](https://github.com/padewitte/mrc/issue) if you find a bug or if you want to suggest an improvement.
Follow [@MongoRESTCamel](https://twitter.com/MongoRESTCamel)to be notified of project updates</p>

Documentation Index
-----------------------------------
- Introduction
  - [History of MRC](https://github.com/padewitte/mrc/wiki/Why-MRC-was-create-&%2363;)
  - [Installation](https://github.com/padewitte/mrc/wiki/Installation)
  - [Getting started](https://github.com/padewitte/mrc/wiki/Getting-Started)
  - [Command line documentation](https://github.com/padewitte/mrc/wiki/Command-Line-Options)

- Manipulate your data
  - [GET /mrc/{collection name}](https://github.com/padewitte/mrc/wiki/GET-&%2347;mrc&%2347;%7Bcollection-name%7D)
  - [POST /mrc/{collection name}](https://github.com/padewitte/mrc/wiki/POST-&%2347;mrc&%2347;%7Bcollection-name%7D)
  - [PUT /mrc/{collection name}](https://github.com/padewitte/mrc/wiki/PUT-&%2347;mrc&%2347;%7Bcollection-name%7D)
  - [DELETE /mrc/{collection name}](https://github.com/padewitte/mrc/wiki/DELETE-&%2347;mrc&%2347;%7Bcollection-name%7D) 
  - [GET /mrc/{collection name}/{id}](https://github.com/padewitte/mrc/wiki/GET-&%2347;mrc&%2347;%7Bcollection-name%7D&%2347;%7Bid%7D)
  - [PUT /mrc/{collection name}/{id}](https://github.com/padewitte/mrc/wiki/PUT-&%2347;mrc&%2347;%7Bcollection-name%7D&%2347;%7Bid%7D)
  - [DELETE /mrc/{collection name}/{id}](https://github.com/padewitte/mrc/wiki/DELETE-&%2347;mrc&%2347;%7Bcollection-name%7D&%2347;%7Bid%7D)

- Developer corner
  - Building MRC **Documentation in progress**
  - Include MRC in your project **Documentation in progress**
