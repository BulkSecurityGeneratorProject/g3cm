'use strict';

g3charactermanagerApp.controller('CharacterSkillController', function ($scope, resolvedCharacterSkill, CharacterSkill, resolvedCharacter, resolvedSkill) {

        $scope.characterSkills = resolvedCharacterSkill;
        $scope.characters = resolvedCharacter;
        $scope.skills = resolvedSkill;

        $scope.create = function () {
            CharacterSkill.save($scope.characterSkill,
                function () {
                    $scope.characterSkills = CharacterSkill.query();
                    $('#saveCharacterSkillModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.characterSkill = CharacterSkill.get({id: id});
            $('#saveCharacterSkillModal').modal('show');
        };

        $scope.delete = function (id) {
            CharacterSkill.delete({id: id},
                function () {
                    $scope.characterSkills = CharacterSkill.query();
                });
        };

        $scope.clear = function () {
            $scope.characterSkill = {bonus: null, points: null, level: null, id: null};
        };
    });
