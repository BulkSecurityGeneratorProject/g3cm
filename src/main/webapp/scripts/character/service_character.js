'use strict';

g3charactermanagerApp.factory('Character', function ($resource) {
        return $resource('app/rest/characters/:id', {}, {
            'query': { method: 'GET', isArray: true},
            'get': { method: 'GET'}
        });
    });

g3charactermanagerApp.factory('RACharacter', function (Restangular) {
	console.log("getting RACharacter");
	 return Restangular.service('characters');
});

