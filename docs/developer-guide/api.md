Overview
========

CouponCodes features a full API for developers.  Developers who want to hook into this API can add the it as a dependency for their project.

Maven Repository: [http://repo.drevelopment.com/content/repositories/public](http://repo.drevelopment.com/content/repositories/public)

Javadocs: [http://drevelopment.com/javadocs/couponcodes/](http://drevelopment.com/javadocs/couponcodes/)

Examples
========

Maven
-----

	<repositories>
	    <repository>
	        <id>drevelopment</id>
	        <name>Drevelopment repo</name>
	        <url>http://repo.drevelopment.com/content/repositories/public</url>
	    </repository>
	</repositories>

	<dependencies>
	    <dependency>
	        <groupId>tech.feldman.couponcodes</groupId>
	        <artifactId>api</artifactId>
	        <version>3.1.2</version>
	    </dependency>
	</dependencies>

Gradle
------

	repositories {
	    mavenCentral()
	    maven {
	        name 'Drevelopment repo'
	        url 'http://repo.drevelopment.com/content/repositories/public'
	    }
	}

	dependencies {
	    compile 'tech.feldman.couponcodes:api:3.1.2'
	}