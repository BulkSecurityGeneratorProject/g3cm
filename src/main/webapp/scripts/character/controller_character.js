'use strict';

g3charactermanagerApp.controller('CharacterController', function ($scope, resolvedCharacter, Character, resolvedCampaign, resolvedForm, resolvedUser, resolvedCharacterSkill, resolvedCharacterAttribute, resolvedCharacterAdvantages) {

        $scope.characters = resolvedCharacter;
        $scope.campaigns = resolvedCampaign;
        $scope.forms = resolvedForm;
        $scope.users = resolvedUser;
        $scope.characterSkills = resolvedCharacterSkill;
        $scope.characterAttributes = resolvedCharacterAttribute;
        $scope.characterAdvantagess = resolvedCharacterAdvantages;

        $scope.create = function () {
            Character.save($scope.character,
                function () {
                    $scope.characters = Character.query();
                    $('#saveCharacterModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.character = Character.get({id: id});
            $('#saveCharacterModal').modal('show');
        };

        $scope.delete = function (id) {
            Character.delete({id: id},
                function () {
                    $scope.characters = Character.query();
                });
        };

        $scope.clear = function () {
            $scope.character = {name: null, age: null, description: null, bio: null, id: null};
        };
    });
