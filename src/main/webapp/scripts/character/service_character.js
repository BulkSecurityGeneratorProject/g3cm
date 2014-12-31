'use strict';

g3charactermanagerApp.factory('Character', function ($resource) {
        return $resource('app/rest/characters/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });
