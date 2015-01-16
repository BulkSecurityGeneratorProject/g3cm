/**
 * 
 */

g3charactermanagerApp.directive('characterAttributes', function() {
    return {
        restrict: 'E',
        templateUrl: 'views/tabbedCharacterAttributes.html',
        controller: function($scope,  CharacterAttribute, AttributeType,Restangular,$routeParams){
        	$scope.characterAttributes = Restangular.one('characters', $routeParams.characterId).getList('characterAttributes').$object;
            $scope.attributeTypes = AttributeType.query();
            

            $scope.createAttribute = function () {
                $scope.characterAttribute.character = $scope.selectedCharacter;
                console.log($scope.selectedCharacter);
                CharacterAttribute.save($scope.characterAttribute,
                    function () {
                        $scope.characterAttributes = CharacterAttribute.query();
                        $('#saveCharacterAttributeModal').modal('hide');
                        $scope.clear();
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
                    });
            };

            $scope.clearAttribute = function () {
                $scope.characterAttribute = {rating: null, bonus: null, points: null, id: null};
            };
        }
    };
});