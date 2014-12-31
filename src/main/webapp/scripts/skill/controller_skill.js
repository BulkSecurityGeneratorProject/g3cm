'use strict';

g3charactermanagerApp.controller('SkillController', function ($scope, resolvedSkill, Skill, resolvedAttributeType) {

        $scope.skills = resolvedSkill;
        $scope.attributeTypes = resolvedAttributeType;

        $scope.create = function () {
            Skill.save($scope.skill,
                function () {
                    $scope.skills = Skill.query();
                    $('#saveSkillModal').modal('hide');
                    $scope.clear();
                });
        };

        $scope.update = function (id) {
            $scope.skill = Skill.get({id: id});
            $('#saveSkillModal').modal('show');
        };

        $scope.delete = function (id) {
            Skill.delete({id: id},
                function () {
                    $scope.skills = Skill.query();
                });
        };

        $scope.clear = function () {
            $scope.skill = {name: null, description: null, difficulty: null, id: null};
        };
    });
