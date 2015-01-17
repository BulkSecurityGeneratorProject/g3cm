'use strict';

g3charactermanagerApp.controller('CharacterDetailController', function ($scope, $routeParams,$location, $log,$http,RACharacter,resolvedCharacter, Character, resolvedCampaign, resolvedCharacterAdvantage, resolvedCharacterSkill, resolvedForm,CharacterAdvantage,resolvedAdvantage) {

        $scope.characters = resolvedCharacter;
        $scope.campaigns = resolvedCampaign;
        $scope.characterAdvantages = resolvedCharacterAdvantage;//resolvedCharacterAdvantageByCharacter//resolvedCharacterAdvantage;
        $scope.advantages = resolvedAdvantage;
        $scope.characterSkills = resolvedCharacterSkill;
        $scope.forms = resolvedForm;
        $scope.currentDescription = '';
        $scope.currentTitle = '';
        
//        $scope.tester =Restangular.one('characters', 1).getList('characterSkills')
//        console.log("Tester");
//        console.log($scope.tester);
//        console.log("End Tester");
        $scope.selectedCharacter=Character.get({id:$routeParams.characterId});
        $scope.basechar=RACharacter.one($routeParams.characterId).get();
        
        $scope.refreshCharacter = function(){
        	$http.get('/app/rest/characterpoints/'+$routeParams.characterId).
        	  success(function(data, status, headers, config) {
                  $scope.selectedCharacter=Character.get({id:$routeParams.characterId});
        	  });
        }

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

g3charactermanagerApp.filter('forSelectedCharacter', function ($routeParams) {
	  return function (items) {
		  var filtered = [];
		    for (var i = 0; i < items.length; i++) {
		      var item = items[i];
			  console.log(item)
		      if (item.character && item.character.id==$routeParams.characterId) {
		        filtered.push(item);
		      }
		    }
		    return filtered;
		  };
		});
