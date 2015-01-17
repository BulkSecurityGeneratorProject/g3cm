/**
 * 
 */

g3charactermanagerApp.directive('characterSkills', function() {
    return {
        restrict: 'E',
        templateUrl: 'views/tabbedCharacterSkills.html',
        controller: function($scope, Skill, CharacterSkill,Restangular,$routeParams) {
            $scope.characterSkills = Restangular.one('characters', $routeParams.characterId).getList('characterSkills').$object;
            console.log("char skills");
            console.log($scope.characteSkills);
//            $scope.characters = resolvedCharacter;
            $scope.skills = Skill.query();

            $scope.createSkills = function () {
            	$scope.characterSkill.character = $scope.selectedCharacter;
                CharacterSkill.save($scope.characterSkill,
                    function () {
                        $scope.characterSkills = Restangular.one('characters', $routeParams.characterId).getList('characterSkills').$object;
                        $('#saveCharacterSkillModal').modal('hide');
                        $scope.clear();
	                    $scope.refreshCharacter();
                    });
            };

            $scope.updateSkills = function (id) {
                $scope.characterSkill = CharacterSkill.get({id: id});
                $('#saveCharacterSkillModal').modal('show');
            };

            $scope.deleteSkills = function (id) {
                CharacterSkill.delete({id: id},
                    function () {
                        $scope.characterSkills = CharacterSkill.query();
	                    $scope.refreshCharacter();
                    });
            };

            $scope.clearSkills = function () {
                $scope.characterSkill = {bonus: null, points: null, level: null, id: null};
            };
        }
    };
});