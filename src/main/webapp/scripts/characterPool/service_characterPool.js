'use strict';

g3charactermanagerApp.factory('CharacterPool', function ($resource) {
        return $resource('app/rest/characterPools/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
