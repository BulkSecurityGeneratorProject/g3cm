'use strict';

g3charactermanagerApp.controller('CharacterPoolController', function ($scope, resolvedCharacterPool, CharacterPool, resolvedCharacter, resolvedPool) {

        $scope.characterPools = resolvedCharacterPool;
        $scope.characters = resolvedCharacter;
        $scope.pools = resolvedPool;

        $scope.create = function () {
            CharacterPool.save($scope.characterPool,
                function () {
                    $scope.characterPools = CharacterPool.query();
                    $('#saveCharacterPoolModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.characterPool = CharacterPool.get({id: id});
            $('#saveCharacterPoolModal').modal('show');
        };

        $scope.delete = function (id) {
            CharacterPool.delete({id: id},
                function () {
                    $scope.characterPools = CharacterPool.query();
                });
        };

        $scope.clear = function () {
            $scope.characterPool = {max: null, current: null, id: null};
        };
    });
