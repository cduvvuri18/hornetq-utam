app.controller('unitTestingController', function($scope, $http, RestService) {
	$scope.message_types = [ 'TextMessage', 'MapMessage', 'StreamMessage', 'ByteMessage', 'ObjectMessage' ];
	$scope.destinations = [];
	$scope.publish_form = {};
	$scope.publish_form.hqType = 'JMS';
	$scope.destination_meta = {};
	$scope.destination_messages = [];
	$scope.publish_form.destinationType = 'Q';
	
	//retrieve all queue destinations
	var responseGetDestinations = RestService.getDestinations($http, $scope);

	//on recieving response
	responseGetDestinations.success(function(data) {
		$scope.destinations = data;
		console.log("responseGetDestinations :: getDestinations() :: " + data);
	});

	$scope.uploadComplete = function (content) {
		alert(content);
	}
	
	$scope.onDestinationSelected = function(item) {
		$scope.publish_form.destinationJndiName = item.objects.JNDIBindings[0];
		$scope.publish_form.destinationName = item.name;
		$scope.destinations.forEach(function(index) {
			Object.keys(index).forEach(function(key) {
				if (key === "name") {
					$scope.destination_meta[key] = index[key];
				} else if (key === "objects") {
					var objects = index[key];
					Object.keys(objects).forEach(function(key) {
						$scope.destination_meta[key] = objects[key];
					});
				} 
			});
		});
	}

	$scope.onChooseTextMessageInputType = function() {
		if($scope.publish_form.messageInputType == 'enterMessage') {
			$scope.display_enterMessage = "true";
			$scope.display_uploadMessage = "false";
		} else if ($scope.publish_form.messageInputType == 'uploadMessage') {
			$scope.display_enterMessage = "false";
			$scope.display_uploadMessage = "true";
		}
	}
	
	$scope.onDestinationTypeSelected = function() {
		
	}
	
	//toggle
	$scope.hideAll = function() {
		$scope.display_messageInputType = "false";
		$scope.display_fullyQualClassName = "false";
		$scope.display_enterMessage = "false";
		$scope.display_uploadMessage = "false";
	}
	
	$scope.onMessageTypeSelected = function(item) {
		console.log("on message type selected :: value :: "+item);
		$scope.hideAll();
		if(item == 'TextMessage') {
			console.log("on message type selected :: display message input type ");
			$scope.display_messageInputType = "true";
		} else {
			console.log("on message type selected :: value :: "+item);
			$scope.display_uploadMessage = "true";
		}
		
		if(item == 'ObjectMessage') {
			$scope.display_fullyQualClassName = "true";
		}
	}
	
	$scope.hideAll();
	

	
	
});