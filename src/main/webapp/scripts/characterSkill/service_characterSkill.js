'use strict';

g3charactermanagerApp.factory('CharacterSkill', function ($resource) {
        return $resource('app/rest/characterSkills/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
