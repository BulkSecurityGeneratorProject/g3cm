'use strict';

g3charactermanagerApp.controller('CharacterAttributeController', function ($scope, resolvedCharacterAttribute, CharacterAttribute, resolvedAttributeType, resolvedCharacter) {

        $scope.characterAttributes = resolvedCharacterAttribute;
        $scope.attributeTypes = resolvedAttributeType;
        $scope.characters = resolvedCharacter;

        $scope.create = function () {
            CharacterAttribute.save($scope.characterAttribute,
                function () {
                    $scope.characterAttributes = CharacterAttribute.query();
                    $('#saveCharacterAttributeModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.characterAttribute = CharacterAttribute.get({id: id});
            $('#saveCharacterAttributeModal').modal('show');
        };

        $scope.delete = function (id) {
            CharacterAttribute.delete({id: id},
                function () {
                    $scope.characterAttributes = CharacterAttribute.query();
                });
        };

        $scope.clear = function () {
            $scope.characterAttribute = {rating: null, bonus: null, points: null, id: null};
        };
    });
