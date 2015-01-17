/**
 * 
 */
g3charactermanagerApp.directive('characterAdvantages',function(){
	return {
		restrict:'E',
		templateUrl:'views/tabbedCharacterAdvantages.html',
		controller: function($scope, Advantage, CharacterAdvantage,Restangular,$routeParams){
			$scope.characterAdvantages = Restangular.one('characters', $routeParams.characterId).getList('characterAdvantages').$object;
	        $scope.advantages = Advantage.query();
//	        $scope.characters = resolvedCharacter;

//	        $scope.characterAdvantages = resolvedCharacterAdvantage;
	        console.log("in CharacterAdvantage");
//	        $scope.characters = resolvedCharacter;

	        $scope.createAdvantage = function () {
	        	$scope.characterAdvantage.character=$scope.selectedCharacter;
	            CharacterAdvantage.save($scope.characterAdvantage,
	                function () {
	                    $scope.characterAdvantages = Restangular.one('characters', $routeParams.characterId).getList('characterAdvantages').$object;
	                    $('#saveCharacterAdvantageModal').modal('hide');
	                    $scope.clearAdvantage();
	                    $scope.refreshCharacter();
	                });
	        };

	        $scope.updateAdvantage = function (id) {
	            $scope.characterAdvantage = CharacterAdvantage.get({id: id});
	            $('#saveCharacterAdvantageModal').modal('show');
	        };

	        $scope.deleteAdvantage = function (id) {
	            CharacterAdvantage.delete({id: id},
	                function () {
	                    $scope.characterAdvantages = CharacterAdvantage.query();
	                    $scope.refreshCharacter();
	                });
	        };

	        $scope.clearAdvantage = function () {
	            $scope.characterAdvantage = {points: null, id: null};
	        };
	        
	        $scope.updateAdvPoints=function(){
	        	var advid=$scope.characterAdvantage.advantage.id;
	        	for (var i = 0; i < $scope.advantages.length; i++) {
	        		if($scope.advantages[i].id==advid){
	        			$scope.characterAdvantage.points=$scope.advantages[i].basePoints;
	        			break;
	        		}
	        	}
	        };
	        
	        $scope.showAdvDescription=function(description,title){
	        	$scope.currentDescription=description;
	        	$scope.currentTitle=title;
	        	$('#advantageDescription').modal('show');
	        };
		}
	};
});