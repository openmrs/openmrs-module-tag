
angular.module('Tag',['tagService', 'ui.bootstrap'])
    .controller('tagCtrl',['$scope','tagService', function ($scope, TagService) {

       /* $scope.init = function (patientUuid) {
            $scope.patientUuid = patientUuid;
        }
        $scope.params = {'objectType':'org.openmrs.Patient', 'objectUuid':patientUuid};
        */
       $scope.param = {'uuid' : 'ddb97bda-09bb-4225-a9d4-09ba68d82755'};
            TagService.getTag(param).then(function (response) {
            $scope.tags=(response.data);
        }),
            function () {
            $scope.tags="";
            };
    }]);

