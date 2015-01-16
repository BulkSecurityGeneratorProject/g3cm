'use strict';

g3charactermanagerApp.controller('CharacterDetailController', function ($scope, $routeParams,$location, $log,RACharacter,resolvedCharacter, Character, resolvedCampaign, resolvedCharacterAdvantage, resolvedCharacterSkill, resolvedForm,CharacterAdvantage,resolvedAdvantage) {

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
        
//        $scope.characterAdvantages = resolvedCharacterAdvantage;
//        console.log(CharacterAdvantage);
////        $scope.characters = resolvedCharacter;
//
//        $scope.createAdvantage = function () {
//        	$scope.characterAdvantage.character=$scope.selectedCharacter;
//            CharacterAdvantage.save($scope.characterAdvantage,
//                function () {
//                    $scope.characterAdvantages = CharacterAdvantage.query();
//                    $('#saveCharacterAdvantageModal').modal('hide');
//                    $scope.clearAdvantage();
//                });
//        };
//
//        $scope.updateAdvantage = function (id) {
//            $scope.characterAdvantage = CharacterAdvantage.get({id: id});
//            $('#saveCharacterAdvantageModal').modal('show');
//        };
//
//        $scope.deleteAdvantage = function (id) {
//            CharacterAdvantage.delete({id: id},
//                function () {
//                    $scope.characterAdvantages = CharacterAdvantage.query();
//                });
//        };
//
//        $scope.clearAdvantage = function () {
//            $scope.characterAdvantage = {points: null, id: null};
//        };
//        
//        $scope.updateAdvPoints=function(){
//        	var advid=$scope.characterAdvantage.advantage.id;
//        	for (var i = 0; i < $scope.advantages.length; i++) {
//        		if($scope.advantages[i].id==advid){
//        			$scope.characterAdvantage.points=$scope.advantages[i].basePoints;
//        			break;
//        		}
//        	}
//        };
//        
//        $scope.showAdvDescription=function(description,title){
//        	$scope.currentDescription=description;
//        	$scope.currentTitle=title;
//        	$('#advantageDescription').modal('show');
//        };
        
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
