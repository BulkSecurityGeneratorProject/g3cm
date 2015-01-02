'use strict';

g3charactermanagerApp.controller('CharacterController', function ($scope, resolvedCharacter, Character, resolvedCampaign, resolvedCharacterAdvantage, resolvedCharacterSkill, resolvedForm) {

        $scope.characters = resolvedCharacter;
        $scope.campaigns = resolvedCampaign;
        $scope.characterAdvantages = resolvedCharacterAdvantage;
        $scope.characterSkills = resolvedCharacterSkill;
        $scope.forms = resolvedForm;

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
