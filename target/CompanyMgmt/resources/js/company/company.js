 var CompanyApp = angular.module('CompanyApp', ['ngMaterial']);
   CompanyApp.service('dataService', function($http) {
    	delete $http.defaults.headers.common['X-Requested-With'];
    	this.loadCompanyList = function() {
    	    return $http({
    	        method: 'GET',
    	        url: 'companymgmt/company/list.htm'
    	     });
    	 };
    	 this.loadOwnerList = function() {
     	    return $http({
     	        method: 'GET',
     	        url: 'companymgmt/owner/list.htm'
     	     });
     	 };
     	this.addCompany = function(name,address,city,country,email,phone,ownerIds) {
     		console.log("name : " + name);
     	    return $http({
     	        method: 'POST',
     	        url: 'companymgmt/company/add.htm',
     	       headers: {'Content-Type': 'application/x-www-form-urlencoded'},
     	        data:  "name="+ name + "&address="+ address+ "&city="+  city + "&country="+ country + "&email="+ email+ "&phone="+ phone+ "&ownerIds="+ ownerIds 
     	        
     	     });
     	 };
     	this.updateCompany = function(name,address,city,country,email,phone,ownerIds) {
     		console.log("name : " + name);
     	    return $http({
     	        method: 'POST',
     	        url: 'companymgmt/company/update.htm',
     	       headers: {'Content-Type': 'application/x-www-form-urlencoded'},
     	        data:  "name="+ name + "&address="+ address+ "&city="+  city + "&country="+ country + "&email="+ email+ "&phone="+ phone+ "&ownerIds="+ ownerIds 
     	        
     	     });
     	 };
     	this.loadCompany = function(name) {
     		console.log("name : " + name);
     	    return $http({
     	        method: 'POST',
     	        url: 'companymgmt/company/get.htm',
     	       headers: {'Content-Type': 'application/x-www-form-urlencoded'},
     	        data:  "name="+ name  
     	     });
     	 };
     	this.addOwner = function(name,address,city,country,email,phone,dobStr) {
     		console.log("name : " + name);
     	    return $http({
     	        method: 'POST',
     	        url: 'companymgmt/owner/add.htm',
     	       headers: {'Content-Type': 'application/x-www-form-urlencoded'},
     	        data:  "name="+ name + "&address="+ address+ "&city="+  city + "&country="+ country + "&email="+ email+ "&phone="+ phone+ "&dobStr="+ dobStr 
     	        
     	     });
     	 };
     	this.updateOwner = function(id,name,address,city,country,email,phone,dobStr) {
     		console.log("name : " + name);
     	    return $http({
     	        method: 'POST',
     	        url: 'companymgmt/owner/update.htm',
     	       headers: {'Content-Type': 'application/x-www-form-urlencoded'},
     	        data:  "id=" + id + "&name="+ name + "&address="+ address+ "&city="+  city + "&country="+ country + "&email="+ email+ "&phone="+ phone+ "&dobStr="+ dobStr 
     	        
     	     });
     	 };
     	this.loadOwner = function(id) {
     		console.log("name : " + id);
     	    return $http({
     	        method: 'POST',
     	        url: 'companymgmt/owner/get.htm',
     	       headers: {'Content-Type': 'application/x-www-form-urlencoded'},
     	        data:  "id="+ id  
     	     });
     	 };
    	});

    CompanyApp.controller("companyController", function($scope, dataService) {
    	console.log("abc");
    	$scope.tab = "Company";
    	$scope.action = "Add";
    	$scope.companies = [];
    	$scope.owners = [];
    	$scope.company = null;
    	$scope.owner = null;
    	$scope.msg = "";
    	$scope.setTab = function(tabTemp)  {
    		console.log("set : " );
    		$scope.tab =tabTemp;
    		
        	//console.log("set : " + this.tab );
        };
        console.log("abc" + $scope.companies );
        $scope.getTab = function()  {
        	//console.log("get : " + this.tab );
        	return $scope.tab;
        };
        console.log("abc");
        $scope.loadCompanyList = function()  {
        	$scope.company = null;
        	dataService.loadCompanyList().then(function(dataResponse) {
        	      $scope.companies = dataResponse.data;
        	}, 
            function(dataResponse) { // optional
        		console.log("error" + dataResponse);
        		 $scope.msg = {"Error" :  dataResponse.data};
        });
        };
        $scope.loadCompanyList();
        $scope.loadOwnerList = function()  {
        	dataService.loadOwnerList().then(function(dataResponse) {
        	      $scope.owners = dataResponse.data;
        	}, 
            function(dataResponse) { // optional
        		console.log("error" + dataResponse);
        		 $scope.msg = {"Error" :  dataResponse.data};
        });
        };
        $scope.addCompany = function(name,address,city,country,email,phone,ownerIds)  {
        	dataService.addCompany(name,address,city,country,email,phone,ownerIds).then(function(dataResponse) {
        		  console.log("g" + dataResponse);
        		  //$scope.msg = dataResponse.data;
        		  $scope.msg = dataResponse.data;
        		  $scope.loadCompanyList();
        	}, 
            function(dataResponse) { // optional
        		console.log("error" + dataResponse);
        		 $scope.msg = {"Error" :  dataResponse.data};
            	
        });
        };
        $scope.updateCompany = function(name,address,city,country,email,phone,ownerIds)  {
        	dataService.updateCompany(name,address,city,country,email,phone,ownerIds).then(function(dataResponse) {
        		  console.log("g" + dataResponse);
        		  //$scope.msg = dataResponse.data;
        		  $scope.msg = dataResponse.data;
        	}, 
            function(dataResponse) { // optional
        		console.log("error" + dataResponse);
        		 $scope.msg = {"Error" :  dataResponse.data};
            	
        });
        };
        $scope.loadCompany = function(name)  {
        	dataService.loadCompany(name).then(function(dataResponse) {
        		  console.log("g" + dataResponse);
        		  //$scope.msg = dataResponse.data;
        		  $scope.company = dataResponse.data;
        		  document.getElementById('name').value =  $scope.company.name;
        		  if (!$('#name').attr('disabled')) {
        			  $('#name').attr({
                          'disabled': 'disabled'
                      });
                  };
                  document.getElementById('email').value =  ($scope.company.email)?$scope.company.email:"";
           	 	document.getElementById('address').value =  $scope.company.address;
           	 	document.getElementById('city').value =  $scope.company.city;
           	 	document.getElementById('country').value =  $scope.company.country;
           	 	document.getElementById('phone').value =  ($scope.company.phone)?$scope.company.phone:"";
           	 $('#ownerIds option').each(function(){
		       		$(this).attr("selected",false); 
		    	});
           	 $.each($scope.company.beneficialOwners, function(key,value) {
       		  // alert(value.oid);
       		  $('#ownerIds option').each(function(){
       		      if ($(this).attr("value") == value.oid) { 
       		        $(this).attr("selected",true); // select if same value
       		      }
       		    });
       		}); 
        	}, 
            function(dataResponse) { // optional
        		console.log("error" + dataResponse);
        		 $scope.msg = {"Error" :  dataResponse.data};
            	
        });
        };
        	        $scope.addOwner = function(name,address,city,country,email,phone,dobStr)  {
        	        	dataService.addOwner(name,address,city,country,email,phone,dobStr).then(function(dataResponse) {
        	        		  console.log("g" + dataResponse);
        	        		  //$scope.msg = dataResponse.data;
        	        		  $scope.msg = dataResponse.data;
        	        		  $scope.loadOwnerList();
        	        	}, 
        	            function(dataResponse) { // optional
        	        		console.log("error" + dataResponse);
        	        		 $scope.msg = {"Error" :  dataResponse.data};
        	            	
        	        });
        	        };
        	        $scope.updateOwner = function(id, name,address,city,country,email,phone,dobStr)  {
        	        	dataService.updateOwner(id, name,address,city,country,email,phone,dobStr).then(function(dataResponse) {
        	        		  console.log("g" + dataResponse);
        	        		  //$scope.msg = dataResponse.data;
        	        		  $scope.msg = dataResponse.data;
        	        		  $scope.loadOwnerList();
        	        	}, 
        	            function(dataResponse) { // optional
        	        		console.log("error" + dataResponse);
        	        		 $scope.msg = {"Error" :  dataResponse.data};
        	            	
        	        });
        	        };
        	        $scope.loadOwner = function(id)  {
        	        	dataService.loadOwner(id).then(function(dataResponse) {
        	        		  console.log("gs" + dataResponse.data);
        	        		  //$scope.msg = dataResponse.data;
        	        		  $scope.owner = dataResponse.data;
        	        		 
                    
        	        		  document.getElementById('name').value =  $scope.owner.name;
             	 	document.getElementById('email').value =  ($scope.owner.email)?$scope.owner.email:"";
             	 	document.getElementById('address').value =  $scope.owner.address;
             	 	document.getElementById('city').value =  $scope.owner.city;
             	 	document.getElementById('country').value =  $scope.owner.country;
             	 	document.getElementById('phone').value =  ($scope.owner.phone)?$scope.owner.phone:"";
             	 	document.getElementById('dob').value =  $scope.owner.dobStr;
        		  
               	 
        	}, 
            function(dataResponse) { // optional
        		console.log("error" + dataResponse);
        		 $scope.msg = {"Error" :  dataResponse.data};
            	
        });
        };
        $scope.loadOwnerList();
        console.log("abc");
        $scope.ownerId = -1;
        
        $scope.submit = function(){
        	$scope.msg ="";
        	// Validation 	 
        	 var name = document.getElementById('name').value;
        	 var email = document.getElementById('email').value;
        	 var address = document.getElementById('address').value;
        	 var city = document.getElementById('city').value;
        	 var country = document.getElementById('country').value;
        	 var ownerIds = document.getElementById('ownerIds').value;
        	 var phone = document.getElementById('phone').value;
        	 var dobStr = document.getElementById('dob').value;
        	 var filter = /^([a-zA-Z0-9_\.\-])+\@(([a-zA-Z0-9\-])+\.)+([a-zA-Z0-9]{2,4})+$/;  
        	 var regex = /^[a-zA-Z][a-zA-Z0-9\_\-\ ]*$/;	
        	 if (name == "") {
        			    name_validation.innerHTML = "Name Required";
        			    return;
        			  } else {
        				  name_validation.innerHTML = "";
        			  }
        	 if( $scope.tab == "Company") {
	        	 if (address == "") {
	        		    address_validation.innerHTML = "Address Required";
	        		    return;
	        		  } else {
	        			  address_validation.innerHTML = "";
	        		  }
	        	 if (city == "") {
	        		    city_validation.innerHTML = "City Required";
	        		    return;
	        		  } else {
	        			  city_validation.innerHTML = "";
	        		  }
	        	 if (country == "") {
	        		    country_validation.innerHTML = "Country Name Required";
	        		    return;
	        		  } else {
	        			  country_validation.innerHTML = "";
	        		  }
	        	 if (ownerIds == "") {
	   			  owners_validation.innerHTML = "One or more owner(s) required";
	   			    return;
	   			  } else {
	   				  owners_validation.innerHTML = "";
	   			  }
	        	var options = document.getElementById("ownerIds").options;
	        	ownerIds = [];
 				for ( var i = 0; i < options.length; i++) {
 					var opt = options[i];
 					if (opt.selected) {
 						ownerIds.push(opt.value);
 					}
 				}
        	 } else {
        		 if (email == "") {
        			    email_validation.innerHTML = "Email Required";
        			    return;
        			  } else {
        				  email_validation.innerHTML = "";
        			  }
        			  
        	 }
	        	if(regex.test(name)){
        	    	 name_validation.innerHTML ="";
        	     }else{
        	    	 name_validation.innerHTML = "Special character not allowed and should start with a letter";
        	     	return;
        	     }
        	 	  
        			  
        			  if(email != "" && !filter.test(email)) {
        			    email_validation.innerHTML = "Invalid email address";
        			    return;
        			  } else {
        				  email_validation.innerHTML = "";
        			  }
        			  
        			   
        			  
        	// Validation Done
        	
        			 if( $scope.tab == "Company" ) {
        				 
        				 if( $scope.action == "Add") {
        				 	$scope.addCompany(name,address,city,country,email,phone,ownerIds);
        				 }
        				 if( $scope.action == "Update") {
         				 	$scope.updateCompany(name,address,city,country,email,phone,ownerIds);
         				 }
        			 } else {
        				 if( $scope.action == "Add") {
         				 	$scope.addOwner(name,address,city,country,email,phone,dobStr);
         				 }
         				 if( $scope.action == "Update") {
          				 	$scope.updateOwner( $scope.ownerId,name,address,city,country,email,phone,dobStr);
          				 }
        			 }
        			 if($scope.msg.Error == null || $scope.msg.Error == '') {
    					  $scope.clearForm();
    				 }
        			 
        	};

        	 $scope.load = function(name){
        			$scope.action = "Update";
                    
             	$scope.msg ="";
             	if( $scope.tab == "Company") {
             		console.log("namefsd : " + name );
             		$scope.loadCompany(name);
             	} else {
             		$scope.loadOwner(name);
             		$scope.ownerId = name;
             	}
             	
             	

           	 
             	
             	};
             	
             	$scope.clearForm = function() {
             		document.getElementById('name').value = "";
               	 	document.getElementById('email').value = "";
               	 	document.getElementById('address').value = "";
               	 	document.getElementById('city').value = "";
               	 	document.getElementById('country').value = "";
               	 document.getElementById('phone').value = "";
               	 	document.getElementById('dob').value = "";
               	 $scope.msg = "";
               	 	$('#ownerIds option').each(function(){
     		       		$(this).attr("selected",false); 
     		    	});
               		$scope.action = "Add";
               		if ($('#name').attr('disabled')) {
               			$('#name').removeAttr('disabled');
               		}
             	};
             	
             	$( "#dob" ).datepicker({
      		      changeMonth: true,
      		      changeYear: true
      		    });
    });