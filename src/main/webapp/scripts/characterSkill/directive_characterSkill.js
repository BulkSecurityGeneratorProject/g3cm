/**
 * 
 */

g3charactermanagerApp.directive('characterSkills', function() {
    return {
        restrict: 'E',
        templateUrl: 'views/tabbedCharacterSkills.html',
        controller: function($scope, Skill, CharacterSkill) {

            $scope.characterSkills = CharacterSkill.query();
//            $scope.characters = resolvedCharacter;
            $scope.skills = Skill.query();

            $scope.createSkills = function () {
            	$scope.characterSkill.character = $scope.selectedCharacter;
                CharacterSkill.save($scope.characterSkill,
                    function () {
                        $scope.characterSkills = CharacterSkill.query();
                        $('#saveCharacterSkillModal').modal('hide');
                        $scope.clear();
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
                    });
            };

            $scope.clearSkills = function () {
                $scope.characterSkill = {bonus: null, points: null, level: null, id: null};
            };
        }
    };
});