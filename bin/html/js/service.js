app.service('RestService', function() {
	this.postMessage = function($http, $scope) {
    	console.log("Posting Message :: postMessage()");
/*	    var dataObject = {
	       hqType : $scope.publish_form.hqType
	       ,destinationName  : $scope.publish_form.destination.name
	       ,destinationJndiName : $scope.publish_form.destination.objects.JNDIBindings[0]
	       ,messageType : $scope.publish_form.messageType
	       ,messageInputType : $scope.publish_form.messageInputType
	       ,messageByTextarea : $scope.publish_form.messageByTextarea
	       ,messageByLocalFilePath : $scope.publish_form.messageByLocalFilePath
	       //,message_by_uploadfile : $scope.publish_form.message_by_uploadfile
	    };
*/	    
	    //alert($scope.publish_form.destination.objects.JNDIBindings[0]);
	    
	    var responsePromise = $http.post("/hqutam/rs/pub", $scope.publish_form);
	    
	    return responsePromise;
	}
	
	this.getDestinations = function($http, $scope) {
		var response = $http.get("/hqutam/rs/getAllQNames");
		return response;
	}
	
	this.getDestinationInfo = function($http, $scope) {
		console.log("DestinationInfo :: getDestinationInfo :: "+$scope.publish_form.destinationType+" destination name :: "+$scope.publish_form.destinationName);
		var dataObject = {dName :$scope.publish_form.destinationName};
		if($scope.publish_form.destinationType == 'Q') {
			return $http.get("/hqutam/rs/getQInfo", {params:dataObject});
		}
		
		if($scope.publish_form.destinationType == 'T') {
			return $http.get("/hqutam/rs/getTInfo", {params:dataObject});
		}
	}	
});