'use strict';

g3charactermanagerApp.controller('CharacterController', function ($scope, $location,resolvedCharacter, Character, resolvedCharacterAdvantage, resolvedCharacterSkill, resolvedCharacterAttribute, resolvedUser, resolvedForm, resolvedCampaign) {

        $scope.characters = resolvedCharacter;
        $scope.characterAdvantages = resolvedCharacterAdvantage;
        $scope.characterSkills = resolvedCharacterSkill;
        $scope.characterAttributes = resolvedCharacterAttribute;
        $scope.users = resolvedUser;
        $scope.forms = resolvedForm;
        $scope.campaigns = resolvedCampaign;

        $scope.create = function () {
            Character.save($scope.character,
                function () {
                    $scope.characters = Character.query();
                    $('#saveCharacterModal').modal('hide');
                    $scope.clear();
                });
        };
        
        $scope.showMore = function(id){

            $location.path("/character/"+id);
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
