'use strict';

g3charactermanagerApp.controller('CharacterAdvantageController', function ($scope, resolvedCharacterAdvantage, CharacterAdvantage, resolvedAdvantage, resolvedCharacter) {

        $scope.characterAdvantages = resolvedCharacterAdvantage;
        $scope.advantages = resolvedAdvantage;
        $scope.characters = resolvedCharacter;

        $scope.create = function () {
            CharacterAdvantage.save($scope.characterAdvantage,
                function () {
                    $scope.characterAdvantages = CharacterAdvantage.query();
                    $('#saveCharacterAdvantageModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.characterAdvantage = CharacterAdvantage.get({id: id});
            $('#saveCharacterAdvantageModal').modal('show');
        };

        $scope.delete = function (id) {
            CharacterAdvantage.delete({id: id},
                function () {
                    $scope.characterAdvantages = CharacterAdvantage.query();
                });
        };

        $scope.clear = function () {
            $scope.characterAdvantage = {points: null, id: null};
        };
    });
