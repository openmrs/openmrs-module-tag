
var app = angular.module('taggedObjects',['personService','tagService']);

app.config(['$locationProvider', function($locationProvider){
    $locationProvider.html5Mode({
        enabled: true,
        requireBase: false
    });
}]);

app.controller('taggedObjectsCtrl',['$scope','$location','Person','TagService', function ($scope,$location,Person,TagService) {

    var tags = [];
    $scope.init = function () {
        $scope.tag = $location.search().tag;
        loadTags();
    }

    var loadTags =function () {
        var payload = {
            objectType: 'org.openmrs.Person',
            tag: $scope.tag,
            v: 'full'
        };
        TagService.getTags(payload).then(function (res) {
            tags = res;
            loadPatients();
        });
    };

    $scope.patients = [];
    var loadPatients = function () {
        angular.forEach(tags, function (t) {
            var payload = {
                uuid: t.objectUuid,
            }
           Person.get(payload).$promise.then(function (res) {
               $scope.patients.push(res);
           });
        });
    };
}]);
