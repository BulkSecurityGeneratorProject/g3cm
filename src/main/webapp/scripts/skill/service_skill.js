'use strict';

g3charactermanagerApp.factory('Skill', function ($resource) {
        return $resource('app/rest/skills/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
