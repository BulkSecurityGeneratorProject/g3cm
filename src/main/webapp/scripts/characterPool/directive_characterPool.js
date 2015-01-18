/**
 * 
 */

g3charactermanagerApp.directive('characterPools', function() {
    return {
        restrict: 'E',
        templateUrl: 'views/tabbedCharacterPools.html',
        controller: function($scope, Pool, CharacterPool,Restangular,$routeParams,$http){
        	$scope.characterPools = Restangular.one('characters', $routeParams.characterId).getList('characterPools').$object;
            $scope.pools = Pool.query();

            $scope.createPool = function () {
            	$scope.characterPool.character=$scope.selectedCharacter;
                CharacterPool.save($scope.characterPool,
                    function () {
                        $scope.characterPools = CharacterPool.query();
                        $('#saveCharacterPoolModal').modal('hide');
                        $scope.clear();
                    });
            };

            $scope.updatePool = function (id) {
                $scope.characterPool = CharacterPool.get({id: id});
                $('#saveCharacterPoolModal').modal('show');
            };
            
            $scope.modpool=function(id,value){
	        	 Restangular.one('characterPools', id).get().then(function(pool){
        		 console.log("entering then");
        		 console.log(pool);
        		 console.log(value);
        		 pool.current = pool.current+value;
        		 pool.save().then(function(data){
        			 $scope.characterPools = Restangular.one('characters', $routeParams.characterId).getList('characterPools').$object;
        		 });
	        	 });
            	 
            	
            };

            $scope.deletePool = function (id) {
                CharacterPool.delete({id: id},
                    function () {
                        $scope.characterPools = CharacterPool.query();
                    });
            };

            $scope.clearPool = function () {
                $scope.characterPool = {max: null, current: null, id: null};
            };
            
            $scope.set_meter_width = function (characterPool) {
            	var perc = (characterPool.current / characterPool.max)*100;
            	  perc = perc+"%";
            	  return { width: perc };
        	};
        }
    };
});