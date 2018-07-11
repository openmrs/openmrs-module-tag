
var app = angular.module('tag.taggedObjects',['personService','tagService']);

app.controller('taggedObjectsCtrl',['$scope','Person','TagService',
    function ($scope,Person,TagService) {

    $scope.init = function (tag,returnUrl) {
        $scope.tag = tag;
        $scope.returnUrl = returnUrl;
        loadTags();
    }

    var tags = [];
    $scope.dataLoaded = false;

    var loadTags = function () {
        var payload = {
            objectType: 'org.openmrs.Patient',
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
        var count = 0;
        angular.forEach(tags, function (t) {
            var payload = {
                uuid: t.objectUuid,
            }
           Person.get(payload).$promise.then(function (res) {
               $scope.patients.push(res);
               count=count+1;
               if (count == tags.length){
                   $scope.dataLoaded = true;
               }
           });
        });
    };
    $scope.navigate = function () {
        emr.navigateTo({ url: $scope.returnUrl });
    };
    }]);
