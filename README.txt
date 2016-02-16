Candidate Name : Brucelin Mento Clemens
Title : Application Assignment

Problem Description :
---------------------
Web service
Create a tiny REST / JSON web service in Java using a framework (e.g. Spark, Spring MVC
(RestController) or similar) with an API that supports the following:
 Create new company
 Get a list of all companies
 Get details about a company
 Able to update a company
 Able to add beneficial owner(s) of the company
Create a tiny client using a well­known Javascript framework e.g. AngularJS, React or like

Solution: 
---------
1) Application is deployed on heroku at https://quiet-peak-90040.herokuapp.com/
2) Source code is at https://github.com/cbrucelinm/CompanyMgmt.git

Technologies used:
------------------
 Spring MVC, JSON, Angular JS, JQuery, Postgres Database, Tomcat Application Server.
 
Web application usage:
---------------------- 
  1) Load URL https://quiet-peak-90040.herokuapp.com/ in browser
  2) Click "BENEFICIAL OWNER" tab and enter owner details in the form and click "Add Beneficial owner" button to add a owner to the system.
  		Notes : i)Name and email id are mandatory fields for owner in GUI.
  				ii)Duplicate email id is not allowed.
  3) All available Beneficial owners are listed in a table below the input form under "BENEFICIAL OWNER" tab, each owner entry will have an update button. Clicking on the update button will bring all details of the owner into the form and allows users to update owner details.
  4) Click "COMPANY" tab and enter company details in the form and click "Add Company" button to add a companyto the system.
  		Notes : i)Company Name, Address, City, Country and at least one Beneficial owner is required to add a company in GUI.
  				ii)Duplicate Company name is not allowed.
  5) All available Companies are listed in a table below the input form under "COMPANY" tab, each company entry will have an update button. Clicking on the update button will bring all details of the company into the form and allows users to update company details.
    
Web Services and usage:
-----------------------
	1) Install cURL on your desktop/laptop
	2) To add a company use the following sample CURL command
   		
   		curl -X POST  https://quiet-peak-90040.herokuapp.com/companymgmt/company/add.htm --data "name=Company 3&address=23 South Avenue&city=New Delhi&country=India"
   		
		A successful execution will result the following output from curl and web application https://quiet-peak-90040.herokuapp.com/ will reflect the new company. 		
		{"Status":"Successfully created company Company 3"}
	3) To get a list of all companies use the following sample CURL command
  		
  		curl -X GET  https://quiet-peak-90040.herokuapp.com/companymgmt/company/list.htm
  		
  		A successful execution will result the following sample output from curl.
		["Company 1","Company2","Company 3"]

	4) To get details about a company use the following sample CURL command
   	
   		curl -X POST  https://quiet-peak-90040.herokuapp.com/companymgmt/company/get.htm --data "name=Company 3"
   		
   		A successful execution will result the following sample output from curl.
		{"country":"India","address":"23 South Avenue","phone":"","utime":"Tue Feb 16 08:58:11 UTC 2016","city":"New Delhi","name":"Company 3","ctime":"Tue Feb 16 08:58:11 UTC 2016","owners":[],"beneficialOwners":[],"id":4,"email":""}
		
	5) To add beneficial owner(s) of the company use the following sample CURL command
	
		curl -X POST  https://quiet-peak-90040.herokuapp.com/companymgmt/company/addowners.htm --data "companyName=Company 3&owners=Test Owner1,Test Owner2"
		
		A successful execution will result the following sample output from curl.
		{Status=Added owners successfully}
	
	6) To update a company use the following sample CURL command
	
		curl -X POST  https://quiet-peak-90040.herokuapp.com/companymgmt/company/update.htm --data "name=Company 3&address=1 South Avenue&city=San Franscisco&country=USA"
		
		A successful execution will result the following sample output from curl.
		{"Status":"Successfully updated company Company 3"}

Considerations:
---------------

1) Authentication protocol:
-------------------------
	As the system is already using spring mvc and spring is a stable standard and open source framework we can use spring-security for Authentication purposes.
	Which has options to customize autorization and authentication for specific needs.

2) How can you make the service redundant? What considerations should you do?
    The web services can be made redundant with multiple load balancing application servers, example tomcat load balancing feature which transfers multiple requests to available servers at run time, each application servers can handing different set of customers.
    
    
     