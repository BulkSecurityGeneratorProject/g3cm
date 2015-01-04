'use strict';

g3charactermanagerApp.controller('CharacterDetailController', function ($scope, $routeParams,$location, $log,resolvedCharacter, Character, resolvedCampaign, resolvedCharacterAdvantage, resolvedCharacterSkill, resolvedForm) {

        $scope.characters = resolvedCharacter;
        $scope.campaigns = resolvedCampaign;
        $scope.characterAdvantages = resolvedCharacterAdvantage;
        $scope.characterSkills = resolvedCharacterSkill;
        $scope.forms = resolvedForm;
        
        $scope.selectedCharacter=Character.get({id:$routeParams.characterId});

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
        

        $scope.deleteCharacter = function (id) {
            Character.delete({id: id},
                function () {
                    $scope.characters = Character.query();
                });
        };

        $scope.clear = function () {
            $scope.character = {name: null, age: null, description: null, bio: null, id: null};
        };
    });

g3charactermanagerApp.controller("TabController", function() {
    this.tab = 1;

    this.isSet = function(checkTab) {
      return this.tab === checkTab;
    };

    this.setTab = function(setTab) {
      this.tab = setTab;
    };
  });
