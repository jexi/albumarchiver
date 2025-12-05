# README #

### Introduction ###

AlbumArchiver is a desktop application which aims to organize all your music albums (CDs, LPs etc etc). It started as a personal home project. 
Some features added and one nice day i thought that may by useful to other users :-)

Supported operations are: 

* Insert album
* Delete album
* Search / Edit album
* Search / Edit artist
* Export your database in native MySQL format
* Export your database in html format
* Export your database in epub format (with css style suitable for Kindle users)

### How do I get set up? ###

Click on *Downloads*. Download `AlbumArchiver.zip`.

Unzip file and run `AlbumArchiver.jar`

It is assumed that you have already a MySQL database up and running. 
The very first time you'll run the app, you will notified that no config file exists. After that, you will prompted 
for database credentials (username / password with privilege to create database and tables) and database name.
(note that if there's already a database with the same name installation will fail).
Installation will create database, database tables and `dbconfig.properties` file with your credentials.


### External Libraries ###

Project users the following libraries

* [Epublib](http://www.siegmann.nl/epublib) for creating epub book.
* [itext 7](https://itextpdf.com/) for creating pdf
* iTunes API [https://github.com/mdewilde/itunes-api] for displaying iTunes artwork covers
* jFreeChart [https://www.jfree.org/jfreechart/] for displaying charts
* Keep in mind that in order to export mysql data you must have mysqldump in path `/usr/local/mysql/bin/mysqldump`

### Contribution guidelines ###

* If you have some great idea or you just simply want to improve functionality or you have found a bug 
* please report it :-) at jexi@noc.uoa.gr
