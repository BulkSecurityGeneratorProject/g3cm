/**
 * 
 */

g3charactermanagerApp.directive('characterAttributes', function() {
    return {
        restrict: 'E',
        templateUrl: 'views/tabbedCharacterAttributes.html',
        controller: function($scope,  CharacterAttribute, AttributeType,Restangular,$routeParams){
        	console.log("att dir");
        	$scope.characterAttributes = Restangular.one('characters', $routeParams.characterId).getList('characterAttributes').$object;
        	console.log($scope.characterAttributes);
            $scope.attributeTypes = AttributeType.query();
            

            $scope.createAttribute = function () {
                $scope.characterAttribute.character = $scope.selectedCharacter;
                console.log($scope.selectedCharacter);
                CharacterAttribute.save($scope.characterAttribute,
                    function () {
                        $('#saveCharacterAttributeModal').modal('hide');
                        $scope.clear();
	                    $scope.refreshCharacter();
	                    $scope.characterAttributes = null;
                        $scope.characterAttributes = CharacterAttribute.query();
                    });
            };

            $scope.updateAttribute = function (id) {
                $scope.characterAttribute = CharacterAttribute.get({id: id});
                $('#saveCharacterAttributeModal').modal('show');
            };

            $scope.deleteAttribute = function (id) {
                CharacterAttribute.delete({id: id},
                    function () {
                        $scope.characterAttributes = CharacterAttribute.query();
	                    $scope.refreshCharacter();
                    });
            };

            $scope.clearAttribute = function () {
                $scope.characterAttribute = {rating: null, bonus: null, points: null, id: null};
            };

	        $scope.guessPoints=function(){
	        	console.log("Guessing points");
	        	var bonus=0;
	        	if($scope.characterAttribute.bonus){
	        		bonus=$scope.characterAttribute.bonus;
	        	}
	        	var rating=$scope.characterAttribute.rating - bonus;
	        	var points=0;
	        	console.log("rating:"+rating);
	        	switch(rating){
		        	case 1:
		        		points = -80;
		        		break;
		        	case 2:
		        		points = -70;
		        		break;
		        	case 3:
		        		points = -60;
		        		break;
		        	case 4:
		        		points = -50;
		        		break;
		        	case 5:
		        		points = -40;
		        		break;
	        		case 6:
		        		points = -30;
		        		break;
	        		case 7:
		        		points = -20;
		        		break;
	        		case 8:
		        		points = -15;
		        		break;
	        		case 9:
		        		points = -10;
		        		break;
	        		case 10:
		        		points = 0;
		        		break;
	        		case 11:
		        		points = 10;
		        		break;
	        		case 12:
		        		points = 20;
		        		break;
	        		case 13:
		        		points = 30;
		        		break;
	        		case 14:
		        		points = 45;
		        		break;
	        		case 15:
		        		points = 60;
		        		break;
	        		case 16:
		        		points = 80;
		        		break;
	        		case 17:
		        		points = 100;
		        		break;
	        		case 18:
		        		points = 125;
		        		break;
	        		case 19:
		        		points = 150;
		        		break;
	        		case 20:
		        		points = 175;
		        		break;
	        		default:
	        			if(rating>20){
	        				var extrapts =(rating-20)*25;
	        				points=175+extrapts;
	        			}
	        			break;	
	        	}
	        	
    			$scope.characterAttribute.points = points;
    			console.log($scope.characterAttribute.points);
	        }
        }
    };
});