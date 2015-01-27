app.controller('unitTestingController', function($scope, $http, RestService) {
	$scope.message_types = [ 'TextMessage', 'MapMessage', 'StreamMessage', 'BytesMessage', 'ObjectMessage' ];
	$scope.destinations = [];
	$scope.publish_form = {};
	$scope.publish_form.hqType = 'JMS';
	$scope.destination_meta = {};
	$scope.destination_messages = [];
	$scope.publish_form.destinationType = 'Q';
	$scope.publish_form.destinationName = '';
	$scope.message_type = '';

	// retrieve all queue destinations
	var responseGetDestinations = RestService.getDestinations($http, $scope);

	// on recieving response
	responseGetDestinations.success(function(data) {
		$scope.destinations = data;
		console.log("responseGetDestinations :: getDestinations() :: " + data);
	});

	$scope.uploadComplete = function(content) {
		if(content == 'success') {
			populateDestinationInfo(content);
			alert(content);
		} else {
			alert(content);
		}
	}

	$scope.onDestinationSelected = function(item) {
		$scope.publish_form.destinationName = item;
		populateDestinationInfo(item);
	}

	var populateDestinationInfo = function(item) {
		var responseGetDestinationInfo = RestService.getDestinationInfo($http, $scope);

		// on recieving response
		responseGetDestinationInfo.success(function(data) {
			$scope.publish_form.destinationJndiName = data.objects.JNDIBindings[0];
			$scope.publish_form.destinationName = data.name;
			console.log("destination jndi name :: " + $scope.publish_form.destinationJndiName);
			console.log("destination name :: " + $scope.publish_form.destinationName);
			var destinationInfoTemp = [ data ];
			destinationInfoTemp.forEach(function(index) {
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
			$scope.display_destination_info = "true";
			console.log("responseGetDestinations :: getDestinations() :: " + data);
		});
	}

	$scope.onChooseTextMessageInputType = function() {
		if ($scope.publish_form.messageInputType == 'TEXT') {
			$scope.display_enterMessage = "true";
			$scope.display_uploadMessage = "false";
		} else if ($scope.publish_form.messageInputType == 'FILE') {
			$scope.display_enterMessage = "false";
			$scope.display_uploadMessage = "true";
		}
	}

	// toggle
	$scope.hideAll = function() {
		$scope.display_messageInputType = "false";
		$scope.display_fullyQualClassName = "false";
		$scope.display_enterMessage = "false";
		$scope.display_uploadMessage = "false";
	}

	$scope.onMessageTypeSelected = function(item) {
		$scope.message_type = item;
		console.log("on message type selected :: value :: " + item);
		$scope.hideAll();
		if (item == 'TextMessage') {
			console.log("on message type selected :: display message input type ");
			$scope.display_messageInputType = "true";
		} else {
			console.log("on message type selected :: value :: " + item);
			$scope.display_uploadMessage = "true";
		}

		if (item == 'ObjectMessage') {
			$scope.display_fullyQualClassName = "true";
		}
	}

	$scope.hideAll();

});